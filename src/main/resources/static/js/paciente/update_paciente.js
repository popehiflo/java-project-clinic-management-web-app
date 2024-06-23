window.addEventListener('load', function () {

    //Buscamos y obtenemos el formulario donde están
    //los datos que el usuario pudo haber modificado del paciente
    const formulario = document.querySelector('#update_paciente_form');

    formulario.addEventListener('submit', function (event) {
        let pacienteId = document.querySelector('#paciente_id').value;

        //creamos un JSON que tendrá los datos del paciente
        //a diferencia de un odontólogo nuevo en este caso enviamos su id
        //para poder identificarla y modificarla para no cargarla como nueva
        const formData = {
            id: document.querySelector('#paciente_id').value,
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

        //invocamos utilizando la función fetch la API odontólogos con el método PUT que modificará
        //la película que enviaremos en formato JSON
        const url = '/pacientes';
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
    const url = '/pacientes' + "/" + id;
    const settings = {
        method: 'GET'
    }
    fetch(url, settings)
        .then(response => response.json())
        .then(data => {
            let odontologo = data;
            document.querySelector('#paciente_id').value = odontologo.id;
            document.querySelector('#nombre').value = odontologo.nombre;
            document.querySelector('#apellido').value = odontologo.apellido;
            document.querySelector('#cedula').value = odontologo.cedula;
            document.querySelector('#fechaIngreso').value = odontologo.fechaIngreso;
            document.querySelector('#calle').value = odontologo.domicilio.calle;
            document.querySelector('#numero').value = odontologo.domicilio.numero;
            document.querySelector('#localidad').value = odontologo.domicilio.localidad;
            document.querySelector('#provincia').value = odontologo.domicilio.provincia;
            document.querySelector('#correo').value = odontologo.correo;
            //el formulario por default esta oculto y al editar se habilita
            document.querySelector('#div_paciente_updating').style.display = "block";
        }).catch(error => {
        alert("Error: " + error);
    })
}