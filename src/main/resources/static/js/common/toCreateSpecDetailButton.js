window.addEventListener('DOMContentLoaded', () => {
    fetch("/js/common/include/toCreateSpecDetailButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("toCreateSpecDetailButton").innerHTML = html);
});
