import {authData} from "./db/loginData.js";

var signInData = {
    email: 'pubudu@gmail.com',
    password: CryptoJS.SHA256('pubudu123').toString(CryptoJS.enc.Hex)
};

$.ajax({
    type: 'POST',
    url: 'http://localhost:9090/shoeshop/api/v1/auth/signin',
    contentType: 'application/json',
    data: JSON.stringify(signInData),
    success: function(response) {
        authData.token = response.token;
        authData.employee = response.employee;
        alert("Welcome! You are now acting as "+ response.employee.name);
    },
    error: function(xhr, status, error) {
        alert('Sign-in failed');
        // console.error('Sign-in failed:', error);
    }
});

