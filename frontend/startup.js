import {authData} from "./db/loginData.js";

var signInData = {
    email: 'krishan@gmail.com',
    password: 'password123'
};

$.ajax({
    type: 'POST',
    url: 'http://localhost:9090/shoeshop/api/v1/auth/signin',
    contentType: 'application/json',
    data: JSON.stringify(signInData),
    success: function(response) {
        authData.token = response.token;
        authData.employee = response.employee;
    },
    error: function(xhr, status, error) {
        alert('Sign-in failed');
        // console.error('Sign-in failed:', error);
    }
});

