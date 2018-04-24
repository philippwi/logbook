var username = "#{homeBean.activeUser}";
if (username === "") {
    alert("Keine Berechtigung");
    location.href = "login.xhtml";
}