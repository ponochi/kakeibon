window.addEventListener('DOMContentLoaded', () => {
    fetch("/js/common/include/toCreateSpecButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("toCreateSpecButton").innerHTML = html);
});
