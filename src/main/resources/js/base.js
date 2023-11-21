let exercises;
const destinationContainer = $("#destination__container")[0];
const originContainer = $("#origin__container");
const originalText = $("#original__text");
let words = $(".word");
let destinationPosDefault = destinationContainer.getBoundingClientRect();
let destinationArray = [];
const originArray = [];
let answer = "";
const checkAnswerBtn = $(".check_button");
let score = 0;
let life = 5;
var timeLeft = 5;
async function loadData() {
    try {
        const result = await $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/questions"
        });
        exercises = result;
        draw();
    } catch (err) {
        console.log(err.toString(), "Error - LoadListItemsHelper");
    }
}

function draw() {
    let exercise = exercises[Math.floor(Math.random() * exercises.length)];
    let englishSentence = exercise.vietnamese.split(" ");
    let listOfWords = exercise.words.split(",");
    let content = exercise.content;

    // Tạo phần tử audio
    const audioElement = document.createElement("audio");
    audioElement.controls = true;

    const sourceElement = document.createElement("source");
    sourceElement.src = content;
    sourceElement.type = "audio/mpeg";

    audioElement.appendChild(sourceElement);

    // Chèn phần tử audio vào thẻ div "audioContent"
    const audioContentNode = document.createElement("div");
    audioContentNode.id = "audioContent";
    audioContentNode.appendChild(audioElement);
    originContainer[0].appendChild(audioContentNode);

    for (let i = 0; i < englishSentence.length; i++) {
        const spanNode = document.createElement("span");
        spanNode.textContent = englishSentence[i];
        originalText[0].appendChild(spanNode);
    }

    for (let i = 0; i < listOfWords.length; i++) {
        const wordNode = document.createElement("div");
        wordNode.textContent = listOfWords[i];
        wordNode.classList.add("word");
        originContainer[0].appendChild(wordNode);
        createOriginArray(wordNode);
    }

    attachEventListeners();
}
function calibrateDestinationCursorPos(destinationArray) {
    if (destinationArray.length === 0) {
        return destinationPosDefault.x;
    } else {
        let sum = destinationPosDefault.x;
        destinationArray.forEach((element) => {
            sum += element.width + 20;
        });
        return sum;
    }
}


function createOriginArray(word) {
    let wordPosition = word.getBoundingClientRect();
    let newWordObject = Object.assign(wordPosition);
    newWordObject.word = word.textContent;
    newWordObject.location = "origin";
    originArray.push(newWordObject);
}

function attachEventListeners() {
    words = $(".word");
    for (let i = 0; i < words.length; i++) {
        words[i].addEventListener("click", () => {
            let destinationStartPos = calibrateDestinationCursorPos(destinationArray);

            let yTravel =
                originArray[i].y -
                (destinationPosDefault.y +
                    (destinationPosDefault.height - originArray[i].height) / 2);
            let xTravel = 0;

            if (originArray[i].x > destinationStartPos) {
                xTravel = -(originArray[i].x - destinationStartPos);
            } else {
                xTravel = destinationStartPos - originArray[i].x;
            }

            if (originArray[i].location === "origin") {
                originArray[i].location = "destination";
                destinationArray.push(originArray[i]);
            } else if (originArray[i].location === "destination") {
                yTravel = 0;
                xTravel = 0;
                originArray[i].location = "origin";
                destinationArray = destinationArray.filter(
                    (wordObject) => wordObject.word !== originArray[i].word
                );
            }

            words[i].style.transform = `translate(${xTravel}px,-${yTravel}px)`;

            getAnswer();
        });
    }

    checkAnswerBtn.on("click", () => {
        if (life == 0){
            alert('life run out');
            return;
        }
        checkAnswer();
        console.log("your life: " + life)
    });
}

function getAnswer() {
    let selectedWords = destinationArray.map((wordObject) => wordObject.word.trim());
    answer = selectedWords.join(" ");
    console.log(answer);
}

function checkAnswer() {
    let flag = false;
    for (let i = 0; i < exercises.length; i++) {
        if (answer.trim() == exercises[i].english.trim()) {
            flag = true;
            break;
        }
    }
    if (flag) {
        score += 10;
        console.log("Correct answer!");
        console.log("Score:", score);
    } else {
        if (life > 0) {
            life--;
            heart = document.getElementById("heart")
            heart.innerHTML =''
            for (let i = 0; i < life ; i++){
                heart.innerHTML += `<div class="heart">&#x2665;</div>`;
            }
            if (life === 0) {
                clearInterval(timer); // Dừng bộ đếm thời gian khi hết mạng
                $("#countdown").text("GAME OVER");
                window.location.href = "http://localhost:8080/quiz/drunk"; // Chuyển trang khi hết mạng
            }
        }
        if (score >= 5) {
            score -= 5;
        } else {
            score = 0; // Điểm không bao giờ âm
        }
        console.log("Incorrect answer!");
        console.log("Score:", score);
    }
}

function countdown() {
    timeLeft--;
    $("#countdown").text(timeLeft);
    if (timeLeft === 0) {
        clearInterval(timer);
        $("#countdown").text("TIME OUT");
        life--;
    }
}
var timer = setInterval(countdown, 1000);

$(document).ready(function() {
    var heartElement = $(".heart");
    var heartCount = 1;
    var maxHeartCount = 5;

    function updateHeartCount(count) {
        heartCount = Math.max(1, Math.min(count, maxHeartCount));
        heartElement.attr("data-count", heartCount);
    }

    updateHeartCount(3);
});

loadData();