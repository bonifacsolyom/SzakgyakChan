var boardDivs = document.getElementsByClassName("board-div");

boardDivs.forEach(boardDiv => {
    boardDiv.addEventListener('animationend', function() {
        boardDiv.classList.remove("animate__animated");
        //boardDiv.removeEventListener("animationend", this);
    });
});