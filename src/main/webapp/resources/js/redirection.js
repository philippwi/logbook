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
