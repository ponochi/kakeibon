// Reset balance type to 0 when loading the page
window.sessionStorage.clear();
window.sessionStorage.setItem("resetBalanceType", "t");

function* getUI() {
  const ui = document.getElementById("ui");
  const create = document.getElementById("create");
  const userIdSrc = document.getElementById("userIdSrc");
  const userIdDest = document.getElementById("userIdDest");

  userIdDest.setAttribute("value", userIdSrc.value);
  create.action += ("/" + ui.value);
}
