// DOM refs
const btnReview = document.querySelector(".btn-review");
const modal = document.querySelector(".modal");
const closeModal = document.querySelector(".close-modal");

// Add event listener
btnReview.addEventListener("click", () => {
    modal.style.display = "flex";
});

closeModal.addEventListener("click", () => {
    modal.style.display = "none";
});
