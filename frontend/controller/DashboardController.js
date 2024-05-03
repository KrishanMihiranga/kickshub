import {authData} from "../db/loginData.js";
import {dataEmp} from "../db/detailCheck.js";
import {employeeData} from "../db/employee.js";
var pop_email = null;
var pop_password = null;
var pop_rePass = null;

$(document).ready(function() {
    
    $('#update-profile input, #update-profile select').prop('disabled', true);
    $('#save-changes-employee').hide();

    $('#ep-no-btn').click(function() {
        $('#save-changes-employee').hide();
    });

    $('#ep-yes-btn').click(function() {
        var isDisabled = $('#update-profile input, #update-profile select').prop('disabled');
        $('#update-profile input, #update-profile select').prop('disabled', !isDisabled);
        $('#save-changes-employee').show();

        $('.popup-edit-confirm').removeClass("active-popup");
        $('.overlay').removeClass("active-overlay"); 
    });
});


$('#dashboard-photo').on('click', () => {
    //route
    $('#save-changes-employee, .charts, .recent-orders, .sales, .expenses, .income, #page, #page-customer,#page-supplier, #information-page, #refund-page, #add-item-page, #add-product-page, #inventory-page, #sale-page').hide();
    $('#update-profile').show();

    $('#update-profile input, #update-profile select').prop('disabled', true);
    console.log(authData);

    $('.update-profile-user-unique-code').text(authData.employee.employeeCode);
    $('#update-profile-top-left-profilePic').attr("src", "data:image/png;base64," + authData.employee.profilePic);
    $('#update-profile-user-role').text(authData.employee.role);
    
    var fullName = authData.employee.name;
    $('#up-user-name').text(fullName);
    var nameParts = fullName.split(" ");
    var lastName = nameParts[0];
    var firstName = nameParts.slice(1).join(" ");
    $('#up-ln').val(firstName);
    $('#up-fn').val(lastName);

    var dateString = authData.employee.dob;
    var dateParts = dateString.split("T")[0].split("-");
    var year = dateParts[0];
    var month = dateParts[1];
    var day = dateParts[2]; 

    $('#up-d').val(day);
    $('#up-m').val(month);
    $('#up-y').val(year);


    var gender = authData.employee.gender;
    var status = authData.employee.status;
    var designation = authData.employee.designation;

    if (gender == 'MALE') {
        $('.update-profile-details-personal #emp-g-m').prop('checked', true);
    } else if (gender == 'FEMALE') {
        $('.update-profile-details-personal #emp-g-f').prop('checked', true);
    } else {
        $('.update-profile-details-personal #emp-g-o').prop('checked', true);
    }
    
    if (designation == 'MANAGER') {
        $('.update-profile-details-personal #emp-des-m').prop('checked', true);
    } else {
        $('.update-profile-details-personal #emp-des-s').prop('checked', true);
    }
    
    if (status == 'MARRIED') {
        $('.update-profile-details-personal #emp-s-m').prop('checked', true);
    } else if (status == 'UNMARRIED') {
        $('.update-profile-details-personal #emp-s-um').prop('checked', true);
    } else {
        $('.update-profile-details-personal #emp-s-std').prop('checked', true);
    }

    var branch = authData.employee.branch;
    $('#up-bn option').each(function() {
        if ($(this).val() === branch) {
            $(this).prop('selected', true);
        }
    });

    var formattedJoinedDate = new Date(authData.employee.joinedDate).toISOString().split('T')[0];
    $('#up-jd').val(formattedJoinedDate);


    $('#up-tel').val(authData.employee.phone);
    $('#up-street').val(authData.employee.addressLane);
    $('#up-building').val(authData.employee.addressNo);
    $('#up-city').val(authData.employee.addressCity);
    $('#up-province').val(authData.employee.addressState);
    $('#up-zip').val(authData.employee.postalCode);
    $('#up-email').val(authData.employee.email);
    $('#up-nom').val(authData.employee.guardianOrNominatedPerson);

    var country = authData.employee.country;
    $('#country-ep').each(function() {
        if ($(this).val() === country) {
            $(this).prop('selected', true);
        }
    });

});


//popup
$('#save-changes-employee').on('click', () => {
    $('.popup').addClass("active-popup");
    $('.overlay').addClass("active-overlay");


    var imageSrc = $('#update-profile-top-left-profilePic').attr('src');
    // todo: fix profile picture bug 
    employeeData.profilePic = imageSrc.split(',')[1];
    employeeData.employeeCode = $('.update-profile-user-unique-code').text();
    employeeData.name = $('#up-fn').val()+" "+$('#up-ln').val();
    employeeData.gender = $('.radio-up-emp-gender input[type="radio"]:checked').val();
    employeeData.status = $('.radio-up-emp-status input[type="radio"]:checked').val();

    var day = parseInt($('#up-d').val());
    var month = parseInt($('#up-m').val());
    var year = parseInt($('#up-y').val());

    var empdob = year+"-"+month+"-"+day;

    employeeData.dob = empdob;
    employeeData.joinedDate= $('#up-jd').val();
    employeeData.branch= $('#up-bn').val();
    employeeData.addressNo= $('#up-building').val();
    employeeData.addressLane= $('#up-street').val();
    employeeData.addressCity= $('#up-city').val();
    employeeData.addressState= $('#up-province').val();
    employeeData.postalCode= $('#up-zip').val();
    employeeData.email= $('#up-email').val();
    employeeData.phone= $('#up-tel').val();
    employeeData.password= null;
    employeeData.guardianOrNominatedPerson= $('#up-nom').val();
    employeeData.role= $('#update-profile-user-role').text();
    employeeData.designation= $('.radio-up-emp-designation input[type="radio"]:checked').val();
    employeeData.emergencyContact= $('#up-tel').val();
    console.log(employeeData);
  });

$('#toggle-password').change(function () {
    var passwordInput = $('#password-popup');
    var confirmPasswordInput = $('#password-confirm-popup');
    if ($(this).is(':checked')) {
        passwordInput.attr('type', 'text');
        confirmPasswordInput.attr('type', 'text');
    } else {
        passwordInput.attr('type', 'password');
        confirmPasswordInput.attr('type', 'password');
    }
});



  $('#save-up-btn').on('click', () => {
    var pop_email = $('#email-popup').val();
    var pop_password = $('#password-popup').val();
    var pop_rePass = $('#password-confirm-popup').val();

    if (pop_password !== pop_rePass) {
        alert("Password Mismatch");
        return;
    }

    var dataEmp = {
        email: pop_email,
        password: CryptoJS.SHA256(pop_password).toString(CryptoJS.enc.Hex)
    };

    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/employee/check',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: JSON.stringify(dataEmp),
        success: function (response) {
            if(response) {
                var formData = new FormData();
                formData.append('profilePic', employeeData.profilePic);
                formData.append('employeeCode', employeeData.employeeCode);
                formData.append('name', employeeData.name);
                formData.append('gender', employeeData.gender);
                formData.append('status', employeeData.status);
                formData.append('designation', employeeData.designation);
                formData.append('role', employeeData.role);
                formData.append('dob', employeeData.dob);
                formData.append('joinedDate', employeeData.joinedDate);
                formData.append('branch', employeeData.branch);
                formData.append('addressNo', employeeData.addressNo);
                formData.append('addressLane', employeeData.addressLane);
                formData.append('addressCity', employeeData.addressCity);
                formData.append('addressState', employeeData.addressState);
                formData.append('postalCode', employeeData.postalCode);
                formData.append('email', employeeData.email);
                formData.append('phone', employeeData.phone);
                formData.append('guardianOrNominatedPerson', employeeData.guardianOrNominatedPerson);
                formData.append('emergencyContact', employeeData.emergencyContact);
                formData.append('password', CryptoJS.SHA256(employeeData.password).toString(CryptoJS.enc.Hex));

                $.ajax({
                    url: 'http://localhost:9090/shoeshop/api/v1/employee',
                    type: 'PATCH',
                    headers: {
                        'Authorization': 'Bearer ' + authData.token
                    },
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function(response) {
                        $('.close-btn-popup').click();
                        alert("Update Successful");
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', error);
                    }
                });
            } else {
                alert("No User Found");
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
});




// $('#save-up-btn').on('click', () => {
//     pop_email = $('#email-popup').val();
//     pop_password = $('#password-popup').val();
//     pop_rePass = $('#password-confirm-popup').val();

//     if (pop_password !== pop_rePass) {
//         alert("Password Mismatch");
//         return;
//     }

//     dataEmp.email = pop_email;
//     dataEmp.password = pop_password;

//     $.ajax({
//         url: 'http://localhost:9090/shoeshop/api/v1/employee/check',
//         type: 'POST',
//         contentType: 'application/json',
//         headers: {
//             'Authorization': 'Bearer ' + authData.token
//         },
//         data: JSON.stringify(dataEmp),
//         success: function (response) {
//             if(response == true){
//                 var formData = new FormData();
//                 formData.append('profilePic', employeeData.profilePic);
//                 formData.append('employeeCode', employeeData.employeeCode);
//                 formData.append('name', employeeData.name);
//                 formData.append('gender', employeeData.gender);
//                 formData.append('status', employeeData.status);
//                 formData.append('designation', employeeData.designation);
//                 formData.append('role', employeeData.role);
//                 formData.append('dob',employeeData.dob);
//                 formData.append('joinedDate', employeeData.joinedDate);
//                 formData.append('branch', employeeData.branch);
//                 formData.append('addressNo', employeeData.addressNo);
//                 formData.append('addressLane',employeeData.addressLane);
//                 formData.append('addressCity', employeeData.addressCity);
//                 formData.append('addressState', employeeData.addressState);
//                 formData.append('postalCode', employeeData.postalCode);
//                 formData.append('email',employeeData.email);
//                 formData.append('phone', employeeData.phone);
//                 formData.append('guardianOrNominatedPerson', employeeData.guardianOrNominatedPerson);
//                 formData.append('emergencyContact', employeeData.emergencyContact);
//                 formData.append('password', employeeData.password);


//                 $.ajax({
//                     url: 'http://localhost:9090/shoeshop/api/v1/employee',
//                     type: 'PATCH',
//                     headers: {
//                       'Authorization': 'Bearer ' + authData.token
//                     },
//                     data: formData,
//                     contentType: 'application/json',
//                     success: function(response) {
//                       console.log('Success:', response);
//                     },
//                     error: function(xhr, status, error) {
//                       console.error('Error:', error);
//                     }
//                   });
            
//             }else{
//                 alert("No User Found");
//             }
//         },
//         error: function (xhr, status, error) {
//             console.error('Error:', error);
//         }
//     });

    
// });


