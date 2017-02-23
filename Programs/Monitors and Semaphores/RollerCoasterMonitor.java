public class RollerCoasterMonitor {

    int boarding = 0, riders = 0, unboarding = 0;
    final int CAPACITY =5;
    boolean loading = false, unloading = false;
   

    public synchronized void Passenger(RollerCoasterMonitor coaster) {
        while (true) {
            coaster.passengerBoarding();
            coaster.passengerUnboarding();
        }
    }

    public synchronized void Car(RollerCoasterMonitor coaster) {
        while (true) {
            coaster.carBoarding();
            //runCoaster(); // whee
            coaster.carUnboarding();
        }
    }

    public synchronized void passengerBoarding() {
        while (boarding + riders+ unboarding == CAPACITY) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RollerCoasterMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        ++boarding;
        
        if (boarding == CAPACITY) {
            notify();
        }

        while (!loading) {
            try {
                wait();
             } catch (InterruptedException ex) {
                Logger.getLogger(RollerCoasterMonitor.class.getName()).log(Level.SEVERE, null, ex);
             }
        }

        --boarding;
        ++riders;

        if (riders == CAPACITY) {
           notify();
        }
    }

    public synchronized void carBoarding() {
        while (boarding < CAPACITY) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RollerCoasterMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        loading = true;
        notifyAll();

        while (riders < CAPACITY) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RollerCoasterMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        loading = false;
    }

    public synchronized void passengerUnboarding() {
        while (!unloading) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RollerCoasterMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        --riders;
        ++unboarding;
        
        if (unboarding == CAPACITY) {
            notify();
        }
    }

    public synchronized void carUnboarding() {
        unloading = true;
        notifyAll();
        
        while (unboarding < CAPACITY) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(RollerCoasterMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        unloading = false;
        unboarding = 0;
        notifyAll();
    }
}
