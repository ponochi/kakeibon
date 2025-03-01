const shopButton = document.getElementById("shopButton");
const accountSourceButton = document.getElementById("accountSourceButton");
const accountDestinationButton = document.getElementById("accountDestinationButton");

const createSpecDetailButton = document.getElementById("toCreateSpecDetailButton");
const editSpecDetailButton = document.getElementById("toEditSpecDetailButton");

function saveSession() {
    sessionStorage.setItem("url", window.location.href);
}

function transitShopList() {
    let groupId = document.getElementById("specificationGroupId");
    let userId = document.getElementById("userId");
    let accountAndBalanceId = document.getElementById("accountAndBalanceId");
    let shopId = document.getElementById("shopId");
    let balanceTypeId = document.getElementById("balanceTypeId");
    let accountSourceId = document.getElementById("accountSourceId");
    let accountDestinationId = document.getElementById("accountDestinationId");

    let sgDeleted = document.getElementById("sg-deleted");

    sgDeleted.value = false;

    let saveGroup
        = document.getElementById("commit");

    if (saveGroup) {
        saveGroup.action
            = "/" + groupId.value +
            "/" + userId.value +
            "/" + accountAndBalanceId.value +
            "/" + shopId.value +
            "/" + balanceTypeId.value +
            "/" + accountSourceId.value +
            "/" + accountDestinationId.value +
            "/searchShop";

        saveGroup.method = "POST";
        document.forms["commit"].submit();
    }
}

function transitAccountSourceList() {
    let groupId = document.getElementById("specificationGroupId");
    let userId = document.getElementById("userId");
    let accountAndBalanceId = document.getElementById("accountAndBalanceId");
    let shopId = document.getElementById("shopId");
    let balanceTypeId = document.getElementById("balanceTypeId");
    let accountSourceId = document.getElementById("accountSourceId");
    let accountDestinationId = document.getElementById("accountDestinationId");

    let sgDeleted = document.getElementById("sg-deleted");

    sgDeleted.value = false;

    let saveGroup
        = document.getElementById("commit");

    if (saveGroup) {
        saveGroup.action
            = "/" + groupId.value +
            "/" + userId.value +
            "/" + accountAndBalanceId.value +
            "/" + shopId.value +
            "/" + balanceTypeId.value +
            "/" + accountSourceId.value +
            "/" + accountDestinationId.value +
            "/searchAccountSource";

        saveGroup.method = "POST";
        document.forms["saveGroup"].submit();
    }
}

function transitAccountDestinationList() {
    let groupId = document.getElementById("specificationGroupId");
    let userId = document.getElementById("userId");
    let accountAndBalanceId = document.getElementById("accountAndBalanceId");
    let shopId = document.getElementById("shopId");
    let balanceTypeId = document.getElementById("balanceTypeId");
    let accountSourceId = document.getElementById("accountSourceId");
    let accountDestinationId = document.getElementById("accountDestinationId");

    let sgDeleted = document.getElementById("sg-deleted");

    sgDeleted.value = false;

    let saveGroup
        = document.getElementById("commit");

    if (saveGroup) {
        saveGroup.action
            = "/" + groupId.value +
            "/" + userId.value +
            "/" + accountAndBalanceId.value +
            "/" + shopId.value +
            "/" + balanceTypeId.value +
            "/" + accountSourceId.value +
            "/" + accountDestinationId.value +
            "/searchAccountDestination";

        saveGroup.method = "POST";
        document.forms["commit"].submit();
    }
}

function transitCreateSpecDetail() {
    let groupId = document.getElementById("specificationGroupId");
    let userId = document.getElementById("userId");
    let accountAndBalanceId = document.getElementById("accountAndBalanceId");
    let shopId = document.getElementById("shopId");
    let balanceTypeId = document.getElementById("balanceTypeId");
    let accountSourceId = document.getElementById("accountSourceId");
    let accountDestinationId = document.getElementById("accountDestinationId");

    console.log("transitCreateSpecDetail");

    let sgDeleted = document.getElementById("sg-deleted");

    sgDeleted.value = false;

    let saveGroup
        = document.getElementById("commit");

    if (saveGroup) {
        saveGroup.action
            = "/spec/create/detail" +
            "/" + groupId.value +
            "/" + userId.value +
            "/" + accountAndBalanceId.value +
            "/" + shopId.value +
            "/" + balanceTypeId.value +
            "/" + accountSourceId.value +
            "/" + accountDestinationId.value;

        saveGroup.method = "POST";
        document.forms["commit"].submit();
    }
}

function transitEditSpecDetail() {
    let groupId = document.getElementById("specificationGroupId");
    let userId = document.getElementById("userId");
    let accountAndBalanceId = document.getElementById("accountAndBalanceId");
    let shopId = document.getElementById("shopId");
    let balanceTypeId = document.getElementById("balanceTypeId");
    let accountSourceId = document.getElementById("accountSourceId");
    let accountDestinationId = document.getElementById("accountDestinationId");

    let sgDeleted = document.getElementById("sg-deleted");

    let specDetail
        = "specDetail" + document.getElementById("specDetailId");

    sgDeleted.value = false;

    let specDetailForm
        = document.getElementById(specDetail);

    specDetailForm.action
        = "/spec/create/detail" +
        "/" + groupId.value +
        "/" + userId.value +
        "/" + accountAndBalanceId.value +
        "/" + shopId.value +
        "/" + balanceTypeId.value +
        "/" + accountSourceId.value +
        "/" + accountDestinationId.value;

    specDetailForm.method = "POST";
    document.forms[specDetail].submit();
}

function blurSave() {
    let groupId = document.getElementById("specificationGroupId");
    let userId = document.getElementById("userId");
    let accountAndBalanceId = document.getElementById("accountAndBalanceId");
    let shopId = document.getElementById("shopId");
    let balanceTypeId = document.getElementById("balanceTypeId");
    let accountSourceId = document.getElementById("accountSourceId");
    let accountDestinationId = document.getElementById("accountDestinationId");

    let sgDeleted = document.getElementById("sg-deleted");

    let updateGroup
        = document.getElementById("commit");

    updateGroup.action
        = "/spec/update/group" +
        "/" + groupId.value +
        "/" + userId.value;

    updateGroup.method = "POST";
    document.forms["updateGroup"].submit();
}

window.addEventListener("DOMContentLoaded", saveSession);

shopButton.addEventListener("click", transitShopList);
accountSourceButton.addEventListener("click", transitAccountSourceList);
accountDestinationButton.addEventListener("click", transitAccountDestinationList);
createSpecDetailButton.addEventListener("click", transitCreateSpecDetail);
editSpecDetailButton.addEventListener("click", transitEditSpecDetail);
