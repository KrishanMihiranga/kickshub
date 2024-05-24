
import { inventoryItems } from "../db/inventory.js";

$('#inventory-btn').on('click', () => {
  $('#page-user, #save-changes-employee, .charts, .recent-orders, .sales, .expenses, .income, #page, #page-customer,#page-supplier, #update-profile, #information-page, #recent-orders-refund-page, #refund-page, #add-item-page, #add-product-page, #sale-page').hide();
  $('#inventory-page').show();



  setDataToInventoryTable();
});


function setDataToInventoryTable() {
  var tableBody = document.querySelector("#inventory-page tbody");

  tableBody.innerHTML = "";

  inventoryItems.forEach(function (item) {
    var newRow = "<tr>";

    var colorIndicator = "<div class='inv-p-t-color " + item.colors.toLowerCase() + "'></div>";

    newRow += "<td>" + colorIndicator + "</td>";
    newRow += "<td><img src='data:image/png;base64," + item.itemImage.image + "'></td>";
    newRow += "<td>" + item.inventoryCode + "</td>";
    newRow += "<td>" + item.item.description + "</td>";
    newRow += "<td>" + item.item.supplierName + "</td>";
    newRow += "<td>" + item.size + "</td>";
    newRow += "<td>" + item.originalQty + "</td>";
    newRow += "<td>" + item.currentQty + "</td>";
    newRow += "<td>" + item.status + "</td>";
    newRow += "</tr>";

    tableBody.innerHTML += newRow;
  });
}

