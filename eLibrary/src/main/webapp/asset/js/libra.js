const active = "active";

const readers = document.querySelector(".reader-js");
const readerList = document.querySelector(".reader-content-js");
const books = document.querySelector(".books-js");
const booksList = document.querySelector(".books-content-js");

const readerAdd = document.querySelector(".reader-add-js");
const readerEdit = document.querySelectorAll(".reader-edit-js");
const readerDel = document.querySelectorAll(".reader-delete-js");
const borrowBtn = document.querySelectorAll(".borrow-js");
const returnBtn = document.querySelectorAll(".return-btn");
const readerAddModal = document.querySelector(".reader-add-modal");
const readerEditModal = document.querySelector(".reader-edit-modal");
const readerDelModal = document.querySelector(".reader-delete-modal");
const borrowModal = document.querySelector(".borrow-modal");
const returnModal = document.querySelector(".return-modal");

const booksAdd = document.querySelector(".books-add-js");
const booksEdit = document.querySelectorAll(".books-edit-js");
const booksAddModal = document.querySelector(".books-add-modal");
const booksEditModal = document.querySelector(".books-edit-modal");

const closeModal = document.querySelectorAll(".modal-close-js");
const modalContent = document.querySelectorAll(".body-content");

const readerItem = document.querySelectorAll(".reader-item-js");
const booksItem = document.querySelectorAll(".books-item-js");

function showReaderList() {
	readerList.classList.add(active);
	readers.classList.add(active);
}

function showBooksList() {
	booksList.classList.add(active);
	books.classList.add(active);
}

function removeReaderList() {
	readerList.classList.remove(active);
	readers.classList.remove(active);
}

function removeBooksList() {
	booksList.classList.remove(active);
	books.classList.remove(active);
}


function showAddReaderModal() {
	readerAddModal.classList.add(active);
}

function showEditReaderModal() {
	readerEditModal.classList.add(active);
}

function showDelReaderModal() {
	readerDelModal.classList.add(active);
}

function showBorrowModal() {
	borrowModal.classList.add(active);
}

function showReturnModal() {
	returnModal.classList.add(active);
}

function showAddBooksModal() {
	booksAddModal.classList.add(active);
}

function showEditBooksModal() {
	booksEditModal.classList.add(active);
}

function removeModal() {
	readerAddModal.classList.remove(active);
	readerEditModal.classList.remove(active);
	readerDelModal.classList.remove(active);
	borrowModal.classList.remove(active);
	returnModal.classList.remove(active);
	booksAddModal.classList.remove(active);
	booksEditModal.classList.remove(active);
}

function clickOutToClose(e) {
	e.stopPropagation();
}

// Modal handler

readerAdd.addEventListener("click", showAddReaderModal);

for (let i = 0; i < readerEdit.length; i++) {
	readerEdit[i].addEventListener("click", function() {
		var readerItemValue = readerItem[i].querySelectorAll(".reader-item-value-js");
		var editReaderInfoInput = readerEditModal.querySelectorAll(".edit-info--inp-js");
		for (let j = 0; j < readerItemValue.length; j++) {
			editReaderInfoInput[j].value = readerItemValue[j].innerHTML;
		}
		showEditReaderModal();
	});
}

for (let i = 0; i < readerDel.length; i++) {
	readerDel[i].addEventListener("click", function() {
		var readerItemValue = readerItem[i].querySelector(".reader-item-value-js");
		var deleteReaderInfoInput = readerDelModal.querySelector(".delete-info--inp-js");
		deleteReaderInfoInput.value = readerItemValue.innerHTML;
		showDelReaderModal();
	});
}

for (let i = 0; i < borrowBtn.length; i++) {
	borrowBtn[i].addEventListener("click", function() {
		let readerItemValue = readerItem[i].querySelector(".reader-item-value-js");
		let readerIdToBorrow = borrowModal.querySelector(".reader-input-js");
		readerIdToBorrow.value = readerItemValue.innerHTML;
		showBorrowModal();
	});
}

for (let i = 0; i < returnBtn.length; i++) {
	returnBtn[i].addEventListener("click", function() {
		let readerItemValue = readerItem[i].querySelector(".reader-item-value-js");
		let readerIdToReturn = returnModal.querySelector(".reader-input-js");
		readerIdToReturn.value = readerItemValue.innerHTML;
		showReturnModal();
	});
}

booksAdd.addEventListener("click", showAddBooksModal);

for (let i = 0; i < booksEdit.length; i++) {
	booksEdit[i].addEventListener("click", function() {
		var bookItemValue = booksItem[i].querySelectorAll(".books-item-value-js");
		var editBookInfoInput = booksEditModal.querySelectorAll(".book-input-js");
		for (let j = 0; j < bookItemValue.length; j++) {
			editBookInfoInput[j].value = bookItemValue[j].innerHTML;
		}
		showEditBooksModal();
	});
}

for (const modal of closeModal) {
	modal.addEventListener("click", removeModal);
}

readerAddModal.addEventListener("click", removeModal);
readerEditModal.addEventListener("click", removeModal);
readerDelModal.addEventListener("click", removeModal);
borrowModal.addEventListener("click", removeModal);
returnModal.addEventListener("click", removeModal);
booksAddModal.addEventListener("click", removeModal);
booksEditModal.addEventListener("click", removeModal);

for (const content of modalContent) {
	content.addEventListener("click", clickOutToClose);
}

// Navbar handler

readers.addEventListener("click", function() {
	removeModal();
	removeBooksList();
	showReaderList();
});

books.addEventListener("click", function() {
	removeModal();
	removeReaderList();
	showBooksList();
});


// Notice javascript

const notice = document.querySelector(".notice-js");

notice.onclick = function() {
	notice.classList.remove(active);
}



