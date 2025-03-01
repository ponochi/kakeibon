window.addEventListener('DOMContentLoaded', function() {
    fetch("/js/common/include/toEditUserButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("toEditUserButton").innerHTML = html);
});