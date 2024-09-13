public class MultiAlley2 extends Alley {

    int up, down;
    Semaphore upSem, downSem;

    protected MultiAlley2() {
        up = 0; down = 0;
        upSem = new Semaphore(1);
        downSem = new Semaphore(1);
    }

    /* Block until car no. may enter alley */
    public void enter(int no) throws InterruptedException {
        if (no < 5) {
            downSem.P();
            if (down == 0) upSem.P();
            Thread.sleep(3000);
            down++;
            downSem.V();
        } else {
            upSem.P();
            if (up == 0) downSem.P();
            up++;
            upSem.V();
        }
    }

    /* Register that car no. has left the alley */
    public void leave(int no) {
        if (no < 5) {
            down--;
            if (down == 0) upSem.V();    // enable up-going cars again
        } else {
            up--;
            if (up == 0) downSem.V();    // enable down-going cars again
        }
    }
}
