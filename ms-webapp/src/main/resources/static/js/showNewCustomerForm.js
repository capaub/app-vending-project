import {formNewCustomerAttachEventListeners} from "./newCustomer.js";

let baseUrl = window.location.origin;
let url = new URL('/customers/create', baseUrl);

let customerContainer = document.querySelector('.customerContainer');

let btnAddCustomer = customerContainer.querySelector('.btnAddCustomer');
export function btnAddCustomerAttachEvent(btnAddCustomer) {
    btnAddCustomer.addEventListener('click', () => {

        refreshMainForAddCustomerForm();

    })
}

export function refreshMainForAddCustomerForm() {

    let main = document.querySelector('.Main');

    fetch(url.toString(), {method: 'GET'})
        .then( response => response.text() )
        .then( data => main.outerHTML = data )
        .then( () => {
            let formNewCustomer = document.querySelector('.new_customer_form')
            formNewCustomerAttachEventListeners(formNewCustomer)
        })
}

btnAddCustomerAttachEvent(btnAddCustomer)