window.addEventListener('DOMContentLoaded', function() {
    fetch("/js/common/include/toUsersListButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("toUsersListButton").innerHTML = html);
});