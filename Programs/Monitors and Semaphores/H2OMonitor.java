Condition hydrogen, oxygen, lock;
int waitingHydro = 0;
int waitingOxy = 0;
int acceptedHydro = 0;
int acceptedOxy = 0;

public synchronized void Hydrogen() {
  lock.wait();
  waitingHydro++;

  while (acceptedHydro == 0) {
    if (waitingHydro >= 2 && waitingOxy >= 1) {
      bond();
      waitingHydro-=2;
      acceptedHydro+=2;
      waitingOxy--;
      acceptedOxy++;
      hydrogen.signal();
      oxygen.signal();
    } else {
      hydrogen.wait();
    }
  }

  acceptedHydro--;
  lock.notify();
}

public synchronized void Oxygen() {
  lock.wait();
  waitingOxy++;

  while (acceptedOxy == 0) {
    if (waitingHydro >= 2 && waitingOxy >= 1) {
      bond();
      waitingHydro-=2;
      acceptedHydro+=2;
      waitingOxy--;
      acceptedOxy++;
      hydrogen.signal();
      hydrogen.signal();
    } else {
      oxygen.wait();
    }
  }

  acceptedOxy--;
  lock.notify();
}
