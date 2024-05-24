import { authData } from "../db/loginData.js";
import { supplierData } from "../db/supplier.js";
import {
    validateInput,
    validatePhone,
    validateName,
    validateAddress,
    validateOptionalAddress,
    validateZip,
    validateEmail,
    validateRadioGroup
} from '../security/FieldValidation.js';

$('#add-sup-btn').on('click', function () {
    
    supplierData.code = null;
    supplierData.supplierName = $('#reg-sup-fn').val() + " " + $('#reg-sup-ln').val();
    supplierData.category = $('#reg-sup-radio-cat input[type="radio"]:checked').val();
    supplierData.addressNo = $('#reg-sup-add-no').val();
    supplierData.addressLane = $('#reg-sup-add-street').val();
    supplierData.addressCity = $('#reg-sup-add-city').val();
    supplierData.addressState = $('#reg-sup-add-state').val();
    supplierData.postalCode = $('#reg-sup-add-zip').val();
    supplierData.originCountry = $('#country-Supplier').val();
    supplierData.email = $('#reg-sup-email').val();
    supplierData.contactNo1 = $('#reg-sup-tel').val();
    supplierData.contactNo2 = $('#reg-sup-tel-2').val();



    function validateSupplierForm() {
        let isValid = true;
    
        isValid &= validateInput($('#reg-sup-fn'), $('#fn-error-sup'), 'First Name');
        isValid &= validateInput($('#reg-sup-ln'), $('#ln-error-sup'), 'Last Name');
        isValid &= validateRadioGroup('category', $('#category-error-sup'), 'Category');
        isValid &= validatePhone($('#reg-sup-tel'), $('#phone-error-sup'));
        isValid &= validatePhone($('#reg-sup-tel-2'), $('#phone-2-error-sup'));
        isValid &= validateAddress($('#reg-sup-add-street'), $('#street-error-sup'), 'Street Address');
        isValid &= validateOptionalAddress($('#reg-sup-add-no'), $('#building-error-sup'), 'Suite/Building');
        isValid &= validateName($('#reg-sup-add-city'), $('#city-error-sup'), 'City');
        isValid &= validateName($('#reg-sup-add-state'), $('#state-error-sup'), 'State');
        isValid &= validateZip($('#reg-sup-add-zip'), $('#zip-error-sup'));
        isValid &= validateEmail($('#reg-sup-email'), $('#email-error-sup'));
    
        return isValid;
    }
    

    if(validateSupplierForm()){
        $.ajax({
            url: 'http://localhost:9090/shoeshop/api/v1/supplier',
            type: 'POST',
            contentType: 'application/json',
            headers: {
                'Authorization': 'Bearer ' + authData.token
            },
            data: JSON.stringify(supplierData),
            success: function (response) {
                console.log('Success:', response);
                showSuccess('Supplier saved successfully');
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
                showError('Failed to save Supplier');
            }
        });
    }else{
        showError('Please correct the errors in the form');
    }
    

});
