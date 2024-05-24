
function validateInput(input, errorSpan, fieldName) {


    const value = input.val().trim();
    errorSpan.text('');

    if (value === '') {
        errorSpan.text(`${fieldName} is required`);
        return false;
    } else if (!/^[a-zA-Z]+$/.test(value)) {
        errorSpan.text(`${fieldName} must contain only letters`);
        return false;
    }
    return true;
}

function validatePhone(input, errorSpan) {
    const value = input.val().trim();
    errorSpan.text('');

    const phoneRegex = /^(?:\+94|0)?\d{9,10}$/;
    if (value === '') {
        errorSpan.text('Phone is required');
        return false;
    } else if (!phoneRegex.test(value)) {
        errorSpan.text('Phone must be in the correct format');
        return false;
    }
    return true;
}

function validateName(input, errorSpan, fieldName) {
    const value = input.val().trim();
    errorSpan.text('');

    if (value === '') {
        errorSpan.text(`${fieldName} is required`);
        return false;
    } else if (!/^[a-zA-Z\s]+$/.test(value)) {
        errorSpan.text(`${fieldName} must contain only letters and spaces`);
        return false;
    }
    return true;
}

function validateAddress(input, errorSpan, fieldName) {
    const value = input.val().trim();
    errorSpan.text('');

    if (value === '') {
        errorSpan.text(`${fieldName} is required`);
        return false;
    }
    return true;
}

function validateOptionalAddress(input, errorSpan, fieldName) {
    const value = input.val().trim();
    errorSpan.text('');

    if (value !== '' && !/^[a-zA-Z0-9\s,\/]+$/.test(value)) {
        errorSpan.text(`${fieldName} must contain only letters, numbers, spaces, commas, and slashes`);
        return false;
    }
    return true;
}

function validateZip(input, errorSpan) {
    const value = input.val().trim();
    errorSpan.text('');

    const zipRegex = /^\d{5}$/;
    if (value === '') {
        errorSpan.text('Zip Code is required');
        return false;
    } else if (!zipRegex.test(value)) {
        errorSpan.text('Zip Code must be exactly 5 digits');
        return false;
    }
    return true;
}

function validateEmail(input, errorSpan) {
    const value = input.val().trim();
    errorSpan.text('');

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (value === '') {
        errorSpan.text('Email is required');
        return false;
    } else if (!emailRegex.test(value)) {
        errorSpan.text('Please enter a valid email address');
        return false;
    }
    return true;
}

function validateNominee(input, errorSpan) {
    const value = input.val().trim();
    errorSpan.text('');

    if (value === '') {
        errorSpan.text('Guardian or Nominated Person is required');
        return false;
    } else if (!/^[a-zA-Z\s]+$/.test(value)) {
        errorSpan.text('Guardian or Nominated Person must contain only letters and spaces');
        return false;
    }
    return true;
}

function validateUsername(input, errorSpan) {
    const value = input.val().trim();
    errorSpan.text('');

    if (value === '') {
        errorSpan.text('Username is required');
        return false;
    } else if (!/^\w+$/.test(value)) {
        errorSpan.text('Username must contain only letters, numbers, and underscores');
        return false;
    }
    return true;
}

function validatePassword(passwordInput, confirmPasswordInput, errorSpan) {
    const password = passwordInput.val().trim();
    const confirmPassword = confirmPasswordInput.val().trim();
    errorSpan.text('');

    const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;

    if (password === '') {
        errorSpan.text('Password is required');
        return false;
    } else if (confirmPassword === '') {
        errorSpan.text('Please confirm your password');
        return false;
    } else if (!passwordRegex.test(password)) {
        errorSpan.text('Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one number');
        return false;
    } else if (password !== confirmPassword) {
        errorSpan.text('Passwords do not match');
        return false;
    }
    return true;
}

function validateDOB(dayInput, monthInput, yearInput, errorSpan) {
    const day = dayInput.val().trim();
    const month = monthInput.val().trim();
    const year = yearInput.val().trim();
    errorSpan.text('');

    if (day === '' || month === '' || year === '') {
        errorSpan.text('Date of Birth is required');
        return false;
    }

    const dayNum = parseInt(day, 10);
    const monthNum = parseInt(month, 10);
    const yearNum = parseInt(year, 10);

    if (isNaN(dayNum) || isNaN(monthNum) || isNaN(yearNum)) {
        errorSpan.text('Date of Birth must be numeric');
        return false;
    }

    if (dayNum < 1 || dayNum > 31 || monthNum < 1 || monthNum > 12 || yearNum < 1900 || yearNum > 2050) {
        errorSpan.text('Invalid Date of Birth');
        return false;
    }

    const date = new Date(yearNum, monthNum - 1, dayNum);
    if (date.getFullYear() !== yearNum || date.getMonth() + 1 !== monthNum || date.getDate() !== dayNum) {
        errorSpan.text('Invalid Date of Birth');
        return false;
    }

    return true;
}

function validateRadioGroup(name, errorSpan, fieldName) {
    const selected = $(`input[name=${name}]:checked`).val();
    errorSpan.text('');

    if (!selected) {
        errorSpan.text(`${fieldName} is required`);
        return false;
    }
    return true;
}

function validateBranch(branchInput, errorSpan) {
    const selectedBranch = branchInput.val();
    errorSpan.text('');

    if (selectedBranch === '') {
        errorSpan.text('Attached Branch is required');
        return false;
    }
    return true;
}

function validateJoinedDate(joinedDateInput, errorSpan) {
    const joinedDate = joinedDateInput.val();
    errorSpan.text('');

    if (joinedDate === '') {
        errorSpan.text('Joined Date is required');
        return false;
    }

    const date = new Date(joinedDate);
    if (isNaN(date.getTime())) {
        errorSpan.text('Invalid Joined Date');
        return false;
    }
    return true;
}
function validatePriceInput(input, errorSpan, fieldName) {
    const value = input.val().trim();
    errorSpan.text('');

    if (value === '') {
        errorSpan.text(`${fieldName} is required`);
        return false;
    } else if (isNaN(parseFloat(value))) {
        errorSpan.text(`${fieldName} must be a valid number`);
        return false;
    }
    return true;
}

function validateMarginInput(input, errorSpan) {
    const value = input.val().trim();
    errorSpan.text('');

    if (value === '') {
        errorSpan.text('Profit Margin is required');
        return false;
    } else if (isNaN(parseFloat(value)) || parseFloat(value) < 0 || parseFloat(value) > 100) {
        errorSpan.text('Profit Margin must be a valid percentage between 0 and 100');
        return false;
    }
    return true;
}  

function validateInputTwoNames(input, errorSpan, fieldName) {
    const value = input.val().trim();
    errorSpan.text('');

    if (value === '') {
        errorSpan.text(`${fieldName} is required`);
        return false;
    } else if (!/^[a-zA-Z]+\s[a-zA-Z]+$/.test(value)) {
        errorSpan.text(`${fieldName} must contain two names separated by a space`);
        return false;
    }
    return true;
}

export {
    validateInput,
    validatePhone,
    validateName,
    validateAddress,
    validateOptionalAddress,
    validateZip,
    validateEmail,
    validateNominee,
    validateUsername,
    validatePassword,
    validateDOB,
    validateRadioGroup,
    validateBranch,
    validateJoinedDate,
    validatePriceInput,
    validateMarginInput,
    validateInputTwoNames
};

//register employee
$('#reg-emp-fn').on('input', function () {
    validateInput($(this), $('#fn-error'), 'First name');
});

$('#reg-emp-ln').on('input', function () {
    validateInput($(this), $('#ln-error'), 'Last name');
});

$('#reg-emp-phone').on('input', function () {
    validatePhone($(this), $('#phone-error'));
});

$('#reg-emp-street').on('input', function () {
    validateAddress($(this), $('#street-error'), 'Street address');
});

$('#reg-emp-building').on('input', function () {
    validateOptionalAddress($(this), $('#building-error'), 'Building');
});

$('#reg-emp-city').on('input', function () {
    validateName($(this), $('#city-error'), 'City');
});

$('#reg-emp-state').on('input', function () {
    validateName($(this), $('#state-error'), 'State');
});

$('#reg-emp-zip').on('input', function () {
    validateZip($(this), $('#zip-error'));
});

$('#reg-emp-email').on('input', function () {
    validateEmail($(this), $('#email-error'));
});

$('#reg-emp-nominee').on('input', function () {
    validateNominee($(this), $('#nominee-error'));
});

$('#reg-emp-username').on('input', function () {
    validateUsername($(this), $('#username-error'));
});

$('#reg-emp-password, #reg-emp-confirmp').on('input', function () {
    validatePassword($('#reg-emp-password'), $('#reg-emp-confirmp'), $('#password-match-error'));
});

$('#reg-mp-date, #reg-mp-month, #reg-mp-year').on('input', function () {
    validateDOB($('#reg-mp-date'), $('#reg-mp-month'), $('#reg-mp-year'), $('#dob-error'));
});

$('#page input[name=gender]').on('change', function () {
    validateRadioGroup('gender', $('#gender-error'), 'Gender');
});

$('#page input[name=designation]').on('change', function () {
    validateRadioGroup('designation', $('#designation-error'), 'Designation');
});

$('#page input[name=status]').on('change', function () {
    validateRadioGroup('status', $('#status-error'), 'Status');
});

$('#branch-employee').on('change', function () {
    validateBranch($('#branch-employee'), $('#branch-error'));
});

$('#reg-emp-jdate').on('input', function () {
    validateJoinedDate($('#reg-emp-jdate'), $('#joined-date-error'));
});

//customer register
$('#reg-cus-fn').on('input', function () {
    validateInput($(this), $('#fn-error-cus'), 'First name');
});

$('#reg-cus-ln').on('input', function () {
    validateInput($(this), $('#ln-error-cus'), 'Last name');
});

$('#reg-cus-day, #reg-cus-month, #reg-cus-year').on('input', function () {
    validateDOB($('#reg-cus-day'), $('#reg-cus-month'), $('#reg-cus-year'), $('#dob-error-cus'));
});

$('#page-customer input[name=gender]').on('change', function () {
    validateRadioGroup('gender', $('#gender-error-cus'), 'Gender');
});

$('#cus-reg-joined-loyal').on('input', function () {
    validateJoinedDate($('#cus-reg-joined-loyal'), $('#joined-date-error-cus'));
});

$('#cus-reg-phone').on('input', function () {
    validatePhone($(this), $('#phone-error-cus'));
});

$('#cus-reg-street').on('input', function () {
    validateAddress($(this), $('#street-error-cus'), 'Street address');
});

$('#cus-reg-building').on('input', function () {
    validateOptionalAddress($(this), $('#building-error-cus'), 'Building');
});

$('#cus-reg-city').on('input', function () {
    validateName($(this), $('#city-error-cus'), 'City');
});

$('#cus-reg-state').on('input', function () {
    validateName($(this), $('#state-error-cus'), 'State');
});

$('#cus-reg-zip').on('input', function () {
    validateZip($(this), $('#zip-error-cus'));
});

$('#reg-cus-email').on('input', function () {
    validateEmail($(this), $('#email-error-cus'));
});

//supplier register
$('#reg-sup-fn').on('input', function () {
    validateInput($(this), $('#fn-error-sup'), 'First name');
});

$('#reg-sup-ln').on('input', function () {
    validateInput($(this), $('#ln-error-sup'), 'Last name');
});

$('#reg-sup-tel').on('input', function () {
    validatePhone($(this), $('#phone-error-sup'));
});

$('#reg-sup-tel-2').on('input', function () {
    validatePhone($(this), $('#phone-2-error-sup'));
});

$('#reg-sup-add-street').on('input', function () {
    validateAddress($(this), $('#street-error-sup'), 'Street address');
});

$('#reg-sup-add-no').on('input', function () {
    validateOptionalAddress($(this), $('#building-error-sup'), 'Building');
});

$('#reg-sup-add-city').on('input', function () {
    validateName($(this), $('#city-error-sup'), 'City');
});

$('#reg-sup-add-state').on('input', function () {
    validateName($(this), $('#state-error-sup'), 'State');
});

$('#reg-sup-add-zip').on('input', function () {
    validateZip($(this), $('#zip-error-sup'));
});

$('#reg-sup-email').on('input', function () {
    validateEmail($(this), $('#email-error-sup'));
});

//register item
$('#reg-item-name').on('input', function () {
    validateInputTwoNames($(this), $('#name-error-itm'), 'Item Name');
});

$('#add-item-page input[name="category"]').on('change', function () {
    validateRadioGroup('category', $('#category-error-itm'), 'Item Category');
});

$('#add-item-page input[name="size"]').on('change', function () {
    validateRadioGroup('size', $('#size-error-itm'), 'Size');
});

$('#add-item-page input[name="gender"]').on('change', function () {
    validateRadioGroup('gender', $('#gender-error-itm'), 'Gender');
});

$('#add-item-page input[name="occasion"]').on('change', function () {
    validateRadioGroup('occasion', $('#occasion-error-itm'), 'Occasion');
});

$('#reg-i-pur').on('input', function () {
    validatePriceInput($(this), $('#purchase-error-itm'), 'Purchase Price');
});

$('#reg-i-sell').on('input', function () {
    validatePriceInput($(this), $('#selling-error-itm'), 'Selling Price');
});

$('#reg-i-ex-profit').on('input', function () {
    validatePriceInput($(this), $('#profit-error-itm'), 'Expected Profit');
});

$('#reg-i-mar').on('input', function () {
    validateMarginInput($(this), $('#margin-error-itm'));
});
