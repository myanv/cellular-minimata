package myanv.cellularautomata;

import java.util.Arrays;
import org.springframework.stereotype.Service;

@Service
abstract class Relation {
    protected String condition;

    public Relation(String condition) {
        this.condition = condition;
    }
    public abstract boolean evaluate(int numberOfNeighbours);
}

class Equals extends Relation {
    public Equals(String condition) {
        super(condition);
    }
    @Override
    public boolean evaluate(int numberOfNeighbours) {
        String[] stringOperands = condition.substring(1).split(",");
        int[] operands = Arrays.stream(stringOperands)
                            .mapToInt(i -> Integer.parseInt(i))
                            .toArray();
        for (int i : operands) {
            if (i == numberOfNeighbours) {
                return true;
            }        
        }
        return false;
    }
}

class LessThan extends Relation {
    public LessThan(String condition) {
        super(condition);
    }
    public boolean evaluate(int numberOfNeighbours) {
        int operand = Integer.valueOf(condition.charAt(1));
        return numberOfNeighbours < operand;
    }
}

class GreaterThan extends Relation {
    public GreaterThan(String condition) {
        super(condition);
    }
    public boolean evaluate(int numberOfNeighbours) {
        int operand = Integer.valueOf(condition.charAt(1));
        return numberOfNeighbours > operand;
    }
}