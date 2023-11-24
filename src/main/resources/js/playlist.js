let listAudio = [];

async function loadData() {
    try {
        const response = await fetch("http://localhost:8080/api/questions");
        const result = await response.json();
        listAudio = result;
        for (let i = 0; i < listAudio.length; i++) {
            createTrackItem(i, listAudio[i].name, listAudio[i].duration);
        }
        var indexAudio = 0;
        document.querySelector('#source-audio').src = listAudio[indexAudio].content;
        document.querySelector('.title').innerHTML = listAudio[indexAudio].name;
        var currentAudio = document.getElementById("myAudio");
        currentAudio.load();
        currentAudio.onloadedmetadata = function() {
            document.getElementsByClassName('duration')[0].innerHTML = getMinutes(currentAudio.duration);
        };
        currentAudio.addEventListener("timeupdate", onTimeUpdate);
        currentAudio.addEventListener("ended", onAudioEnded);
        var playListItems = document.querySelectorAll(".playlist-track-ctn");
        for (let i = 0; i < playListItems.length; i++) {
            playListItems[i].addEventListener("click", getClickedElement.bind(this));
        }
    } catch (err) {
        console.log(err.toString(), "Error - LoadListItemsHelper");
    }
}

function createTrackItem(index, name, duration) {
    var trackItem = document.createElement('div');
    trackItem.setAttribute("class", "playlist-track-ctn");
    trackItem.setAttribute("id", "ptc-" + index);
    trackItem.setAttribute("data-index", index);
    document.querySelector(".playlist-ctn").appendChild(trackItem);

    var playBtnItem = document.createElement('div');
    playBtnItem.setAttribute("class", "playlist-btn-play");
    playBtnItem.setAttribute("id", "pbp-" + index);
    document.querySelector("#ptc-" + index).appendChild(playBtnItem);

    var btnImg = document.createElement('i');
    btnImg.setAttribute("class", "fas fa-play");
    btnImg.setAttribute("height", "40");
    btnImg.setAttribute("width", "40");
    btnImg.setAttribute("id", "p-img-" + index);
    document.querySelector("#pbp-" + index).appendChild(btnImg);

    var trackInfoItem = document.createElement('div');
    trackInfoItem.setAttribute("class", "playlist-info-track");
    trackInfoItem.innerHTML = name;
    document.querySelector("#ptc-" + index).appendChild(trackInfoItem);

    var trackDurationItem = document.createElement('div');
    trackDurationItem.setAttribute("class", "playlist-duration");
    trackDurationItem.innerHTML = duration;
    document.querySelector("#ptc-" + index).appendChild(trackDurationItem);
}

async function loadNewTrack(index) {
    var player = document.querySelector('#source-audio');
    player.src = listAudio[index].file;
    document.querySelector('.title').innerHTML = listAudio[index].name;
    var currentAudio = document.getElementById("myAudio");
    currentAudio.load();
    await toggleAudio();
    currentAudio.removeEventListener("timeupdate", onTimeUpdate);
    currentAudio.removeEventListener("ended", onAudioEnded);
    currentAudio.addEventListener("timeupdate", onTimeUpdate);
    currentAudio.addEventListener("ended", onAudioEnded);
    updateStylePlaylist(indexAudio, index);
    indexAudio = index;
}

var indexAudio = 0;
document.querySelector('#source-audio').src = listAudio[indexAudio].content;
document.querySelector('.title').innerHTML = listAudio[indexAudio].name;

var currentAudio = document.getElementById("myAudio");
currentAudio.load();
currentAudio.onloadedmetadata = function() {
    document.getElementsByClassName('duration')[0].innerHTML = getMinutes(currentAudio.duration);
};
currentAudio.addEventListener("timeupdate", onTimeUpdate);
currentAudio.addEventListener("ended", onAudioEnded);

var playListItems = document.querySelectorAll(".playlist-track-ctn");
for (let i = 0; i < playListItems.length; i++) {
    playListItems[i].addEventListener("click", getClickedElement.bind(this));
}

function getClickedElement(event) {
    for (let i = 0; i < playListItems.length; i++) {
        if (playListItems[i] == event.target) {
            var clickedIndex = event.target.getAttribute("data-index");
            if (clickedIndex == indexAudio) {
                toggleAudio();
            } else {
                loadNewTrack(clickedIndex);
            }
        }
    }
}

function toggleAudio() {
    if (currentAudio.paused) {
        document.querySelector('#icon-play').style.display = 'none';
        document.querySelector('#icon-pause').style.display = 'block';
        document.querySelector('#ptc-' + indexAudio).classList.add("active-track");
        playToPause(indexAudio);
        currentAudio.play();
    } else {
        document.querySelector('#icon-pause').style.display = 'none';
        document.querySelector('#icon-play').style.display = 'block';
        pauseToPlay(indexAudio);
        currentAudio.pause();
    }
}

function onTimeUpdate() {
    var progressBar = document.querySelector('.progress-bar');
    var currentTime = currentAudio.currentTime;
    var duration = currentAudio.duration;
    var progress = (currentTime / duration) * 100;
    progressBar.style.width = progress + '%';
    document.querySelector('.current-time').innerHTML = getMinutes(currentTime);
}

function onAudioEnded() {
    if (indexAudio < listAudio.length - 1) {
        next();
    } else {
        currentAudio.currentTime = 0;
        document.querySelector('.current-time').innerHTML = '0:00';
        pauseToPlay(indexAudio);
        document.querySelector('#ptc-' + indexAudio).classList.remove("active-track");
        indexAudio = 0;
        document.querySelector('#source-audio').src = listAudio[indexAudio].file;
        document.querySelector('.title').innerHTML = listAudio[indexAudio].name;
    }
}

function setBarProgress(event) {
    var progressBar = document.querySelector('.progress-bar');
    var rect = progressBar.getBoundingClientRect();
    var progressBarWidth = rect.width;
    var clickX = event.clientX - rect.left;
    var currentTime = (clickX / progressBarWidth) * currentAudio.duration;
    currentAudio.currentTime = currentTime;
}

function getMinutes(time) {
    var minutes = Math.floor(time / 60);
    var seconds = Math.floor(time % 60);
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    return minutes + ":" + seconds;
}

function seek(direction) {
    var currentTime = currentAudio.currentTime;
    var seekTime;
    if (direction === "forward") {
        seekTime = currentTime + 5;
    } else if (direction === "rewind") {
        seekTime = currentTime - 5;
    }
    currentAudio.currentTime = seekTime;
}

function next() {
    if (indexAudio < listAudio.length - 1) {
        loadNewTrack(indexAudio + 1);
    }
}

function previous() {
    if (indexAudio > 0) {
        loadNewTrack(indexAudio - 1);
    }
}

function updateStylePlaylist(prevIndex, nextIndex) {
    document.querySelector('#ptc-' + prevIndex).classList.remove("active-track");
    document.querySelector('#ptc-' + nextIndex).classList.add("active-track");
}

function playToPause(index) {
    var playBtn = document.querySelector('#pbp-' + index);
    playBtn.innerHTML = '<i class="fas fa-pause" height="40" width="40" id="p-img-' + index + '"></i>';
}

function pauseToPlay(index) {
    var playBtn = document.querySelector('#pbp-' + index);
    playBtn.innerHTML = '<i class="fas fa-play" height="40" width="40" id="p-img-' + index + '"></i>';
}

function toggleMute() {
    currentAudio.muted = !currentAudio.muted;
    var muteBtn = document.querySelector('.volume-btn');
    if (currentAudio.muted) {
        muteBtn.innerHTML = '<i class="fas fa-volume-mute"></i>';
    } else {
        muteBtn.innerHTML = '<i class="fas fa-volume-up"></i>';
    }
}