package myanv.cellularautomata;

public class RequestWrapper {
    private State[][] grid;
    private RuleDTO[] ruleset;
    
    public RequestWrapper() {}

    public RequestWrapper(State[][] grid, RuleDTO[] ruleset) {
        this.grid = grid;
        this.ruleset = ruleset;
    }
    public State[][] getGrid() {
        return grid;
    }
    public RuleDTO[] getRuleset() {
        return ruleset;
    }
}

