// @ https://github.com/myanv/cellular-minimata

package myanv.cellularautomata;

// * This class serves as a data transfer object (DTO) facilitating the transmission of rulesets
// * between the front-end and back-end. It acts as an intermediary between the JSON ruleset array
// * and the Rule class, which uses custom typed fields instead of String fields.

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