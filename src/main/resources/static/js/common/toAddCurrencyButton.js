window.addEventListener('DOMContentLoaded', () => {
    fetch("/js/common/include/toAddCurrencyButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("toAddCurrencyButton").innerHTML = html);
});
