public synchronized void criticalSection() {
    doProcess();
}

public synchronized void BarrierGate(){
    waitingCount=0;
    
    doPreProcess();

    if(waitingCount < 2) {
        waitingCount++;
        wait();
    } else {
        notifyAll();
        waitingCount = 0;
    }

    doPostProcess();
}

public synchronized void ReaderWriter() {
    int readers = 0;
    boolean writing;

    public void tryRead() {
        while(writing) {
            wait();
        }
        
        readers++;
    }

    public void endRead() {
        readers--;

        if(readers == 0)
            notifyAll();
    }

    public void tryWrite() {
        while(readers > 0 || writing){
            wait();
        }

        writer = true;
    }

    public void endWrite(){
        writer = false;
        notifyAll()
    }
}