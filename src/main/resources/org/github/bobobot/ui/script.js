console.log("SzakgyakChan script loaded.");

$(document).arrive(".board-div", function() {
	console.log("Board divs arrived!");
	let boardDivs = Array.from(document.getElementsByClassName("board-div"));

	boardDivs.forEach(boardDiv => {
		console.log("Adding event listeners to board divs");
		boardDiv.addEventListener("animationend", function () {
			console.log("removing animated class from board divs");
			boardDiv.classList.remove("animate__animated");
			//boardDiv.removeEventListener("animationend", this);
		});
	});
});

$(document).arrive(".login-form", function() {
	console.log("Login form arrived!");

	let loginComponents = Array.from($(".login-form .v-slot"));
	let i = 0;

	loginComponents.forEach(loginComponent => {
		loginComponent.firstChild.classList.add("animate__animated");
		loginComponent.firstChild.classList.add("animate__backInLeft");
		loginComponent.firstChild.classList.add("d" + i++);
	});
});

$(document).arrive(".register-form", function () {
	console.log("Register form arrived!");

	let registerComponents = Array.from($(".register-form .v-formlayout-row"));
	let i = 0;

	registerComponents.forEach(registerComponent => {
		registerComponent.classList.add("animate__animated");
		registerComponent.classList.add("animate__backInLeft");
		registerComponent.classList.add("d" + i++);
	});
});