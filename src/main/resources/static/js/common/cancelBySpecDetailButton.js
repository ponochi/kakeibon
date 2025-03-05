window.addEventListener('DOMContentLoaded', function() {
    fetch("/js/common/include/cancelBySpecDetailButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("cancelBySpecDetailButton").innerHTML = html);
});
