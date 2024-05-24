import { items, itemImages, addToInventory } from "../db/item.js";
import { authData } from "../db/loginData.js";
var relatedArray = null;
var imgArray = null;

$('#add-product-btn').on('click', () => {
    $('#page-user, #save-changes-employee, .charts, .recent-orders, .sales, .expenses, .income, #page, #page-customer,#page-supplier, #update-profile, #information-page, #recent-orders-refund-page, #refund-page, #add-item-page, #inventory-page, #sale-page').hide();
    $('#add-product-page').show();
    console.log(items, itemImages)
    createCards();
});

function createCards() {
    $('.container-ap-wrapper').empty();

    items.forEach(item => {
        createCard(item);
    });
}

function createCard(item) {
    var card = $('<div class="container-ap"></div>');

    var imageContainer = $('<div class="image-container-ap"></div>');
    var image = $('<img src="' + getImageForItemId(item.itemCode) + '" alt="" class="image-ap">');

    var textContainer = $('<div class="text-container-ap"></div>');
    var heading = $('<div class="heading-ap">' + item.description + '</div>');
    var description = $('<div class="description-ap"><p>' + item.supplier.supplierName + '</p></div>');

    var footer = $('<div class="footer-ap"></div>');
    var price = $('<div class="price-ap">$' + item.unitPriceSale + '</div>');
    var button = $('<div class="button-ap"><span class="material-icons-sharp">add</span> Add</div>');

    button.on('click', function () {
        const role = authData.employee.role;

        if (role === 'ADMIN') {
            relatedArray = item;
            imgArray = getImage(item.itemCode);
            $('.popup-stock').addClass("active-popup");
            $('.overlay').addClass("active-overlay"); // Add class to overlay
        }else if (role === 'USER'){
            showError("You don't have permissions to do this");
        }

    });

    imageContainer.append(image);
    textContainer.append(heading);
    textContainer.append(description);
    footer.append(price);
    footer.append(button);

    card.append(imageContainer);
    card.append(textContainer);
    card.append(footer);

    $('.container-ap-wrapper').append(card);
}

function getImage(itemId) {
    var itemImage = itemImages.find(item => item.id === itemId);
    return itemImage;
}

function getImageForItemId(itemId) {
    var itemImage = itemImages.find(item => item.id === itemId);
    return itemImage ? 'data:image;base64,' + itemImage.image : 'default-image-url.jpg';
}


// $('.button-ap').on('click', () => {

// // });
// function popupt(){
//     $('.popup-stock').addClass("active-popup");
//     $('.overlay').addClass("active-overlay"); // Add class to overlay
// }


$('#toggle-password-inv').change(function () {
    var passwordInput = $('#password-popup-inv');
    var confirmPasswordInput = $('#password-confirm-popup-inv');
    if ($(this).is(':checked')) {
        passwordInput.attr('type', 'text');
        confirmPasswordInput.attr('type', 'text');
    } else {
        passwordInput.attr('type', 'password');
        confirmPasswordInput.attr('type', 'password');
    }
});

$('.upi-item-radio-color input[type="radio"]').on('change', function () {
    if ($(this).is(':checked')) {
        var colorValue = $(this).val();

        // Remove styles from all labels
        $('.upi-item-radio-color label').css({
            'background-color': '', // Remove background color
            'border': '' // Remove border
        });

        // Apply styles to the label of the selected radio button
        $(this).next('label').css({
            'background-color': colorValue,
            'border': '2px solid var(--color-primary)'
        });
    }
});

$('#popup-stock-update-btn').on('click', () => {
    var amountToUpdate = $('#stock-amount-popup').val();
    var size = $('.upi-item-radio-size input[type="radio"]:checked').val();
    var color = $('.upi-item-radio-color input[type="radio"]:checked').val();
    addToInventory.size = size;
    addToInventory.colors = color;
    addToInventory.originalQty = amountToUpdate;
    addToInventory.currentQty = amountToUpdate;
    addToInventory.status = 'AVAILABLE';
    addToInventory.item = relatedArray;
    addToInventory.itemImage = imgArray;
    console.log(addToInventory);
});

$('#save-up-btn-inv').on('click', () => {
    var pop_email = $('#email-popup-inv').val();
    var pop_password = $('#password-popup-inv').val();
    var pop_rePass = $('#password-confirm-popup-inv').val();

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
                    url: 'http://localhost:9090/shoeshop/api/v1/inventory/saveinventoryitem',
                    type: 'POST',
                    contentType: 'application/json',
                    headers: {
                        'Authorization': 'Bearer ' + authData.token
                    },
                    data: JSON.stringify(addToInventory),
                    success: function (response) {
                        console.log('Inventory updated : ' + response);
                        showSuccess('Inventory Successfully updated');
                    },
                    error: function (xhr, status, error) {
                        console.error('Error:', error);
                        showError('Failed to update Inventory');
                    }
                });



            } else {
                showError('Error while updating Inventory');
            }
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
            showError('User not found');
        }
    });

});
