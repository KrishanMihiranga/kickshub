import { suppliers, supplierDataInfo } from "../db/supplier.js";
import { customers, customerDataInfo } from "../db/Customer.js";
import { employees, employeeDataInfo } from "../db/employee.js";
import { authData } from "../db/loginData.js";
var selectedEmployee;
var selectedSupplier;
var selectedCustomer;
var changeCat;

// $('#inf-cat').change(function() {
//     changeCat = $('input[name="category"]:checked').val();

//     if(changeCat == 'customer'){
//         $('#information-table-customer-wrapper').show();
//         $('#information-table-supplier-wrapper').hide();
//         $('#information-table-employee-wrapper').hide();
//       }else if((changeCat == 'supplier')){
//         $('#information-table-customer-wrapper').hide();
//         $('#information-table-supplier-wrapper').show();
//         $('#information-table-employee-wrapper').hide();
//       }else if((changeCat == 'employee')){
//         $('#information-table-customer-wrapper').hide();
//         $('#information-table-supplier-wrapper').hide();
//         $('#information-table-employee-wrapper').show();
//       }else{
//         $('#information-table-customer-wrapper').hide();
//         $('#information-table-supplier-wrapper').hide();
//         $('#information-table-employee-wrapper').hide();
//       }
// });


// $('#info-log').on('click', () => {
//   $('#save-changes-employee, .charts, .recent-orders, .sales, .expenses, .income, #page, #page-customer,#page-supplier, #update-profile, #refund-page, #add-item-page, #add-product-page, #inventory-page, #sale-page').hide();
//   $('#information-page, #information-table-employee-wrapper').show();

//   setDataToTable();
//   setDataToCustomerTable();
//   setDataToEmployeeTable();
// });



function setDataToTable() {
    var tableBody = document.querySelector("#information-table-supplier-wrapper tbody");

    tableBody.innerHTML = "";

    suppliers.forEach(function (supplier) {
        var newRow = "<tr>";
        newRow += "<td>" + supplier.code + "</td>";
        newRow += "<td>" + supplier.supplierName + "</td>";
        newRow += "<td>" + supplier.email + "</td>";
        newRow += "<td>" + supplier.contactNo1 + "</td>";
        newRow += "<td>" + supplier.addressNo + ", " + supplier.addressLane + ", " + supplier.addressCity + "</td>";
        newRow += "<td>" + supplier.category + "</td>";
        newRow += "<td>" + supplier.postalCode + "</td>";
        newRow += "</tr>";

        tableBody.innerHTML += newRow;
    });
}

function setDataToCustomerTable() {
    var tableBody = document.querySelector("#information-table-customer-wrapper tbody");

    tableBody.innerHTML = "";

    customers.forEach(function (customer) {
        var newRow = "<tr>";
        newRow += "<td>" + customer.customerCode + "</td>";
        newRow += "<td>" + customer.name + "</td>";
        newRow += "<td>" + customer.addressNo + ", " + customer.addressLane + ", " + customer.addressCity + "</td>";
        newRow += "<td>" + customer.email + "</td>";
        newRow += "<td>" + customer.gender + "</td>";
        newRow += "<td>" + customer.dob + "</td>";
        newRow += "<td>" + customer.level + "</td>";
        newRow += "</tr>";

        tableBody.innerHTML += newRow;
    });
}

function setDataToEmployeeTable() {
    var tableBody = document.querySelector("#information-table-employee-wrapper tbody");

    tableBody.innerHTML = "";

    employees.forEach(function (employee) {
        var newRow = "<tr>";
        newRow += "<td><img src='data:image/png;base64," + employee.profilePic + "'></td>";
        newRow += "<td>" + employee.employeeCode + "</td>";
        newRow += "<td>" + employee.name + "</td>";
        newRow += "<td>" + employee.phone + "</td>";
        newRow += "<td>" + employee.addressNo + ", " + employee.addressLane + ", " + employee.addressCity + "</td>";
        newRow += "<td>" + employee.designation + "</td>";
        newRow += "<td>" + employee.email + "</td>";
        newRow += "</tr>";

        tableBody.innerHTML += newRow;
    });
}


// $(document).ready(function(){
//   $('#information-table-customer-wrapper tbody tr').click(function(){
//       var customerName = $(this).find('td').eq(1).text();
//       alert('Customer Name: ' + customerName);
//   });
//   $('#information-table-supplier-wrapper tbody tr').click(function(){
//       var supplierName = $(this).find('td').eq(1).text();
//       alert('Supplier Name: ' + supplierName);
//   });
//   $('#information-table-employee-wrapper tbody tr').click(function(){
//       var employeeName = $(this).find('td').eq(2).text();
//       alert('Employee Name: ' + employeeName);
//   });
// });

$(document).ready(function () {
    $('#info-log').on('click', function () {
        // Hide all other sections
        $('#page-user, #save-changes-employee, .charts, .recent-orders, .sales, .expenses, .income, #page, #page-customer, #page-supplier, #update-profile, #refund-page, #add-item-page, #add-product-page, #inventory-page, #sale-page,#Information-update-profile, #Information-update-supplier, #Information-update-customer').hide();

        // Show the information page and the employee table by default
        $('#information-page').show();
        $('#information-table-employee-wrapper').show();
        $('#information-table-customer-wrapper, #information-table-supplier-wrapper').hide();

        // Set data to the tables
        setDataToTable();
        setDataToCustomerTable();
        setDataToEmployeeTable();
    });

    // Function to toggle the visibility of the tables based on selected radio button
    function toggleTables() {
        var selectedValue = $('input[name="category"]:checked').val();
        $('#information-table-employee-wrapper, #information-table-customer-wrapper, #information-table-supplier-wrapper').hide();
        if (selectedValue === 'employee') {
            $('#information-table-employee-wrapper').show();
        } else if (selectedValue === 'supplier') {
            $('#information-table-supplier-wrapper').show();
        } else if (selectedValue === 'customer') {
            $('#information-table-customer-wrapper').show();
        }
    }


    toggleTables();

    $('input[name="category"]').change(function () {
        toggleTables();
    });

    $(document).on('click', '#information-table-customer-wrapper tbody tr', function () {
        const role = authData.employee.role;

        if (role === 'ADMIN') {
            var customerCode = $(this).find('td').eq(0).text();
            selectedCustomer = customers.find(function (customer) {
                return customer.customerCode == customerCode;
            });
            if (selectedCustomer) {
                console.log(selectedCustomer);
                $('#information-page, #Information-save-changes-customer').hide();
                $('#Information-update-customer input, #Information-update-customer select').prop('disabled', true);
                $('#Information-update-customer').show();

                var fullName = selectedCustomer.name;
                var nameParts = fullName.split(" ");
                var lastName = nameParts[0];
                var firstName = nameParts.slice(1).join(" ");
                $('#info-reg-customer-fn').val(lastName);
                $('#info-reg-customer-ln').val(firstName);


                var date = new Date(selectedCustomer.dob);


                var day = date.getDate().toString().padStart(2, '0');
                var month = (date.getMonth() + 1).toString().padStart(2, '0');
                var year = date.getFullYear().toString();
                $('#reg-customer-day').val(day);
                $('#reg-customer-month').val(month);
                $('#reg-customer-year').val(year);

                var gender = selectedCustomer.gender;
                $('.reg-customer-info-radio-gender input[name="gender"]').filter('[value="' + gender + '"]').prop('checked', true);

                var datePart = selectedCustomer.joinedDateAsLoyalty.split('T')[0];
                $('#info-customer-reg-joined-loyal').val(datePart);

                $('#info-cus-reg-phone').val(selectedCustomer.phone);
                $('#info-cus-reg-street').val(selectedCustomer.addressLane);
                $('#info-cus-reg-building').val(selectedCustomer.addressNo);
                $('#info-cus-reg-city').val(selectedCustomer.addressCity);
                $('#info-cus-reg-state').val(selectedCustomer.addressState);
                $('#info-cus-reg-zip').val(selectedCustomer.postalCode);

                $('#info-reg-customer-email').val(selectedCustomer.email);

            } else {
                alert("Customer Not found");
            }
        } else if (role === 'USER') {
            showError("You don't have permissons to do this");
        }
    });

    $('#information-edit-customer-back').on('click', () => {
        $('#information-page').show();
        $('#Information-update-customer').hide();

    });

    $('#information-edit-supplier-back').on('click', () => {
        $('#information-page').show();
        $('#Information-update-supplier').hide();

    });

    $(document).on('click', '#information-table-supplier-wrapper tbody tr', function () {

        const role = authData.employee.role;

        if (role === 'ADMIN') {

            var supplierCode = $(this).find('td').eq(0).text();

            // Find the supplier object with the matching code
            selectedSupplier = suppliers.find(function (supplier) {
                return supplier.code === supplierCode;
            });

            if (selectedSupplier) {
                console.log(selectedSupplier);
                // alert('Supplier Code: ' + supplierCode + '\nName: ' + selectedSupplier.name);
                $('#information-page, #Information-save-changes-supplier').hide();
                $('#Information-update-supplier input, #Information-update-supplier select').prop('disabled', true);
                $('#Information-update-supplier').show();

                var fullName = selectedSupplier.supplierName;
                var nameParts = fullName.split(" ");
                var lastName = nameParts[0];
                var firstName = nameParts.slice(1).join(" ");
                $('#info-reg-sup-ln').val(firstName);
                $('#info-reg-sup-fn').val(lastName);

                var category = selectedSupplier.category;
                if (category === 'LOCAL') {
                    $('#info-sup-supplier-cat-local').prop('checked', true);
                } else if (category === 'INTERNATIONAL') {
                    $('#info-sup-supplier-cat-international').prop('checked', true);
                }

                $('#info-reg-sup-tel').val(selectedSupplier.contactNo1);
                $('#info-reg-sup-tel-2').val(selectedSupplier.contactNo2);
                $('#info-reg-sup-add-street').val(selectedSupplier.addressLane);
                $('#info-reg-sup-add-no').val(selectedSupplier.addressNo);
                $('#info-reg-sup-add-city').val(selectedSupplier.addressCity);
                $('#info-reg-sup-add-state').val(selectedSupplier.addressState);
                $('#info-reg-sup-add-zip').val(selectedSupplier.postalCode);

                var country = selectedSupplier.originCountry;
                $('#country-Supplier-up option').each(function () {
                    if ($(this).val() === country) {
                        $(this).prop('selected', true);
                    }
                });

                $('#info-reg-sup-email').val(selectedSupplier.email);
            } else {
                alert('Supplier not found');
            }
        } else if (role === 'USER') {
            showError("You don't have permissions to this");
        }

    });

    $(document).on('click', '#information-table-employee-wrapper tbody tr', function () {

        const role = authData.employee.role;
        if (role === 'ADMIN') {
            var employeeCode = $(this).find('td').eq(1).text();
            selectedEmployee = employees.find(function (employee) {
                return employee.employeeCode === employeeCode;
            });

            console.log(selectedEmployee);

            if (selectedEmployee) {
                // alert('Employee Code: ' + employeeCode + '\nName: ' + selectedEmployee.name);
                $('#information-page, #Information-save-changes-employee').hide();
                $('#Information-update-profile input, #Information-update-profile select').prop('disabled', true);

                $('#Information-update-profile').show();

                $('.Information-update-profile-user-unique-code').text(selectedEmployee.employeeCode);
                $('#Information-update-profile-top-left-profilePic').attr("src", "data:image/png;base64," + selectedEmployee.profilePic);
                $('#Information-update-profile-user-role').text(selectedEmployee.role);

                var fullName = selectedEmployee.name;
                $('#Information-up-user-name').text(fullName);
                var nameParts = fullName.split(" ");
                var lastName = nameParts[0];
                var firstName = nameParts.slice(1).join(" ");
                $('#Information-up-ln').val(firstName);
                $('#Information-up-fn').val(lastName);

                var dateString = selectedEmployee.dob;
                var dateParts = dateString.split("T")[0].split("-");
                var year = dateParts[0];
                var month = dateParts[1];
                var day = dateParts[2];

                $('#Information-up-d').val(day);
                $('#Information-up-m').val(month);
                $('#Information-up-y').val(year);


                var gender = selectedEmployee.gender;
                var status = selectedEmployee.status;
                var designation;
                if (selectedEmployee.role === 'ADMIN') {
                    designation = 'MANAGER'
                } else {
                    designation = 'SALESMAN'
                }


                if (gender == 'MALE') {
                    $('.Information-update-profile-details-personal #Information-emp-g-m').prop('checked', true);
                } else if (gender == 'FEMALE') {
                    $('.Information-update-profile-details-personal #Information-emp-g-f').prop('checked', true);
                } else {
                    $('.Information-update-profile-details-personal #Information-emp-g-o').prop('checked', true);
                }
                if (designation == 'MANAGER') {
                    $('.Information-update-profile-details-personal #Information-emp-des-m').prop('checked', true);
                } else {
                    $('.Information-update-profile-details-personal #Information-emp-des-s').prop('checked', true);
                }

                if (status == 'MARRIED') {
                    $('.Information-update-profile-details-personal #Information-emp-s-m').prop('checked', true);
                } else if (status == 'UNMARRIED') {
                    $('.Information-update-profile-details-personal #Information-emp-s-um').prop('checked', true);
                } else {
                    $('.Information-update-profile-details-personal #Information-emp-s-std').prop('checked', true);
                }

                var branch = selectedEmployee.branch;
                $('#Information-up-bn option').each(function () {
                    if ($(this).val() === branch) {
                        $(this).prop('selected', true);
                    }
                });

                var formattedJoinedDate = new Date(selectedEmployee.joinedDate).toISOString().split('T')[0];
                $('#Information-up-jd').val(formattedJoinedDate);


                $('#Information-up-tel').val(selectedEmployee.phone);
                $('#Information-up-street').val(selectedEmployee.addressLane);
                $('#Information-up-building').val(selectedEmployee.addressNo);
                $('#Information-up-city').val(selectedEmployee.addressCity);
                $('#Information-up-province').val(selectedEmployee.addressState);
                $('#Information-up-zip').val(selectedEmployee.postalCode);
                $('#Information-up-email').val(selectedEmployee.email);
                $('#Information-up-nom').val(selectedEmployee.guardianOrNominatedPerson);

                var country = selectedEmployee.country;
                $('#Information-country-ep').each(function () {
                    if ($(this).val() === country) {
                        $(this).prop('selected', true);
                    }
                });


            } else {
                alert('Employee not found');
            }
        } else if (role === 'USER') {
            showError("You don't have permissions to do this");
        }

    });
});

$('#information-edit-back').on('click', () => {
    $('#information-page').show();
    $('#Information-update-profile').hide();
});

$('#Information-save-changes-employee').on('click', () => {

    var imageSrc = $('#Information-update-profile-top-left-profilePic').attr('src');
    var base64Data = imageSrc.split(',')[1];
    var byteCharacters = atob(base64Data);
    var byteNumbers = new Array(byteCharacters.length);
    for (var i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    var byteArray = new Uint8Array(byteNumbers);
    var blob = new Blob([byteArray], { type: 'image/jpeg' });

    employeeDataInfo.profilePic = blob;
    employeeDataInfo.employeeCode = $('.Information-update-profile-user-unique-code').text();
    employeeDataInfo.name = $('#Information-up-fn').val() + " " + $('#Information-up-ln').val();
    employeeDataInfo.gender = $('.Information-radio-up-emp-gender input[type="radio"]:checked').val();
    employeeDataInfo.status = $('.Information-radio-up-emp-status input[type="radio"]:checked').val();

    var day = parseInt($('#Information-up-d').val());
    var month = parseInt($('#Information-up-m').val());
    var year = parseInt($('#Information-up-y').val());

    var empdob = year + "-" + month + "-" + day;

    employeeDataInfo.dob = empdob;
    employeeDataInfo.joinedDate = $('#Information-up-jd').val();
    employeeDataInfo.branch = $('#Information-up-bn').val();
    employeeDataInfo.addressNo = $('#Information-up-building').val();
    employeeDataInfo.addressLane = $('#Information-up-street').val();
    employeeDataInfo.addressCity = $('#Information-up-city').val();
    employeeDataInfo.addressState = $('#Information-up-province').val();
    employeeDataInfo.postalCode = $('#Information-up-zip').val();
    employeeDataInfo.email = $('#Information-up-email').val();
    employeeDataInfo.phone = $('#Information-up-tel').val();
    employeeDataInfo.password = null;
    employeeDataInfo.guardianOrNominatedPerson = $('#Information-up-nom').val();
    employeeDataInfo.role = $('#Information-update-profile-user-role').text();
    employeeDataInfo.designation = $('.Information-radio-up-emp-designation input[type="radio"]:checked').val();
    employeeDataInfo.emergencyContact = $('#Information-up-tel').val();
    console.log(employeeDataInfo);
    $('.popup-info').addClass("active-popup");
    $('.overlay').addClass("active-overlay");


});

$('#Information-save-changes-supplier').on('click', () => {
    var firstname = $('#info-reg-sup-fn').val();
    var lastname = $('#info-reg-sup-ln').val();
    var categorysel = $('.radio-wapper.radio-up-supplier-cat input[type="radio"]:checked').val();
    var phone = $('#info-reg-sup-tel').val();
    var phone2 = $('#info-reg-sup-tel-2').val();
    var addressStreet = $('#info-reg-sup-add-street').val();
    var building = $('#info-reg-sup-add-no').val();
    var city = $('#info-reg-sup-add-city').val();
    var state = $('#info-reg-sup-add-state').val();
    var postalcode = $('#info-reg-sup-add-zip').val();
    var country = $('#country-Supplier-up').val();
    var email = $('#info-reg-sup-email').val();
    supplierDataInfo.supplierName = firstname + " " + lastname;
    supplierDataInfo.category = categorysel;
    supplierDataInfo.addressNo = building;
    supplierDataInfo.addressLane = addressStreet;
    supplierDataInfo.addressCity = city;
    supplierDataInfo.addressState = state;
    supplierDataInfo.postalCode = postalcode;
    supplierDataInfo.originCountry = country;
    supplierDataInfo.email = email;
    supplierDataInfo.contactNo1 = phone;
    supplierDataInfo.contactNo2 = phone2;
    supplierDataInfo.code = selectedSupplier.code;

    console.log(supplierDataInfo);
    $('.popup-info-supplier').addClass("active-popup");
    $('.overlay').addClass("active-overlay");
});

$('#save-up-btn-supplier').on('click', () => {
    var pop_email = $('#email-popup-info-supplier').val();
    var pop_password = $('#password-popup-info-supplier').val();
    var pop_rePass = $('#password-confirm-popup-info-supplier').val();

    if (pop_password !== pop_rePass) {
        showError("Password you entered didn't match");
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
            if (response) {
                $.ajax({
                    url: 'http://localhost:9090/shoeshop/api/v1/supplier/updatesupplier',
                    type: 'POST',
                    contentType: 'application/json',
                    headers: {
                        'Authorization': 'Bearer ' + authData.token
                    },
                    data: JSON.stringify(supplierDataInfo),
                    success: function (response) {

                        $('.popup-info-supplier').removeClass("active-popup");
                        $('.overlay').removeClass("active-overlay");
                        showSuccess('Supplier Successfully Updated');
                    },
                    error: function (xhr, status, error) {
                        console.error('Error:', error);
                        showError('Failed to update Supplier');
                    }
                });

            } else {
                showError('Error While Updating Supplier');
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            showError('User Not Found');
        }
    });
});

$('#save-up-btn-info').on('click', () => {
    var pop_email = $('#email-popup-info').val();
    var pop_password = $('#password-popup-info').val();
    var pop_rePass = $('#password-confirm-popup-info').val();

    if (pop_password !== pop_rePass) {
        showError("Password you entered didn't match");
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
            if (response) {
                var formData = new FormData();
                formData.append('profilePic', employeeDataInfo.profilePic);
                formData.append('employeeCode', employeeDataInfo.employeeCode);
                formData.append('name', employeeDataInfo.name);
                formData.append('gender', employeeDataInfo.gender);
                formData.append('status', employeeDataInfo.status);
                formData.append('designation', employeeDataInfo.designation);
                formData.append('role', employeeDataInfo.role);
                formData.append('dob', employeeDataInfo.dob);
                formData.append('joinedDate', employeeDataInfo.joinedDate);
                formData.append('branch', employeeDataInfo.branch);
                formData.append('addressNo', employeeDataInfo.addressNo);
                formData.append('addressLane', employeeDataInfo.addressLane);
                formData.append('addressCity', employeeDataInfo.addressCity);
                formData.append('addressState', employeeDataInfo.addressState);
                formData.append('postalCode', employeeDataInfo.postalCode);
                formData.append('email', employeeDataInfo.email);
                formData.append('phone', employeeDataInfo.phone);
                formData.append('guardianOrNominatedPerson', employeeDataInfo.guardianOrNominatedPerson);
                formData.append('emergencyContact', employeeDataInfo.emergencyContact);
                formData.append('password', CryptoJS.SHA256(employeeDataInfo.password).toString(CryptoJS.enc.Hex));

                $.ajax({
                    url: 'http://localhost:9090/shoeshop/api/v1/employee',
                    type: 'PATCH',
                    headers: {
                        'Authorization': 'Bearer ' + authData.token
                    },
                    data: formData,
                    contentType: false,
                    processData: false,
                    success: function (response) {
                        $('.close-btn-popup').click();
                        showSuccess('Employee Successfully updated');
                    },
                    error: function (xhr, status, error) {
                        console.error('Error:', error);
                        showError('Failed to update Employee');
                    }
                });
            } else {
                showError('Error while updating Employee');
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            showError('User Not found');
        }
    });
});

$('#Information-save-changes-customer').on('click', () => {

    var firstName = $('#info-reg-customer-fn').val();
    var lastName = $('#info-reg-customer-ln').val();
    var dayOfBirth = $('#reg-customer-day').val();
    var monthOfBirth = $('#reg-customer-month').val();
    var yearOfBirth = $('#reg-customer-year').val();
    var gender = $('.reg-customer-info-radio-gender input[name="gender"]:checked').val();
    var joinedDateAsLoyalty = $('#info-customer-reg-joined-loyal').val();

    var phone = $('#info-cus-reg-phone').val();
    var streetAddress = $('#info-cus-reg-street').val();
    var building = $('#info-cus-reg-building').val();
    var city = $('#info-cus-reg-city').val();
    var state = $('#info-cus-reg-state').val();
    var zipCode = $('#info-cus-reg-zip').val();
    var country = $('#info-country-customer').val();
    var email = $('#info-reg-customer-email').val();

    customerDataInfo.name = firstName + ' ' + lastName;
    customerDataInfo.gender = gender;
    customerDataInfo.joinedDateAsLoyalty = joinedDateAsLoyalty;
    customerDataInfo.addressNo = streetAddress;
    customerDataInfo.addressLane = building;
    customerDataInfo.addressCity = city;
    customerDataInfo.addressState = state;
    customerDataInfo.postalCode = zipCode;
    customerDataInfo.email = email;
    customerDataInfo.phone = phone;
    customerDataInfo.customerCode = selectedCustomer.customerCode;
    customerDataInfo.level = null;
    customerDataInfo.totalPoints = null;
    customerDataInfo.recentPurchaseDateTime = null;
    dayOfBirth = dayOfBirth.padStart(2, '0');
    monthOfBirth = monthOfBirth.padStart(2, '0');
    var formattedDOB = yearOfBirth + '-' + monthOfBirth + '-' + dayOfBirth;
    customerDataInfo.dob = formattedDOB;

    console.log(customerDataInfo);
    $('.popup-info-customer').addClass("active-popup");
    $('.overlay').addClass("active-overlay");

});

$('#save-up-btn-customer').on('click', () => {
    var pop_email = $('#email-popup-info-customer').val();
    var pop_password = $('#password-popup-info-customer').val();
    var pop_rePass = $('#password-confirm-popup-info-customer').val();

    if (pop_password !== pop_rePass) {
        showError("Password you entered didn't match");
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
            if (response) {
                $.ajax({
                    url: 'http://localhost:9090/shoeshop/api/v1/customer/updatecustomer',
                    type: 'PATCH',
                    contentType: 'application/json',
                    headers: {
                        'Authorization': 'Bearer ' + authData.token
                    },
                    data: JSON.stringify(customerDataInfo),
                    success: function (response) {
                        showSuccess('Customer successfully saved');
                        $('.popup-info-customer').removeClass("active-popup");
                        $('.overlay').removeClass("active-overlay");
                    },
                    error: function (xhr, status, error) {
                        showError('failed to update customer');
                        console.error('Error:', error);
                    }
                });

            } else {
                showError('No Customer Found');
            }
        },
        error: function (xhr, status, error) {
            showError('User Not Found');
            console.error('Error:', error);
        }
    });
});