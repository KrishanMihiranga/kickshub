import { items, itemImages, addToInventory } from "../db/Item.js";
import { authData } from "../db/loginData.js";
import { inventoryItems } from "../db/inventory.js";

let relatedArray = null;
let imgArray = null;

$('#add-product-btn').on('click', () => {
    $('#page-user, #save-changes-employee, .charts, .recent-orders, .sales, .expenses, .income, #page, #page-customer, #page-supplier, #update-profile, #information-page, #recent-orders-refund-page, #refund-page, #add-item-page, #inventory-page, #sale-page').hide();
    $('#add-product-page').show();
    createCards(items);
});

function createCards(itemsArray) {
    $('.container-ap-wrapper').empty();
    itemsArray.forEach(item => {
        createCard(item);
    });
}

function createCard(item) {
    const card = $('<div class="container-ap"></div>');
    const imageContainer = $('<div class="image-container-ap"></div>');
    const image = $('<img src="' + getImageForItem(item.itemCode) + '" alt="" class="image-ap">');

    const textContainer = $('<div class="text-container-ap"></div>');
    const heading = $('<div class="heading-ap">' + item.description + '</div>');
    const description = $('<div class="description-ap"><p>' + item.supplier.supplierName + '</p></div>');

    const footer = $('<div class="footer-ap"></div>');
    const price = $('<div class="price-ap">$' + item.unitPriceSale + '</div>');
    const button = $('<div class="button-ap"><span class="material-icons-sharp">add</span> Add</div>');

    button.on('click', function () {
        if (authData.employee.role === 'ADMIN') {
            relatedArray = item;
            imgArray = getItemImage(item.itemCode);
            $('.popup-stock').addClass("active-popup");
            $('.overlay').addClass("active-overlay");
        } else {
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

function getItemImage(itemId) {
    return itemImages.find(item => item.id === itemId);
}

function getImageForItem(itemId) {
    const itemImage = itemImages.find(item => item.id === itemId);
    return itemImage ? 'data:image;base64,' + itemImage.image : 'default-image-url.jpg';
}

$('#toggle-password-inv').change(function () {
    const type = $(this).is(':checked') ? 'text' : 'password';
    $('#password-popup-inv, #password-confirm-popup-inv').attr('type', type);
});

$('.upi-item-radio-color input[type="radio"]').on('change', function () {
    if ($(this).is(':checked')) {
        const colorValue = $(this).val();
        $('.upi-item-radio-color label').css({ 'background-color': '', 'border': '' });
        $(this).next('label').css({ 'background-color': colorValue, 'border': '2px solid var(--color-primary)' });
    }
});

$('#popup-stock-update-btn').on('click', () => {
    const amountToUpdate = $('#stock-amount-popup').val();
    const size = $('.upi-item-radio-size input[type="radio"]:checked').val();
    const color = $('.upi-item-radio-color input[type="radio"]:checked').val();

    Object.assign(addToInventory, {
        size,
        colors: color,
        originalQty: amountToUpdate,
        currentQty: amountToUpdate,
        status: 'AVAILABLE',
        item: relatedArray,
        itemImage: imgArray
    });
});

$('#save-up-btn-inv').on('click', () => {
    const pop_email = $('#email-popup-inv').val();
    const pop_password = $('#password-popup-inv').val();
    const pop_rePass = $('#password-confirm-popup-inv').val();

    if (pop_password !== pop_rePass) {
        showError("Password you entered didn't match");
        return;
    }

    const dataEmp = {
        email: pop_email,
        password: CryptoJS.SHA256(pop_password).toString(CryptoJS.enc.Hex)
    };

    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/employee/check',
        type: 'POST',
        contentType: 'application/json',
        headers: { 'Authorization': 'Bearer ' + authData.token },
        data: JSON.stringify(dataEmp),
        success: function (response) {
            if (response) {
                $.ajax({
                    url: 'http://localhost:9090/shoeshop/api/v1/inventory/saveinventoryitem',
                    type: 'POST',
                    contentType: 'application/json',
                    headers: { 'Authorization': 'Bearer ' + authData.token },
                    data: JSON.stringify(addToInventory),
                    success: function (response) {
                        $('.popup-inv').removeClass("active-popup");
                        $('.overlay').removeClass("active-overlay");
                        $('#email-popup-inv').val('');
                        $('#password-popup-inv').val('');
                        $('#password-confirm-popup-inv').val('');
                        showSuccess('Inventory Successfully updated');
                        refreshInventory();
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

function refreshInventory() {
    $.ajax({
        url: 'http://localhost:9090/shoeshop/api/v1/inventory/getall',
        type: 'GET',
        headers: { 'Authorization': 'Bearer ' + authData.token },
        success: function (response) {
            inventoryItems.length = 0;
            inventoryItems.push(...response);
            console.log('inventoryItems Array:', inventoryItems);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

let selectedGender = '';
let selectedOccasion = '';

function applyFiltersAndDisplayProducts() {
    const filteredProducts = items.filter(item =>
        (!selectedGender || item.gender.toUpperCase() === selectedGender.toUpperCase()) &&
        (!selectedOccasion || item.occasion.toUpperCase() === selectedOccasion.toUpperCase())
    );
    createCards(filteredProducts);
}

$(document).ready(function () {
    $('.radio-reg-emp-gender > input').click(function () {
        selectedGender = $(this).val();
        applyFiltersAndDisplayProducts();
    });

    $('.radio-reg-emp-occasion > input').click(function () {
        selectedOccasion = $(this).val();
        applyFiltersAndDisplayProducts();
    });

    $('#radio-reg-emp-occasion-remove span').click(function () {
        selectedGender = '';
        selectedOccasion = '';
        $('.radioG').prop('checked', false);
        createCards(items);
    });

    $('#search-bar-stock input').on('input', function () {
        const searchText = $(this).val().trim().toLowerCase();
        $('.heading-ap').each(function () {
            const headingText = $(this).text().trim().toLowerCase();
            const highlightedText = highlightMatchedText(headingText, searchText);
            $(this).html(highlightedText);
        });
    });

    createCards(items);
});

function highlightMatchedText(text, searchText) {
    if (!searchText) return text;
    const regex = new RegExp(searchText, 'gi');
    return text.replace(regex, match => `<span class="highlight">${match}</span>`);
}