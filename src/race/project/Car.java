package race.project;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            Main.START.countDown();    // we expect the readiness
            Main.START.await();        // of all racers
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
            if (Main.FINISH.getCount() ==  // determine the winner
                    Main.CARS_COUNT)       // of the race
                System.out.println(name + " - WIN");

            try {
                Main.FINISH.countDown();   // we expect the finish
                Main.FINISH.await();       // of all racers
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
