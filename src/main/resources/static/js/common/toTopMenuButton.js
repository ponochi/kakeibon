window.addEventListener('DOMContentLoaded', function() {
    fetch("/js/common/include/toTopMenuButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("toTopMenuButton").innerHTML = html);
});