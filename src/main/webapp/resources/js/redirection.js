function redirectIfNotLoggedIn(username, url) {
    if (username === "")
    {
        location.href = url;
    }
}

function redirectIfLoggedIn(username, url) {
    if(username !== "")
    {
        location.href = url;
    }
}

function redirectIfNotAdmin(isAdmin, url) {
    if(isAdmin !== "true")
    {
        alert("Keine Admin-Rechte!");
        location.href = url;
    }
}