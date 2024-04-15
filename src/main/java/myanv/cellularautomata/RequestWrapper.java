// @ https://github.com/myanv/cellular-minimata

package myanv.cellularautomata;

// * This class serves as a container to store the two parameters
// * passed in the HTTP POST request body ('grid' and 'ruleset').
// * Spring parses the request JSON into a single object instance of this class
// * This is to work around the fact that Spring doesn't support 
// * multiple @RequestBody annotations in a method's parameter list.

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