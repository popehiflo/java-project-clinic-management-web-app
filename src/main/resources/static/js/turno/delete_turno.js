function deleteBy(id) {
    // con fetch invocamos a la API de paciente con el método DELETE
    // pasándole su id en la URL
    const url = '/turnos/'+ id;
    const settings = {
        method: 'DELETE'
    }
    fetch(url, settings)
        .then(response => {
            if (response.ok) {
                // Borrar la fila del paciente eliminado
                let row_id = "#tr_" + id;
                document.querySelector(row_id).remove();
                // Mostrar el mensaje de error en el template
                let successAlert = '<div class="alert alert-success alert-dismissible fade show" role="alert">' +
                    '<i class="bi bi-check-circle me-1"></i>' +
                    '¡Turno eliminado exitosamente!' +
                    '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
                    '</div>';
                document.querySelector('#response').innerHTML = successAlert;
                document.getElementById('response').style.display = 'block';
                //document.getElementById('response').textContent = 'Odontólogo eliminado exitosamente';
            } else {
                // Obtener el mensaje de error
                return response.text();
            }
        })
        .then(message => {
            if (message) {
                let errorAlert = '<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                    '<i class="bi bi-exclamation-octagon me-1"></i>' +
                    message +
                    '<button type="button" class="btn-close" data-dismiss="alert" aria-label="Close"></button>' +
                    '</div>';
                // Mostrar el mensaje de error en el template
                document.querySelector('#response').innerHTML = errorAlert;
                document.getElementById('response').style.display = 'block';
                //document.getElementById('response').textContent = message;
            }
        })
        .catch(error => {
            console.error('Error al eliminar el paciente:', error);
            // Mostrar un mensaje de error genérico
            let errorAlert = '<div class="alert alert-danger alert-dismissible fade show" role="alert">' +
                '<i class="bi bi-exclamation-octagon me-1"></i>' +
                'Ocurrió un error al eliminar el turno' +
                '<button type="button" class="btn-close" data-dismiss="alert" aria-label="Close"></button>' +
                '</div>';
            document.querySelector('#response').innerHTML = errorAlert;
            document.getElementById('response').style.display = 'block';
            //document.getElementById('response').textContent = 'Ocurrió un error al eliminar el paciente';
        });

}