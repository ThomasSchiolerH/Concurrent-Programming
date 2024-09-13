//Naive implementation of Barrier class
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2023

//Hans Henrik Lovengreen     Oct 26, 2023

class NaiveBarrier extends Barrier {

    int arrived = 0;
    boolean active = false;

    public NaiveBarrier(CarDisplayI cd) {
        super(cd);
    }

    @Override
    public synchronized void sync(int no) throws InterruptedException {
        if (!active) return;
        Thread.sleep(2000);
        arrived++;

        if (arrived < 9) {
            wait();
        } else {
            arrived = 0;
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
        arrived = 0;
        notifyAll();
    }

    @Override
    public synchronized void set(int k) {
        for (int i = 0; i < k; i++) {
            notify();
        }
    }
}
