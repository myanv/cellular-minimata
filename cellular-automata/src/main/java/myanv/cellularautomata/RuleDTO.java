package myanv.cellularautomata;

import org.springframework.stereotype.Service;

@Service
public class RuleDTO {
    private String startState;
    private String condition;
    private String endState;

    public RuleDTO() {}
    
    public RuleDTO(String startState, String condition, String endState) {
        this.startState = startState;
        this.condition = condition;
        this.endState = endState;
    }
    public String getStartState() {
        return startState.toUpperCase();
    }
    public String getEndState() {
        return endState.toUpperCase();
    }
    public String getCondition() {
        return condition;
    }
}