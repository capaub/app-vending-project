const baseUrl = window.location.origin;
const batchSelect = document.querySelectorAll('.select_dlc_batch');

export function goodsOptionAttachEventListener(select) {
    select.forEach(select => {
        select.addEventListener('change', handleChangeOption)
    })
}

function handleChangeOption(event) {

    const select = event.currentTarget;
    const selectedOption = select.options[select.selectedIndex];
    const selectedValue = selectedOption.value;
    const targetGoodsUl = selectedOption.closest('ul');

    const url = new URL(`/products/ajax/refreshBatch/${encodeURIComponent(selectedValue)}`, baseUrl);

    fetch(url.toString(), {method: 'GET'})
        .then(response => response.json())
        .then(data => {

            const quantity = data.quantity;
            const updatedAtDate = data.updatedDateAt;
            const updatedAtTime = data.updatedTimeAt;

            changeBatchInfos(targetGoodsUl, quantity, updatedAtDate, updatedAtTime)

        })
}

function changeBatchInfos(targetGoodsUl, quantity, updatedAtDate, updatedAtTime) {
    const quantityLi = targetGoodsUl.querySelector('.quantity');
    const updatedAtDateLi = targetGoodsUl.querySelector('.updated_at_date');
    const updatedAtTimeLi = targetGoodsUl.querySelector('.updated_at_time');

    quantityLi.innerHTML = quantity;
    updatedAtDateLi.innerHTML = updatedAtDate;
    updatedAtTimeLi.innerHTML = updatedAtTime;
}

goodsOptionAttachEventListener(batchSelect);


