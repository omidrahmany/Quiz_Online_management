let fetchApi = new FetchService();
let url = serverUrl().concat("/manager/get-manager-info");
let p = document.querySelector("#username");

fetchApi.get(url)
    .then(data => {
        console.log(data.username);
        p.textContent = data.username;
    });


