window.addEventListener('load', function () {
    const formulario = document.querySelector('#add_new_turno');

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();
        const formData = {
            pacienteId: document.getElementById('pacienteId').value,
            odontologoId: document.getElementById('odontologoId').value,
            fecha: document.getElementById('fecha').value
        };

        const url = '/turnos';
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
                    if (response.status === 404) {
                        throw new Error('Paciente u odontólogo no encontrado');
                    } else {
                        return response.json().then(error => {
                            throw new Error(error.message);
                        });
                    }
                }
                return response.json();
            })
            .then(data => {
                let successAlert = '<div class="alert alert-success alert-dismissible fade show" role="alert">' +
                    '<i class="bi bi-check-circle me-1"></i>' +
                    '¡Turno Guardado!' +
                    '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
                    '</div>';

                document.querySelector('#response').innerHTML = successAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();
            })
            .catch(error => {
                let errorAlert = '<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                    '<i class="bi bi-exclamation-octagon me-1"></i>' +
                    error.message +
                    '<button type="button" class="btn-close" data-dismiss="alert" aria-label="Close"></button>' +
                    '</div>';

                document.querySelector('#response').innerHTML = errorAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();
            });
    });

    function resetUploadForm() {
        document.querySelector('#pacienteId').value = "";
        document.querySelector('#odontologoId').value = "";
        document.querySelector('#fecha').value = "";
    }

    (function () {
        let pathname = window.location.pathname;
        if (pathname === "/") {
            document.querySelector(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "/turnos.html") {
            document.querySelector(".nav .nav-item a:last").addClass("active");
        }
    })();
});