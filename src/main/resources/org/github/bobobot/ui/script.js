console.log("SzakgyakChan script loaded.");

$(document).arrive(".board-div", function() {
    console.log("Board divs arrived!")
    var boardDivs = Array.from(document.getElementsByClassName("board-div"));

    boardDivs.forEach(boardDiv => {
        console.log("Adding event listeners to board divs");
        boardDiv.addEventListener("animationend", function () {
            console.log("removing animated class from board divs");
            boardDiv.classList.remove("animate__animated");
            //boardDiv.removeEventListener("animationend", this);
        });
    });
});