import {vendingLocationAttachEventListeners} from './addBatchToVending.js';
import {handleImageSize} from "./handleImageSize.js";

const baseUrl = window.location.origin;

const vendingContainer = document.querySelector('.vendingGrid');

export function vendingListAttachEventListeners() {

    const vendings = document.querySelectorAll('ul.vending');

    vendings.forEach(vending => {
        vending.addEventListener('click', handleClickVendingList);
    })
}

export function handleClickVendingList(event)
{
    const id = event.currentTarget.dataset.vendingId;

    const btnAddVending = document.querySelector('.btn_add_vending');
    const btnBackVendingToVendingList = document.querySelector('.btn_back_to_vendingList');
    const sectionVendingList = document.querySelector('.vending_list_container');

    const url = new URL(`/vendings/ajax/build/${encodeURIComponent(id)}`, baseUrl);

    fetch(url.toString(), {
        method: 'GET',
        headers: { 'Content-Type': 'application/json'}
    })
        .then( response => response.text())
        .then( data => {
            buildVendingContainer(data)
        })
        .then( () => {
            btnAddVending.classList.toggle('hidden')
            sectionVendingList.classList.toggle('hidden')
            btnBackVendingToVendingList.classList.toggle('hidden')

            const vendings = document.querySelectorAll('ul.vending');

            vendings.forEach(vending => {
                vending.removeEventListener('click', handleClickVendingList);
            })
        })
}

export function attachEventBackActionButton()
{
    const btnBackVendingToVendingList = document.querySelector('.btn_back_to_vendingList');

    btnBackVendingToVendingList.addEventListener('click', backAction);
}

function backAction()
{
    const btnBackVendingToVendingList = document.querySelector('.btn_back_to_vendingList');
    const sectionVendingList = document.querySelector('.vending_list_container');
    const btnAddVending = document.querySelector('.btn_add_vending');
    const sectionVending = document.querySelector('.vendingGrid');
    btnBackVendingToVendingList.classList.toggle('hidden');
    sectionVendingList.classList.toggle('hidden');
    btnAddVending.classList.toggle('hidden');
    sectionVending.classList.toggle('hidden');
    btnBackVendingToVendingList.removeEventListener('click', backAction);
    const vendings = document.querySelectorAll('ul.vending');

    vendings.forEach(vending => {
        vending.addEventListener('click', handleClickVendingList);
    })
}

export function buildVendingContainer(data) {

    showVending(vendingContainer);
    vendingContainer.innerHTML = data;

    styleVending();

    attachEventBackActionButton();
}

function styleVending() {
    const trays = document.querySelectorAll('ul.plateau');
    trays.forEach( tray => {

        const spirals = tray.querySelectorAll('li.spiral');
        const numberOfSpiral = spirals.length;

        tray.style.gridTemplateColumns = `repeat(${numberOfSpiral}, 1fr)`;

        const imgDir = "/uploads/";
        const prefix = 'vendo_';
        spirals.forEach((spiral) => {
            const id = spiral.dataset.productBarcode ?? '';
            const imageUrl = `${baseUrl}${imgDir}${prefix}${id}.jpg` ;
            const selector = '.batch_picture';

            handleImageSize(spiral, imageUrl, selector);

        });
    });
    vendingLocationAttachEventListeners(vendingContainer)
}

export function showVending(container)
{
    container.classList.remove('hidden');
}

vendingListAttachEventListeners();
styleVending();