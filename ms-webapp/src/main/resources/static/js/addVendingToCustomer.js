import {getCsrfToken, moveLabel} from "./global.js";
import {attachEventListenerCustomerVending} from "./generateCustomerVending.js";

const baseUrl = window.location.origin;
const urlGetAvailableVending = new URL('/customers/ajax/getAvailableVending', baseUrl);

export function addVendingToCustomerAttachEventListeners(container) {
    const customers = container.querySelectorAll('section .Customer_name');

    customers.forEach(customer => {
        customer.removeEventListener('click', showContainerAddVending);
        customer.addEventListener('click', showContainerAddVending);
    });

    const addVendingSubmit = container.querySelector('.addVending');

    if (addVendingSubmit !== null) {
        addVendingSubmit.removeEventListener('click', addVendingToCustomer);
        addVendingSubmit.addEventListener('click', addVendingToCustomer);
    }

}

function addVendingToCustomer(event) {
    event.preventDefault();

    const {csrfToken, csrfHeader} = getCsrfToken();

    const formElement = event.currentTarget.closest('[data-customer-id]');
    if (!formElement) {
        console.error("Form element not found.");
        return;
    }

    const vendingId = formElement.querySelector('#vending').value;
    const vendingName = formElement.querySelector('#name').value;
    const customerId = formElement.dataset.customerId;

    if (!vendingId || !vendingName || !customerId) {
        console.error("Missing vending ID, name, or customer ID.");
        return;
    }

    const url = new URL(`/customers/ajax/addVendingToCustomer/${encodeURIComponent(vendingId)}/${encodeURIComponent(customerId)}/${encodeURIComponent(vendingName)}`, baseUrl);

    if (formElement.checkValidity()) {
        fetch(url, {
            method: 'POST',
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                [csrfHeader]: csrfToken
            }
        })
            .then(response => response.text())
            .then(data => {
                const targetCustomerContainer = document.querySelector(`section[data-customer-id='${customerId}']`);
                refreshCustomer(targetCustomerContainer, data);
            })
            .then(() => {
                hideAddVendingForm();
                attachEventListenerCustomerVending();
            })
            .catch(error => console.error("Erreur lors de l'ajout du distributeur :", error));
    } else {
        const invalidFields = Array.from(formElement.elements).filter(element => !element.validity.valid);

        invalidFields.forEach(field => {
            field.classList.add("field_empty");
        });
    }
}

function showContainerAddVending(event) {
    event.preventDefault();

    const {csrfToken, csrfHeader} = getCsrfToken();

    const targetCustomer = event.currentTarget.closest('[data-customer-id]');
    const customerId = targetCustomer.dataset.customerId;
    const companyName = targetCustomer.dataset.companyName;

    let addVendingData = {
        customerId: customerId,
        companyName: companyName
    };

    fetch(urlGetAvailableVending.toString(), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(addVendingData)
    })
        .then(response => response.json())
        .then(data => {
            const containerAddVending = document.querySelector('.container_add_vending_form');
            const companyNameElement = containerAddVending.querySelector('.company_name');
            const customerIdElement = containerAddVending.querySelector('.add_vending_form');
            const vendingSelect = containerAddVending.querySelector('#vending');

            companyNameElement.textContent = companyName;
            companyNameElement.dataset.companyName = companyName;

            customerIdElement.dataset.customerId = customerId;

            vendingSelect.innerHTML = '<option value="">Choisissez une machine</option>';
            data.availableVending.forEach(vending => {
                const option = document.createElement('option');
                option.value = vending.vendingId;
                option.textContent = `${vending.brand} - ${vending.model}`;
                vendingSelect.appendChild(option);
            });

            containerAddVending.classList.remove('hidden');
            moveLabel();
            listenCloseButton();
        })
        .catch(error => console.error("Erreur lors de la récupération des distributeurs :", error));
}

function listenCloseButton() {
    const close = document.querySelector('.closeAddVendingForm');
    const container = document.querySelector('.container_add_vending_form');

    if (close) {
        close.removeEventListener('click', toggleAddVendingForm); // Suppression de l'écouteur précédent
        close.addEventListener('click', toggleAddVendingForm);
    }
}

function toggleAddVendingForm(event) {
    event.preventDefault();
    const container = document.querySelector('.container_add_vending_form');
    container.classList.toggle('hidden');
}

function hideAddVendingForm() {
    const container = document.querySelector('.container_add_vending_form');
    container.classList.add('hidden');
}

function refreshCustomer(container, data) {
    container.outerHTML = data;
    addVendingToCustomerAttachEventListeners(container);
    listenCloseButton();
}

const customerContainer = document.querySelector('.CustomerContainer');
addVendingToCustomerAttachEventListeners(customerContainer);