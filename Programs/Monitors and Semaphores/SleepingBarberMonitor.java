public class SleepingBarberMonitor {
 
    public static void main(String a[]) {
        Bshop shop = new Bshop();
        Barber barber = new Barber(shop);
        CustomerGenerator cg = new CustomerGenerator(shop);
 
        Thread barberThread = new Thread(barber);
        Thread customerSpawner = new Thread(cg);
        
        customerSpawner.start();
        barberThread.start();
    }
}
 
class Barber implements Runnable {
    Bshop shop;
 
    public Barber(Bshop shop) {
        this.shop = shop;
    }

    public void run() {
        System.out.println("Barberbot initialized");
        
        while(true) {
            shop.cutHair();
        }
    }
}

class Customer implements Runnable {
    Bshop shop;
     
    public Customer(Bshop shop) {
        this.shop = shop;
    }

    public void run() {
        goForHairCut();
    }

    private synchronized void goForHairCut() {
        shop.add(this);
    }
}
 
class CustomerGenerator implements Runnable {
    Bshop shop;
 
    public CustomerGenerator(Bshop shop) {
        this.shop = shop;
    }
 
    public void run() {
        while(true) {
            Customer customer = new Customer(shop);
            Thread thcustomer = new Thread(customer);
            thcustomer.start();
 
            try {
                TimeUnit.SECONDS.sleep((long)(Math.random()*10));
            } catch(InterruptedException iex) {
                iex.printStackTrace();
            }
        }
    }
}
 
class Bshop {
    int nchair;
    List<Customer> listCustomer;
 
    public Bshop() {
        nchair = 3;
        listCustomer = new LinkedList<Customer>();
    }
 
    public void cutHair() {
        Customer customer;

        synchronized (listCustomer) {
            while(listCustomer.size()==0) {
                System.out.println("Barber fell asleep");
                
                try {
                    listCustomer.wait();
                } catch(InterruptedException iex) {
                    iex.printStackTrace();
                }
            }

            System.out.println("Barber found a customer in the queue.");
            customer = (Customer)((LinkedList<?>)listCustomer).poll();
        }
  
        System.out.println("Yay a haircut has been completed");
    }
 
    public void add(Customer customer) {
        synchronized (listCustomer) {
            if(listCustomer.size() == nchair) {
                System.out.println("No chair, customer left to get a taco");
                return ;
            }
 
            ((LinkedList<Customer>)listCustomer).offer(customer);
            System.out.println("Customer entered the waiting room"
                    + "");
             
            if(listCustomer.size()==1)
                listCustomer.notify();
        }
    }
}