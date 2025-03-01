let balanceType
    = window.sessionStorage.getItem("balanceType");

const specificationGroupId
    = document.getElementById("specificationGroupId");
const userId
    = document.getElementById("userId");

let balanceTypeId
    = document.getElementById("balanceTypeId");
let balanceTypeIdInput
    = document.getElementById("balanceTypeIdInput");
var shopId
    = document.getElementById("shopId");
var accountAndBalanceId
    = document.getElementById("accountAndBalanceId");
var accountSourceId
    = document.getElementById("accountSourceId");
var accountDestinationId
    = document.getElementById("accountDestinationId");

const receivingAndPaymentDate = document.getElementById("receivingAndPaymentDate");
const receivingAndPaymentTime = document.getElementById("receivingAndPaymentTime");
const groupMemo = document.getElementById("groupMemo");

let price
    = document.getElementById("price");
let currencyId
    = document.getElementById("currencyId");
let quantity
    = document.getElementById("quantity");
let taxTypeId
    = document.getElementById("taxTypeId");
let taxRateId
    = document.getElementById("taxRateId");

function changedShop() {
    let specificationGroupId
        = document.getElementById("specificationGroupId");
    let userId
        = document.getElementById("userId");
    let shopId
        = document.getElementById("shopId");
    let accountSourceId
        = document.getElementById("accountSourceId");
    let accountDestinationId
        = document.getElementById("accountDestinationId");
    let balanceTypeId
        = document.getElementById("balanceTypeId");
    if (shopId.value === 1 && accountSourceId.value === 1 && accountDestinationId.value === 1) {
        balanceType = 1;
        console.log("changedShop()")
        changedBalanceType();
    }
    window.sessionStorage.setItem("balanceType", balanceTypeId.value);
}

function changedAccountSource() {
    let specificationGroupId
        = document.getElementById("specificationGroupId");
    let userId
        = document.getElementById("userId");
    let shopId
        = document.getElementById("shopId");
    let accountSourceId
        = document.getElementById("accountSourceId");
    let accountDestinationId
        = document.getElementById("accountDestinationId");
    let balanceTypeId
        = document.getElementById("balanceTypeId");
    if (shopId.value === 1 && accountSourceId.value === 1 && accountDestinationId.value === 1) {
        balanceType = 1;
        console.log("changedAccountSource()")
        changedBalanceType();
    }
    window.sessionStorage.setItem("balanceType", balanceTypeId.value);
}

function changedAccountDestination() {
    let specificationGroupId
        = document.getElementById("specificationGroupId");
    let userId
        = document.getElementById("userId");
    let shopId
        = document.getElementById("shopId");
    let accountSourceId
        = document.getElementById("accountSourceId");
    let accountDestinationId
        = document.getElementById("accountDestinationId");
    let balanceTypeId
        = document.getElementById("balanceTypeId");
    if (shopId.value === 1 && accountSourceId.value === 1 && accountDestinationId.value === 1) {
        balanceType = 1;
        console.log("changedAccountDestination()")
        changedBalanceType();
    }
    window.sessionStorage.setItem("balanceType", balanceTypeId.value);
}

//=========================================================
// Change event of Balance Type
// @param none
// @return none
//=========================================================
function setActionOfSearchButtons() {
    const receivingAndPaymentDateControl
      = document.getElementById("receivingAndPaymentDate");
    const receivingAndPaymentTimeControl
      = document.getElementById("receivingAndPaymentTime");

    let balanceTypeId
        = document.getElementById("balanceTypeId");
    let balanceTypeIdInput
        = document.getElementById("balanceTypeIdInput");

    const searchShop
      = document.getElementById("searchShop");
    const searchAccountSource
      = document.getElementById("searchAccountSource");
    const searchAccountDestination
      = document.getElementById("searchAccountDestination");


    //==== Set action of search shop form ====//
    searchShop.action = "/" + specificationGroupId.value;
    searchShop.action += "/" + userId.value;
    searchShop.action += "/" + accountAndBalanceId.value;
    if (shopId.value) {
        searchShop.action += "/" + shopId.value;
    } else {
        searchShop.action += "/1";
    }
    if (balanceTypeIdInput.value) {
        searchShop.action += "/";
        searchShop.action
          += balanceTypeIdInput.value;
    } else {
        searchShop.action += "/1";
    }
    if (accountSourceId.value) {
        searchShop.action += "/" + accountSourceId.value;
    } else {
        searchShop.action += "/1";
    }
    if (accountDestinationId.value) {
        searchShop.action += "/" + accountDestinationId.value;
    } else {
        searchShop.action += "/1";
    }
    searchShop.action += "/searchShop";

    //==== Set action of search account source form ====//
    searchAccountSource.action = "/" + specificationGroupId.value;
    searchAccountSource.action += "/" + userId.value;
    searchAccountSource.action += "/" + accountAndBalanceId.value;
    if (shopId.value) {
        searchAccountSource.action += "/" + shopId.value;
    } else {
        searchAccountSource.action += "/1";
    }
    if (balanceTypeIdInput.value) {
        searchAccountSource.action += "/";
        searchAccountSource.action
          += balanceTypeIdInput.value;
    } else {
        searchAccountSource.action += "/1";
    }
    if (accountSourceId.value) {
        searchAccountSource.action += "/" + accountSourceId.value;
    } else {
        searchAccountSource.action += "/1";
    }
    if (accountDestinationId.value) {
        searchAccountSource.action += "/" + accountDestinationId.value;
    } else {
        searchAccountSource.action += "/1";
    }
    searchAccountSource.action += "/searchAccountSource";

    //==== Set action of search account destination form ====//
    searchAccountDestination.action = "/" + specificationGroupId.value;
    searchAccountDestination.action += "/" + userId.value;
    searchAccountDestination.action += "/" + accountAndBalanceId.value;
    if (shopId.value) {
        searchAccountDestination.action += "/" + shopId.value;
    } else {
        searchAccountDestination.action += "/1";
    }
    if (balanceTypeIdInput.value) {
        searchAccountDestination.action += "/";
        searchAccountDestination.action
          += balanceTypeIdInput.value;
    } else {
        searchAccountDestination.action += "/1";
    }
    if (accountSourceId.value) {
        searchAccountDestination.action += "/" + accountSourceId.value;
    } else {
        searchAccountDestination.action += "/1";
    }
    if (accountDestinationId.value) {
        searchAccountDestination.action += "/" + accountDestinationId.value;
    } else {
        searchAccountDestination.action += "/1";
    }
    searchAccountDestination.action += "/searchAccountDestination";
}

//=========================================================
function changedBalanceType() {
    let option = balanceTypeId[balanceTypeId.selectedIndex];
    balanceTypeIdInput.value = option.value;
    window.sessionStorage.setItem(
        "balanceType", balanceTypeIdInput.value);

    let accountSourceName
        = document.getElementById("accountSourceName");
    let accountSourceButton
        = document.getElementById("accountSourceButton");
    let accountDestinationName
        = document.getElementById("accountDestinationName");
    let accountDestinationButton
        = document.getElementById("accountDestinationButton");

    console.log("balanceTypeIdInput.value : " + balanceTypeIdInput.value);

    if (Number(balanceTypeIdInput.value) === 1) {
        accountSourceName.disabled = false;
        accountSourceButton.disabled = false;
        accountDestinationName.disabled = true;
        accountDestinationButton.disabled = true;
    } else if (Number(balanceTypeIdInput.value) === 2) {
        accountSourceName.disabled = true;
        accountSourceButton.disabled = true;
        accountDestinationName.disabled = false;
        accountDestinationButton.disabled = false;
    } else if (Number(balanceTypeIdInput.value) === 3) {
        accountSourceName.disabled = false;
        accountSourceButton.disabled = false;
        accountDestinationName.disabled = false;
        accountDestinationButton.disabled = false;
    }
}

function getDate(now) {
    return now.getFullYear() + "-"
        + ("0" + (now.getMonth() + 1)).slice(-2) + "-"
        + ("0" + now.getDate()).slice(-2);
}

function getTime(now) {
    return ("0" + now.getHours()).slice(-2) + ":"
        + ("0" + now.getMinutes()).slice(-2);
}

function calculateTax() {
    let taxTypeId = document.getElementById("taxTypeId");
    let taxRateId = document.getElementById("taxRateId");

    let price = document.getElementById("price");
    let quantity = document.getElementById("quantity");
    let taxRate = document.getElementById("taxRate");
    let tax = document.getElementById("tax");

    let str = taxRateId.options[taxRateId.selectedIndex].innerText;
    let rate = Number(str.substring(0, str.length - 1));

    if (Number(taxTypeId.value) === 1) {
        tax.value
            = Math.round(
            price.value
            * quantity.value
            * rate / 100);
    } else if (Number(taxTypeId.value) === 2) {
        tax.value
            = Math.round(
            price.value
            * quantity.value
            / ((rate + 100) / 100) * (rate / 100));
    } else {
        tax.value = 0;
    }
}

//=========================================================
// Load event of window
// @param none
// @return none
//=========================================================
function windowPageShow() {
    balanceType
        = window.sessionStorage.getItem("balanceType");
    let receivingAndPaymentDate
        = window.sessionStorage.getItem("receivingAndPaymentDate");
    let receivingAndPaymentTime
        = window.sessionStorage.getItem("receivingAndPaymentTime");

    let balanceTypeId
        = document.getElementById("balanceTypeId");
    let receivingAndPaymentDateControl
        = document.getElementById("receivingAndPaymentDate");
    let receivingAndPaymentTimeControl
        = document.getElementById("receivingAndPaymentTime");
    let shopId
        = document.getElementById("shopId");
    let accountSourceId
        = document.getElementById("accountSourceId");
    let accountDestinationId
        = document.getElementById("accountDestinationId");

    let now = new Date();
    let nowDate = getDate(now);
    let nowTime = getTime(now);

    if (Number(shopId.value) === 1
        && Number(accountSourceId.value) === 1
        && Number(accountDestinationId.value) === 1) {
        balanceType = 1;
    }

    url
        = window.location.href.split('/');

    balanceType
        = window.sessionStorage.getItem("balanceType");

    if (!balanceType) {
        if (!balanceTypeIdInput.value) {
            balanceTypeId.selectedIndex = 1;
            window.sessionStorage.setItem("balanceType", "1");
        } else {
            balanceTypeId.selectedIndex
                = balanceTypeIdInput.value;
            window.sessionStorage.setItem(
                "balanceType", balanceTypeIdInput.value);
        }
    } else {
        balanceTypeId.selectedIndex
            = balanceTypeIdInput.value;
        window.sessionStorage.setItem(
            "balanceType", balanceTypeIdInput.value);
    }

    console.log("windowPageShow()")
    changedBalanceType();
}

window.addEventListener("pageshow", windowPageShow, false);

balanceTypeId.addEventListener("change", changedBalanceType, false);
shopId.addEventListener("change", changedShop, false);
accountSourceId.addEventListener("change", changedAccountSource, false);
accountDestinationId.addEventListener("change", changedAccountDestination, false);

receivingAndPaymentDate.addEventListener("blur", blurSave);
receivingAndPaymentTime.addEventListener("blur", blurSave);
groupMemo.addEventListener("blur", blurSave);

price.addEventListener("change", calculateTax, false);
currencyId.addEventListener("change", calculateTax, false);
quantity.addEventListener("change", calculateTax, false);
taxTypeId.addEventListener("change", calculateTax, false);
taxRateId.addEventListener("change", calculateTax, false);
