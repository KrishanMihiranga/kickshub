
import {authData} from "../db/loginData.js";
import {
    validateInput,
    validatePhone,
    validateName,
    validateAddress,
    validateOptionalAddress,
    validateZip,
    validateEmail,
    validateDOB,
    validateRadioGroup,
    validateJoinedDate
} from './security/FieldValidation.js';


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


    function validateForm(){
        let isValid = true;

        isValid = validateInput($('#reg-cus-fn'), $('#fn-error'), 'First Name') && isValid;
        isValid = validateInput($('#reg-cus-ln'), $('#ln-error'), 'Last Name') && isValid;
        isValid = validateDOB($('#reg-cus-day'), $('#reg-cus-month'), $('#reg-cus-year'), $('#dob-error')) && isValid;
        isValid = validateRadioGroup('gender', $('#gender-error'), 'Gender') && isValid;
        isValid = validateJoinedDate($('#cus-reg-joined-loyal'), $('#joined-date-error')) && isValid;
        isValid = validatePhone($('#cus-reg-phone'), $('#phone-error')) && isValid;
        isValid = validateAddress($('#cus-reg-street'), $('#street-error'), 'Street Address') && isValid;
        isValid = validateOptionalAddress($('#cus-reg-building'), $('#building-error'), 'Suite, Building') && isValid;
        isValid = validateName($('#cus-reg-city'), $('#city-error'), 'City') && isValid;
        isValid = validateName($('#cus-reg-state'), $('#state-error'), 'State') && isValid;
        isValid = validateZip($('#cus-reg-zip'), $('#zip-error')) && isValid;
        isValid = validateEmail($('#reg-cus-email'), $('#email-error')) && isValid;

        return isValid;
    }
    
    if(validateForm()){
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
                showSuccess("Customer saved successfully");
            },
            error: function(xhr, status, error) {
                console.error("Failed to save customer:", error);
                showError('Failed to save customer');
            }
        });
    }else{
        showError('Please correct the errors in the form');
    }
    
});



