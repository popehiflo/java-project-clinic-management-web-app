window.addEventListener('load', function () {

    //Al cargar la página buscamos y obtenemos el formulario donde estarán
    //los datos que el usuario cargará del nuevo paciente
    const formulario = document.querySelector('#add_new_paciente');

    //Ante un submit del formulario se ejecutará la siguiente funcion
    formulario.addEventListener('submit', function (event) {

        // Previene el comportamiento por defecto del formulario
        event.preventDefault();
        //creamos un JSON que tendrá los datos del nuevo paciente
        const formData = {
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,
            cedula: document.querySelector('#cedula').value,
            fechaIngreso: document.querySelector('#fechaIngreso').value,
            domicilio: {
                calle: document.querySelector('#calle').value,
                numero: document.querySelector('#numero').value,
                localidad: document.querySelector('#localidad').value,
                provincia: document.querySelector('#provincia').value,
            },
            correo: document.querySelector('#correo').value
        };
        // Invocamos utilizando la función fetch la API con el método POST que guardará
        // el paciente que enviaremos en formato JSON
        const url = '/pacientes';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Problema con la respuesta del servidor');
                }
                return response.json();
            })
            .then(data => {
                //Si no hay ningun error se muestra un mensaje diciendo que el paciente
                //se agrego bien
                let successAlert = '<div class="alert alert-success alert-dismissible fade show" role="alert">' +
                    '<i class="bi bi-check-circle me-1"></i>' +
                    '¡Paciente Guardado!' +
                    '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
                    '</div>'

                document.querySelector('#response').innerHTML = successAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();
                //window.location.href='../get_pacientes.html';
            })
            .catch(error => {
                //Si hay algun error se muestra un mensaje diciendo que la pelicula
                //no se pudo guardar y se intente nuevamente
                let errorAlert = '<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                    '<i class="bi bi-exclamation-octagon me-1"></i>' +
                    '¡Error! intente nuevamente' +
                    '<button type="button" class="btn-close" data-dismiss="alert" aria-label="Close"></button>' +
                    '</div>'

                document.querySelector('#response').innerHTML = errorAlert;
                document.querySelector('#response').style.display = "block";
                //se dejan todos los campos vacíos por si se quiere ingresar otra pelicula
                resetUploadForm();
            });
    });


    function resetUploadForm() {
        document.querySelector('#nombre').value = "";
        document.querySelector('#apellido').value = "";
        document.querySelector('#cedula').value = "";
        document.querySelector('#correo').value = "";
        document.querySelector('#fechaIngreso').value = "";
    }

    (function () {
        let pathname = window.location.pathname;
        if (pathname === "/") {
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/get_pacientes.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});