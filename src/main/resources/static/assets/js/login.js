/*----------------- Variables Definition -----------------*/
let fetch = new FetchService();
let username = document.querySelector("#username");
let password = document.querySelector("#password");
let form = document.querySelector("form");
let url = serverUrl().concat("/sign-in");
let msg = document.querySelector("p");
let signInAccount;

/*----------------- Class Definition -----------------*/
class SignInAccount {
    constructor(username, password) {
        this.password = password;
        this.username = username;
    }
}




/*----------------- functions Definition -----------------*/

function hideError() {
    setTimeout(msg.textContent="",3000);
}

function showError(err){
    msg.style.color = "red";
    msg.textContent = err;
    hideError();
}
/*----------------- Event Definition -----------------*/
form.addEventListener("submit", ev => {
    ev.preventDefault();
    signInAccount = {"username":username.value , "password":password.value};
    console.log(signInAccount);
    fetch.post(url,signInAccount)
        .then(data => {
        console.log(data);
        if(data === null) showError("نام کاربری یا رمز ورود اشتباه است.");
        else console.log(data);
    });

});