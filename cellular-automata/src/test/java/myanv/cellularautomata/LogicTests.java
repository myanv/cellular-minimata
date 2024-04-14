package myanv.cellularautomata;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LogicTests {

	// * Unit test for the default cellular automata logic (Conway's Game of Life).
	// * Creates an input grid to be mutated and an expected output grid, then compare the two using assertArrayEquals

	@Test
	void testConwayLogic() {
		State[][] inputGrid = { 
			{ State.ALIVE, State.DEAD, State.ALIVE, State.ALIVE },
			{ State.DEAD, State.ALIVE, State.DEAD, State.DEAD },
			{ State.ALIVE, State.DEAD, State.ALIVE, State.DEAD }
		};
		
		CellularAutomataGrid grid = new CellularAutomataGrid(inputGrid);
		grid.mutate();
		State[][] mutatedGrid = grid.getStateGrid();

		State[][] expectedGrid = {
			{ State.DEAD, State.ALIVE, State.ALIVE, State.DEAD },
			{ State.ALIVE, State.DEAD, State.DEAD, State.ALIVE },
			{ State.DEAD, State.ALIVE, State.DEAD, State.DEAD }
		};
		
		assertArrayEquals(mutatedGrid, expectedGrid);
	}

	@Test
	void testMutationLogic() {
		State[][] inputGrid = { 
			{ State.ALIVE, State.DEAD, State.ALIVE, State.ALIVE },
			{ State.DEAD, State.ALIVE, State.DEAD, State.DEAD },
			{ State.ALIVE, State.DEAD, State.ALIVE, State.DEAD }
		};
		
		CellularAutomataGrid grid = new CellularAutomataGrid(inputGrid);
		
		RuleDTO[] ruleDTOArray = {
            new RuleDTO("ALIVE", "<2", "DEAD"),
            new RuleDTO("ALIVE", "==2,3", "ALIVE"),
            new RuleDTO("ALIVE", ">3", "DEAD"),
            new RuleDTO("DEAD", "==3", "ALIVE")
        };

        // Convert the RuleDTO array to Rule array
		Rule[] rules = Arrays.stream(ruleDTOArray)
		.map(Rule::new) // Create Rule objects from RuleDTO
		.toArray(Rule[]::new);

		grid.mutate(rules);
		State[][] mutatedGrid = grid.getStateGrid();

		State[][] expectedGrid = {
			{ State.DEAD, State.ALIVE, State.ALIVE, State.DEAD },
			{ State.ALIVE, State.DEAD, State.DEAD, State.ALIVE },
			{ State.DEAD, State.ALIVE, State.DEAD, State.DEAD }
		};
		
		assertArrayEquals(mutatedGrid, expectedGrid);
	}

	@Test
    void testConvertRuleDTOArrayToRuleArray() {
        // Create a sample array of RuleDTO
        RuleDTO[] ruleDTOArray = {
            new RuleDTO("ALIVE", "<2", "DEAD"),
            new RuleDTO("ALIVE", "2,3", "ALIVE"),
            new RuleDTO("ALIVE", ">3", "DEAD"),
            new RuleDTO("DEAD", "==3", "ALIVE")
        };

        // Convert the RuleDTO array to Rule array
		Rule[] rules = Arrays.stream(ruleDTOArray)
		.map(Rule::new) // Create Rule objects from RuleDTO
		.toArray(Rule[]::new);

        // Assert that the length of the converted Rule array matches the length of the original RuleDTO array
        assertEquals(ruleDTOArray.length, rules.length);

        // Assert that each Rule object in the converted array has the expected properties
        for (int i = 0; i < ruleDTOArray.length; i++) {
            RuleDTO ruleDTO = ruleDTOArray[i];
            Rule rule = rules[i];
            assertEquals(ruleDTO.getStartState(), rule.getStartState().toString());
            assertEquals(ruleDTO.getCondition(), rule.getCondition().toString());
            assertEquals(ruleDTO.getEndState(), rule.getEndState().toString());
        }
    }

}

