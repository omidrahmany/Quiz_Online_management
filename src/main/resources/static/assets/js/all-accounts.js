/*----------------- Variables Definition -----------------*/
let fetchApi = new FetchService();
let url = serverUrl().concat("/manager/get-all-accounts");
let deleteUrl = serverUrl().concat("/manager/delete-account/");


/*-------- Modal Variables --------*/
let firstName = document.querySelector("#first-name");
let lastName = document.querySelector("#last-name");
let username = document.querySelector("#username");
let roleStatus = document.querySelector("#role");
let email = document.querySelector("#email");
let enabledStatus = document.querySelector("#status");


/*-------- table Variables --------*/
let table = document.querySelector("table");
let th = `<tr >
                <th > حذف </th> 
                <th > ویرایش </th>
                <th > وضعیت حساب </th>
                <th > ایمیل </th>  
                 <th > نام کاربری </th>
                <th > نقش کاربری </th>
                <th > نام خانوادگی </th>
                <th > نام</th>
                <th >ردیف</th>                                
            </tr>`;
let tr;

/*----------------- Function Definition -----------------*/

function deleteAccount(url) {
    if (confirm("آیا از حذف این حساب کاربری مطمئن هستید؟")) {
        fetchApi.delete(url);
        location.reload();
    }
}

function setUserInfoToModal(id) {
    sessionStorage.setItem("accountId", id);
    fetchApi.get(serverUrl().concat("/manager/get-account/", id))
        .then(data => {
            !data.enable ? enabledStatus.selectedIndex = 1 : enabledStatus.selectedIndex = 0;
            firstName.value = data.firstName;
            lastName.value = data.lastName;
            username.value = data.username;
            data.roleType === "student" ? roleStatus.selectedIndex = 0 : roleStatus.selectedIndex = 1;
            email.value = data.email;
        });
}

function updateTableInfo() {
    fetchApi.get(url).then(data => {
        if (data.length !== 0) {
            document.querySelector("#nothing-msg").style.display = "none";
            let i = 0;
            tr = th;
            data.forEach(account => {
                let status;
                if (account.enable) status = "فعال";
                else status = 'غیرفعال';
                let roleOption;
                account.roleType === "student" ? roleOption = "دانشجو" : roleOption = "استاد";
                i++;

                tr += `
                <tr> 
                    <td >
                    <a  style="color: #ff2b4b" class="cursor-pointer" onclick='deleteAccount(deleteUrl.concat(${account.accountId}))'>
                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                    </a>
                     </td>
                    <td >              
                    <!-- Button trigger modal -->
                    <a  style="color: #378041" class="cursor-pointer" onclick='setUserInfoToModal(${account.accountId})'   data-toggle="modal" data-target="#showModal">
                      <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                    </a>
                     </td>                       
                    <td >${status}</td>
                    <td >${account.email}</td>
                    <td >${account.username}</td>
                    <td >${roleOption}</td>
                    <td >${account.lastName}</td>
                     <td >${account.firstName}</td>
                    <td >${i}</td>
                </tr>`;
            });
            table.innerHTML = tr;
        }
    });
}

/*----------------- Event Definition -----------------*/

document.addEventListener("DOMContentLoaded", updateTableInfo());


document.querySelector("#save-change").addEventListener("click", () => {
    let isActive = enabledStatus.value === "فعال";
    let roleOption = roleStatus.selectedIndex === 0 ? "student" : "teacher";

    let account = new AccountDto(sessionStorage.getItem("accountId"), firstName.value, lastName.value, username.value, email.value, roleOption, isActive);
    fetchApi
        .put(serverUrl().concat("/manager/update-account"), account)
        .then(data => {
            console.log(data.message);
        });
    $("#showModal").modal('hide');
    updateTableInfo();
    location.reload();
});
