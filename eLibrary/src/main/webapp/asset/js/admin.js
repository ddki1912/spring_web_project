const active = "active";

const addLibra = document.querySelector(".add-js");
const editLibras = document.querySelectorAll(".edit-js");
const deleteLibras = document.querySelectorAll(".delete-js");

const addModal = document.querySelector("#add-modal");
const editModal = document.querySelector("#edit-modal");
const deleteModal = document.querySelector("#delete-modal");

const closeModal = document.querySelectorAll(".modal-close-js");
const modalContent = document.querySelectorAll(".body-content");

const libraList = document.querySelectorAll(".libra-list-js");

function showAddModal() {
	addModal.classList.add(active);
}

function showEditModal() {
	editModal.classList.add(active);
}

function showDeleteModal() {
	deleteModal.classList.add(active);
}

function removeModal() {
	addModal.classList.remove(active);
	editModal.classList.remove(active);
	deleteModal.classList.remove(active);
}

function clickOutToClose(e) {
	e.stopPropagation();
}

addLibra.addEventListener("click", showAddModal);

for (let i = 0; i < editLibras.length; i++) {
	editLibras[i].addEventListener("click", function() {
		var libraListValue = libraList[i].querySelectorAll(".libra-list-value-js");
		var infoInput = editModal.querySelectorAll(".info-input-js");
		for (let j = 0; j < libraListValue.length; j++) {
			infoInput[j].value = libraListValue[j].innerHTML;
		};
		showEditModal();
	});
}


for (let i = 0; i < deleteLibras.length; i++) {
	deleteLibras[i].addEventListener("click", function() {
		var libraListValue = libraList[i].querySelector(".libra-list-value-js");
		var infoInput = deleteModal.querySelector(".info-input-js");
		infoInput.value = libraListValue.innerHTML;
		showDeleteModal();
	});
}

for (const closeBtn of closeModal) {
	closeBtn.addEventListener("click", removeModal);
}

addModal.addEventListener("click", removeModal);
editModal.addEventListener("click", removeModal);
deleteModal.addEventListener("click", removeModal);

for (const content of modalContent) {
	content.addEventListener("click", clickOutToClose);
}

// Notice javascript

const notice = document.querySelector(".notice-js");

notice.onclick = function(){
    notice.classList.remove(active);
}
