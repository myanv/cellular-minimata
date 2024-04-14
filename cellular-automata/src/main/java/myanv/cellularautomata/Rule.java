// @ https://github.com/myanv/cellular-minimata

package myanv.cellularautomata;

// * This class defines the rules of the cellular automata.
// * A rule is composed of a start state, a condition, and an end state
// * (e.g. "alive <3 dead" reads as "if a cell is alive and has less than 3 live neighbours, then it dies."

public class Rule {
    private State startState;
    private Relation condition;
    private State endState;

    public Rule() {}

    public Rule(RuleDTO ruleDTO) {
        this.startState = State.valueOf(ruleDTO.getStartState());
        this.condition = createRelation(ruleDTO.getCondition());
        this.endState = State.valueOf(ruleDTO.getEndState());
    }
    
    public Rule(String startState, String stringCondition, String endState) {
        this.startState = State.valueOf(startState.toUpperCase());
        this.condition = createRelation(stringCondition);
        this.endState = State.valueOf(endState.toUpperCase());
    }
    
    public State getEndState() {
        return endState;
    }

    public Relation getCondition() {
        return condition;
    }

    public State getStartState() {
        return startState;
    }

    // * Initialises the relation/condition using subclasses of the Relation abstract class

    public Relation createRelation(String stringCondition) {
        if (stringCondition.startsWith("<")) {
            return new LessThan(stringCondition);
        } else if (stringCondition.startsWith(">")) {
            return new GreaterThan(stringCondition);
        } else {
            return new Equals(stringCondition);
        }
    }

    // * Evaluates the condition.

    public boolean evaluateCondition(int numberOfNeighbours) {
        return condition.evaluate(numberOfNeighbours);
    }
}
