let chart;

function generateRandomColors(count) {
    const colors = [];
    for (let i = 0; i < count; i++) {
        const color = `rgba(${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, 0.7)`;
        colors.push(color);
    }
    return colors;
}

export function initializeChart(chartType) {
    const ctx = document.getElementById('acquisitions');
    if (!ctx) return;

    const jsonData = document.querySelector('#jsonData').textContent;
    if(!jsonData) return;

    const data = JSON.parse(jsonData);

    if (chart) {
        chart.destroy();
    }

    const backgroundColors = chartType === 'bar' ? generateRandomColors(data.length) : undefined;

    chart = new Chart(ctx, {
        type: chartType,
        data: {
            labels: data.map(row => row.brand),
            datasets: [{
                label: 'Part of product in vending',
                data: data.map(row => row.total_quantity),
                backgroundColor: backgroundColors
            }]
        },
        options: {
            responsive: false,
            plugins: {
                legend: {
                    position: chartType === 'pie' ? 'right' : 'top',
                },
                title: {
                    display: true,
                    text: 'Chart',
                    padding: {
                        top: 10
                    },
                    font: {
                        size: 12
                    }
                }
            },
            scales: chartType === 'bar' ? {
                y: {
                    beginAtZero: true
                }
            } : {}
        }
    });
}

document.addEventListener("DOMContentLoaded", initializeChart("bar"));