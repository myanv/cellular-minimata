// @ https://github.com/myanv/cellular-minimata

// Get the start button and define the canvas
const startButton = document.getElementById("start-btn");
const gridButton = document.getElementById("get-grid");
const newButton = document.getElementById("get-blank");

// Default parameter values
let rows = 25;
let columns = 25;
let resolution = 40;
let steps = 250;
let rules = document.getElementById("rules").value;
let grid;

// p5.js setup function, defining the default canvas and grid
async function setup() {    
    createCanvas(1000, 1000);
    const canvas = document.getElementById("defaultCanvas0");

    background(235);
    let response = await fetch(`/api/cellular-automata/grid?rows=${rows}&columns=${columns}`, {
        method: 'GET',
    });
    grid = await response.json();
    draw();
}

// Function to create a new blank grid when the new grid button is clicked
newButton.addEventListener("click", async () => {
    background(235);
    rows = document.getElementById("rows").valueAsNumber;
    columns = document.getElementById("columns").valueAsNumber;
    resolution = document.getElementById("resolution").valueAsNumber;
    const width = columns * resolution;
    const height = rows * resolution;

    resizeCanvas(width, height);
    let response = await fetch(`/api/cellular-automata/emptygrid?rows=${rows}&columns=${columns}`, {
        method: 'GET',
    });
    grid = await response.json();
    draw();
})

// Function to get a new random grid when random grid button is clicked
gridButton.addEventListener("click", async () => {
    background(235);
    rows = document.getElementById("rows").valueAsNumber;
    columns = document.getElementById("columns").valueAsNumber;
    resolution = document.getElementById("resolution").valueAsNumber;
    const width = columns * resolution;
    const height = rows * resolution;

    resizeCanvas(width, height);
    let response = await fetch(`/api/cellular-automata/grid?rows=${rows}&columns=${columns}`, {
        method: 'GET',
    });
    grid = await response.json();
    draw();
});

// Function to handle the start simulator button click
startButton.addEventListener("click", async () => {
    background(235);
    steps = document.getElementById("steps").valueAsNumber;
    rules = document.getElementById("rules").value;

    let ruleset = parseRuleset(rules);
    
    for (let i = 0; i < steps; i++) {
        draw();
        await delay(50);
        let response = await fetch(`/api/cellular-automata/mutate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 
                grid: grid,
                ruleset: ruleset
            })
        });
        grid = await response.json();
        await delay(50);
        draw();
    };
});


// p5.js draw function to draw the 2D array
function draw() {
    background(235);
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

// Defines the delay between each grid stages/mutations
function delay(milliseconds) {
    return new Promise(resolve => {
        setTimeout(resolve, milliseconds);
    });
}

// Parses the ruleset input into an array to be passed to the backend
// and carries out response validation.
function parseRuleset(rules) {
    const ruleset = rules.split(/\n+/);
    const parsedRules = [];

    for (let rule of ruleset) {
        rule = rule.toUpperCase();
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
    return parsedRules;
}


// Function to change the state of the cell when the mouse is clicked
function mouseClicked() {
    const i = Math.floor(mouseY / resolution);        
    const j = Math.floor(mouseX / resolution);
    grid[i][j] = grid[i][j] == 'ALIVE' ? 'DEAD' : 'ALIVE';
    draw();
    return false;
}