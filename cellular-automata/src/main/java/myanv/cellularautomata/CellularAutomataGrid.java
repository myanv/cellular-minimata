package myanv.cellularautomata;

import java.util.stream.*;

import org.springframework.stereotype.Component;

// * This is the main class for handling the cellular automata logic.
// * It creates a 2D array filled with random states.    
// * Then converts to an integer array to pass to the controller.

@Component
public class CellularAutomataGrid {

    // Initialises the number of rows and columns, as well as the state grid
    protected int rows = 10;
    protected int columns = 10;
    protected State[][] stateGrid = new State[rows][columns];

    public CellularAutomataGrid() {}

    public CellularAutomataGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public State[][] getStateGrid() {
        return stateGrid;
    }

    public void initialiseStateGrid() {
        stateGrid = IntStream.range(0, rows)
            .mapToObj(i -> IntStream.range(0, columns)
                        .mapToObj(j -> Math.random() > 0.5 ? State.ALIVE : State.DEAD)
                        .toArray(State[]::new))
            .toArray(State[][]::new);
    }
    
    public int[][] getStateGridAsInt() {
        int[][] intGrid = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                intGrid[i][j] = stateGrid[i][j].getState();
            }
        }
        return intGrid;
    }
    public State getState(int i, int j) {
        return stateGrid[i][j];
    }
    public void setState(int i, int j, State state) {
        stateGrid[i][j] = state;
    }
}
