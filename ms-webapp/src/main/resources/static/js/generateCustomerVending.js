export function attachEventListenerCustomerVending()
{
    const vendings = document.querySelectorAll('.Vending');
    vendings.forEach(vending => {
        vending.addEventListener('click', generateCustomerVending)
    })
}

function generateCustomerVending(event) {
    const vending = event.currentTarget;
    const vendingId = vending.dataset.vendingId;

    const baseUrl = window.location.origin;
    const url = new URL(`/vendings/ajax/build/${encodeURIComponent(vendingId)}/fromCustomer`, baseUrl);

    fetch(url.toString(), {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.text())
        .then(data => {
            const containerRefresh = document.querySelector('.Container_main ');
            containerRefresh.outerHTML = data;
            loadScript('/js/showVending.js');
            loadScript('/js/addBatchToVending.js');
        })
}

function loadScript(src) {
    const script = document.createElement('script');
    script.src = src;
    script.type = 'module';
    document.body.appendChild(script);
}

attachEventListenerCustomerVending()