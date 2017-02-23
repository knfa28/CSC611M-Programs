public class RollerCoasterSemaphore {
    private final Semaphore permissionToBoard = new Semaphore(0);
    private final Semaphore allAboard = new Semaphore(0);
    private final Semaphore permissionToUnload = new Semaphore(0);
    private final Semaphore allDeparted = new Semaphore(0);

    public synchronized void rollerCoaster() {
      while(true) {
        try {
            board();
            permissionToBoard.release();
            allAboard.acquire();
            runRollerCoaster();
            unload();
            permissionToUnload.release();
            allDeparted.acquire();
        } catch (InterruptedException ex) {}
      }
    }
        
    public synchronized void passenger() {
      while (true) {
        try {
          permissionToBoard.acquire();
          board();
          allAboard.release();
          permissionToUnload.acquire();
          unboard();
          allDeparted.release();
        } catch (InterruptedException ex) {}
      }
    }
       
    public void unload(){}
       
    public void board(){}
    
    public void unboard(){}
        
    public void runRollerCoaster(){}
}
