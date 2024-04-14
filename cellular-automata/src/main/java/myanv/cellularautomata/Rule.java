package myanv.cellularautomata;

import org.springframework.stereotype.Service;;

@Service
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
    
    public State getStartState() {
        return startState;
    }

    public Relation createRelation(String stringCondition) {
        if (stringCondition.startsWith("<")) {
            return new LessThan(stringCondition);
        } else if (stringCondition.startsWith(">")) {
            return new GreaterThan(stringCondition);
        } else {
            return new Equals(stringCondition);
        }
    }

    public boolean evaluateCondition(int numberOfNeighbours) {
        return condition.evaluate(numberOfNeighbours);
    }
}
