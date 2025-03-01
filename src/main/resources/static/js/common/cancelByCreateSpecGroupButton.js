window.addEventListener('DOMContentLoaded', function() {
    fetch("/js/common/include/cancelByCreateSpecGroupButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("cancelByCreateSpecGroupButton").innerHTML = html);
});
