let exercises;
const destinationContainer = $("#destination__container")[0];
const originContainer = $("#origin__container");
const originalText = $("#original__text");
let audioContainer = $("#audio-container")
let words = $(".word");
let destinationPosDefault = destinationContainer.getBoundingClientRect();
let destinationArray = [];
let originArray = [];
let answer = "";
const checkAnswerBtn = $(".check_button");
let score = 0;
let life = 5;
var timeLeft = 60;
async function loadData() {
    try {
        const result = await $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/questions"
        });
        exercises = result;
        randomQuestion();
        draw();
        drawAudio();
    } catch (err) {
        console.log(err.toString(), "Error - LoadListItemsHelper");
    }
}

let exercise = [];
function randomQuestion(){
    exercise = exercises[Math.floor(Math.random() * exercises.length)];
}
function drawAudio(){
    let content = exercise.content;
    const audioElement = document.createElement("audio");
    audioElement.controls = true;

    const sourceElement = document.createElement("source");
    sourceElement.src = content;
    sourceElement.type = "audio/mpeg";
    audioElement.appendChild(sourceElement);
    const audioContentNode = document.createElement("div");
    audioContentNode.id = "audioContent";
    audioContentNode.appendChild(audioElement);
    audioContainer[0].appendChild(audioContentNode)
}

function draw() {

    let englishSentence = exercise.vietnamese.split(" ");
    let listOfWords = exercise.words.split(",");
    originalText[0].innerHTML ='';
    originContainer[0].innerHTML = ''

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
            console.log(originArray);
            console.log(destinationArray)
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
        clearInterval(timer);
        const modal = document.querySelector('.modal');
        const modalHeading = modal.querySelector('h3');
        const scoreElement = document.getElementById('score');
        modalHeading.textContent = 'Correct Answer!';
        scoreElement.textContent = score;
        modal.style.display = 'block';

    } else {
        clearInterval(timer);
        if (life > 0) {
            life--;
            heart = document.getElementById("heart")
            heart.innerHTML = '';
            for (let i = 0; i < life ; i++){
                heart.innerHTML += `<div class="heart">&#x2665;</div>`;
            }
            if (life === 0) {
                clearInterval(timer);
                $("#countdown").text("GAME OVER");
                window.location.href = "http://localhost:8080/quiz/drunk"; // Chuyển trang khi hết mạng
            }
        }
        if (score >= 5) {
            score -= 5;
        } else {
            score = 0;
        }
        console.log("Incorrect answer!");
        console.log("Score:", score);

        // Hiển thị modal sai
        const modal = document.querySelector('.modal');
        const modalHeading = modal.querySelector('h3');
        const scoreElement = document.getElementById('score');
        modalHeading.textContent = 'Incorrect Answer!';
        scoreElement.textContent = score;
        modal.style.display = 'block';
    }
}


const closeModalButton = document.querySelector('.close-modal');
closeModalButton.addEventListener('click', function () {
    const modal = document.querySelector('.modal');
    modal.style.display = 'none';
    // Khởi động lại đếm thời gian
    timer = setInterval(countdown, 1000);
});

function countdown() {
    timeLeft--;
    $("#countdown").text(timeLeft);
    if (timeLeft === 0) {
        clearInterval(timer);
        $("#countdown").text("TIME OUT");
        life--;
        // Chuyển hướng khi hết thời gian; // Gọi hàm để chuyển sang câu hỏi mới
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
$(document).ready(function() {
    const clearAllButton = $(".clear_button");
    clearAllButton.on("click", function() {
        draw();
        destinationArray = []
        originArray.forEach(e => e.location = "origin")
    });
});
loadData();

function getNewQuestion() {
    randomQuestion();
    draw();
    drawAudio();
    const modal = document.querySelector('.modal');
    modal.style.display = 'none';
    timeLeft = 60;
    $("#score").text(score);
    $("#countdown").text(timeLeft);
}