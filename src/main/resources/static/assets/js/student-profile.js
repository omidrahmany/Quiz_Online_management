let fetchApi = new FetchService();
let url = serverUrl().concat("/student/get-student-info");
let msg = document.querySelector("#msg");

fetchApi.get(url)
    .then(data => {
        if (data.enable) msg.textContent = ` نام: ${data.firstName} ${data.lastName} `;
    });


