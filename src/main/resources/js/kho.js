

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
    let count = 0;
    const shuffledPictures = shuffleArray(pictures);
    console.log(shuffledPictures)
    let shuffledQuestion = [];
    const randomQuestion = Math.floor(Math.random()*shuffledPictures.length);
    for (let i = 0; i < shuffledPictures.length && count < 2; i++) {
        if(i !== randomQuestion){
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
                </div>
            `;
        content.innerHTML += card;


    });

    const quest = `
                <h2 class="question">
                    Which one of these is "${shuffledPictures[randomQuestion].name}"?
                </h2>
            `;
    question.innerHTML += quest;

}

loadData();
