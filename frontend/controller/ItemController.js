import { authData } from "../db/loginData.js";
import { suppliers } from "../db/supplier.js";
import { regItem } from "../db/Item.js";
import { supplierData } from "../db/supplier.js";
var supCode;
import {
    validateInput,
    validateRadioGroup,
    validatePriceInput,
    validateMarginInput,
    validateInputTwoNames
} from '../security/FieldValidation.js';


$('#add-item-btn').on('click', () => {
    // Hide other elements and show the add item page
    $('#page-user, #save-changes-employee, .charts, .recent-orders, .sales, .expenses, .income, #page, #page-customer,#page-supplier, #update-profile, #information-page, #recent-orders-refund-page, #refund-page, #add-product-page, #inventory-page, #sale-page').hide();
    $('#add-item-page').show();

    // Populate the supplier dropdown with options
    $.each(suppliers, function (index, supplier) {
        var option = $('<option></option>').attr('value', supplier.supplierName).text(supplier.supplierName);
        $('.reg-item-supplier').append(option);
    });

    // Event listener for supplier change
    $('.reg-item-supplier').change(function () {
        var selectedName = $(this).val();

        var selectedSupplier = suppliers.find(function (supplier) {
            return supplier.supplierName === selectedName;
        });

        if (selectedSupplier) {
            // Set supplier code
            supCode = selectedSupplier.code;
            $('.reg-item-supplier-code span').text(selectedSupplier.code);
        } else {
            $('.reg-item-supplier-code span').text("Supplier code not found");
        }
    });
});


$('#reg-item-btn').on('click', () => {

    regItem.description = $('#reg-item-name').val();
    regItem.category = $('.reg-item-category').val();
    regItem.occasion = $('.reg-item-radio-occasion input[type="radio"]:checked').val();
    regItem.gender = $('.reg-item-radio-gender input[type="radio"]:checked').val();
    regItem.supplierName = $('.reg-item-supplier').val();
    regItem.unitPriceBuy = $('#reg-i-pur').val();
    regItem.unitPriceSale = $('#reg-i-sell').val();
    regItem.expectedPrice = $('#reg-i-ex-profit').val();
    regItem.profitMargin = $('#reg-i-mar').val();


    var selectedSupplier = suppliers.find(function (supplier) {
        return supplier.supplierName === regItem.supplierName;
    });

    if (selectedSupplier) {
        regItem.supplier = {
            code: selectedSupplier.code,
            supplierName: selectedSupplier.supplierName,
            category: selectedSupplier.category,
            addressNo: selectedSupplier.addressNo,
            addressLane: selectedSupplier.addressLane,
            addressCity: selectedSupplier.addressCity,
            addressState: selectedSupplier.addressState,
            postalCode: selectedSupplier.postalCode,
            originCountry: selectedSupplier.originCountry,
            email: selectedSupplier.email,
            contactNo1: selectedSupplier.contactNo1,
            contactNo2: selectedSupplier.contactNo2
        };
    } else {
        console.log("Selected supplier not found");
    }

    var formData = new FormData();
    formData.append('id', regItem.id);
    formData.append('image', $('#file')[0].files[0]);
    formData.append('itemCode', regItem.itemCode);
    formData.append('description', regItem.description);
    formData.append('category', regItem.category);
    formData.append('occasion', regItem.occasion);
    formData.append('gender', regItem.gender);
    formData.append('supplierName', regItem.supplierName);
    formData.append('unitPriceSale', regItem.unitPriceSale);
    formData.append('unitPriceBuy', regItem.unitPriceBuy);
    formData.append('expectedPrice', regItem.expectedPrice);
    formData.append('profitMargin', regItem.profitMargin);
    formData.append('supplier', JSON.stringify(regItem.supplier));

    // Log the FormData object
    console.log('FormData:');
    for (var pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
    }


    function validateItemForm() {
        let isValid = true;

        isValid = validateInputTwoNames($('#reg-item-name'), $('#name-error-itm'), 'Item Name') && isValid;
        isValid = validateRadioGroup('category', $('#category-error-itm'), 'Item Category') && isValid;
        isValid = validateRadioGroup('size', $('#size-error-itm'), 'Size') && isValid;
        isValid = validateRadioGroup('gender', $('#gender-error-itm'), 'Gender') && isValid;
        isValid = validateRadioGroup('occasion', $('#occasion-error-itm'), 'Occasion') && isValid;
        isValid = validatePriceInput($('#reg-i-pur'), $('#purchase-error-itm'), 'Purchase Price') && isValid;
        isValid = validatePriceInput($('#reg-i-sell'), $('#selling-error-itm'), 'Selling Price') && isValid;
        isValid = validatePriceInput($('#reg-i-ex-profit'), $('#profit-error-itm'), 'Expected Profit') && isValid;
        isValid = validateMarginInput($('#reg-i-mar'), $('#margin-error-itm')) && isValid;

        return isValid;
    }


    if (validateItemForm()) {

        $.ajax({
            url: 'http://localhost:9090/shoeshop/api/v1/svi/saveimage',
            type: 'POST',
            headers: {
                'Authorization': 'Bearer ' + authData.token
            },
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                console.log('Success:', response);
                showSuccess('Item saved successfully');
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
                showError('Failed to save Item');
            }
        });

    } else {
        showError('Please correct the errors in the form');
    }

});
