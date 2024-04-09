package myanv.cellularautomata;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

// * This class will implement the neighbour-finding logic of cellular automata
// * Its main function is to create an array of 8 neighbouring elements
// * and count the number of live cells.

@Service
public class Neighbourhood extends CellularAutomataGrid {

    // Initialises the index of the element in the array, its state, and its neighbourhood
    private int ith = 0;
    private int jth = 0;
    private ArrayList<State> neighbourhood = new ArrayList<>();

    public Neighbourhood(int rows, int columns, int ith, int jth) {
        super(rows, columns);
        this.ith = ith;
        this.jth = jth;
        getNeighbourhood();
    }
    public ArrayList<State> getNeighbourhood() {
        neighbourhood.clear(); // Clears the neighbourhood array -> maybe integrate with DB for storage
        for (int i = ith - 1; i <= ith + 1; i++) {
            for (int j = jth - 1; j <= jth + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < columns && !(i == ith && j == jth)) {
                    neighbourhood.add(super.getState(i, j));
                }
            }
        }
        return neighbourhood;
    }
    public int countLiveNeighbours() {
        return neighbourhood.stream()
                .mapToInt(State::getState)
                .sum();
    }
}
