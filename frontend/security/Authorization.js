import { authData } from "../db/loginData.js";


$(document).ready(function () {
    $(document).on('authDataUpdated', function () {
        const role = authData.employee.role;

        $('#dashboard-user-name').text(authData.employee.name);
        $('#dashboard-user-role').text(role);

        if (role === 'ADMIN') {
            $('#add-employee, #add-supplier, #add-item-btn, #dashboard-btn').css('display', 'flex');
            $('#page-user').hide();
        } else if (role === 'USER') {

            $('.insights').hide();
            $('.charts').hide();
            $('.recent-orders').hide();
            $('#page-user').show();
            $('#add-product-btn').hide();
        } else {
            showError('Authorization Error');
        }
    });
});