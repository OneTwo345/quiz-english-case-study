

async function loadData() {
    try {
        const result = await $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/pictures"
        });
        pictures = result;
        renderPictures()
    } catch (err) {
        console.log(err.toString(), "Error - LoadListItemsHelper");
    }
}
let pictures = []


function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}

function renderPictures() {
    const content = document.querySelector(".middle");
    const question = document.querySelector(".prompt");
    const result = document.querySelector(".result");
    let count = 0;
    const shuffledPictures = shuffleArray(pictures);
    let shuffledQuestion = [];
    const randomQuestion = Math.floor(Math.random() * shuffledPictures.length);
    for (let i = 0; i < shuffledPictures.length && count < 2; i++) {
        if (i !== randomQuestion) {
            shuffledQuestion.push(shuffledPictures[i]);
            count++;
        }
    }

    shuffledQuestion.push(shuffledPictures[randomQuestion]);
    shuffledQuestion = shuffleArray(shuffledQuestion);
    shuffledQuestion.forEach((d) => {
        const card = `
            <div class="card">
                <img src=${d.content}>
                <span >${d.name}</span>
            </div>
        `;
        content.innerHTML += card;
    });

    const quest = `
        <h2 class="question">
            Đâu là "${shuffledPictures[randomQuestion].translation}"?
        </h2>
    `;
    question.innerHTML += quest;

    const images = document.querySelectorAll('.card img');
    images.forEach((img) => {
        img.addEventListener('click', () => {
            checkAnswer(img.getAttribute('src'), shuffledPictures[randomQuestion].content,shuffledPictures[randomQuestion].name);
        });
    });

    function checkAnswer(selectedImageSrc, correctImageSrc, correctName) {
        const result = document.querySelector(".result");
        const bottom = document.querySelector(".bottom");

        const cards = document.querySelectorAll('.card');
        const selectedCard = document.querySelector(`img[src="${selectedImageSrc}"]`).parentNode;

        if (selectedImageSrc === correctImageSrc) {
            bottom.style.backgroundColor = "#d7ffb8";
            bottom.innerHTML = "<strong style='color: green;'>Chính xác!</strong>";
            bottom.style.textAlign = "center";
            bottom.style.fontWeight = "bold";
            bottom.style.fontSize = "24px";

            cards.forEach(card => {
                card.style.backgroundColor = "";
                card.style.border = "";
            });
            selectedCard.style.backgroundColor = "#d7ffb8";
            selectedCard.style.border = "3px solid darkgreen";
        } else {
            bottom.style.backgroundColor = "#ffdfe0";
            bottom.innerHTML = "<strong style='color: orangered;'>Đáp án chính xác là " + correctName.toUpperCase() + "!</strong>";
            bottom.style.textAlign = "center";
            bottom.style.fontWeight = "bold";
            bottom.style.fontSize = "24px";
            cards.forEach(card => {
                card.style.backgroundColor = "";
                card.style.border = "";
            });

            selectedCard.style.backgroundColor = "#ffdfe0";
            selectedCard.style.border = "3px solid red";
        }
    }
}

loadData();
