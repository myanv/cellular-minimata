// @ https://github.com/myanv/cellular-minimata

// Get the start button and define the canvas
const startButton = document.getElementById("start-btn");
const gridContainer = document.getElementById("grid-container");

// Default parameter values
let rows = 25;
let columns = 25;
let resolution = 40;
let steps = 150;
let rules = document.getElementById("rules").value;

// Function to handle the start button click
startButton.addEventListener("click", async () => {
    rows = document.getElementById("rows").valueAsNumber;
    columns = document.getElementById("columns").valueAsNumber;
    resolution = document.getElementById("resolution").valueAsNumber;
    steps = document.getElementById("steps").valueAsNumber;
    rules = document.getElementById("rules").value;

    let ruleset = parseRuleset(rules);
    
    // Create a dynamic canvas using the p5.js library
    createCanvas(columns * resolution, rows * resolution);

    let response = await fetch(`/api/cellular-automata/grid?rows=${rows}&columns=${columns}`, { 
        method: 'GET' 
    });
    let grid = await response.json();

    draw(grid);

    for (let i = 0; i < steps; i++) { 
        response = await fetch(`/api/cellular-automata/mutate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ grid, ruleset: ruleset.map(rule => ({
                startState: rule.startState,
                condition: rule.condition,
                endState: rule.endState
            }))})
        });
        grid = await response.json();
        await delay(100);
        draw(grid);
    };
});

function draw(grid) {
    background(255);
    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < columns; j++) {
            let y = i * resolution;
            let x = j * resolution;
            if (grid[i][j] == 'ALIVE') {
                fill(0);
                stroke(255);
                rect(x, y, resolution, resolution);
            }
        }
    }
}

function delay(milliseconds) {
    return new Promise(resolve => {
        setTimeout(resolve, milliseconds);
    });
}

function parseRuleset(rules) {
    const ruleset = rules.split(/\n+/);
    const parsedRules = [];

    for (const rule of ruleset) {
        const [startState, condition, endState] = rule.split(' ');
        
        // if (responseValidation([startState, condition, endState])) {
            // * Then continue as below
        // } else {
            // * Throw an exception
        // } 

        parsedRules.push({
            startState,
            condition,
            endState,
          });
        }
    console.log(parsedRules);
    return parsedRules;
}