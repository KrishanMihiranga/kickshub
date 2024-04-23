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

// Show sidebar
menuBtn.on('click', function() {
  sideMenu.css("display", "block");
});

// Close sidebar
closeBtn.on('click', function() {
  sideMenu.css("display", "none");
});

//notification
submitButton.on('click',()=>{
  toast.addClass("active-noti");
  progressNoti.addClass("active-noti");
  setTimeout(()=>{
    toast.removeClass("active-noti");
  },5000);

  setTimeout(()=>{
    progressNoti.removeClass("active-noti");
  },5300);
  
});
closeIcon.on('click',()=>{
  toast.removeClass("active-noti");

  setTimeout(()=>{
    progressNoti.removeClass("active-noti");
  },300);
});

// Change theme
themeToggler.on('click', function() {
    $("body").toggleClass("dark-theme-variables");
    themeToggler.find("span:nth-child(1)").toggleClass("active");
    themeToggler.find("span:nth-child(2)").toggleClass("active");
});

//swap active class
$('#dashboard-btn, #add-employee, #add-customer, #add-supplier').click(function() {
  $('#dashboard-btn, #add-employee, #add-customer, #add-supplier').removeClass('active');
  $(this).addClass('active');
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
  select.empty();
  selectCustomer.empty();
  selectSupplier.empty();

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
  });
}

$(document).ready(function() {
  populateCountries();
  $('#country').val('Sri Lanka');
  $('#country-customer').val('Sri Lanka');
  $('#country-Supplier').val('Sri Lanka');
});


//register form
nextButton.on('click',function(){
  active++;
  activeCustomer++;
  activeSupplier++;
  if(active > steps.length){
    active = steps.length;
  }else if(activeCustomer > stepsCustomer.length){
    activeCustomer = stepsCustomer.length;
  }else if(activeSupplier > stepsSupplier.length){
    activeSupplier = stepsSupplier.length;
  }
  updateProgress();
});

prevButton.on('click',function(){
  active--;
  activeCustomer--;
  activeSupplier--;
  if(active < 1){
    active = 1;
  }else if(activeCustomer < 1){
    activeCustomer = 1;
  }else if(activeSupplier < 1){
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

  stepsSupplier.each((i, step)=>{
    if(i === (activeSupplier -1)){
      $(step).addClass('active-step');
      $(form_stepsSupplier[i]).addClass('active-step');
      console.log('i +> '+ i);
    }else{
      $(step).removeClass('active-step');
      $(form_stepsSupplier[i]).removeClass('active-step');
    }
  });

  stepsCustomer.each((i, step)=>{
    if(i === (activeCustomer -1)){
      $(step).addClass('active-step');
      $(form_stepsCustomer[i]).addClass('active-step');
      console.log('i +> '+ i);
    }else{
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

//choose dp
file.on('change', function() {
  const choosedFile = this.files[0];
  if (choosedFile) {
      const reader = new FileReader();

      reader.onload = function() {
          img.attr('src', reader.result);
      };

      reader.readAsDataURL(choosedFile);
  }
});

//*********charts**********

//bar chart
const barChartOptions = {
    series: [
      {
        data: [10, 8, 6, 4, 2],
        name: 'Products',
      },
    ],
    chart: {
      type: 'bar',
      background: 'transparent',
      height: 350,
      toolbar: {
        show: false,
      },
    },
    colors: ['#2962ff', '#ff7782', '#41f1b6', '#ffbb55', '#7380ec'],
    plotOptions: {
      bar: {
        distributed: true,
        borderRadius: 4,
        horizontal: false,
        columnWidth: '40%',
      },
    },
    dataLabels: {
      enabled: false,
    },
    fill: {
      opacity: 1,
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
    stroke: {
      colors: ['transparent'],
      show: true,
      width: 2,
    },
    tooltip: {
      shared: true,
      intersect: false,
      theme: 'dark',
    },
    xaxis: {
      categories: ['Laptop', 'Phone', 'Monitor', 'Headphones', 'Camera'],
      title: {
        style: {
          color: '#f5f7ff',
        },
      },
      axisBorder: {
        show: true,
        color: '#55596e',
      },
      axisTicks: {
        show: true,
        color: '#55596e',
      },
      labels: {
        style: {
          colors: '#f5f7ff',
        },
      },
    },
    yaxis: {
      title: {
        text: 'Count',
        style: {
          color: '#f5f7ff',
        },
      },
      axisBorder: {
        color: '#55596e',
        show: true,
      },
      axisTicks: {
        color: '#55596e',
        show: true,
      },
      labels: {
        style: {
          colors: '#f5f7ff',
        },
      },
    },
  };
  
  const barChart = new ApexCharts(
    document.querySelector('#bar-chart'),
    barChartOptions
  );
  barChart.render();

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

  
  //routes
  $('#add-employee').on('click', () => {
    $('.charts, .recent-orders, .sales, .expenses, .income, #page-customer, #page-supplier').hide();
    $('#page').show();
  });
  $('#dashboard-btn').on('click', () => {
    $('.charts, .recent-orders, .sales, .expenses, .income').show();
    $('#page, #page-customer, #page-supplier').hide();
  });
  $('#add-customer').on('click', () => {
    $('.charts, .recent-orders, .sales, .expenses, .income, #page, #page-supplier').hide();
    $('#page-customer').show();
  });
  $('#add-supplier').on('click', () => {
    $('.charts, .recent-orders, .sales, .expenses, .income, #page, #page-customer').hide();
    $('#page-supplier').show();
  });