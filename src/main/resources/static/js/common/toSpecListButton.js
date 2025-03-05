window.addEventListener('DOMContentLoaded', () => {
    fetch("/js/common/include/toSpecListButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("toSpecListButton").innerHTML = html);
});
