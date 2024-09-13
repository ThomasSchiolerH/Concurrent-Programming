public class SafeAlley extends Alley {

    int up, down;
    Semaphore upSem, downSem, controlSem;

    protected SafeAlley() {
        up = 0;
        down = 0;
        upSem = new Semaphore(1);
        downSem = new Semaphore(1);
        controlSem = new Semaphore(1);
    }

    public void enter(int no) throws InterruptedException {
        if (no < 5) {
            downSem.P();
            if (down == 0) controlSem.P();
            down++;
            downSem.V();
        } else {
            upSem.P();
            if (up == 0) controlSem.P();
            up++;
            upSem.V();
        }
    }

    public void leave(int no) {
        try {
            if (no < 5) {
                downSem.P();
                down--;
                if (down == 0) controlSem.V();
                downSem.V();
            } else {
                upSem.P();
                up--;
                if (up == 0) controlSem.V();
                upSem.V();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}