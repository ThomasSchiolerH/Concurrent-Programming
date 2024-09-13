//Implementation of a basic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2023

//Hans Henrik Lovengreen     Oct 26, 2023

class SafeBarrier extends Barrier {

    int arrived = 0;
    boolean barrierCheck = false;
    boolean firstBarrierCheck = true;
    boolean active = false;

    public SafeBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {
        while (!firstBarrierCheck) {
            wait();
        }
        if (!active) return;

        arrived++;

        if (arrived < 9) {
            while (!barrierCheck) {
                if (!active) {
                    break;
                }
                wait();
            }
        } else {
            firstBarrierCheck = false;
            barrierCheck = true;
            notifyAll();
        }

        arrived--;

        if (arrived == 0) {
            barrierCheck = false;
            firstBarrierCheck = true;
            notifyAll();
        }

    }

    @Override
    public synchronized void on() {
        active = true;
    }

    @Override
    public synchronized void off() {
        active = false;
        // Reset the barrier regardless of the current state
        barrierCheck = false;
        firstBarrierCheck = true;
        notifyAll();
    }

    @Override
    public synchronized void set(int k) {
        for (int i = 0; i < k; i++) {
            notify();
        }
    }
}

