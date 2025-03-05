function goBackUrl() {
  const specificationGroupId = document.getElementById("specificationGroupId");
  const specificationId = document.getElementById("specificationId");
  const userId = document.getElementById("userId");
  const accountAndBalanceId = document.getElementById("accountAndBalanceId");
  const shopId = document.getElementById("shopId");
  const balanceTypeId = document.getElementById("balanceTypeId");
  const accountSourceId = document.getElementById("accountSourceId");
  const accountDestinationId = document.getElementById("accountDestinationId");
  const receivingAndPaymentDate = document.getElementById("receivingAndPaymentDate");
  const receivingAndPaymentTime = document.getElementById("receivingAndPaymentTime");
  const groupMemo = document.getElementById("groupMemo");

  let urlStr = window.location.href;

  const pattern1 = "/spec/create/group";
  const pattern2 = "/spec/edit/group";
  const pattern3 = "/spec/create/detail";
  const pattern4 = "/spec/edit/detail";
  const pattern5 = "/spec";

  // let transitUrlStr = sessionStorage.getItem("url");
  // let start = urlStr.indexOf(pattern5);
  // let end = urlStr.length;

  let transitUrl = "";

  if (urlStr.indexOf(pattern3) > -1) {
    transitUrl = pattern2 +
      "/" + specificationGroupId.value +
      // "/" + specificationId.value +
      "/" + userId.value +
      "/" + accountAndBalanceId.value +
      "/" + shopId.value +
      "/" + balanceTypeId.value +
      "/" + accountSourceId.value +
      "/" + accountDestinationId.value +
      "/" + receivingAndPaymentDate.value +
      "/" + receivingAndPaymentTime.value +
      "?groupMemo=" + encodeURIComponent(groupMemo.value);
  }
  if (urlStr.indexOf(pattern4) > -1) {
    transitUrl = pattern2 +
      "/" + specificationGroupId.value +
      // "/" + specificationId.value +
      "/" + userId.value +
      "/" + accountAndBalanceId.value +
      "/" + shopId.value +
      "/" + balanceTypeId.value +
      "/" + accountSourceId.value +
      "/" + accountDestinationId.value +
      "/" + receivingAndPaymentDate.value +
      "/" + receivingAndPaymentTime.value +
      "?groupMemo=" + encodeURIComponent(groupMemo.value);
  }

  let f = document.createElement("form");
  f.action = transitUrl;
  f.method = "GET";
  document.body.append(f);
  f.submit();
}
