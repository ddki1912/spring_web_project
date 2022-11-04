const active = "active";

const readers = document.querySelector(".reader-js");
const readerList = document.querySelector(".reader-content-js");
const books = document.querySelector(".books-js");
const booksList = document.querySelector(".books-content-js");

const readerAdd = document.querySelector(".reader-add-js");
const readerEdit = document.querySelectorAll(".reader-edit-js");
const readerDel = document.querySelectorAll(".reader-delete-js");
const borrowBtn = document.querySelectorAll(".borrow-js");
const returnBtn = document.querySelectorAll(".return-js");
const readerAddModal = document.querySelector(".reader-add-modal");
const readerEditModal = document.querySelector(".reader-edit-modal");
const readerDelModal = document.querySelector(".reader-delete-modal");
const borrowModal = document.querySelector(".borrow-modal");
const returnModal = document.querySelectorAll(".return-modal-adjust");

const booksAdd = document.querySelector(".books-add-js");
const booksEdit = document.querySelectorAll(".books-edit-js");
const booksDel = document.querySelectorAll(".books-delete-js");
const showBorrowed = document.querySelectorAll(".show-borrowed-js");

const booksAddModal = document.querySelector(".books-add-modal");
const booksEditModal = document.querySelector(".books-edit-modal");
const booksDelModal = document.querySelector(".books-delete-modal");
const borrowedListModal = document.querySelectorAll(".borrowed-list-modal");

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

function showAddBooksModal() {
	booksAddModal.classList.add(active);
}

function showEditBooksModal() {
	booksEditModal.classList.add(active);
}

function showDelBooksModal() {
	booksDelModal.classList.add(active);
}

function removeModal() {
	readerAddModal.classList.remove(active);
	readerEditModal.classList.remove(active);
	readerDelModal.classList.remove(active);
	borrowModal.classList.remove(active);
	booksAddModal.classList.remove(active);
	booksEditModal.classList.remove(active);
	booksDelModal.classList.remove(active);
	for (let i = 0; i < borrowedListModal.length; i++) {
		borrowedListModal[i].classList.remove(active);
	}
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
        let bookIdReaderItemValue = readerItem[i].querySelector(".book-id-js");
		let readerIdToReturn = returnModal[i].querySelector(".reader-input-js");
        let bookIdToReturn = returnModal[i].querySelector(".book-input-js");
		readerIdToReturn.value = readerItemValue.innerHTML;
        bookIdToReturn.value = bookIdReaderItemValue.innerHTML;
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

for (let i = 0; i < booksDel.length; i++) {
	booksDel[i].addEventListener("click", function() {
		var booksItemValue = booksItem[i].querySelector(".books-item-value-js");
		var deleteBookInfoInput = booksDelModal.querySelector(".delete-info--inp-js");
		deleteBookInfoInput.value = booksItemValue.innerHTML;
		showDelBooksModal();
	});
}

for (let i = 0; i < showBorrowed.length; i++) {
	showBorrowed[i].addEventListener("click", function() {
		borrowedListModal[i].classList.add(active);
	});
}

for (const modal of closeModal) {
	modal.addEventListener("click", removeModal);
}

readerAddModal.addEventListener("click", removeModal);
readerEditModal.addEventListener("click", removeModal);
readerDelModal.addEventListener("click", removeModal);
borrowModal.addEventListener("click", removeModal);
booksAddModal.addEventListener("click", removeModal);
booksEditModal.addEventListener("click", removeModal);
booksDelModal.addEventListener("click", removeModal);

for (let i = 0; i < borrowedListModal.length; i++) {
	borrowedListModal[i].addEventListener("click", removeModal);
}

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



