let http = new EasyHTTP();
let firstName = document.querySelector("#first-name");
let lastName = document.querySelector("#last-name");
let email = document.querySelector("#email");
let username = document.querySelector("#username");
let password = document.querySelector("#password");
let confirmedPassword = document.querySelector("#confirmed-password");
let role = "";
let form = document.querySelector("form");
let url = "http://localhost:8585/sign-up";


form.addEventListener("submit", (e) => {
    e.preventDefault();
    if (document.querySelector("#STUDENT").checked) role = 'STUDENT';
    else if (document.querySelector("#TEACHER").checked) role = 'TEACHER';

    let user = {
        "firstName": firstName.value, "lastName": lastName.value
        , "email": email.value, "username": username.value
        , "password": password.value, "role": role, "isEnable":false
    };
    http.post(url, user);
    console.log(user);
    firstName.value="";
    lastName.value="";
    username.value="";
    password.value="";
    confirmedPassword.value="";
    email.value="";
});
