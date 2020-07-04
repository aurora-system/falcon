const login = document.querySelector('form#loginForm');

login.addEventListener('submit', (e) => {
	e.preventDefault();
	console.log(e.target.href);
	let url = 'http://localhost:8080/authenticate';
	let username = document.getElementById('username').value;
	let password = document.getElementById('password').value;
	console.log(JSON.stringify({username,password}));
	return fetch(url,{
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({username, password})
	})
		.then(handleResponse) // usually, res => res.json()
		.then(data => {
			console.log(data.jwttoken);
			localStorage.setItem('ehrJwtToken', data.jwttoken);
			return data;
		});
});

function logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('ehrJwtToken');
}

function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        if (!response.ok) {
            if (response.status === 401) {
                // auto logout if 401 response returned from api
                logout();
                location.reload(true);
            }

            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }

        return data;
    });
}