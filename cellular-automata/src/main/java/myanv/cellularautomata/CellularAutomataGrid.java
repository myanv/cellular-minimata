package myanv.cellularautomata;

import java.util.stream.*;

import org.springframework.stereotype.Component;

// * This is the main class for handling the cellular automata logic.
// * It creates a 2D array filled with random states.    
// * Then converts to an integer array to pass to the controller.

@Component
public class CellularAutomataGrid {

    // * Initialises the number of rows and columns, as well as the current/previous state grids.
    
    private int rows = 10;
    private int columns = 10;
    private State[][] stateGrid;
    private State[][] previousStateGrid;

    public CellularAutomataGrid() {}

    public CellularAutomataGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        initialiseStateGrids();
    }

    public State[][] getStateGrid() {
        return stateGrid;
    }

    // * Creates a state grid/previous state grid with the desired dimensions
    // * Then fill them with random 1s and 0s, representing the ALIVE or DEAD states of the cells. 
    
    public void initialiseStateGrids() {
        stateGrid = new State[rows][columns];
        previousStateGrid = new State[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                stateGrid[i][j] = Math.random() > 0.5 ? State.ALIVE : State.DEAD;
                previousStateGrid[i][j] = stateGrid[i][j];
            }
        }
    }

    // * This method is to convert the State[][] grid to a int[][] grid 
    // * for easier passing to the front-end JavaScript.

    public int[][] getStateGridAsInt() {
        int[][] intGrid = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                intGrid[i][j] = stateGrid[i][j].getState();
            }
        }
        return intGrid;
    }

    // * This method updates the previous state grid so that each mutation take into account
    // * only the current and previous state grids. After each mutations, sets the previousStateGrid
    // * to be the current stateGrid.
    
    public void updatePreviousStateGrid() {
        for (int i = 0; i < rows; i++) {
            if (columns >= 0) {
                System.arraycopy(stateGrid[i], 0, previousStateGrid[i], 0, columns); 
            }
        }
    }

    // * This method handles the mutation logic of the cellular automata
    // * Let A be the 2D array. First, it creates a neighbourhood of A[i][j] and then counts
    // * the number of live cells, afterwards applies to rules to determine if a[i][j] is alive or dead.
    
    public void mutate() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Neighbourhood neighbourhood = new Neighbourhood(rows, columns, i, j, previousStateGrid);
                int numberOfNeighbours = neighbourhood.countLiveNeighbours();

                switch (previousStateGrid[i][j]) {
                    case ALIVE:
                        if (numberOfNeighbours < 2 || numberOfNeighbours > 3) {
                            stateGrid[i][j] = State.DEAD;
                        } else {
                            stateGrid[i][j] = State.ALIVE;
                        }
                        break;
                    case DEAD:
                        if (numberOfNeighbours == 3) {
                            stateGrid[i][j] = State.ALIVE;
                        } else {
                            stateGrid[i][j] = State.DEAD;
                        }
                        break;
                }
            }
        }
        updatePreviousStateGrid();
    }
}