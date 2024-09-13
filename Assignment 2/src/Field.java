//Prototype implementation of Field class
//Mandatory assignment 2
//Course 02158 Concurrent Programming, DTU,  Fall 2023

//Hans Henrik Lovengreen     Sep 26, 2023SafeAlley

public class Field {

    private Semaphore[][] semMatrix;

    public Field() {
        semMatrix = new Semaphore[11][12];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 12; j++) {
                semMatrix[i][j] = new Semaphore(1);
            }
        }
    }

    /* Block until car no. may safely enter tile at pos */
    public void enter(int no, Pos pos) throws InterruptedException {
        semMatrix[pos.row][pos.col].P();
    }

    /* Release tile at position pos */
    public void leave(Pos pos) {
        semMatrix[pos.row][pos.col].V();
    }

}
