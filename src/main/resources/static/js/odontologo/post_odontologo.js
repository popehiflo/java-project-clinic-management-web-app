window.addEventListener('load', function () {

    //Al cargar la página buscamos y obtenemos el formulario donde estarán
    //los datos que el usuario cargará del nuevo odontólogo
    const formulario = document.querySelector('#add_new_odontologo');

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {

        // Previene el comportamiento por defecto del formulario
        event.preventDefault();
        //creamos un JSON que tendrá los datos del nuevo odontólogo
        const formData = {
            matricula: document.querySelector('#matricula').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,

        };
        // Invocamos utilizando la función fetch la API odontólogo con el método POST que guardará
        // el odontólogo que enviaremos en formato JSON
        const url = '/odontologos';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                //Si no hay ningún error se muestra un mensaje diciendo que el odontólogo
                //se agregó bien
                let successAlert = '<div class="alert alert-success alert-dismissible fade show" role="alert">' +
                    '<i class="bi bi-check-circle me-1"></i>' +
                    '¡Odontologo Guardado!' +
                    '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
                    '</div>'

                document.querySelector('#response').innerHTML = successAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();

            })
            .catch(error => {
                //Si hay algún error se muestra un mensaje diciendo que el odontólogo
                //no se pudo guardar y se intente nuevamente
                let errorAlert = '<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                    '<i class="bi bi-exclamation-octagon me-1"></i>' +
                    '¡Error! intente nuevamente' +
                    '<button type="button" class="btn-close" data-dismiss="alert" aria-label="Close"></button>' +
                    '</div>'

                document.querySelector('#response').innerHTML = errorAlert;
                document.querySelector('#response').style.display = "block";
                //se dejan todos los campos vacíos por si se quiere ingresar otro odontólogo
                resetUploadForm();
            })
    });


    function resetUploadForm() {
        document.querySelector('#matricula').value = "";
        document.querySelector('#nombre').value = "";
        document.querySelector('#apellido').value = "";

    }

    (function () {
        let pathname = window.location.pathname;
        if (pathname === "/") {
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/odontologos.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});