/*----------------- Variables Definition -----------------*/
let http = new FetchService();
let firstName = document.querySelector("#first-name");
let lastName = document.querySelector("#last-name");
let email = document.querySelector("#email");
let username = document.querySelector("#username");
let password = document.querySelector("#password");
let confirmedPassword = document.querySelector("#confirmed-password");
let err = document.querySelector("#error-place");
let role = "";
let form = document.querySelector("form");
let url = serverUrl().concat("/sign-up");
let option;

/*----------------- functions Definition -----------------*/
function checkElementValidation() {

    if (firstName.value === "") {
        showMessageToUser("نام را وارد کنید", 'error');
        throw new DOMException("empty first name field");
    } else if (lastName.value === "") {
        showMessageToUser("نام خانوادگی را وارد کنید", 'error');

        throw new DOMException("empty last name field");
    } else if (email.value === "") {
        showMessageToUser("ایمیل را وارد کنید", 'error');

        throw new DOMException("empty email field");
    } else if (username.value === "") {
        showMessageToUser("نام کاربری را وارد کنید", 'error');

        throw new DOMException("empty username field");
    } else if (password.value === "") {
        showMessageToUser("رمز ورود را وارد کنید", 'error');

        throw new DOMException("empty password field");
    } else if (confirmedPassword.value === "") {
        showMessageToUser("رمز ورود را تأیید کنید", 'error');

        throw new DOMException("empty confirmed password field");
    } else if (username.value.length < 6) {
        showMessageToUser("نام کاربری باید بیشتر از 5 کاراکتر باشد.", 'error');

        throw new DOMException("invalid username");
    } else if (password.value.length < 8) {
        showMessageToUser("رمز ورود باید بیشتر از 7 کاراکتر باشد", 'error');

        throw new DOMException("invalid password");
    } else if (password.value !== confirmedPassword.value.toLowerCase()) {
        showMessageToUser("عدم تطابق رمز ورود", 'error');
        throw new DOMException("invalid confirmed password");
    }
}

function clearFields() {
    firstName.value = "";
    lastName.value = "";
    username.value = "";
    password.value = "";
    confirmedPassword.value = "";
    email.value = "";
    option.selectedIndex = 0;
}

function showMessageToUser(msg, styleClassName) {
    err.textContent = "";
    err.className = styleClassName;
    err.textContent = msg;
    setTimeout(() => {
        err.textContent = "";
        err.className = "";
    }, 5000)
}

/*----------------- Event Definition -----------------*/
form.addEventListener("submit", e => {
    e.preventDefault();
    option = document.querySelector("#role-options");
    if (option.selectedIndex === 0) role = 'student';
    else if (option.selectedIndex === 1) role = 'teacher';
    let user = {
        "firstName": firstName.value.toLowerCase(), "lastName": lastName.value.toLowerCase()
        , "email": email.value.toLowerCase(), "username": username.value.toLowerCase()
        , "password": password.value, "roleType": role, "isEnable": false};

    checkElementValidation();
    http.post(url, user)
        .then(msg => {
            console.log(msg.message);
            if(msg.messageType){
                showMessageToUser(msg.message, "success");
                clearFields();
            }
            else if(!msg.messageType) showMessageToUser(msg.message, "error");
        });
});

