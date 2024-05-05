import {authData} from "./db/loginData.js";
import {suppliers} from "../db/supplier.js";
import {employees} from "../db/employee.js";
import {customers} from "../db/Customer.js";
import {items,itemImages} from "../db/item.js";
import {inventoryItems} from "../db/inventory.js";
var signInData = {
    // email: 'adminkrishan@gmail.com',
    // password: CryptoJS.SHA256('admin123').toString(CryptoJS.enc.Hex)
    email: 'krishande@gmail.com',
    password: CryptoJS.SHA256('1234').toString(CryptoJS.enc.Hex)
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
        $('#dashboard-right-top-dp').attr("src", "data:image/png;base64," + authData.employee.profilePic);    
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

        $.ajax({
            url: 'http://localhost:9090/shoeshop/api/v1/customer/getAllCustomers',
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + authData.token
            },
            success: function(response) {
                customers.length = 0;
                customers.push(...response);
                console.log('Customers Array:', customers);
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });

        $.ajax({
            url: 'http://localhost:9090/shoeshop/api/v1/employee/getAllEmployees',
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + authData.token
            },
            success: function(response) {
                employees.length = 0;
                employees.push(...response);
                console.log('Employees Array:', employees);
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });

        $.ajax({
            url: 'http://localhost:9090/shoeshop/api/v1/item/getall',
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + authData.token
            },
            success: function(response) {
                items.length = 0;
                items.push(...response);
                console.log('items Array:', items);
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });

        $.ajax({
            url: 'http://localhost:9090/shoeshop/api/v1/itemimage/getall',
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + authData.token
            },
            success: function(response) {
                itemImages.length = 0;
                itemImages.push(...response);
                console.log('itemImages Array:', itemImages);
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });

        $.ajax({
            url: 'http://localhost:9090/shoeshop/api/v1/inventory/getall',
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + authData.token
            },
            success: function(response) {
                inventoryItems.length = 0;
                inventoryItems.push(...response);
                console.log('inventoryItems Array:', inventoryItems);
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

