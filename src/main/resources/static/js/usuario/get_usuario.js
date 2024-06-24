window.addEventListener('load', function () {

    // Realiza una solicitud GET al endpoint que devuelve la información del usuario.
    fetch('/usuario/info')
        .then(response => {
            // Asegúrate de que la solicitud fue exitosa.
            if (!response.ok) {
                throw new Error('Esto no esta bien ' + response.statusText);
            }
            return response.text(); // o response.json() si el servidor devuelve JSON
        })
        .then(username => {
            // Actualiza el elemento que tiene id 'username' con el nombre del usuario.
            document.getElementById('username').textContent = username;
            document.getElementById('user-name').textContent = username;
            document.getElementById('user-rol').textContent = username;
        })
        .catch(error => {
            console.error('Tenemos problemas:', error);
        });
});
