
const API_BASE = "http://localhost:8080";


const volverAtras = document.getElementById("volverAtras");
const err = document.getElementById('error');
const table = document.querySelector('table');
const success = document.getElementById('success');
const btnAdd = document.getElementById('btnAdd');
const btnEdit = document.getElementById('btnEdit');         
const formAdd = document.querySelector('.add-form-section');
const formEdit = document.querySelector('.edit-form-section');
const formSearch = document.getElementById('search-form');
const btnReload = document.getElementById('btn-reload'); 


if(volverAtras){
    volverAtras.addEventListener("click", () => {
        location.replace("/");
    })
}

function showError(error){
    err.style.display = "block";
    err.classList.add("error");
    err.innerHTML = `<p>${error}</p>`;

    const closeError = document.getElementById('close-error');

    // closeError.addEventListener('click', () => {
    //     err.style.display = "none";
    // })
}