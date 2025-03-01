async function replaceHtml(i) {
    let userId = await tBody.rows[i].cells[0].innerHTML;
    div = await document.getElementById("toShowUserDetailsButton");
    div.id = await "toShowUserDetailsButton" + userId;

    // replace the div with the new html
    div.outerHTML = await "<form id=\"toDetailButtonInForm" + userId.toString() + "\"" + " name=\"toDetailButtonInForm\" class=\"userInfo\" method=\"GET\" action=\"/users/" + userId.toString() + "/show\">" +
        "<button form=\"toDetailButtonInForm" + userId.toString() + "\"" + " class=\"btn btn-outline-success\">詳&nbsp;細</button></form>";
}

window.addEventListener('load', async () => {
    tBody = document.getElementById("tableBody");
    rowsLength = tBody.rows.length;

    for (let i = 0; i < rowsLength; i++) {
        await replaceHtml(i);
    }
});
