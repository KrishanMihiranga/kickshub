import {suppliers} from "../db/supplier.js";
import {customers} from "../db/Customer.js";
import {employees} from "../db/employee.js";
var changeCat;

$('#inf-cat').change(function() {
    changeCat = $('input[name="category"]:checked').val();
    
    if(changeCat == 'customer'){
        $('#information-table-customer-wrapper').show();
        $('#information-table-supplier-wrapper').hide();
        $('#information-table-employee-wrapper').hide();
      }else if((changeCat == 'supplier')){
        $('#information-table-customer-wrapper').hide();
        $('#information-table-supplier-wrapper').show();
        $('#information-table-employee-wrapper').hide();
      }else if((changeCat == 'employee')){
        $('#information-table-customer-wrapper').hide();
        $('#information-table-supplier-wrapper').hide();
        $('#information-table-employee-wrapper').show();
      }else{
        $('#information-table-customer-wrapper').hide();
        $('#information-table-supplier-wrapper').hide();
        $('#information-table-employee-wrapper').hide();
      }
});


$('#info-log').on('click', () => {
  $('#save-changes-employee, .charts, .recent-orders, .sales, .expenses, .income, #page, #page-customer,#page-supplier, #update-profile, #refund-page, #add-item-page, #add-product-page, #inventory-page, #sale-page').hide();
  $('#information-page, #information-table-employee-wrapper').show();

  setDataToTable();
  setDataToCustomerTable();
  setDataToEmployeeTable();
});



function setDataToTable() {
  var tableBody = document.querySelector("#information-table-supplier-wrapper tbody");

  tableBody.innerHTML = "";

  suppliers.forEach(function(supplier) {
      var newRow = "<tr>";
      newRow += "<td>" + supplier.code + "</td>";
      newRow += "<td>" + supplier.supplierName + "</td>";
      newRow += "<td>" + supplier.email + "</td>";
      newRow += "<td>" + supplier.contactNo1 + "</td>";
      newRow += "<td>" + supplier.addressNo + ", " + supplier.addressLane + ", " + supplier.addressCity + "</td>";
      newRow += "<td>" + supplier.category + "</td>";
      newRow += "<td>" + supplier.postalCode + "</td>";
      newRow += "</tr>";

      tableBody.innerHTML += newRow;
  });
}

function setDataToCustomerTable() {
  var tableBody = document.querySelector("#information-table-customer-wrapper tbody");

  tableBody.innerHTML = "";

  customers.forEach(function(customer) {
      var newRow = "<tr>";
      newRow += "<td>" + customer.customerCode + "</td>";
      newRow += "<td>" + customer.name + "</td>";
      newRow += "<td>" + customer.addressNo + ", " + customer.addressLane + ", " + customer.addressCity + "</td>";
      newRow += "<td>" + customer.email + "</td>";
      newRow += "<td>" + customer.gender + "</td>";
      newRow += "<td>" + customer.dob + "</td>";
      newRow += "<td>" + customer.level + "</td>";
      newRow += "</tr>";

      tableBody.innerHTML += newRow;
  });
}

function setDataToEmployeeTable() {
  var tableBody = document.querySelector("#information-table-employee-wrapper tbody");

  tableBody.innerHTML = "";

  employees.forEach(function(employee) {
      var newRow = "<tr>";
      newRow += "<td><img src='data:image/png;base64," +employee.profilePic+"'></td>";
      newRow += "<td>" + employee.employeeCode + "</td>";
      newRow += "<td>" + employee.name + "</td>";
      newRow += "<td>" + employee.phone + "</td>";
      newRow += "<td>" + employee.addressNo + ", " + employee.addressLane + ", " + employee.addressCity + "</td>";
      newRow += "<td>" + employee.designation + "</td>";
      newRow += "<td>" + employee.email + "</td>";
      newRow += "</tr>";

      tableBody.innerHTML += newRow;
  });
}

