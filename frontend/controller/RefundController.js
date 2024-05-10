export{authData} from "../db/loginData.js";
import{refundOrders} from "../db/Orders.js";
import { authData } from "./RefundController.js";
import {inventoryItems} from "../db/inventory.js";

var order;
var itemCode;
var orderId;
var quantity;
var description;

$('#refund-btn').on('click', () => {
    $('#save-changes-employee, .charts, .recent-orders, .sales, .expenses, .income, #page, #page-customer,#page-supplier, #update-profile, #information-page, #recent-orders-refund-page, #add-item-page, #add-product-page, #inventory-page, #sale-page').hide();
    $('#refund-page').show();
    console.log('clicked');
});
// Event delegation for dynamically added elements
$('#refund-table-orders-wrapper').on('click', 'tbody tr', function() {

    console.log("Token : ",authData.token);
    order = $(this).data('order');

    var response = $(this).data('response');
    var items = response.split(',');

    
    $('#refund-page-right-cusName>span').text(order.customer.name);


    $('#recent-orders-refund-page').show();
    $('#recent-orders-refund').hide();

    setRefundItems(order);
    
});

function createRefundPageDetails(item, index, order) { 
    var refundPageDetails = `
        <div class="recent-orders-refund-page-details" id="recent-orders-refund-page-details-${index}">
            <div class="refund-page-left">
                 <img src="data:image/png;base64,${item.image}" alt="image-refund">
            </div>
            <div class="refund-page-right">
                <p class="refund-page-right-cat">${item.itemCategory}</p>
                <h1 class="refund-page-right-item-name">${item.itemName}</h1>
                <p>The preferred choice of a vast range of acclaimed DJs. Punchy, bass-focused sound and high isolation. Sturdy headband and on-ear cushions suitable for live performance</p>
                <hr>
                <div class="refund-page-right-cs">
                    <div>
                        <p>Color</p>
                        <div class="refund-page-color"></div>
                    </div>   
                    <div>
                        <p>Size</p>
                        <div class="radio-wapper radio-ref">
                            <input type="radio" id="ref-g-m-${index}"  class="radioG" checked>
                            <label class="labelE label-1" for="ref-g-m-${index}">${item.size}</label>
                        </div>
                    </div>
                    <div>
                        <p>Qty</p>
                        <div class="radio-wapper radio-ref">
                            <input type="radio" id="ref-g-s-${index}"  class="radioref" checked>
                            <label class="labelE label-2" for="ref-g-s-${index}">${item.qty}</label>
                        </div>
                    </div>
                </div>
                <small>Refunds can't be undone</small>
                <h2>${item.price}</h2>
                <hr>
                <p>Customer Details</p>
                <div class="refund-page-right-cusDetails">
                    <div class="refund-page-right-cusName">
                        <span>${order.customer.name}</span>
                    </div>
                    <div class="refund-page-right-date">
                        <span>${item.date.split('T')[0]}</span>
                    </div>
                </div>
                <textarea class="refund-desc"></textarea><br>
                <button class="refund-page-refund-btn" data-qty="${item.qty}" data-order="${order.orderId}" data-item-code="${item.itemCode}">Open Refund</button>
            </div>
        </div>
    `;
    return refundPageDetails;
}

function setRefundItems(order){
    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/refund/getrefunddetails',
        type: 'POST',
        contentType: 'text/plain',
        headers: {
            'Authorization': 'Bearer ' + authData.token
        },
        data: order.orderId,
        success: function(response) {
            console.log(response);
            response.forEach((item, index) => {
                var refundPageDetailsHTML = createRefundPageDetails(item, index,order);
                $('#recent-orders-refund-page').append(refundPageDetailsHTML);
                $('.refund-page-color').eq(index).css('background-color', item.color);
            });
        },
        error: function(xhr, status, error) {
            // Handle error
            console.error('Error:', error);
        }
    });
}

$(document).on('click', '.refund-page-refund-btn', function() {
    itemCode = $(this).data('item-code');
    orderId = $(this).data('order');
    quantity = $(this).data('qty');

    description = $(this).closest('.refund-page-right').find('.refund-desc').val();

    console.log("inventory Code : ", itemCode);
    console.log("Order Id : ", orderId);
    console.log("Employeee Code : ", authData.employee.employeeCode);
    console.log("Description : ", description);
    console.log("Quantity : ", quantity);



    $('.popup-refund').addClass("active-popup");
    $('.overlay').addClass("active-overlay"); 
});

$('#save-up-btn-refund').on('click', () => {
   var email = $('#email-popup-refund').val();
   var password = $('#password-popup-refund').val();
   var reenter= $('#password-confirm-popup-refund').val();

   if (password !== reenter) {
    alert("Password Mismatch");
    return;
   }
var dataEmp = {
    email: email,
    password: CryptoJS.SHA256(password).toString(CryptoJS.enc.Hex)
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
            $.ajax({
                url: 'http://localhost:9090/shoeshop/api/v1/refund/saverefund',
                type: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': 'Bearer ' + authData.token
                },
                data: JSON.stringify({
                    refundId: null,
                    qty: quantity,
                    description: description,
                    refundDate: new Date(),
                    status:'Refunded',
                    employee: {
                        employeeCode: authData.employee.employeeCode
                    },
                    saleItem: {
                        saleItemId: {
                            sale: {
                                orderId: orderId
                            },
                            item: {
                                inventoryCode: itemCode
                            }
                        }
                    }
                }),
                success: function(response) {
                    console.log('Success:', response);
                },
                error: function(xhr, status, error) {
                    console.log('Error:', error);
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


// refundId, refundDate