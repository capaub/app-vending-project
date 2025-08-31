import {buildGridTemplateColumns, getCsrfToken} from "./global.js";
import {SnapShotForm} from "./SnapShotForm.js";

const baseUrl = window.location.origin;
const url = new URL('/users/ajax/update', baseUrl);
const deletePath = '/users/ajax/delete';

let initialFormValues = {};

function createInitialShot(formUser) {
    const userId = formUser.dataset.id;
    const snapshotForm = new SnapShotForm(formUser);

    snapshotForm.shot();

    initialFormValues[userId] = snapshotForm;
}

function listenUpdateButton(container) {
    const updateButtons = container.querySelectorAll('.update');
    updateButtons.forEach(button => {
        button.addEventListener('click', updateUser);
    });
}

function updateUser(event) {
    event.preventDefault();
    const userElement = event.currentTarget.closest('[data-id]');

    DisableOtherButton(userElement);
    listenCancelButton(userElement);
}

function DisableOtherButton(userElement) {
    toggleDisabledForm(userElement);

    listenSaveButton(userElement);
    listenDeleteButton(userElement);

    const allUserElements = document.querySelectorAll('[data-id]');
    allUserElements.forEach((element) => {
        if (element !== userElement) {
            const userButton = element.querySelectorAll('.update, .delete');
            userButton.forEach(button => {
                button.disabled = true;
            })
        }
    });
    toggleBtns(userElement);
}

function listenDeleteButton(container) {
    const deleteButtons = container.querySelectorAll('.delete');
    deleteButtons.forEach(button => {
        button.addEventListener('click', deleteUser);
    });
}

function deleteUser(event) {
    event.preventDefault();
    if (window.confirm('souhaitez-vous vraiment supprimer cet utilisateur ?')) {
        const userElement = event.currentTarget.closest('[data-id]');
        const deleteUrl = new URL(deletePath + '/' + userElement.dataset.id, baseUrl)
        const { csrfToken, csrfHeader } = getCsrfToken();

        fetch(deleteUrl.toString(), {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json',
                'X-Requested-With': 'XMLHttpRequest',
                [csrfHeader]: csrfToken
            },
        })
            .then(() => deleteContainer(userElement));
    }
}

function listenSaveButton(container) {
    const saveButton = container.querySelector('.save');
    saveButton.setAttribute("disabled", "disabled");
    container.addEventListener('change', removeDisabledOnSaveButton);

    saveButton.addEventListener('click', userSaveChange);
}

function userSaveChange(event) {
    event.preventDefault();
    const userElement = event.currentTarget.closest('[data-id]');
    const userId = userElement.getAttribute('data-id');

    const { csrfToken, csrfHeader } = getCsrfToken();

    let formData = new FormData(userElement);
    let formObject = {};
    formData.forEach((value, key) => {
        if (key === 'authorities') {
            if (!formObject[key]) {
                formObject[key] = [];
            }
            formObject[key].push(value);
        } else {
            formObject[key] = value;
        }
    });
    formObject['id'] = userId;

    if (userElement.checkValidity()) {
        fetch(url.toString(), {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json',
                'X-Requested-With': 'XMLHttpRequest',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(formObject)
        })
            .then(response => response.json())
            .then(data => {
                replaceContainer(userElement, data);
                toggleDisabledForm(userElement);
                toggleBtns(userElement);
            })
            .then(() => {
                const usersForm = usersContainer.querySelectorAll('.user form');
                usersForm.forEach(userForm => {
                    createInitialShot(userForm)
                })
                const container = document.querySelector('.UsersContainer')
                removeDisabled(container)
            })
    } else {
        const invalidFields = Array.from(userElement.elements).filter(element => !element.validity.valid);

        invalidFields.forEach(field => {
            field.classList.add("field_empty");
        });
    }
}

function listenCancelButton(container) {
    const cancelButton = container.querySelector('.cancel');
    cancelButton.addEventListener('click', cancelModify);
}

function cancelModify(event) {

    event.preventDefault();
    const userForm = event.currentTarget.closest('[data-id]');
    const userId = userForm.dataset.id;

    initialFormValues[userId].restoreShot();

    toggleBtns(userForm);
    toggleDisabledForm(userForm);

    const container = document.querySelector('.UsersContainer');
    removeDisabled(container);
}

function removeDisabledOnSaveButton(event) {
    const saveButton = event.currentTarget.querySelector('.save');
    saveButton.removeAttribute("disabled");
}

function toggleDisabledForm(formElement) {
    const inputItems = formElement.querySelectorAll('form input,form select');
    for (let i = 0; i < inputItems.length; i++) {
        inputItems[i].disabled = !inputItems[i].disabled;
    }
}

function toggleBtns(formElement) {

    const buttons = formElement.querySelectorAll('button');
    buttons.forEach(button => {
        button.classList.toggle("hidden")
    });
}

function removeDisabled(container) {
    const userButton = container.querySelectorAll('.update, .delete');
    userButton.forEach(button => {
        button.disabled = false;
    })
}

function replaceContainer(container, data) {
    container.querySelector('input[name="firstname"]').value = data.firstname;
    container.querySelector('input[name="lastname"]').value = data.lastname;
    container.querySelector('input[name="email"]').value = data.email;
    container.querySelector('select[name="authorities"]').value = data.authorities[0];

    const roleSelect = container.querySelector('select[name="authorities"]');
    roleSelect.value = data.authorities ? data.authorities[0] : "";

    if (data.connectedAt != null) {
        container.querySelector('.connected_at_date').value = data.connectedAt.substring(0, 10);
        container.querySelector('.connected_at_time').value = data.connectedAt.substring(12, 19);
    } else {
        console.log(data.connectedAt)
        console.log(container.querySelector('.connected_at_date'))
        container.querySelector('.connected_at_date').value = 'aucune';
        container.querySelector('.connected_at_time').value = 'connexion';
    }

    listenUpdateButton(container);
    listenDeleteButton(container);
}

function deleteContainer(container) {
    container.parentNode.removeChild(container);
}

const gridContainerForm = document.querySelectorAll('.grid_container_form');
buildGridTemplateColumns(gridContainerForm);

const usersContainer = document.querySelector('.UsersContainer');
listenUpdateButton(usersContainer);
listenDeleteButton(usersContainer);

const usersForm = usersContainer.querySelectorAll('.user form');
usersForm.forEach(userForm => {
    createInitialShot(userForm)
})