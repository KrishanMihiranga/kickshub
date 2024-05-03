import {authData} from "./db/loginData.js";
import {suppliers} from "../db/supplier.js";


var signInData = {
    email: 'adminkrishan@gmail.com',
    password: CryptoJS.SHA256('admin123').toString(CryptoJS.enc.Hex)
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

        $.ajax({
            url: 'http://localhost:9090/shoeshop/api/v1/supplier/getAllSuppliers',
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + authData.token
            },
            success: function(response) {
                suppliers.length = 0;
                suppliers.push(...response);
                console.log('Suppliers Array:', suppliers);
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });

    },
    error: function(xhr, status, error) {
        alert('Sign-in failed');
    }
});

