import {buildVendingContainer} from './showVending.js';
import {getCsrfToken, moveLabel} from "./global.js";

const baseUrl = window.location.origin;

export function vendingLocationAttachEventListeners(container) {

    const vendingLocation = container.querySelectorAll('li.spiral');

    vendingLocation.forEach(spiral => {
        spiral.addEventListener('click', handleClickVendingLocation);
    });
}

function handleClickVendingLocation(event) {
    const {csrfToken, csrfHeader} = getCsrfToken();

    const target = event.currentTarget.querySelector('.location_identifier');
    const locationIdentifier = target.innerHTML;
    const vendingTags = target.dataset.vending;
    const vendingId = target.dataset.vendingId;

    const formAddBatch = document.querySelector('.add_batch_form');

    const url = new URL(`/products/ajax/forAddBatch/${encodeURIComponent(vendingId)}/${encodeURIComponent(locationIdentifier)}/${encodeURIComponent(vendingTags)}`, baseUrl);

    fetch(url.toString(), {
        method: 'GET',
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            [csrfHeader]: csrfToken
        }
    })
        .then(response => response.text())
        .then(data => replaceContainer(formAddBatch, data))
        .then(() => formAddBatch.setAttribute('data-vending-id', vendingId))
        .then(() => {
            const container = document.querySelector('.container_add_batch_form');
            showAddBatchForm(container)
        })
}

function listenCloseButton(container) {
    const close = container.querySelector('.close');
    close.addEventListener('click', handleClickCloseBtn);
}

function handleClickCloseBtn(event) {
    event.preventDefault();
    const container = document.querySelector('.container_add_batch_form');
    container.classList.toggle('hidden')
}

function listenSubmitButton() {
    const submit = document.querySelector('.add_batch_submit');

    submit.addEventListener('click', handleClickSubmitBtn);
}

function handleClickSubmitBtn(event) {
    event.preventDefault();

    const form = event.currentTarget.closest('[data-vending-id]');

    const location = form.querySelector('input[name=field_location]').value;
    const vendingId = form.getAttribute('data-vending-id');
    const batchId = form.querySelector('select[name=field_batch]').value;
    const quantity = form.querySelector('input[name=field_quantity]').value;

    const url = new URL(`/vendings/ajax/addBatchToVending/${encodeURIComponent(location)}/${encodeURIComponent(vendingId)}/${encodeURIComponent(batchId)}/${encodeURIComponent(quantity)}`, baseUrl);

    const {csrfToken, csrfHeader} = getCsrfToken();

    if (form.checkValidity()) {
        fetch(url.toString(), {
            method: 'POST',
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                [csrfHeader]: csrfToken}
        })
            .then(response => response.text())
            .then(() => refreshVendingGrid(vendingId))
            .then(() => hideAddBatchForm())
    } else {
        const invalidFields = Array.from(form.elements).filter(element => !element.validity.valid);

        invalidFields.forEach(field => {
            field.classList.add("field_empty");
        });
    }
}

function refreshVendingGrid(id) {

    const url = new URL(`/vendings/ajax/build/${encodeURIComponent(id)}`, baseUrl);

    fetch(url.toString(), {
        method: 'GET',
        headers: { 'Content-Type': 'application/json'}
    })
        .then(response => response.text())
        .then(data => buildVendingContainer(data))
}

function replaceContainer(container, data) {
    container.innerHTML = data;
}

function showAddBatchForm(container) {
    container.classList.toggle('hidden');
    listenCloseButton(container);
    listenSubmitButton();
    moveLabel();
}

function hideAddBatchForm() {
    const container = document.querySelector('.container_add_batch_form');
    container.classList.add('hidden');
}

//TODO implémenter jsQR avec les media d'HTML 5 pour facilité l'ajout d'un lot