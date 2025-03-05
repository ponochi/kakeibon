const cancelByCreateSpecGroupButton = document.getElementById("cancelByCreateSpecGroupButton");

//=========================================================================
// Cancel feature.
// @param void
// @return void
//=========================================================================
function doCancelByCreateSpecGroup() {

    const pattern1 = "/spec/delete/group";
    const pattern2 = "/spec/create/group";

    let urlStr = window.location.href;
    let transitUrl = pattern1 +
        "/" + specificationGroupId.value +
        "/" + userId.value;

    console.log(urlStr + " to " + transitUrl);

    if (urlStr.indexOf(pattern2) > -1) {

        console.log(urlStr + " to " + transitUrl);

        let f = document.createElement("form");
        f.action = transitUrl;
        f.method = "POST";
        document.body.append(f);
        f.submit();
    }
}

cancelByCreateSpecGroupButton.addEventListener("click",  doCancelByCreateSpecGroup);
