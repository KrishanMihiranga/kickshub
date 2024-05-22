const sideMenu = $("aside");
const menuBtn = $("#menu-btn");
const closeBtn = $("#close-btn");
const themeToggler = $(".theme-toggler");
const nextButton = $(".btn-next");
const prevButton = $(".btn-prev");
const submitButton = $(".btn-submit");
const steps = $(".step");
const stepsCustomer = $(".stepCustomer");
const stepsSupplier = $(".stepSupplier");
const form_steps = $(".form-step");
const form_stepsCustomer = $(".form-stepCustomer");
const form_stepsSupplier = $(".form-stepSupplier");
let active = 1;
let activeCustomer = 1;
let activeSupplier = 1;
const imgDiv = $('.user-img');
const img = $('#profile-picture-employee');
const file = $('#employee-file');
const employeeDpUploadBtn = $('#emp-file-upload-btn');
const toast = $(".toast");
const closeIcon = $(".noti-close");
const progressNoti = $(".progress-noti");
const dropDown = $(".information-dropdown");
let token = 'eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJzdWIiOiJuaWxzYWxpQGdtYWlsLmNvbSIsImlhdCI6MTcxNDMyMDAzMywiZXhwIjoxNzE0MzIwNjMzfQ.ipCuGP-GzrXfI2doGF9lDr09wEtBS1PAtleduDQIgQQ';

$('#getb').on('click', () => {
  fetch('http://localhost:9090/shoeshop/api/v1/employee/getAllEmployees', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(data => {
      console.log(data);
    })
    .catch(error => {
      console.error('There was a problem with your fetch operation:', error);
    });
});


// Show sidebar
menuBtn.on('click', function () {
  sideMenu.css("display", "block");
});

// Close sidebar
closeBtn.on('click', function () {
  sideMenu.css("display", "none");
});

// //notification
// submitButton.on('click',()=>{
//   toast.addClass("active-noti");
//   progressNoti.addClass("active-noti");
//   setTimeout(()=>{
//     toast.removeClass("active-noti");
//   },5000);

//   setTimeout(()=>{
//     progressNoti.removeClass("active-noti");
//   },5300);

// });
// closeIcon.on('click',()=>{
//   toast.removeClass("active-noti");

//   setTimeout(()=>{
//     progressNoti.removeClass("active-noti");
//   },300);
// });

// Change theme
themeToggler.on('click', function () {
  $("body").toggleClass("dark-theme-variables");
  themeToggler.find("span:nth-child(1)").toggleClass("active");
  themeToggler.find("span:nth-child(2)").toggleClass("active");
});

//swap active class
$('#dashboard-btn, #add-employee, #add-customer, #add-supplier,#edit-profile, #info-log, #register-side-btn, #refund-btn, #add-item-btn, #inventory-btn, #add-product-btn, #sale-btn').click(function () {
  $('#dashboard-btn, #add-employee, #add-customer, #add-supplier,#edit-profile, #info-log, #register-side-btn, #refund-btn, #add-item-btn, #inventory-btn, #add-product-btn, #sale-btn').removeClass('active');
  $(this).addClass('active');
});

//information options
function showInfo(info) {
  $('.information-textBox').val(info);
  if (info == 'Customer') {
    $('#information-table-customer-wrapper').show();
    $('#information-table-supplier-wrapper').hide();
    $('#information-table-employee-wrapper').hide();
  } else if ((info == 'Supplier')) {
    $('#information-table-customer-wrapper').hide();
    $('#information-table-supplier-wrapper').show();
    $('#information-table-employee-wrapper').hide();
  } else if ((info == 'Employee')) {
    $('#information-table-customer-wrapper').hide();
    $('#information-table-supplier-wrapper').hide();
    $('#information-table-employee-wrapper').show();
  } else {
    $('#information-table-customer-wrapper').hide();
    $('#information-table-supplier-wrapper').hide();
    $('#information-table-employee-wrapper').hide();
  }
}

dropDown.on('click', () => {
  dropDown.toggleClass('active-information');
});

//aside dropdown
$('#register-side-btn').on('click', function () {
  var $dropdown = $(this).next('.aside-menu-dropdown').children('.aside-sub-menu');
  var $icon = $(this).find('.fa-angle-down');

  $dropdown.slideToggle();
  $('.aside-sub-menu').not($dropdown).slideUp();

  if ($icon.hasClass('rotate')) {
    $icon.removeClass('rotate');
  } else {
    $icon.addClass('rotate');
  }
});

$(document.body).on('click', function (event) {
  if (!$(event.target).closest('#register-side-btn').length) {
    $('.aside-sub-menu').slideUp();
    $('#register-side-btn .fa-angle-down').removeClass('rotate');
  }
});
$('.aside-sub-menu').slideUp();
$('#register-side-btn .fa-angle-down').removeClass('rotate');

//copy id
$('.update-profile-user-unique-code-copy').click(function () {
  var $this = $(this);
  $this.addClass('active-copy');
  var textToCopy = $this.siblings('.update-profile-user-unique-code').text();

  navigator.clipboard.writeText(textToCopy)
    .then(function () {
      console.log('Text copied to clipboard: ' + textToCopy);
    })
    .catch(function (err) {
      console.error('Error copying text to clipboard: ', err);
    });

  setTimeout(function () {
    $this.removeClass('active-copy');
  }, 3000);
});

$('.Information-update-profile-user-unique-code-copy').click(function () {
  var $this = $(this);
  $this.addClass('active-copy');
  var textToCopy = $this.siblings('.Information-update-profile-user-unique-code').text();

  navigator.clipboard.writeText(textToCopy)
    .then(function () {
      console.log('Text copied to clipboard: ' + textToCopy);
    })
    .catch(function (err) {
      console.error('Error copying text to clipboard: ', err);
    });

  setTimeout(function () {
    $this.removeClass('active-copy');
  }, 3000);
});


//search button 
const toggleSearch = (search, button) => {
  $(`#${button}`).on('click', () => {
    $(`#${search}`).toggleClass('show-search');
  });
};

toggleSearch('search-bar', 'search-button');
toggleSearch('search-bar-stock', 'search-button-stock');
toggleSearch('search-bar-inv', 'search-button-inv');
toggleSearch('search-bar-sale', 'search-button-sale');

//sale remove item cart
$(document).on('click', '.overlay-active-btn-sale', function () {
  var overlay = $(this).closest('.sale-cart-item').find('.sale-item-overlay');
  overlay.addClass('active-overlay-sale');
});
$(document).on('click', '.sale-item-overlay-no', function () {
  var overlay = $(this).closest('.sale-item-overlay');
  overlay.removeClass('active-overlay-sale');
});



//reg item image 
$('.select-image').on('click', function () {
  $('#file').click();
});

$('#file').on('change', function () {
  const image = this.files[0];
  if (image.size < 2000000) {
    const reader = new FileReader();
    reader.onload = function () {
      $('.img-area img').remove();
      const imgUrl = reader.result;
      const img = $('<img>');
      img.attr('src', imgUrl);
      $('.img-area').append(img);
      $('.img-area').addClass('active').attr('data-img', image.name);
    };
    reader.readAsDataURL(image);
  } else {
    alert("Image size more than 2MB");
  }
});




//load countries
const countries = [
  "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda",
  "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain",
  "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia",
  "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso",
  "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic",
  "Chad", "Chile", "China", "Colombia", "Comoros", "Congo, Democratic Republic of the",
  "Congo, Republic of the", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic",
  "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor (Timor-Leste)",
  "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini",
  "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany",
  "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti",
  "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland",
  "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati",
  "Korea, North", "Korea, South", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia",
  "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
  "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
  "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia",
  "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal",
  "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Macedonia",
  "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea",
  "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia",
  "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines",
  "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia",
  "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
  "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname",
  "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand",
  "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu",
  "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States",
  "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen",
  "Zambia", "Zimbabwe"
];

function populateCountries() {
  const select = $('#country');
  const selectCustomer = $('#country-customer');
  const selectSupplier = $('#country-Supplier');
  const selectSupplierUp = $('#country-Supplier-up');
  select.empty();
  selectCustomer.empty();
  selectSupplier.empty();
  selectSupplierUp.empty();

  countries.forEach(country => {
    select.append($('<option>', {
      value: country,
      text: country
    }));
    selectCustomer.append($('<option>', {
      value: country,
      text: country
    }));
    selectSupplier.append($('<option>', {
      value: country,
      text: country
    }));
    selectSupplierUp.append($('<option>', {
      value: country,
      text: country
    }));
  });
}

$(document).ready(function () {
  populateCountries();
  $('#information-table-customer-wrapper').hide();
  $('#information-table-supplier-wrapper').hide();
  $('#information-table-employee-wrapper').hide();
  $('#country').val('Sri Lanka');
  $('#country-customer').val('Sri Lanka');
  $('#country-Supplier').val('Sri Lanka');
  $('#country-Supplier-up').val('Sri Lanka');
});


//register form
nextButton.on('click', function () {
  active++;
  activeCustomer++;
  activeSupplier++;
  if (active > steps.length) {
    active = steps.length;
  } else if (activeCustomer > stepsCustomer.length) {
    activeCustomer = stepsCustomer.length;
  } else if (activeSupplier > stepsSupplier.length) {
    activeSupplier = stepsSupplier.length;
  }
  updateProgress();
});

prevButton.on('click', function () {
  active--;
  activeCustomer--;
  activeSupplier--;
  if (active < 1) {
    active = 1;
  } else if (activeCustomer < 1) {
    activeCustomer = 1;
  } else if (activeSupplier < 1) {
    activeSupplier = 1;
  }
  updateProgress();
});

const updateProgress = () => {
  console.log('steps.length => ' + steps.length);
  console.log('stepsCustomer.length => ' + stepsCustomer.length);
  console.log('stepsSupplier.length => ' + stepsSupplier.length);
  console.log('active => ' + active);
  console.log('active Customer => ' + activeCustomer);
  console.log('active Supplier => ' + activeSupplier);

  stepsSupplier.each((i, step) => {
    if (i === (activeSupplier - 1)) {
      $(step).addClass('active-step');
      $(form_stepsSupplier[i]).addClass('active-step');
      console.log('i +> ' + i);
    } else {
      $(step).removeClass('active-step');
      $(form_stepsSupplier[i]).removeClass('active-step');
    }
  });

  stepsCustomer.each((i, step) => {
    if (i === (activeCustomer - 1)) {
      $(step).addClass('active-step');
      $(form_stepsCustomer[i]).addClass('active-step');
      console.log('i +> ' + i);
    } else {
      $(step).removeClass('active-step');
      $(form_stepsCustomer[i]).removeClass('active-step');
    }
  });

  steps.each((i, step) => {
    if (i === (active - 1)) {
      $(step).addClass('active-step');
      $(form_steps[i]).addClass('active-step');
      console.log('i => ' + i);
    } else {
      $(step).removeClass('active-step');
      $(form_steps[i]).removeClass('active-step');
    }
  });

  if (active === 1) {
    $(prevButton).prop('disabled', true);
  } else if (active === steps.length) {
    $(nextButton).prop('disabled', true);
  } else {
    $(prevButton).prop('disabled', false);
    $(nextButton).prop('disabled', false);
  }
}

//open popup

$('#popup-stock-update-btn').on('click', () => {
  $('.popup-stock').removeClass("active-popup");
  $('.popup-inv').addClass("active-popup");
});

$('#up-btn').on('click', () => {
  $('.popup-edit-confirm').addClass("active-popup");
  $('.overlay').addClass("active-overlay");
});

$('#Information-up-btn').on('click', () => {
  $('.popup-edit-confirm').addClass("active-popup");
  $('.overlay').addClass("active-overlay");
});

$('#up-supplier-btn').on('click', () => {
  $('.popup-edit-confirm').addClass("active-popup");
  $('.overlay').addClass("active-overlay");
});

$('#up-customer-btn').on('click', () => {
  $('.popup-edit-confirm').addClass("active-popup");
  $('.overlay').addClass("active-overlay");
});

$('#ep-no-btn').on('click', () => {
  $('.popup-edit-confirm').removeClass("active-popup");
  $('.overlay').removeClass("active-overlay"); // Remove class from overlay
});
$('.close-btn-popup').on('click', () => {
  $('.popup').removeClass("active-popup");
  $('.popup-info').removeClass("active-popup");
  $('.popup-info-supplier').removeClass("active-popup");
  $('.popup-info-customer').removeClass("active-popup");
  $('.popup-refund').removeClass("active-popup");
  $('.popup-inv').removeClass("active-popup");
  $('.popup-stock').removeClass("active-popup");
  $('.popup-paymentmetod').removeClass("active-popup");
  $('.popup-paymentmetod-card').removeClass("active-popup");
  $('.popup-paymentmetod-cash').removeClass("active-popup");
  $('.popup-edit-confirm').removeClass("active-popup");
  $('.overlay').removeClass("active-overlay"); // Remove class from overlay
});


//*********charts**********


// AREA CHART
const areaChartOptions = {
  series: [
    {
      name: 'Purchase Orders',
      data: [31, 40, 28, 51, 42, 109, 100],
    },
    {
      name: 'Sales Orders',
      data: [11, 32, 45, 32, 34, 52, 41],
    },
  ],
  chart: {
    type: 'area',
    background: 'transparent',
    height: 350,
    stacked: false,
    toolbar: {
      show: false,
    },
  },
  colors: ['#41f1b6', '#ff7782'],
  labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul'],
  dataLabels: {
    enabled: false,
  },
  fill: {
    gradient: {
      opacityFrom: 0.4,
      opacityTo: 0.1,
      shadeIntensity: 1,
      stops: [0, 100],
      type: 'vertical',
    },
    type: 'gradient',
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
      colors: '#f5f7ff',
    },
    show: true,
    position: 'top',
  },
  markers: {
    size: 6,
    strokeColors: '#1b2635',
    strokeWidth: 3,
  },
  stroke: {
    curve: 'smooth',
  },
  xaxis: {
    axisBorder: {
      color: '#55596e',
      show: true,
    },
    axisTicks: {
      color: '#55596e',
      show: true,
    },
    labels: {
      offsetY: 5,
      style: {
        colors: '#f5f7ff',
      },
    },
  },
  yaxis: [
    {
      title: {
        text: 'Purchase Orders',
        style: {
          color: '#f5f7ff',
        },
      },
      labels: {
        style: {
          colors: ['#f5f7ff'],
        },
      },
    },
    {
      opposite: true,
      title: {
        text: 'Sales Orders',
        style: {
          color: '#f5f7ff',
        },
      },
      labels: {
        style: {
          colors: ['#f5f7ff'],
        },
      },
    },
  ],
  tooltip: {
    shared: true,
    intersect: false,
    theme: 'dark',
  },
};

const areaChart = new ApexCharts(
  document.querySelector('#area-chart'),
  areaChartOptions
);
areaChart.render();


