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

function redirectIfNotAdmin(status, url) {
    if(status !== 1)
    {
        location.href = url;
    }
}