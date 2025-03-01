window.addEventListener('DOMContentLoaded', function() {
    fetch("/js/common/include/toCreateUserButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("toCreateUserButton").innerHTML = html);
});