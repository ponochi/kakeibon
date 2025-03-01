window.addEventListener('DOMContentLoaded', function() {
    fetch("/js/common/include/cancelByEditSpecGroupButton.html")
        .then((data) => data.text())
        .then((html) => document.getElementById("cancelByEditSpecGroupButton").innerHTML = html);
});
