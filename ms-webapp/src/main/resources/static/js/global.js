let listenBurger = document.querySelector('.Header_burger');
let displayLinks = document.querySelector('.Sidebar');
let burgerActiveBlur = document.querySelector('.Main');
let listenMoreInfos = document.querySelectorAll('h3 span');
let displayInfos = document.querySelector('.Customer_container_infos')
let burgerAnim = document.querySelector('.Icon');

listenBurger.addEventListener('click', function () {
    displayLinks.classList.toggle('active');
    burgerActiveBlur.classList.toggle('activeBlur');
})

let baseUrl = window.location.origin ;

listenMoreInfos.forEach(span => {
    span.addEventListener('click', function (e) {
        let targetInfo = e.target.parentNode.nextElementSibling.children[1];
        targetInfo.classList.toggle('active');
    });
})

burgerAnim.addEventListener('click',function () {
    if(this.classList.contains('isOpened')){
        this.classList.replace(
            'isOpened',
            'isClosed');
    }else{
        this.classList.replace(
            'isClosed',
            'isOpened');
    }
})

export function buildGridTemplateColumns(gridContainer) {

        gridContainer.forEach( row => {
            const fields = row.children;
            const numberOfColumns = fields.length;
            row.style.gridTemplateColumns = `repeat(${numberOfColumns}, 1fr)`;
        })
}

export function buildSidebarGridTemplateColumns(sidebarGridContainer) {
    if (window.innerWidth >= 1024) {
        const fields = sidebarGridContainer.children;
        const numberOfColumns = fields.length;
        sidebarGridContainer.style.gridTemplateColumns = `repeat(${numberOfColumns}, 1fr)`;
    }
}

export function moveLabel() {

    const inputsText = document.querySelectorAll(
        "input[type=text], input[type=number], input[type=email], input[type=password]");

    inputsText.forEach(input => {
        input.addEventListener('focus', styleInput);
    })
}

function styleInput(event)
{
    const input = event.currentTarget;
    const label = input.nextElementSibling;

    label.classList.add("onFocus")
    let characterTyped = false;
    let characterFocus = true;

    input.addEventListener('input', () => {
        characterTyped = true;
        input.removeEventListener('focus', styleInput);
        input.classList.remove("field_empty")
    });

    input.addEventListener('blur', () => {
        characterFocus = false;
        if (characterTyped === false)
        {
            label.classList.remove("onFocus")
        }

    });

    input.removeEventListener('blur', styleInput);
}

function limitDigits(event) {
    const input = event.currentTarget;
    const maxLength = input.dataset.maxLength;
    if (input.value.length > maxLength) {
        input.value = input.value.slice(0, maxLength);
    }
}

export function listenInputNumber() {
    const inputNumber = document.querySelectorAll('input[type=number], input#postal_code');

    inputNumber.forEach(input => {
        input.addEventListener('input',limitDigits)
    })
}
export function getCsrfToken() {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    return { csrfToken, csrfHeader };
}

document.addEventListener('DOMContentLoaded',listenInputNumber);
document.addEventListener('DOMContentLoaded',moveLabel);
const gridContainer = document.querySelectorAll('.grid_container');
buildGridTemplateColumns(gridContainer);
const sidebarGridContainer = document.querySelector('.Sidebar_links');
buildSidebarGridTemplateColumns(sidebarGridContainer);