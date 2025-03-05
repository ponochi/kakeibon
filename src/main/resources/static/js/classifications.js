const firstClassSelect = document.getElementById("firstClassSelect");
const secondClassSelect = document.getElementById("secondClassSelect");
const thirdClassSelect = document.getElementById("thirdClassSelect");

//=========================================================================
// Initial settings at year, month, date birthday of select box.
// @param void
// @return void
//=========================================================================
function setToClasssField() {
    firstClassesJson = document.getElementById("firstClassJson").value;
    const firstClasses = JSON.parse(firstClassesJson);
    secondClassesJson = document.getElementById("secondClassJson").value;
    const secondClasses = JSON.parse(secondClassesJson);
    thirdClassesJson = document.getElementById("thirdClassJson").value;
    const thirdClasses = JSON.parse(thirdClassesJson);

    //=========================================================================
    // Processing firstClass
    //=========================================================================

    for (var key in firstClasses) {
        var value = firstClasses[key];

        for (var key2 in value) {
            var value2 = value[key2];

            let fc_option = document.createElement("option");

            for (var key3 in value2) {
                if (key3 === "firstClassId") {
                    fc_option.value = value2[key3];
                }

                if (key3 === "firstClassName") {
                    fc_option.textContent = value2[key3];
                    fc_option.id = "firstClassOption";
                    firstClassSelect.appendChild(fc_option);
                }
            }
        }
    }

    //=========================================================================
    // Processing secondClass
    //=========================================================================

    for (var key in secondClasses) {
        let sc_option = document.createElement("option");
        var value = secondClasses[key];
        var doHiddenFlag = true;

        for (var key2 in value) {
            var value2 = value[key2];

            var firstClassSelectedIndex = (Number.parseInt(firstClassSelect.selectedIndex) + 1);
            var firstClassId = Number(value2);

            if (key2 === "firstClassId") {
                if (Object.is(firstClassSelectedIndex, firstClassId)) {
                    doHiddenFlag = false;
                } else {
                    doHiddenFlag = true;
                }
            }

            if ((key2 === "secondClassId")) {
                sc_option.value = value2;
            }

            if ((key2 === "secondClassName")) {
                sc_option.textContent = value2;
                sc_option.hidden = doHiddenFlag;
                sc_option.id = "secondClassOption";
                secondClassSelect.appendChild(sc_option);
            }
        }
    }

    //=========================================================================
    // Processing thirdClass
    //=========================================================================

    secondClassSelectedIndex = new Number(secondClassSelect.selectedIndex);

    for (var key in thirdClasses) {
        let tc_option = document.createElement("option");
        var value = thirdClasses[key];
        var doHiddenFlag = true;

        for (var key2 in value) {
            var value2 = value[key2];

            var secondClassSelectedIndex = (Number.parseInt(secondClassSelect.selectedIndex) + 1);
            var secondClassId = Number(value2);

            if (key2 === "secondClassId") {
                if (Object.is(secondClassSelectedIndex, secondClassId)) {
                    doHiddenFlag = false;
                } else {
                    doHiddenFlag = true;
                }
            }

            if ((key2 === "thirdClassId")) {
                tc_option.value = value[key2];
            }

            if ((key2 === "thirdClassName")) {
                tc_option.textContent = value[key2];
                tc_option.hidden = doHiddenFlag;
                tc_option.id = "thirdClassOption";
                thirdClassSelect.appendChild(tc_option);
            }
        }
    }
}

function setMajorAndMinorExpenseField() {
    firstClassesJson = document.getElementById("firstClassJson").value;
    const firstClasses = JSON.parse(firstClassesJson);
    secondClassesJson = document.getElementById("secondClassJson").value;
    const secondClasses = JSON.parse(secondClassesJson);
    thirdClassesJson = document.getElementById("thirdClassJson").value;
    const thirdClasses = JSON.parse(thirdClassesJson);

    //=========================================================================
    // Processing firstClass
    //=========================================================================
    let firstClassSelect
        = document.getElementById("firstClassSelect");
    let firstClassOption
        = document.getElementById("firstClassOption");

    //=========================================================================
    // Processing secondClass
    //=========================================================================

    /* Remove all options in secondClassSelect */
    for (let i = secondClassSelect.options.length; i >= 0; i--) {
        secondClassSelect.options.remove(i);
    }

    /* Get selected firstClassId */
    const secondClassResults = secondClasses.filter(function(value) {
        return value.firstClassId === Number(firstClassSelect.value);
    });

    /* Set secondClassSelect options */
    for (var key in secondClassResults) {
        let sc_option = document.createElement("option");
        var value = secondClassResults[key];

        for (var key2 in value) {
            if (key2 === "secondClassId") {
                sc_option.value = value[key2];
            }

            if (key2 === "secondClassName") {
                sc_option.textContent = value[key2];
                sc_option.id = "secondClassOption";
                secondClassSelect.appendChild(sc_option);
            }
        }
    }

    //=========================================================================
    // Processing thirdClass
    //=========================================================================

    /* Remove all options in secondClassSelect */
    for (let i = thirdClassSelect.options.length; i >= 0; i--) {
        thirdClassSelect.options.remove(i);
    }

    /* Get selected secondClassId */
    const thirdClassResults = thirdClasses.filter(function(value) {
        return value.secondClassId === Number(secondClassSelect.value);
    });

    /* Set secondClassSelect options */
    for (var key in thirdClassResults) {
        let sc_option = document.createElement("option");
        var value = thirdClassResults[key];

        for (var key2 in value) {
            if (key2 === "thirdClassId") {
                sc_option.value = value[key2];
            }

            if (key2 === "thirdClassName") {
                sc_option.textContent = value[key2];
                sc_option.id = "thirdClassOption";
                thirdClassSelect.appendChild(sc_option);
            }
        }
    }
}

function setMinorExpenseField() {
    secondClassesJson = document.getElementById("secondClassJson").value;
    const secondClasses = JSON.parse(secondClassesJson);
    thirdClassesJson = document.getElementById("thirdClassJson").value;
    const thirdClasses = JSON.parse(thirdClassesJson);

    //=========================================================================
    // Processing secondClass
    //=========================================================================
    let secondClassSelect
        = document.getElementById("secondClassSelect");

    //=========================================================================
    // Processing thirdClass
    //=========================================================================

    /* Remove all options in secondClassSelect */
    for (let i = thirdClassSelect.options.length; i >= 0; i--) {
        thirdClassSelect.options.remove(i);
    }

    /* Get selected secondClassId */
    const thirdClassResults = thirdClasses.filter(function(value) {
        return value.secondClassId === Number(secondClassSelect.value);
    });

    /* Set secondClassSelect options */
    for (var key in thirdClassResults) {
        let sc_option = document.createElement("option");
        var value = thirdClassResults[key];

        for (var key2 in value) {
            if (key2 === "thirdClassId") {
                sc_option.value = value[key2];
            }

            if (key2 === "thirdClassName") {
                sc_option.textContent = value[key2];
                sc_option.id = "thirdClassOption";
                thirdClassSelect.appendChild(sc_option);
            }
        }
    }
}

//=========================================================
// Convert boolean value with a type of string literal
// to value of boolean type.
// @param "true" or "false" type of string.
// @return true or false value, type of boolean.
//=========================================================
function toBoolean(boolStr) {
    return boolStr.toLowerCase() === "true";
}

window.addEventListener("DOMContentLoaded", setToClasssField);
firstClassSelect.addEventListener("change", setMajorAndMinorExpenseField);
secondClassSelect.addEventListener("change", setMinorExpenseField);