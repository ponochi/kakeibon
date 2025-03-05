window.addEventListener('DOMContentLoaded', function() {
    fetch("/js/common/include/cancelButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("cancelButton").innerHTML = html);
});