import {btnAddCustomerAttachEvent} from "./showNewCustomerForm.js";
import {addVendingToCustomerAttachEventListeners} from "./addVendingToCustomer.js";
import {moveLabel, getCsrfToken} from "./global.js";
import {sortCustomers} from "./sortCustomer.js";
import {attachEventListenerCustomerVending} from "./generateCustomerVending.js";

const baseUrl = window.location.origin;
const urlBackToCustomers = new URL('/customers/ajax/backToCustomers', baseUrl)

function refreshMainBackToCustomers() {

    const main = document.querySelector('.Main');

    fetch(urlBackToCustomers.toString(), {method: 'GET'})
        .then(response => response.text())
        .then(data => main.outerHTML = data)
        .then(() => {
            const customerContainer = document.querySelector('.CustomerContainer');

            const btnAddCustomer = customerContainer.querySelector('.btnAddCustomer');
            btnAddCustomerAttachEvent(btnAddCustomer);
            addVendingToCustomerAttachEventListeners(customerContainer);
            sortCustomers();
            attachEventListenerCustomerVending();
        })
}

const handleClick = (event) => {
    event.preventDefault();

    const sectionFormNewCustomer = document.querySelector('.container_new_customer_form');
    const formNewCustomer = sectionFormNewCustomer.querySelector('.new_customer_form');

    newCustomer(formNewCustomer);
}

export function formNewCustomerAttachEventListeners(formNewCustomer) {
    moveLabel();

    const btnBackToCustomers = document.querySelector('.btnBackToCustomers');
    btnBackToCustomers.addEventListener('click', refreshMainBackToCustomers);

    const submitButton = formNewCustomer.querySelector('input[type=submit]');
    submitButton.addEventListener('click', handleClick);
}

function newCustomer(formNewCustomer)
{
    let formData = new FormData(formNewCustomer)
    let formObject = {};
    formData.forEach((value, key) => {
        formObject[key] = value;
    });

    const { csrfToken, csrfHeader } = getCsrfToken();

    const main = document.querySelector('.Main');

    const url = new URL('/customers/create', baseUrl);

    if (formNewCustomer.checkValidity()) {
        fetch(url.toString(), {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json',
                'X-Requested-With': 'XMLHttpRequest',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(formObject)
        })
            .then(response => response.text())
            .then(data => main.outerHTML = data)
            .then(() => {

                const btnAddCustomer = document.querySelector('.btnAddCustomer');

                btnAddCustomerAttachEvent(btnAddCustomer)

                const customerContainer = document.querySelector('.CustomerContainer');
                addVendingToCustomerAttachEventListeners(customerContainer);
                attachEventListenerCustomerVending();
            })
    } else {
        const invalidFields = Array.from(formNewCustomer.elements).filter(element => !element.validity.valid);

        invalidFields.forEach(field => {
            field.classList.add("field_empty");
        });
    }
}
