package myanv.cellularautomata;

// * This enum stores the two possible cell states - alive and dead
public enum State { ALIVE(1), DEAD(0);
    private final int value;

    private State(int value) {
        if (!(value == 0 || value == 1)) {
            throw new IllegalStateException("Illegal state value!");
        }
        this.value = value;
    }

    public int getState() {
        return value;
    }

}