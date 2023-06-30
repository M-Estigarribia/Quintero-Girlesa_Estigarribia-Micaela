window.addEventListener('load', (e) => {
    try {
        listPacientes();
    } catch (error) {
        showError(error);
    }
})

function listPacientes(){
    fetch(API_BASE + '/pacientes')
        .then((res) => {
            if(res.status != 200) throw new Error('No se han podido cargar los pacientes')
            return res.json();
        })
        .then((data) => {
            let pacienteRow = document.querySelector("#pacienteTableBody");
            pacienteRow.innerHTML = ' ';
            data.map((paciente)=>{
                pacienteRow.innerHTML += `
                <tr>
                <td>${paciente.nombre} ${paciente.apellido}, DNI: ${paciente.dni} / Fecha de ingreso: ${paciente.fechaIngreso}</td>
                <td>${paciente.domicilio.calle} ${paciente.domicilio.numero}, ${paciente.domicilio.localidad} ${paciente.domicilio.provincia}</td>
                <td>
                <button id="edit" onclick="handleEdit(${paciente.id})" class="btn btn-secondary"><i class="fa-solid fa-file-pen"></i></button>
                <button id="btnDelete" onclick="deletePaciente(${paciente.id})" class="btn btn-danger"><i class="fa-solid fa-trash-can"></i></button>
                </td>
            </tr>`
            })
        })
        .catch((error) => {
            showError(error);
        });
}

function addPaciente(dataPaciente){
    let settings = {
        method : "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body : JSON.stringify(dataPaciente) 
    };
    
    fetch(`${API_BASE}/pacientes/registrar`, settings)
        .then(res => {
            if(res.status != 201) throw new Error('No se pudo agregar el paciente') 
            return res.json();
        })
        .then(data => {
            listPacientes();
            resetUploadForm()
        })
        .catch(error => {
            showError(error);
        });
}

function searchPaciente(id){
    fetch(`${API_BASE}/pacientes/${id}`)
    .then(res => {
        if(res.status != 200) throw new Error('No se pudo encontrar el paciente') ;
        return res.json();
    })
    .then((data) => {
        let pacienteRow = document.querySelector("#pacienteTableBody");
        pacienteRow.innerHTML = '';
        pacienteRow.innerHTML += `
            <tr>
                <td>${data.nombre} ${data.apellido}, DNI: ${data.dni} / Fecha de ingreso: ${data.fechaIngreso}</td>
                <td>${data.domicilio.calle} ${data.domicilio.numero}, ${data.domicilio.localidad} ${data.domicilio.provincia}</td>
                <td>
                <button id="edit" onclick="handleEdit(${data.id})" class="btn btn-secondary"><i class="fa-solid fa-file-pen"></i></button>
                <button id="btnDelete" onclick="deletePaciente(${data.id})" class="btn btn-danger"><i class="fa-solid fa-trash-can"></i></button>
                </td>
            </tr>`
        resetUploadForm();
    }).catch(error => {
        showError(error);
    });
};

function editPaciente(dataPaciente){
    let settings = {
        method : "PUT",
        headers: {
            'Content-Type': 'application/json'
        },
        body : JSON.stringify(dataPaciente)
    }

    fetch(`${API_BASE}/pacientes/actualizar`, settings) 
    .then((res) =>  {
        if(res.status != 200) throw new Error('No se pudo agregar el paciente') 
        res.json()
    }).then((data) => {
        listPacientes();
        resetEditForm();
    })
    .catch(error => {
        showError(error);
    });
};

function deletePaciente(id){
    let settings = {
        method : "DELETE",
        headers: {
            'Content-Type': 'application/json'
        }
    }

    fetch(`${API_BASE}/pacientes/eliminar/${id}`, settings)
    .then((res) => {
       
        if(res.status === 204) {
            success.innerHTML = `<div class="exito">
                                        <h4>Eliminado con éxito!</h4>
                                    <div>`
            // listadoOdon.innerHTML = "";
        } else throw new Error('No se pudo eliminar el paciente')
    })
    .then((data) => listPacientes())
    .catch(error => {
        showError(error);
    });
};

btnAdd.addEventListener('click', (e) => {
    e.preventDefault();
    try {
        let nombrePacient = document.querySelector("#nombre"),
        apellidoPacient = document.querySelector("#apellido"),
        dni = document.querySelector("#dni"),
        fechaIngreso = document.querySelector("#fechaIngreso"),
        calle = document.querySelector("#calle"),
        numero = document.querySelector("#numero"),
        localidad = document.querySelector("#localidad"),
        provincia = document.querySelector("#provincia")
    
        let dataPaciente = {
            nombre : nombrePacient.value,
            apellido : apellidoPacient.value,
            dni : dni.value,
            fechaIngreso : fechaIngreso.value, 
            domicilio: {
                calle: calle.value,
                numero: numero.value ,
                localidad: localidad.value,
                provincia: provincia.value,
            }
        }
    
        addPaciente(dataPaciente);
    } catch (error) {
        showError(error);
    }
});

formSearch.addEventListener('submit',(e) => {
    e.preventDefault();
    try {
        const searchInput = document.querySelector('.search-input');
        if(isNaN(searchInput.value)) throw new Error('Debe ingresarse un número');
        searchPaciente(searchInput.value);
    } catch (error) {
        showError(error);
    }
})

function handleEdit(id){
    try {
        formAdd.style.display = "none";
        formEdit.style.display = "block";
    
        if(btnEdit){
            btnEdit.addEventListener('click', (e) => {
                e.preventDefault();
    
                let nombrePacient = document.querySelector("#nombreEdit"),
                apellidoPacient = document.querySelector("#apellidoEdit"),
                dni = document.querySelector("#dniEdit"),
                fechaIngreso = document.querySelector("#fechaIngresoEdit"),
                calle = document.querySelector("#calleEdit"),
                numero = document.querySelector("#numeroEdit"),
                localidad = document.querySelector("#localidadEdit"),
                provincia = document.querySelector("#provinciaEdit")
    
                let dataPaciente = {
                    id : id,
                    nombre : nombrePacient.value,
                    apellido : apellidoPacient.value,
                    dni : dni.value, 
                    fechaIngreso : fechaIngreso.value, 
                    calle : calle.value,
                    numero : numero.value,
                    localidad : localidad.value,
                    provincia : provincia.value
                }
        
                editPacient(dataPaciente)
            })
        }
    } catch (error) {
        showError(error);
    }
}

btnReload.addEventListener('click',()=>{
    try {
        listPacientes();
        formEdit.style.display = "none";
        formAdd.style.display = "block";
    } catch (error) {
        showError(error);
    }
})

function resetUploadForm(){
    try {
        document.querySelector("#nombre").value = "";
        document.querySelector("#apellido").value = "";
        document.querySelector("#dni").value = "";
        document.querySelector("#fechaIngreso").value = "";
        document.querySelector("#calle").value = "";
        document.querySelector("#numero").value = "";
        document.querySelector("#localidad").value = "";
        document.querySelector("#provincia").value = "";
    } catch (error) {
        showError(error);
    }
}

function resetEditForm(){
    try {
        document.querySelector("#nombreEdit").value = '';
        document.querySelector("#apellidoEdit").value = '';
        document.querySelector("#dniEdit").value = '';
        document.querySelector("#fechaIngresoEdit").value = '';
        document.querySelector("#calleEdit").value = '';
        document.querySelector("#numeroEdit").value = '';
        document.querySelector("#localidadEdit").value = '';
        document.querySelector("#provinciaEdit").value = '';
    } catch (error) {
        showError(error);
    }
}