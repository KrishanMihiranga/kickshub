import { inventoryItems } from "../db/inventory.js";
import { cart } from "../db/sale.js";
import { dataCus } from "../db/detailCheck.js";
import { authData } from "../db/loginData.js";
import { saleData, saleitem } from "../db/transaction.js";
import { customers } from "../db/Customer.js";
import { employees } from "../db/employee.js"
var discount = 0;
var subtotal = 0;
var total = 0;
var points = 0;
var name = null;
var contact = null;
var profilePic = null;

// Function to initialize sale page
$('#sale-btn').on('click', () => {
    $('#page-user, #save-changes-employee, .charts, .recent-orders, .sales, .expenses, .income, #page, #page-customer,#page-supplier, #update-profile, #information-page, #recent-orders-refund-page, #refund-page, #add-item-page, #add-product-page, #inventory-page').hide();
    $('#sale-page').show();
    $('.sale-page-items').empty();
    $('.sale-page-right-cart').empty();
    cart.length = 0;

    $('#subtotal>span').text(subtotal);
    $('#discounts>span').text(discount);
    $('#total>span').text(total);
    $('#payment-cash-total').text(total);
    renderInventoryItems();
});

// Render inventory items on sale page
function renderInventoryItems() {

    inventoryItems.forEach(item => {
        addSaleItem(item.inventoryCode, item.item.description, item.item.supplier.supplierName, item.item.unitPriceSale, 'data:image;base64,' + item.itemImage.image, item.colors.toLowerCase(), item.item.itemCode);
    });
}

// Add sale item to sale page
function addSaleItem(invcode, name, supplier, price, imageUrl, color, code) {
    var newItem = `
        <div class="sale-page-item" data-invcode="${invcode}">
            <div class="sale-item-img">
                <img src="${imageUrl}">
            </div>
            <div class="sale-item-desc">
                <div>
                    <p class="sale-item-card-name">${name}</p>
                    <p>By <span class="sale-item-card-supplier">${supplier}</span></p> 
                    <div><div class="sale-item-clr" style="background-color: ${color};"></div> <label class="sale-item-code">${code}</label></div> 
                </div>
                <div>
                    <p class="sale-item-card-price">$${price}</p>
                </div>
            </div>
            <div class="sale-item-add-overlay">
                <div class="add-to-cart">
                    <span class="material-icons-sharp">add</span>
                </div>
            </div>
        </div>
    `;
    $('.sale-page-items').append(newItem);
}

// Add sale item to cart
$(document).on('click', '.add-to-cart', function () {
    var itemName = $(this).closest('.sale-page-item').find('.sale-item-card-name').text();
    var price = $(this).closest('.sale-page-item').find('.sale-item-card-price').text();
    var $salePageItem = $(this).closest('.sale-page-item');
    var invcode = $salePageItem.data('invcode');
    var code = $(this).closest('.sale-page-item').find('.sale-item-code').text();
    var imageUrl = $(this).closest('.sale-page-item').find('.sale-item-img img').attr('src');
    var color = $(this).closest('.sale-page-item').find('.sale-item-clr').css('background-color');
    addSaleCartItem(invcode, itemName, price, imageUrl, code, color);
});

// Add sale item to cart items array
function addSaleCartItem(invcode, name, price, imageUrl, code, color) {
    // Remove '$' sign and convert price to an integer
    price = parseInt(price.replace('$', ''), 10);

    if (cart.some(item => item.inventoryCode === invcode)) {
        alert('Item with inventory code ' + invcode + ' already exists in the cart.');
        return;
    }

    var cartItem = {
        inventoryCode: invcode,
        itemCode: code,
        price: price,
        color: color,
        qty: 1
    };
    cart.push(cartItem);

    // Calculate subtotal and total by adding the price
    var subtotalI = parseInt($('#subtotal>span').text().replace('$', ''), 10);
    var totalI = parseInt($('#total>span').text().replace('$', ''), 10);
    subtotal += price;
    total += price;
    // Update subtotal and total in the DOM
    $('#subtotal>span').text(subtotal);
    $('#total>span').text(total);
    $('#payment-cash-total').text(total);

    renderSaleCartItem(name, price, imageUrl, code, color, invcode);
}

// Render sale item in cart
function renderSaleCartItem(name, price, imageUrl, code, color, invcode) {
    var newItem = `
        <div class="sale-cart-item" data-invcode="${invcode}">
            <div>
                <div>
                    <span class="material-icons-sharp overlay-active-btn-sale">remove</span>
                </div>
            </div>
            <div class="sale-cart-item-details">
                <div class="sale-cart-item-img">
                    <img src="${imageUrl}">
                </div>
                <div class="sale-cart-item-cred">
                    <p class="sale-cart-item-name">${name}</p>
                    <p class="sale-cart-item-price">${price}</p>
                    <div class="sale-cart-item-count">
                        <div class="cart-btn-min"><span class="material-icons-sharp">remove</span></div>
                        <input type="number" value="1" id="count-or" readonly>
                        <div class="cart-btn-add"><span class="material-icons-sharp">add</span></div>
                    </div>
                </div>
            </div>
            <div class="sale-item-overlay">
                <span class="material-icons-sharp">delete</span>
                <p>Remove this item?</p>
                <div>
                    <div class="sale-item-overlay-no">No</div>
                    <div class="sale-item-overlay-yes">Yes</div>
                </div>
            </div>
        </div>
    `;
    $('.sale-page-right-cart').append(newItem);
}

$(document).on('click', '.sale-item-overlay-yes', function () {
    var $saleCartItem = $(this).closest('.sale-cart-item');
    var inventoryCode = $saleCartItem.data('invcode');
    var price = parseInt($saleCartItem.find('.sale-cart-item-price').text().replace('$', ''), 10);
    var count = $($saleCartItem).find('#count-or').val();
    // Remove the sale cart item from the DOM
    $saleCartItem.remove();

    // Update subtotal and total by subtracting the price of the removed item
    console.log(price)
    console.log(subtotal)
    console.log(total)
    subtotal -= price * count;
    total -= price * count;

    // Update subtotal and total in the DOM
    $('#subtotal>span').text(subtotal);
    $('#total>span').text(total);
    $('#payment-cash-total').text(total);

    cart.forEach((item, index) => {
        if (item.inventoryCode === inventoryCode) {
            cart.splice(index, 1);
        }
    });

    console.log(cart);
});

$('#popup-payment-cash-btn').on('click', () => {
    $('.popup-paymentmetod').removeClass("active-popup");
    $('.popup-paymentmetod-card').removeClass("active-popup");
    $('.popup-paymentmetod-cash').addClass("active-popup");
});

$('#popup-payment-card-btn').on('click', () => {
    $('.popup-paymentmetod').removeClass("active-popup");
    $('.popup-paymentmetod-card').addClass("active-popup");

});

$('#submit-order').on('click', () => {
    name = $('#sale-cus-name').val();
    contact = $('#sale-cus-tel').val();
    dataCus.name = name;
    dataCus.contact = contact;

    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/customer/check',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: JSON.stringify(dataCus),
        success: function (response) {
            if (response) {
                $('.popup-paymentmetod').addClass("active-popup");
                $('.overlay').addClass("active-overlay");
                prepareSaleData();
            } else {
                showError("Customer not found");
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            showError("Faild to Search Customer");
        }
    });
});

$(document).on('click', '.cart-btn-add', function () {
    var $saleCartItem = $(this).closest('.sale-cart-item');
    var inventoryCode = $saleCartItem.data('invcode');
    var input = $(this).closest('.sale-cart-item-details').find('input[type="number"]');
    var priceI = parseInt($saleCartItem.find('.sale-cart-item-price').text().replace('$', ''), 10);

    input.val(parseInt(input.val()) + 1);
    subtotal += priceI;
    total += priceI;

    cart.forEach((item) => {
        if (item.inventoryCode === inventoryCode) {
            item.qty = parseInt(input.val());
        }
    });

    $('#subtotal>span').text(subtotal);
    $('#total>span').text(total);
    $('#payment-cash-total').text(total);
});

$(document).on('click', '.cart-btn-min', function () {
    var $saleCartItem = $(this).closest('.sale-cart-item');
    var inventoryCode = $saleCartItem.data('invcode');
    var input = $(this).closest('.sale-cart-item-details').find('input[type="number"]');
    var priceI = parseInt($saleCartItem.find('.sale-cart-item-price').text().replace('$', ''), 10);

    var currentValue = parseInt(input.val());

    if (currentValue > 1) {
        input.val(currentValue - 1);
        subtotal -= priceI;
        total -= priceI;

        cart.forEach((item) => {
            if (item.inventoryCode === inventoryCode) {
                item.qty = parseInt(input.val());
            }
        });

        $('#subtotal>span').text(subtotal);
        $('#total>span').text(total);
        $('#payment-cash-total').text(total);
    }
});

$('#payment-cash-paid').on('input', function () {
    var cashPaid = parseInt($(this).val()) || 0;
    if (total === 800) {
        points = 1;
    } else {
        points = Math.floor(total / 800);
    }

    var balance = cashPaid - total;

    if (balance > 0) {
        $('#payment-cash-balance').text(balance);
    } else {
        $('#payment-cash-balance').text(0);
    }


});

$('#btn-order-now').on('click', () => {
    var saleData = prepareSaleData();
    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/sale/savesale',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: JSON.stringify(saleData),
        success: function (response) {
            console.log(response);
            showSuccess('Order saved successfully');

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
            console.error('Error:', error);
            showError('Faild to save order');
        }
    });
});

function prepareSaleData() {
    var saleData = {
        "orderId": 1,
        "totalPrice": total,
        "paymentMethod": 'CASH',
        "addedPoints": points,
        "employee": {
            "employeeCode": authData.employee.employeeCode
        },
        "customer": {
            "customerCode": searchCustomerByName(name)
        },
        "saleItems": []
    };
    cart.forEach(item => {
        var saleitem = {
            "saleItemId": {
                "sale": {
                    "orderId": 1
                },
                "item": {
                    "inventoryCode": item.inventoryCode
                }
            },
            "qty": item.qty,
            "unitPrice": item.price
        };

        saleData.saleItems.push(saleitem);
    });

    console.log(cart);
    console.log(saleData);

    return saleData; // Return the saleData object
}

function searchCustomerByName(name) {
    var customerCode = null;
    customers.forEach(function (customer) {
        if (customer.name === name) {
            customerCode = customer.customerCode;
        }
    });
    return customerCode;
}

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
