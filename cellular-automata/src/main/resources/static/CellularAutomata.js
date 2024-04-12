// CellularAutomata.js

// Get the start button and define the canvas
const startButton = document.getElementById("start-btn");
const gridContainer = document.getElementById("grid-container");
let rows = 10;
let columns = 10;
let resolution = 50;

// Function to handle the start button click
startButton.addEventListener("click", async () => {
    rows = document.getElementById("rows").valueAsNumber;
    columns = document.getElementById("columns").valueAsNumber;
    resolution = document.getElementById("resolution").valueAsNumber;
    steps = document.getElementById("steps").valueAsNumber;

    // Create a dynamic canvas using the p5.js library
    createCanvas(columns * resolution, rows * resolution);
    // checkIfValid(canvas)

    let response = await fetch(`/api/cellular-automata/grid?rows=${rows}&columns=${columns}`, { method: 'GET' });
    let grid = await response.json();

    draw(grid);

    setTimeout(async function () { 
        response = await fetch(`/api/cellular-automata/mutate?intGrid=${grid}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(grid)
        });
        grid = await response.json();
        console.table(grid);
        
        draw(grid);
    }, 300);
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


