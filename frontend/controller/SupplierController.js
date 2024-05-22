import { authData } from "../db/loginData.js";
import { supplierData } from "../db/supplier.js";

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
            alert("Saved");
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });

});
