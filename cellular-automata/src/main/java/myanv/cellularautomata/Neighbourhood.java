package myanv.cellularautomata;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public abstract class Neighbourhood extends CellularAutomataGrid {
    
    // Initialises the index of the element in the array, its state, and its neighbourhood
    private int ith = 0;
    private int jth = 0;
    private State current;
    private ArrayList<State> neighbourhood;

    protected Neighbourhood(int rows, int columns, int ith, int jth) {
        super(rows, columns);
        this.ith = ith;
        this.jth = jth;
        this.neighbourhood = new ArrayList<>();

    }
}
