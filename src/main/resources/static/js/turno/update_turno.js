window.addEventListener('load', function () {

    //Buscamos y obtenemos el formulario donde están
    //los datos que el usuario pudo haber modificado del paciente
    const formulario = document.querySelector('#update_turno_form');

    formulario.addEventListener('submit', function (event) {
        let pacienteId = document.querySelector('#turno_id').value;

        //creamos un JSON que tendrá los datos del paciente
        //a diferencia de un odontólogo nuevo en este caso enviamos su id
        //para poder identificarla y modificarla para no cargarla como nueva
        const formData = {
            id: document.querySelector('#turno_id').value,
            pacienteId: document.querySelector('#pacienteId').value,
            odontologoId: document.querySelector('#odontologoId').value,
            fecha: document.querySelector('#fecha').value
        };

        //invocamos utilizando la función fetch la API odontólogos con el método PUT que modificará
        //la película que enviaremos en formato JSON
        const url = '/turnos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
        fetch(url, settings)
            .then(response => response.json())

    })
})

//Es la funcion que se invoca cuando se hace clic sobre el "id" de un paciente del listado
//se encarga de llenar el formulario con los datos del odontólogo
//que se desea modificar
function findBy(id) {
    const url = '/turnos' + "/" + id;
    const settings = {
        method: 'GET'
    }
    fetch(url, settings)
        .then(response => response.json())
        .then(data => {
            let turno = data;
            document.querySelector('#turno_id').value = turno.id;
            document.querySelector('#pacienteId').value = turno.pacienteId;
            document.querySelector('#odontologoId').value = turno.odontologoId;
            document.querySelector('#fecha').value = turno.fecha;
            //el formulario por default esta oculto y al editar se habilita
            document.querySelector('#div_turno_updating').style.display = "block";
        }).catch(error => {
        alert("Error: " + error);
    })
}