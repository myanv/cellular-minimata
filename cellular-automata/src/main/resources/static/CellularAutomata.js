// CellularAutomata.js

// Get the start button and define the canvas
const startButton = document.getElementById("start-btn");
const resolution = document.getElementById("resolution");

// Function to handle the start button click
startButton.addEventListener("click", async () => {
    const rows = document.getElementById("rows").value;
    const columns = document.getElementById("columns").value;
    const gridContainer = document.getElementById("grid-container");
    
    createCanvas(columns * resolution, rows * resolution);
    // checkIfValid(canvas)

    const response = await fetch(`/api/cellular-automata/grid?rows=${rows}&columns=${columns}`);
    const grid = await response.json();
    console.table(grid);
    draw();
});

function draw() {
    background(0);
    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < columns; j++) {
            
            
        }
    }
}


