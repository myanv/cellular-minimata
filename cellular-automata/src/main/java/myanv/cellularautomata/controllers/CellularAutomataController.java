// * Controller for the cellular automata application
// @ https://github.com/myanv/cellular-minimata

package myanv.cellularautomata.controllers;

import myanv.cellularautomata.CellularAutomataGrid;
import myanv.cellularautomata.RequestWrapper;
import myanv.cellularautomata.State;
import myanv.cellularautomata.Rule;
import myanv.cellularautomata.RuleDTO;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RestController
@RequestMapping("/api/cellular-automata")
public class CellularAutomataController {
    
    // * CORS configuration for the browser.
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    // * HTTP request: GET
    // * Receives parameters rows and columns from the front-end and creates an initial the 2D state grid.
    
    @GetMapping("/grid")
    public State[][] getCellularAutomataGrid(@RequestParam int rows, @RequestParam int columns) {
        CellularAutomataGrid grid = new CellularAutomataGrid(rows, columns);
        return grid.getStateGrid();
    }

    // * HTTP request: POST
    // * Receives the body of an array (automatically parsing from JSON by @RequestBody) from the front-end, mutates it, then returns the mutated state grid. 
    
    /*
    @PostMapping("/mutate")
    public State[][] mutate(@RequestBody State[][] grid) {
        CellularAutomataGrid stateGrid = new CellularAutomataGrid(grid);
        stateGrid.mutate();
        return stateGrid.getStateGrid();
    }
    */

    // Since Spring doesn't support using @RequestBody more than once in a method parameter list,
    // create a request wrapper class to accept both parameters.

    @PostMapping("/mutate")
    public State[][] mutate(@RequestBody RequestWrapper request) {
        CellularAutomataGrid stateGrid = new CellularAutomataGrid(request.getGrid());
        RuleDTO[] ruleset = request.getRuleset();
        Rule[] rules = Arrays.stream(ruleset)
                             .map(Rule::new) // Create Rule objects from RuleDTO
                             .toArray(Rule[]::new);
        stateGrid.mutate(rules);
        return stateGrid.getStateGrid();
    }
}

