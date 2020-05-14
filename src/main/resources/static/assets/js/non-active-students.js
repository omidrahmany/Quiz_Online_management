/*----------------- Variables Definition -----------------*/

let fetchApi = new FetchService();
let url = serverUrl().concat("/manager/non-active-students");
let deleteUrl = serverUrl().concat("/manager/delete-account/");

let table = document.querySelector("table");
let tr = `<tr >
                <th >ردیف</th>
                <th > نام</th>
                <th > نام خانوادگی </th>
                <th > نقش کاربری </th>
                <th > نام کاربری </th>
                <th > ایمیل </th>              
                <th > وضعیت حساب </th>              
                <th > ویرایش </th>              
                <th > حذف </th>              
            </tr>`;

/*----------------- Event Definition -----------------*/
fetchApi.get(url)
    .then(data => {
        let i = 0;
        data.forEach(account => {
            let status;
            if(account.enable) status = "فعال";
            else status='غیرفعال';
            i++;
            tr += `
        <tr> 
            <td >${i}</td>
            <td >${account.firstName}</td>
            <td >${account.lastName}</td>
            <td >${account.role}</td>
            <td >${account.username}</td>
            <td >${account.email}</td>
            <td >${status}</td>
            <td > <a href="#">
            <button type='button'  onclick='editAccount(${account.accountId})'>ویرایش</button>
            </a> </td>
            <td > <a href="#">
            <button type='button'  onclick='deleteAccount(deleteUrl.concat(${account.accountId}))'>حذف</button>
            </a> </td>
            </tr>`;
        });
        table.innerHTML = tr;
    });

function editAccount(id) {
    sessionStorage.setItem("accountId",id);
}
function deleteAccount(url){
    console.log(url);
    if (confirm("آیا از حذف این حساب کاربری مطمئن هستید؟")) {
        fetchApi.delete(url);
        location.reload();
    }
}