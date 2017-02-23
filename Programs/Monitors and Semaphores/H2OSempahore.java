Semaphore oxygen = 0;
Semaphore hydrogen = 0;
Semaphore lock = 1;
int waitingOxy = 0;
int waitingHydro = 0;

Hydrogen() {
	lock.wait();
	waitingHydro++;

	if (waitingHydro >= 2 and waitingOxy >= 1) {
		hydrogen.signal();
		hydrogen.signal();
		
		waitingHydro -= 2;
		oxygen.signal();
		waitingOxy--;
	} else {
		lock.signal();
	}

	hydrogen.wait();
	bond();
}

Oxygen() {
	lock.wait();
	waitingOxy++;
	
	if (waitingHydro >= 2) {
		hydrogen.signal();
		hydrogen.signal();
		
		waitingHydro -= 2;
		oxygen.signal();
		waitingOxy--;
	} else {
		lock.signal();
	}

	oxygen.wait();
	bond();
	lock.signal();
}