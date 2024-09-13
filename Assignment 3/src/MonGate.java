//Monitor implementation of Gate (skeleton)
//Mandatory assignment 3
//Course 02158 Concurrent Programming, DTU, Fall 2023

//Hans Henrik Lovengreen     Oct 26, 2023


public class MonGate extends Gate {

    private boolean isOpen = false;

    public synchronized void pass() throws InterruptedException {
        while (!isOpen) {
            wait();
        }
    }

    public synchronized void open() {
        isOpen = true;
        notifyAll();
    }

    public synchronized void close() {
        isOpen = false;
    }
}
