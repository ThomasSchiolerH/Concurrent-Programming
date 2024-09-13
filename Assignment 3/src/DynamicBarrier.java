//Implementation of dynamic Barrier class (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2023

//Hans Henrik Lovengreen     Oct 26, 2023

class DynamicBarrier extends Barrier {

    int arrived = 0;
    boolean active = false;
    int threshold;
    boolean thresholdChangePending = false;
    int newThreshold = 0;
    boolean barrierCheck = false;
    boolean firstBarrierCheck = true;


    public DynamicBarrier(CarDisplayI cd) {
        super(cd);
        this.threshold = 9;
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {
        while (!firstBarrierCheck) {
            wait();
        }

        if (!active) return;

        arrived++;

        if (arrived < threshold) {
            while (!barrierCheck) {
                if (!active) {
                    break;
                }
                wait();
                if (thresholdChangePending) {
                    threshold = newThreshold;
                    thresholdChangePending = false;
                }
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
        barrierCheck = false;
        firstBarrierCheck = true;
        notifyAll();
    }

    @Override
    /* Set barrier threshold */
    public synchronized void set(int k) {
        try {
            if (active && arrived > 0) {
                if (k > threshold) {
                    thresholdChangePending = true;
                    newThreshold = k;
                    while (thresholdChangePending) {
                        wait();
                    }
                } else {
                    threshold = k;
                    if (arrived >= threshold) {
                        barrierCheck = true;
                        notifyAll();
                    }
                }
            } else {
                threshold = k;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

