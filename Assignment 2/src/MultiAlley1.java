//Attempted implementation of Alley class with multiple cars
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU,  Fall 2023

//Hans Henrik Lovengreen     Sep 26, 2023

public class MultiAlley extends Alley {

    int up, down;
    Semaphore upSem, downSem;
    
    protected MultiAlley() {
        up = 0;   down = 0;
        upSem   = new Semaphore(1);
        downSem = new Semaphore(1);
    }

    /* Block until car no. may enter alley */
    public void enter(int no) throws InterruptedException {
        if (no < 5) {
            downSem.P();
            Thread.sleep(5000); 
            if (down == 0) upSem.P();    // block for up-going cars
            down++;
            downSem.V();
        } else {
            upSem.P();
            Thread.sleep(5000); 
            if (up == 0) downSem.P();    // block for down-going cars
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
