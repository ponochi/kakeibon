const cancelBtn = document.getElementById("cancelButton");

//=========================================================================
// Cancel feature.
// @param void
// @return void
//=========================================================================
function doCancel() {

    let urlStr = window.location.href;

    if (urlStr.indexOf("/currency/add") > -1) {
        window.location.replace("/currency");
    } else if (urlStr.indexOf("/currency/edit") > -1) {
        window.location.replace("/currency");
    } else {
        var reg = new RegExp("\/currency\/([0-9]+)\/edit\?","g");
        var result = urlStr.match(reg);

        if (result) {
            if (urlStr.indexOf(result) > -1) {
                window.location.replace("/currency");
            }
        }
    }
}

cancelBtn.addEventListener("click", doCancel);
