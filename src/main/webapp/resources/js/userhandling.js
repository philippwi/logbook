function checkAtHome(username) {
    if (username === "")
    {
        alert("Keine Berechtigung");
        location.href = "login.xhtml";
    }
}

function checkAtOther(username) {
    if (username !== "")
    {
        location.href = "home.xhtml";
    }
}
