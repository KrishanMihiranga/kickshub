function decrementTime() {
    var spanElement = document.getElementById('session-time');
    var currentTime = spanElement.innerText;
    var timeArray = currentTime.split(':');
    var hours = parseInt(timeArray[0]);
    var minutes = parseInt(timeArray[1]);
    var seconds = parseInt(timeArray[2]);

    // Decrement seconds
    if (hours === 0 && minutes === 0 && seconds === 0) {
        clearInterval(timer);
        console.log("Time's up!");
    } else {
        if (seconds > 0) {
            seconds--;
        } else {
            seconds = 59;
            // Decrement minutes
            if (minutes > 0) {
                minutes--;
            } else {
                minutes = 59;
                // Decrement hours
                if (hours > 0) {
                    hours--;
                }
            }
        }
    }

    // Update the time displayed
    var newTime = padNumber(hours) + ':' + padNumber(minutes) + ':' + padNumber(seconds);
    spanElement.innerText = newTime;
}

// Function to pad single-digit numbers with leading zero
function padNumber(num) {
    return (num < 10 ? '0' : '') + num;
}

// Run decrementTime function every second
var timer = setInterval(decrementTime, 1000);
