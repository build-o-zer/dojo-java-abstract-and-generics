let xmModule;
let channelsText = document.getElementById("channels-text");

libxm.onload = function () {

    console.log("libxm loaded");
    // sample rate, onfillbuffer, onxmdataupdate
    xmModule = new XMModule(48000, null, updateChannelsText);

    var playButton = document.getElementById("play-button");
    playButton.style.display = "none";

    console.log("xmModule created");

    playButton.addEventListener("click", function () {
        if (!xmModule.isModuleLoaded) return;

        if (xmModule.playing) {
            xmModule.pause();
            playButton.textContent = "Play";
        } else {
            xmModule.resume();
            playButton.textContent = "Pause";
        }
    });

    xmModule.load("/music/japan_1997.xm", function (err) {
        // if successful, "err" will be false and this condition won't run
        if (err) {
            console.error(err);
            return;
        }
        console.log("module loaded from URL");
        playButton.textContent = "Play";
        playButton.style.display = "inline-block";

    });



    // volume slider (0 - minimum volume, 100 - maximum volume)
    var volumeSlider = document.getElementById("volume-slider");
    volumeSlider.oninput = function () {
        xmModule.setVolume(this.value);
    };

    // drag & drop
    document.addEventListener("dragover", function (e) {
        e.stopPropagation();
        e.preventDefault();
    });

    
};

let previousRow = "";
function updateChannelsText() {
    var rowNotes = [];
    for (var channelNum = 0; channelNum < xmModule.channelsNum; channelNum++) {
        rowNotes.push(xmModule.getPlayingNoteInChannel(channelNum));
    }

    var rowText = rowNotes.join(" | ");

    // don't update the DOM if it's the same
    if (previousRow != rowText) {
        channelsText.textContent = rowText;
        previousRow = rowText;
    }
}