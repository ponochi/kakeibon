window.addEventListener('DOMContentLoaded', () => {
    fetch("/js/common/include/toCurrencyListButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("toCurrencyListButton").innerHTML = html);
});
