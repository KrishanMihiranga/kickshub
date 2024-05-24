import {
    validateInput,
    validatePhone,
    validateName,
    validateAddress,
    validateOptionalAddress,
    validateZip,
    validateEmail,
    validateNominee,
    validateUsername,
    validatePassword,
    validateDOB,
    validateRadioGroup,
    validateBranch,
    validateJoinedDate
} from './security/FieldValidation.js';

//buttons
const registerEmployee = $('#register-employee-btn');

//data fields
var empprofilePic = $('#profile-picture-employee');
var empfirstName = $('#reg-emp-fn');
var empLastName = $('#reg-emp-ln');
var empdob;
var empgender;
var empdesignation;
var empstatus;
var empRole;
var empattachedBranch = $('.reg-employee-abranch');
var empjoinedDate = $('#reg-emp-jdate');
var empphone = $('#reg-emp-phone');
var empstreet = $('#reg-emp-street');
var empbuilding = $('#reg-emp-building');
var empcity = $('#reg-emp-city');
var empstate = $('#reg-emp-state');
var empzipcode = $('#reg-emp-zip');
var empcountry = $('.reg-emp-country');
var empemail = $('#reg-emp-email');
var empnominee = $('#reg-emp-nominee');
var empusername = $('#reg-emp-username');
var emppassword = $('#reg-emp-password');
var empconfirmpassword = $('#reg-emp-confirmp');
//dob 
var dayInput = $('#reg-mp-date');
var monthInput = $('#reg-mp-month');
var yearInput = $('#reg-mp-year');
//choose dp
file.on('change', function () {
    const choosedFile = this.files[0];
    if (choosedFile) {
        const reader = new FileReader();

        reader.onload = function () {
            img.attr('src', reader.result);
        };

        reader.readAsDataURL(choosedFile);
    }
});

//radio outputs

$('.radio-reg-emp-gender input[type="radio"]').change(function () {
    empgender = $('.radio-reg-emp-gender input[type="radio"]:checked').val();
});
$('.radio-reg-emp-designation input[type="radio"]').change(function () {
    empdesignation = $('.radio-reg-emp-designation input[type="radio"]:checked').val();
    if (empdesignation == "Manager") {
        empRole = "ADMIN";
    } else if (empdesignation == "Salesman") {
        empRole = "USER";
    } else {
        empRole = "USER";
    }
});
$('.radio-reg-emp-status input[type="radio"]').change(function () {
    empstatus = $('.radio-reg-emp-status input[type="radio"]:checked').val();
});

//register employee
registerEmployee.on('click', function () {

    //setdate
    var day = parseInt(dayInput.val());
    var month = parseInt(monthInput.val());
    var year = parseInt(yearInput.val());

    empdob = year + "-" + month + "-" + day;
    // Hash password using SHA-256
    var hashedPassword = CryptoJS.SHA256(emppassword.val()).toString(CryptoJS.enc.Hex);


    var formData = new FormData();
    formData.append('profilePic', $('#employee-file')[0].files[0]);
    formData.append('employeeCode', null);
    formData.append('name', (empfirstName.val() + " " + empLastName.val()));
    formData.append('gender', "MALE");
    formData.append('status', empstatus);
    formData.append('dob', empdob);
    formData.append('joinedDate', empjoinedDate.val());
    formData.append('branch', empattachedBranch.val());
    formData.append('addressNo', empbuilding.val());
    formData.append('addressLane', empstreet.val());
    formData.append('addressCity', empcity.val());
    formData.append('addressState', empstate.val());
    formData.append('postalCode', empzipcode.val());
    formData.append('email', empemail.val());
    formData.append('phone', empphone.val());
    formData.append('password', hashedPassword);
    formData.append('guardianOrNominatedPerson', empnominee.val());
    formData.append('role', empRole);
    formData.append('designation', empdesignation);
    formData.append('emergencyContact', empphone.val());

    // Log form data
    for (var pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
    }


    function validateForm() {
        let isValid = true;

        isValid &= validateInput($('#reg-emp-fn'), $('#fn-error'), 'First name');
        isValid &= validateInput($('#reg-emp-ln'), $('#ln-error'), 'Last name');
        isValid &= validatePhone($('#reg-emp-phone'), $('#phone-error'));
        isValid &= validateAddress($('#reg-emp-street'), $('#street-error'), 'Street address');
        isValid &= validateOptionalAddress($('#reg-emp-building'), $('#building-error'), 'Building');
        isValid &= validateName($('#reg-emp-city'), $('#city-error'), 'City');
        isValid &= validateName($('#reg-emp-state'), $('#state-error'), 'State');
        isValid &= validateZip($('#reg-emp-zip'), $('#zip-error'));
        isValid &= validateEmail($('#reg-emp-email'), $('#email-error'));
        isValid &= validateNominee($('#reg-emp-nominee'), $('#nominee-error'));
        isValid &= validateUsername($('#reg-emp-username'), $('#username-error'));
        isValid &= validatePassword($('#reg-emp-password'), $('#reg-emp-confirmp'), $('#password-match-error'));
        isValid &= validateDOB($('#reg-mp-date'), $('#reg-mp-month'), $('#reg-mp-year'), $('#dob-error'));
        isValid &= validateRadioGroup('gender', $('#gender-error'), 'Gender');
        isValid &= validateRadioGroup('designation', $('#designation-error'), 'Designation');
        isValid &= validateRadioGroup('status', $('#status-error'), 'Status');
        isValid &= validateBranch($('#branch-employee'), $('#branch-error'));
        isValid &= validateJoinedDate($('#reg-emp-jdate'), $('#joined-date-error'));

        return isValid;
    }

    if (validateForm()) {

        $.ajax({
            url: 'http://localhost:9090/shoeshop/api/v1/auth/signup',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                console.log('Success:', response);
                showSuccess('Employee Registered');
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
                showError('Failed to save employee');
            }
        });

    } else {
        showError('Please correct the errors in the form');
    }

});
