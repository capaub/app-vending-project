import {buildGridTemplateColumns, getCsrfToken, moveLabel} from "./global.js";
import { handleClickVendingList } from "./showVending.js";

const baseUrl = window.location.origin;
const url = new URL("/vendings/ajax/create", baseUrl);
const { csrfToken, csrfHeader } = getCsrfToken();

function handleClickBtnBackToVendingList() {

    const newVendingForm = document.querySelector('.new_vending_form');
    const btnAddVending = document.querySelector('.btn_add_vending');
    const btnBackAddVendingToVendingList = document.querySelector('.btn_back_new_vending_to_vendingList');

    btnAddVending.classList.toggle('hidden');
    btnBackAddVendingToVendingList.classList.toggle('hidden');
    newVendingForm.classList.toggle('hidden');

    btnAddVending.addEventListener('click', handleClickBtnAddVending);

    const vendings = document.querySelectorAll('ul.vending');

    vendings.forEach(vending => {
        vending.addEventListener('click', handleClickVendingList);
    })

    btnBackAddVendingToVendingList.removeEventListener('click', handleClickBtnBackToVendingList);

}

function handleClickCloseButton(event)
{
    event.preventDefault();

    const containerNewVendingForm = document.querySelector('.container_new_vending_form');
    const btnAddVending = document.querySelector('.btn_add_vending');
    const btnBackAddVendingToVendingList = document.querySelector('.btn_back_new_vending_to_vendingList');

    console.log(btnAddVending)

    containerNewVendingForm.classList.toggle('hidden');
    btnAddVending.classList.toggle('hidden');
    btnBackAddVendingToVendingList.classList.toggle('hidden');
    btnAddVending.addEventListener('click', handleClickBtnAddVending);
    btnBackAddVendingToVendingList.removeEventListener('click', handleClickBtnBackToVendingList);

    const close = document.querySelector('.close_new_vending_form');
    close.removeEventListener('click', handleClickCloseButton);

    const vendings = document.querySelectorAll('ul.vending');
    vendings.forEach(vending => {
        vending.addEventListener('click', handleClickVendingList);
    })
}

function handleClickBtnAddVending() {

    moveLabel();

    const containerNewVendingForm = document.querySelector('.container_new_vending_form');
    const btnAddVending = document.querySelector('.btn_add_vending');
    const btnBackAddVendingToVendingList = document.querySelector('.btn_back_new_vending_to_vendingList');
    const close = document.querySelector('.close_new_vending_form');

    close.addEventListener('click', handleClickCloseButton);

    btnAddVending.classList.toggle('hidden');
    btnBackAddVendingToVendingList.classList.toggle('hidden');
    containerNewVendingForm.classList.toggle('hidden');

    btnAddVending.removeEventListener('click', handleClickBtnAddVending);
    btnBackAddVendingToVendingList.addEventListener('click', handleClickBtnBackToVendingList);

    const vendings = document.querySelectorAll('ul.vending');

    vendings.forEach(vending => {
        vending.removeEventListener('click', handleClickVendingList);
    })

    const btnSubmitNewVending = document.querySelector('.new_vending_submit');
    btnSubmitNewVending.addEventListener('click', handleClickSubmitNewVending);
}

function handleClickSubmitNewVending(event)
{
    event.preventDefault();
    const newVendingForm = document.querySelector('.new_vending_form');

    const brandSibling = newVendingForm.querySelector('input[name=field_brand]');
    const brand = brandSibling.value;
    const modelSibling = newVendingForm.querySelector('input[name=field_model]');
    const model = modelSibling.value;
    const maxTraySibling = newVendingForm.querySelector('input[name=field_max_tray]');
    const maxTray = maxTraySibling.value;
    const maxSpiralSibling = newVendingForm.querySelector('input[name=field_max_spiral]');
    const maxSpiral = maxSpiralSibling.value;

    const batchData = {
        brand: brand,
        model: model,
        nbMaxTray: maxTray,
        nbMaxSpiral: maxSpiral
    };

    if (newVendingForm.checkValidity()) {
    fetch(url.toString(), {
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json',
            'X-Requested-With': 'XMLHttpRequest',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(batchData)
    })
        .then( response => response.text())
        .then( data => {
            const containerVendingList = document.querySelector('.vending_list_container');
            containerVendingList.innerHTML = data;
            const gridContainer = document.querySelectorAll('.grid_container');
            buildGridTemplateColumns(gridContainer);
        })
        .then( () => {
            const btnSubmitNewVending = document.querySelector('.new_vending_submit');
            btnSubmitNewVending.removeEventListener('click', handleClickSubmitNewVending);

            const vendings = document.querySelectorAll('ul.vending');
            vendings.forEach(vending => {
                vending.addEventListener('click', handleClickVendingList);
            })

            const containerNewVendingForm = document.querySelector('.container_new_vending_form');
            const btnBackAddVendingToVendingList = document.querySelector('.btn_back_new_vending_to_vendingList');

            btnAddVending.classList.toggle('hidden');
            btnAddVending.addEventListener('click', handleClickBtnAddVending);

            btnBackAddVendingToVendingList.classList.toggle('hidden');
            newVendingForm.reset();
            containerNewVendingForm.classList.toggle('hidden');
        })
    } else {
        const invalidFields = Array.from(newVendingForm.elements).filter(element => !element.validity.valid);

        invalidFields.forEach( field => {
            field.classList.add("field_empty");
        });
    }
}

const btnAddVending = document.querySelector('.btn_add_vending');
btnAddVending.addEventListener('click', handleClickBtnAddVending);