public class SleepingBarberSemaphore extends Thread {

    public static Semaphore customers = new Semaphore(0);
    public static Semaphore barber = new Semaphore(0);
    public static Semaphore accessSeats = new Semaphore(1);

    public static final int CHAIRS = 3;
    public static int numberOfFreeSeats = CHAIRS;

    class Customer extends Thread  {
        public void run() {
            try {
                accessSeats.acquire();
                if (numberOfFreeSeats > 0) {
                    System.out.println("Customer just sat down.");
                    numberOfFreeSeats--;
                    customers.release();
                    //accessSeats.release();
                    barber.acquire();
                    this.getHaircut();
                } else {
                    System.out.println("No waiting room space, the customer left");
                    accessSeats.release();
                }
            } catch (InterruptedException ex)        
        }

        public void getHaircut(){}
    }

    class Barber extends Thread {
        public void run() {
            while (true) {
                try {
                    customers.acquire();
                    accessSeats.release();
                    numberOfFreeSeats++;
                    barber.accessSeats();
                    release.release();
                    cutHair();
                } catch (InterruptedException ex)          
            }
        }

        public void cutHair(){}
    }
}


