
import {authData} from "../db/loginData.js";

var cusfirstname = $('#reg-cus-fn');
var cuslastname = $('#reg-cus-ln');
var cusgender;
var cusjoinedDateAsLoyalty= $('#cus-reg-joined-loyal');
var custotalPoints = 0;
var cusdob;
var cusaddressNo = $('#cus-reg-building');
var cusaddressLane = $('#cus-reg-street');
var cusaddressCity = $('#cus-reg-city');
var cusaddressState = $('#cus-reg-state');
var cuspostalCode = $('#cus-reg-zip');
var dayInput = $('#reg-cus-day');
var monthInput = $('#reg-cus-month');
var yearInput = $('#reg-cus-year');
var cusemail = $('#reg-cus-email');
var cusphone = $('#cus-reg-phone');
var cusrecentPurchaseDateTime;

$('.reg-customer-radio-gender input[type="radio"]').change(function() {
    cusgender = $('.reg-customer-radio-gender input[type="radio"]:checked').val();
});

$('#reg-cus-submit').on('click', function() {
    // Get current date and time
    var currentDate = new Date();

    var day = parseInt(dayInput.val());
    var month = parseInt(monthInput.val());
    var year = parseInt(yearInput.val());

    // Format date of birth
    cusdob = year + "-" + month + "-" + day;

    // Format the date to ISO 8601 format with milliseconds
    var isoDate = currentDate.toISOString();

    // Construct customer data object
    var customerData = {
        name: cusfirstname.val() + " " + cuslastname.val(),
        gender: cusgender,
        joinedDateAsLoyalty: cusjoinedDateAsLoyalty.val(),
        totalPoints: custotalPoints,
        dob: cusdob.toString(),
        addressNo: cusaddressNo.val(),
        addressLane: cusaddressLane.val(),
        addressCity: cusaddressCity.val(),
        addressState: cusaddressState.val(),
        postalCode: cuspostalCode.val(),
        email: cusemail.val(),
        phone: cusphone.val(),
        recentPurchaseDateTime: isoDate // Use the ISO 8601 formatted date
    };

    // Send AJAX request
    $.ajax({
        type: "POST",
        url: "http://localhost:9090/shoeshop/api/v1/customer",
        contentType: "application/json",
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: JSON.stringify(customerData),
        success: function(response) {
            console.log("Customer saved successfully:", response);
            alert('success');
        },
        error: function(xhr, status, error) {
            console.error("Failed to save customer:", error);
        }
    });
});



