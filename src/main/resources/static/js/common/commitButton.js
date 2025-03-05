window.addEventListener('DOMContentLoaded', function() {
    fetch("/js/common/include/commitButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("commitButton").innerHTML = html);
});