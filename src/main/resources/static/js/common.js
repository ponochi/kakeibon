//=========================================================
// Convert boolean value with a type of string literal
// to value of boolean type.
// @param "true" or "false" tyoe of string.
// @return true or false value, type of boolean.
//=========================================================
function toBoolean(boolStr) {
    return boolStr.toLowerCase() === "true";
}
