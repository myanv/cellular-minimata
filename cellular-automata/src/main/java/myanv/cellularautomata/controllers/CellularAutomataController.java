



package myanv.cellularautomata.controllers;

import myanv.cellularautomata.CellularAutomataGrid;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RestController
@RequestMapping("/api/cellular-automata")
public class CellularAutomataController {

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

    @GetMapping("/grid")
    public int[][] getCellularAutomataGrid(@RequestParam int rows, @RequestParam int columns) {
        // Create a 2D array to represent the cellular automata grid
        CellularAutomataGrid grid = new CellularAutomataGrid(rows, columns);
        grid.initialiseStateGrid();
        return grid.getStateGridAsInt();
    }
    
}