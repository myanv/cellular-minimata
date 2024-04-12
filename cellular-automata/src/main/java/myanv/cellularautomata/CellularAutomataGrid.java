package myanv.cellularautomata;
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

    // * This constructor is intended to be used to update the state grid passed as parameter.

    public CellularAutomataGrid(State[][] grid) {
        this.stateGrid = grid;
        this.rows = grid.length;
        this.columns = grid[0].length; 
        initialisePrevGrid();       
    }

    // * This constructor is intended to be used to create the very first state grid.

    public CellularAutomataGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        initialiseStateGrid();
        initialisePrevGrid();
    }

    // * Returns the state grid.

    public State[][] getStateGrid() {
        return stateGrid;
    }

    // * Creates a state grid with the desired dimensions
    // * Then fill them with random 1s and 0s, representing the ALIVE or DEAD states of the cells. 
    
    public void initialiseStateGrid() {
        stateGrid = new State[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                stateGrid[i][j] = Math.random() > 0.5 ? State.ALIVE : State.DEAD;
            }
        }
    }

    // * Creates a previous state grid to store the previous states of each cell.
    public void initialisePrevGrid() {
        previousStateGrid = new State[rows][columns];
        updatePreviousStateGrid();
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