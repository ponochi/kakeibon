const cancelByEditButton = document.getElementById("cancelByEditButton");

//=========================================================================
// Cancel feature.
// @param void
// @return void
//=========================================================================
function doCancelByEdit() {

    const pattern1 = "/spec/delete/group";
    const pattern2 = "/spec/edit/group";

    let urlStr = window.location.href;
    let transitUrl = pattern1 +
        "/" + specificationGroupId.value +
        "/" + userId.value;

    console.log(urlStr + " to " + transitUrl);

    if (urlStr.indexOf(pattern2, 0) > -1) {

        let f = document.createElement("form");
        f.action = transitUrl;
        f.method = "POST";
        document.body.append(f);
        f.submit();
    }
}

cancelByEditButton.addEventListener("click",  doCancelByEdit);
