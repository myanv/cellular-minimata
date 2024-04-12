package myanv.cellularautomata;

import java.util.ArrayList;
import org.springframework.stereotype.Service;

// * This class will implement the neighbour-finding logic of cellular automata
// * Its main function is to create an array of 8 neighbouring elements
// * and count the number of live cells.

@Service
public class Neighbourhood {

    // * Initialises the index of the element in the array, its state, and its neighbourhood.
    
    private int ith = 0;
    private int jth = 0;
    private int rows = 0;
    private int columns = 0;
    private ArrayList<State> neighbourhood = new ArrayList<>();

    public Neighbourhood() {}

    // * Initialises a Neighbourhood object with the desired parameters.
    public Neighbourhood(int rows, int columns, int ith, int jth, State[][] previousStateGrid) {
        if (ith < 0 || jth < 0 || ith >= rows || jth >= columns) {
            throw new IllegalArgumentException("Illegal indices!");
        }
        this.ith = ith;
        this.jth = jth;
        this.rows = rows;
        this.columns = columns;
        initialiseNeighbourhood(previousStateGrid);
    }
    
    // * This method initialises the neighbourhood. It takes a State[][] grid (let it be A)
    // * and loops over the elements surrounding A[ith][jth], appending them to the neighbourhood ArrayList.

    public void initialiseNeighbourhood(State[][] grid) {

        // * Clears the neighbourhood array everytime the method is called
        neighbourhood.clear(); 

        for (int i = ith - 1; i <= ith + 1; i++) {
            for (int j = jth - 1; j <= jth + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < columns && !(i == ith && j == jth)) {
                    neighbourhood.add(grid[i][j]);
                }
            }
        }
    }

    // * Counts the number of live neighbours in the neighbourhood ArrayList.
    public int countLiveNeighbours() {
        return neighbourhood.stream()
                .mapToInt(State::getState)
                .sum();
    }
}
