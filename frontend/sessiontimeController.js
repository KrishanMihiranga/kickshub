// import { authData } from "./db/loginData.js";
// import { token } from "./db/Token.js";

function decrementTime() {
    var spanElement = $('#session-time');
    var currentTime = spanElement.text();
    var timeArray = currentTime.split(':');
    var hours = parseInt(timeArray[0]);
    var minutes = parseInt(timeArray[1]);
    var seconds = parseInt(timeArray[2]);

    // Decrement seconds
    if (hours === 0 && minutes === 0 && seconds === 0) {
        clearInterval(timer);
        console.log("Time's up!");
        ('.login').show();
        ('container').hide();
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
    spanElement.text(newTime);

    // Save the current time to localStorage
    localStorage.setItem('sessionTime', newTime);
}

// Function to pad single-digit numbers with leading zero
function padNumber(num) {
    return (num < 10 ? '0' : '') + num;
}

// Initialize the timer
function initializeTimer() {
    var savedTime = localStorage.getItem('sessionTime');
    var initialTime = savedTime ? savedTime : '08:00:00';
    $('#session-time').text(initialTime);
    timer = setInterval(decrementTime, 1000);
}

// Run the initializeTimer function when the page loads
$(document).ready(function () {
    initializeTimer();
});