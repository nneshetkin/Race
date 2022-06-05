/*Организуем гонки:
        Все участники должны стартовать одновременно, несмотря на то, что на подготовку у каждого из них уходит разное время.
        В туннель не может заехать одновременно больше половины участников (условность).
        Попробуйте всё это синхронизировать.
        Только после того как все завершат гонку, нужно выдать объявление об окончании.
        Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.*/

package race.project;

import java.util.concurrent.CountDownLatch; // for start/finish
import java.util.concurrent.Semaphore; // for tunnel

public class Main {

    public static final int CARS_COUNT = 4;
    public static final CountDownLatch START = new CountDownLatch(CARS_COUNT + 1);
    public static final Semaphore TUNNEL = new Semaphore(CARS_COUNT / 2, false);
    public static final CountDownLatch FINISH = new CountDownLatch(CARS_COUNT);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        while (START.getCount() > 1)   // are the racers ready for the start?
            Thread.sleep(100);         // wait 100 ms

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        START.countDown();             // start all the racers

        while (FINISH.getCount() > 0) ; // this race is over?
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");


    }
}

