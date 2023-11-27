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
var timeLeft = 60;
var user;


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

async function getUser() {
    try {
        const result = await $.ajax({
            type: "GET",
            url: "http://localhost:8080/api/users/current"
        });
        user = result;
        let score = user.score;
        // let life = user.heart;
        return user;

    } catch (err) {
        console.log(err.toString(), "Error - LoadListItemsHelper");
    }
}

function drawHeart(heart) {
    let strHeart = ''

    for (let i = 0; i < heart; i++) {
        strHeart += `
                <div class="heart">&#x2665;</div>
            `
    }
    $('#heart').append(strHeart);
}

function drawScore(score) {

    $('#score-point').text(user.score);
}

// async function getNewQuestion() {
//     try {
//         randomQuestion();
//         draw();
//         drawAudio();
//         const modal = document.querySelector('.modal');
//         modal.style.display = 'none';
//         timeLeft = 60;
//         $("#score").text(score);
//         $("#countdown").text(timeLeft);
//     } catch (err) {
//         console.log(err.toString(), "Error - getNewQuestion");
//     }
// }


let exercise = [];

function randomQuestion() {
    exercise = exercises[Math.floor(Math.random() * exercises.length)];
}

function drawAudio() {
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
    originalText[0].innerHTML = '';
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
    checkAnswerBtn.on("click", async () => {
        user = await getUser();
        if (user.heart == 0) {
            alert('life run out');
            return;
        }
        checkAnswer();
        console.log("your life: " + user.heart)
    });
}

function getAnswer() {
    let selectedWords = destinationArray.map((wordObject) => wordObject.word.trim());
    answer = selectedWords.join(" ");
    console.log(answer);
}

function updateUserLife(heart) {
    const url = "http://localhost:8080/api/users/updateHeart/";
    const customer = {heart: heart};
    const userId = user.id;

    $.ajax({
        method: 'PATCH',
        url: url + userId,
        contentType: "application/json",
        data: JSON.stringify(customer),
        accepts: "application/json"
    })
        .done(function (data) {
            $('.heart').eq(0).remove();
        })
        .fail(function (err) {
            alert("Lỗi rồi");
        });
}

function updateUserScore(score) {
    const url = "http://localhost:8080/api/users/updateScore/";
    const customer = {score: score};
    const userId = user.id;

    $.ajax({
        method: 'PATCH',
        url: url + userId,
        contentType: "application/json",
        data: JSON.stringify(customer),
        accepts: "application/json"
    })
        .done(function (data) {
            $('#score-point').text(user.score);

        })
        .fail(function (err) {
            alert("Lỗi score");
        });
}

async function checkAnswer() {
    let flag = false;
    for (let i = 0; i < exercises.length; i++) {
        if (answer.trim() == exercises[i].english.trim()) {
            flag = true;
            break;
        }
    }
    if (flag) {
        user.score += 10;
        updateUserScore(user.score)
        console.log("Correct answer!");
        clearInterval(timer);
        const modal = document.querySelector('.modal');
        const modalHeading = modal.querySelector('h3');
        const scoreElement = document.getElementById('score');
        modalHeading.textContent = 'Correct Answer!';
        scoreElement.textContent = user.score;
        modal.style.display = 'block';

    } else {
        clearInterval(timer);
        // if (user.heart > 0) {
        //     user.heart--;
        //     updateUserLife(user.heart);
        //     if (user.heart === 0) {
        //         clearInterval(timer);
        //         $("#countdown").text("GAME OVER");
        //         window.location.href = "http://localhost:8080/quiz/drunk";
        //     }
        // }
        // if (user.score >= 20) {
        //     user.score -= 20;
        //     await updateUserScore(user.score)
        // } else {
        //     user.score = 0;
        //     await  updateUserScore(user.score)
        // }
        console.log("Incorrect answer!");
        if (user.heart === 0) {
            clearInterval(timer);
            $("#countdown").text("GAME OVER");
            window.location.href = "http://localhost:8080/quiz/drunk";
        }
        if (user.heart > 0) {
            user.heart--;
            user.score -= 20;
            updateHeartAndScore(user.heart, user.score)
            if (user.score <= 20) {
                user.score = 0;
                updateUserScore(0)
            }
        }

        // Hiển thị modal sai
        const modal = document.querySelector('.modal');
        const modalHeading = modal.querySelector('h3');
        const scoreElement = document.getElementById('score');
        modalHeading.textContent = 'Incorrect Answer!';
        scoreElement.textContent = user.score;
        modal.style.display = 'block';
    }
}

function updateHeartAndScore(heart, score) {
    const url = "http://localhost:8080/api/users/updateHeartAndScore/";
    // let customer;
    // if(heart == null){
    //     customer = {score : score}
    // }
    // if(score == null ){
    //     customer = {heart : heart}
    // }
    const customer = {score: score, heart: heart};
    const userId = user.id;

    $.ajax({
        method: 'PATCH',
        url: url + userId,
        contentType: "application/json",
        data: JSON.stringify(customer),
        accepts: "application/json"
    })
        .done(function (data) {
            $('#score-point').text(user.score);
            $('.heart').eq(0).remove();

        })
        .fail(function (err) {
            alert("Lỗi score");
        });
}

const closeModalButton = document.querySelector('.close-modal');
closeModalButton.addEventListener('click', function () {
    const modal = document.querySelector('.modal');
    modal.style.display = 'none';
    timer = setInterval(countdown, 1000);
});

async function countdown() {
    timeLeft--;
    $("#countdown").text(timeLeft);
    if (timeLeft === 0) {
        clearInterval(timer);
        $("#countdown").text("OOPS");
        user.score >= 20 ? user.score -= 20 : 0;
        user.heart--;
        updateHeartAndScore(user.heart, user.score)
    }
}

var timer = setInterval(countdown, 1000);
$(document).ready(function () {
    var heartElement = $(".heart");
    var heartCount = 1;
    var maxHeartCount = 5;

    function updateHeartCount(count) {
        heartCount = Math.max(1, Math.min(count, maxHeartCount));
        heartElement.attr("data-count", heartCount);
    }

    updateHeartCount(3);
});
$(document).ready(function () {
    const clearAllButton = $(".clear_button");
    clearAllButton.on("click", function () {
        draw();
        destinationArray = []
        originArray.forEach(e => e.location = "origin")
    });
});

// async function main() {
//     try {
//     user = await getUser();
//         loadData();
//     } catch (err) {
//         console.log(err.toString(), "Error - main");
//     }
// }
window.onload = async () => {
    try {
        user = await getUser();
        drawHeart(user.heart)
        drawScore(user.score)
        loadData();
    } catch (err) {
        console.log(err.toString(), "Error - main");
    }
}

