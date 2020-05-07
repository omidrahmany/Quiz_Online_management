class EasyHTTP {
    async get(url) {
        let response = await fetch(url);
        let users = await response.json();
        return users;
    }

    async delete(url) {
        let response = await fetch(url, {
            method: "DELETE",
            headers: { "Content-type": "application/json" }
        });
        let msg = await "user deleted...";
        return msg;
    }

    async post(url, data) {
        let response = await fetch(url, {
            method: "POST",
            headers: { "Content-type": "application/json" },
            body: JSON.stringify(data),
            credentials: 'same-origin',
        })
        let post = await response.json();
        return post;
    }

    async put(url, data) {
        let response = await fetch(url, {
            method: "PUT",
            headers: { "Content-type": "application/json" },
            body: JSON.stringify(data)
        })
        let post = await response.json();
        return post;
    }


}