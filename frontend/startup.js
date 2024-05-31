import { authData } from "./db/loginData.js";
import { suppliers } from "../db/supplier.js";
import { employees } from "../db/employee.js";
import { customers } from "../db/Customer.js";
import { items, itemImages } from "../db/item.js";
import { inventoryItems } from "../db/inventory.js";
import { orders, recentOrders, refundOrders } from "../db/Orders.js";
import { top5names, count } from "./db/Dashboard.js";
import { OldToken } from "./db/Token.js";
let barChart;
const colorDark = $(':root').css('--color-label').trim();

$('.container').hide();
const signInDataArr = {
    email: null,
    password: null
};

function startTimer(duration) {
    setTimeout(function () {
        $('.container').hide();
        $('.login').show();
        showError("Your session has expired");
    }, duration);
}

$('#login-btn').on('click', () => {
    const email_login = $('#email-login').val();
    const password_login = $('#password-login').val();

    // Hash the password using CryptoJS SHA256
    const hashedPassword = CryptoJS.SHA256(password_login).toString(CryptoJS.enc.Hex);

    // Update signInDataArr with email and hashed password
    signInDataArr.email = email_login;
    signInDataArr.password = hashedPassword;

    // Call the startDashboard function with signInDataArr
    startDashboard(signInDataArr);

});

function startDashboard(signInData) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:9090/shoeshop/api/v1/auth/signin',
        contentType: 'application/json',
        data: JSON.stringify(signInData),
        success: function (response) {
            OldToken.token = authData.token;
            authData.token = response.token;
            authData.employee = response.employee;
            console.log("Old: "+ OldToken.token);
            console.log("New: "+ authData.token);
            $(document).trigger('authDataUpdated');
            showSuccess("Welcome! You are now acting as " + response.employee.name);
            // alert("Welcome! You are now acting as " + response.employee.name);
            $('#dashboard-right-top-dp').attr("src", "data:image/png;base64," + authData.employee.profilePic);

            $('.login').hide();
            $('.container').show();

            // Start the timer for 8 hours
            // startTimer(8 * 60 * 60 * 1000);
            startTimer(60 * 1000);


            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/supplier/getAllSuppliers',
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                success: function (response) {
                    suppliers.length = 0;
                    suppliers.push(...response);
                    console.log('Suppliers Array:', suppliers);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/customer/getAllCustomers',
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                success: function (response) {
                    customers.length = 0;
                    customers.push(...response);
                    console.log('Customers Array:', customers);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/employee/getAllEmployees',
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                success: function (response) {
                    employees.length = 0;
                    employees.push(...response);
                    console.log('Employees Array:', employees);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/item/getall',
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                success: function (response) {
                    items.length = 0;
                    items.push(...response);
                    console.log('items Array:', items);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/itemimage/getall',
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                success: function (response) {
                    itemImages.length = 0;
                    itemImages.push(...response);
                    console.log('itemImages Array:', itemImages);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/inventory/getall',
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                success: function (response) {
                    inventoryItems.length = 0;
                    inventoryItems.push(...response);
                    console.log('inventoryItems Array:', inventoryItems);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

            $.ajax({
                url: ' http://localhost:9090/shoeshop/api/v1/sale/getall',
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                success: function (response) {
                    orders.length = 0;
                    orders.push(...response);
                    console.log('Orders Array:', orders);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/sale/getrecent',
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                success: function (response) {
                    recentOrders.length = 0;
                    recentOrders.push(...response);
                    console.log('Recent Orders Array:', recentOrders);
                    populateRecentOrdersTable();
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/refund/getavailablerefund',
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                success: function (response) {
                    refundOrders.length = 0;
                    refundOrders.push(...response);
                    console.log('Refund Orders Array:', refundOrders);
                    populateRefundOrdersTable();
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });


            var today = new Date().toLocaleString('en-US', {
                timeZone: 'Asia/Colombo'
            }).split(',')[0].split('/');

            var month = ('0' + today[0]).slice(-2);
            var day = ('0' + today[1]).slice(-2);
            var year = today[2];
            today = year + '-' + month + '-' + day;
            $('#selectedDate').val(today);
            console.log("Date _ " + today);
            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/sale/gettotalsales',
                type: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                data: JSON.stringify(today),
                success: function (response) {
                    var formattedResponse = parseFloat(response).toLocaleString('en-US', {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2
                    });
                    $('#total-sales-amount').text(formattedResponse);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                    // Handle errors here
                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/sale/gettotalexpenses',
                type: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                data: JSON.stringify(today),
                success: function (response) {
                    var formattedResponse = parseFloat(response).toLocaleString('en-US', {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2
                    });
                    $('#total-sales-expenses').text(formattedResponse);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);

                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/sale/gettotalincome',
                type: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                data: JSON.stringify(today),
                success: function (response) {
                    var formattedResponse = parseFloat(response).toLocaleString('en-US', {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2
                    });
                    $('#total-sales-income').text(formattedResponse);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);

                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/sale/gettopproducts',
                type: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                data: JSON.stringify(today),
                success: function (response) {
                    response.forEach(res => {
                        let name = res.name;
                        let itemcount = res.count;
                        top5names.push(name);
                        count.push(itemcount);
                    });
                    barChart = new ApexCharts(
                        document.querySelector('#bar-chart'),
                        barChartOptions
                    );
                    barChart.render();

                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/sale/gettopselling',
                type: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                data: JSON.stringify(today),
                success: function (response) {
                    var formattedPrice = parseFloat(response.price).toLocaleString('en-US', {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2
                    });
                    $('#popular-product-card-right-name').text(response.name);
                    $('#popular-product-card-right-price').text(formattedPrice);
                    $('#popular-product-card-right-image').attr('src', 'data:image/png;base64,' + response.image);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/sale/gettotalpaymentmethods',
                type: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                data: JSON.stringify(today),
                success: function (response) {

                    $('#item-cash-count').text(response.cash);
                    $('#item-card-count').text(response.card);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });
            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/customer/getcustomercount',
                type: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                data: JSON.stringify(today),
                success: function (response) {

                    $('#item-customer-count').text(response);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/alert/getlatestalerts',
                type: 'GET',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                success: function (response) {
                    console.log(response);
                    updateAlertBox(response);
                },
                error: function (xhr, status, error) {
                    console.error('Error:', error);
                }
            });

        },
        error: function (xhr, status, error) {
            showError('Sign-in failed');
        }
    });

}


function populateRecentOrdersTable() {

    $('.recent-orders tbody').empty();

    recentOrders.forEach(function (order) {

        $.ajax({
            url: 'http://localhost:9090/shoeshop/api/v1/sale/getitemname',
            type: 'POST',
            contentType: 'text/plain',
            headers: {
                'Authorization': 'Bearer ' + authData.token
            },
            data: order.orderId,
            success: function (response) {
                var row = $('<tr>');

                row.append($('<td>').text(response));
                row.append($('<td>').text(order.orderId));
                row.append($('<td>').text(order.paymentMethod));
                row.append($('<td class="success">').text("Paid"));
                row.append($('<td class="primary">').text("Details"));

                $('.recent-orders tbody').append(row);

            },
            error: function (xhr, status, error) {
                console.error('Error:', error);

            }
        });



    });
}

function populateRefundOrdersTable() {
    $('#refund-table-orders-wrapper tbody').empty();

    refundOrders.forEach(function (order) {
        $.ajax({
            url: 'http://localhost:9090/shoeshop/api/v1/sale/getitemname',
            type: 'POST',
            contentType: 'text/plain',
            headers: {
                'Authorization': 'Bearer ' + authData.token
            },
            data: order.orderId,
            success: function (response) {
                var row = $('<tr>');
                row.data('order', order);
                row.data('response', response);

                row.append($('<td>').text(response));
                row.append($('<td>').text(order.orderId));
                row.append($('<td>').text(order.paymentMethod));
                row.append($('<td class="primary">').text("Details"));

                $('#refund-table-orders-wrapper tbody').append(row);
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
    });
}

//bar chart
const barChartOptions = {
    series: [
        {
            data: count,
            name: 'Products',
        },
    ],
    chart: {
        type: 'bar',
        background: 'transparent',
        height: 350,
        toolbar: {
            show: false,
        },
    },
    colors: ['#2962ff', '#ff7782', '#41f1b6', '#ffbb55', '#7380ec'],
    plotOptions: {
        bar: {
            distributed: true,
            borderRadius: 4,
            horizontal: false,
            columnWidth: '40%',
        },
    },
    dataLabels: {
        enabled: false,
    },
    fill: {
        opacity: 1,
    },
    grid: {
        borderColor: '#55596e',
        yaxis: {
            lines: {
                show: true,
            },
        },
        xaxis: {
            lines: {
                show: true,
            },
        },
    },
    legend: {
        labels: {
            colors: colorDark,
        },
        show: true,
        position: 'top',
    },
    stroke: {
        colors: ['transparent'],
        show: true,
        width: 2,
    },
    tooltip: {
        shared: true,
        intersect: false,
        theme: 'dark',
    },
    xaxis: {
        categories: top5names,
        title: {
            style: {
                color: colorDark,
            },
        },
        axisBorder: {
            show: true,
            color: '#55596e',
        },
        axisTicks: {
            show: true,
            color: '#55596e',
        },
        labels: {
            style: {
                colors: colorDark,
            },
        },
    },
    yaxis: {
        title: {
            text: 'Count',
            style: {
                color: colorDark,
            },
        },
        axisBorder: {
            color: '#55596e',
            show: true,
        },
        axisTicks: {
            color: '#55596e',
            show: true,
        },
        labels: {
            style: {
                colors: colorDark,
            },
        },
    },
};


$('#selectedDate').change(function () {

    var today = new Date().toLocaleString('en-US', {
        timeZone: 'Asia/Colombo'
    }).split(',')[0].split('/');

    var month = ('0' + today[0]).slice(-2);
    var day = ('0' + today[1]).slice(-2);
    var year = today[2];
    today = year + '-' + month + '-' + day;

    var selectedDate = $('#selectedDate').val();
    if (today == selectedDate) {
        $('.timeDuraion').text("Last 24 Hours");
    } else {
        $('.timeDuraion').text(selectedDate);
    }

    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/sale/gettotalsales',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: JSON.stringify(selectedDate),
        success: function (response) {
            var formattedResponse = parseFloat(response).toLocaleString('en-US', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            });
            $('#total-sales-amount').text(formattedResponse);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            // Handle errors here
        }
    });

    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/sale/gettotalexpenses',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: JSON.stringify(selectedDate),
        success: function (response) {
            var formattedResponse = parseFloat(response).toLocaleString('en-US', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            });
            $('#total-sales-expenses').text(formattedResponse);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);

        }
    });

    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/sale/gettotalincome',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: JSON.stringify(selectedDate),
        success: function (response) {
            var formattedResponse = parseFloat(response).toLocaleString('en-US', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            });
            $('#total-sales-income').text(formattedResponse);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);

        }
    });

    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/sale/gettopproducts',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: JSON.stringify(selectedDate),
        success: function (response) {
            top5names.length = 0;
            count.length = 0;
            response.forEach(res => {
                let name = res.name;
                let itemcount = res.count;

                top5names.push(name);
                count.push(itemcount);
            });

            // Update the series data of the chart
            barChart.updateSeries([{
                data: count,
                name: 'Products',
            }]);

            // Update x-axis categories with animations
            barChart.updateOptions({
                xaxis: {
                    categories: top5names,
                }
            }, animate = true); // Adding animations

        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });

    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/sale/gettopselling',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: JSON.stringify(selectedDate),
        success: function (response) {
            var formattedPrice = parseFloat(response.price).toLocaleString('en-US', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            });
            $('#popular-product-card-right-name').text(response.name);
            $('#popular-product-card-right-price').text(formattedPrice);
            $('#popular-product-card-right-image').attr('src', 'data:image/png;base64,' + response.image);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });

    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/sale/gettotalpaymentmethods',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: JSON.stringify(selectedDate),
        success: function (response) {

            $('#item-cash-count').text(response.cash);
            $('#item-card-count').text(response.card);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/customer/getcustomercount',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: JSON.stringify(selectedDate),
        success: function (response) {

            $('#item-customer-count').text(response);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });

});

function updateAlertBox(response) {
    var updatesContainer = document.getElementById('updates-container');
    updatesContainer.innerHTML = '';

    response.forEach(alert => {
        var profilePic = '/assets/images/default_user.jpg';
        var name = alert.name;
        var message = alert.message;

        employees.forEach(employee => {
            if (employee.employeeCode === alert.empId) {
                profilePic = employee.profilePic ? 'data:image;base64,' + employee.profilePic : profilePic;
            }
        });

        var newUpdateHtml = '<div class="update" data-date="' + alert.date + '">';
        newUpdateHtml += '<div class="profile-photo">';
        newUpdateHtml += '<img src="' + profilePic + '" alt="">';
        newUpdateHtml += '</div>';
        newUpdateHtml += '<div class="message">';
        newUpdateHtml += '<p><b>' + name + '</b> ' + message + '</p>';
        newUpdateHtml += '<small class="text-muted time-ago">' + timeAgo(new Date(alert.date)) + '</small>';
        newUpdateHtml += '</div>';
        newUpdateHtml += '</div>';

        updatesContainer.innerHTML += newUpdateHtml;
    });

    // Call refreshTimes initially and then set interval for periodic updates
    refreshTimes();
    setInterval(refreshTimes, 60000); // Update every minute
}

function timeAgo(date) {
    const now = new Date();
    const secondsAgo = Math.floor((now - date) / 1000);

    if (secondsAgo < 60) {
        return 'Just now';
    } else if (secondsAgo < 3600) {
        const minutesAgo = Math.floor(secondsAgo / 60);
        return `${minutesAgo} minute${minutesAgo > 1 ? 's' : ''} ago`;
    } else if (secondsAgo < 86400) {
        const hoursAgo = Math.floor(secondsAgo / 3600);
        return `${hoursAgo} hour${hoursAgo > 1 ? 's' : ''} ago`;
    } else {
        const daysAgo = Math.floor(secondsAgo / 86400);
        return `${daysAgo} day${daysAgo > 1 ? 's' : ''} ago`;
    }
}

function refreshTimes() {
    const updates = document.querySelectorAll('.update');
    updates.forEach(update => {
        const date = new Date(update.getAttribute('data-date'));
        const timeAgoText = timeAgo(date);
        update.querySelector('.time-ago').textContent = timeAgoText;
    });
}



