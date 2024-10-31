(async function () {
    window.addEventListener('load', function () {

        const ctx = document.getElementById('acquisitions');

        let data = JSON.parse(document.querySelector('#jsonData').outerText);
        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: data.map(row => row.brand),
                datasets: [{
                    label: 'Part of product in vending',
                    data: data.map(row => row.total_quantity),
                }]
            },
            options: {
                responsive: false,
                style: false,
                plugins: {
                    legend: {
                        position: 'right',
                    }
                }
            }
        });
    });
})();