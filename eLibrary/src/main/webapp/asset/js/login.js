const admin = document.querySelector(".admin");
const libra = document.querySelector(".libra");
const loginForm = document.querySelectorAll(".js-show-modal");
const loginModal = document.querySelector(".js-modal");
const loginModalContent = document.querySelector(".js-modal-content");
const loginClose = document.querySelector(".js-modal-close");
const loginTitle = document.querySelector(".js-modal-title");
const loginBtn = document.querySelector(".login-btn")

function showLoginForm() {
	loginModal.classList.add("active");
}

function closeLogin() {
	loginModal.classList.remove("active");
}

admin.onclick = function() {
	loginTitle.innerHTML = "Admin Login";
	loginModalContent.setAttribute("action", "AdminLogin");
}

libra.onclick = function() {
	loginTitle.innerHTML = "Librarian Login";
	loginModalContent.setAttribute("action", "LibrarianLogin");
}

for (const login of loginForm) {
	login.addEventListener("click", showLoginForm);
}

loginClose.addEventListener("click", closeLogin);

loginModal.addEventListener("click", closeLogin);

loginModalContent.addEventListener("click", function(e) {
	e.stopPropagation();
});