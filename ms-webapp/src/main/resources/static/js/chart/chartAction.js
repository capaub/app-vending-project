import { initializeChart } from "../../chartJS/src/acquisitions.js";

document.addEventListener("DOMContentLoaded", () => {
    const vendingButton = document.querySelector(".charts_vendings_btn");
    const totalInButton = document.querySelector(".charts_totalIn_btn");
    const totalOutButton = document.querySelector(".charts_totalOut_btn");
    const customerButtons = document.querySelectorAll(".charts_vendings_customer_btn");
    const vendingOptionsContainers = document.querySelectorAll(".vending-options");
    const vendings = document.querySelectorAll(".charts_vendings_customer_vending_btn");
    const chartContainer = document.querySelector(".Container_chart_graph");
    const baseUrl = window.location.origin;

    function showOnlySelected(selectedButton) {
        [vendingButton, totalInButton, totalOutButton].forEach(button => {
            button.classList.add("minimized");
        });
        vendingButton.classList.remove("minimized");
        selectedButton.classList.add("selected");
    }

    function resetButtons() {
        [vendingButton, totalInButton, totalOutButton].forEach(button => button.classList.remove("selected", "minimized"));
        customerButtons.forEach(button => button.classList.add("hidden"));
        vendingOptionsContainers.forEach(container => container.classList.add("hidden"));
    }

    vendingButton.addEventListener("click", () => {
        resetButtons();
        showOnlySelected(vendingButton);
        customerButtons.forEach(button => button.classList.remove("hidden"));
    });

    customerButtons.forEach((customerButton, index) => {
        customerButton.addEventListener("click", () => {
            customerButtons.forEach(button => button.classList.add("hidden"));
            customerButton.classList.remove("hidden");
            customerButton.classList.add("selected");

            vendingOptionsContainers.forEach((container, i) => {
                if (i === index) {
                    container.classList.remove("hidden");
                } else {
                    container.classList.add("hidden");
                }
            });
        });
    });

    totalInButton.addEventListener("click", () => {
        const url = new URL(`/chartJS/ajax/stockIn`, baseUrl);
        fetch(url)
            .then(response => response.text())
            .then(html => {
                chartContainer.innerHTML = html;
                initializeChart('bar');
            })
            .catch(error => console.error("Erreur lors du chargement des données du graphique:", error));
    })

    totalOutButton.addEventListener("click", () => {
        const url = new URL(`/chartJS/ajax/stockOut`, baseUrl);
        fetch(url)
            .then(response => response.text())
            .then(html => {
                chartContainer.innerHTML = html;
                initializeChart('bar');
            })
            .catch(error => console.error("Erreur lors du chargement des données du graphique:", error));
    })

    vendingOptionsContainers.forEach(container => {
        container.addEventListener("click", (event) => {
            const vendingButton = event.target;
            if (vendingButton.classList.contains("charts_vendings_customer_vending_btn")) {
                const vendingId = vendingButton.getAttribute("data-vending-id");

                const url = new URL(`/chartJS/ajax/vendingStock/${encodeURIComponent(vendingId)}`, baseUrl);

                fetch(url)
                    .then(response => response.text())
                    .then(html => {
                        chartContainer.innerHTML = html;
                        initializeChart('pie');
                    })
                    .catch(error => console.error("Erreur lors du chargement des données du graphique:", error));
            }
        });
    });

    totalInButton.addEventListener("click", () => {
        resetButtons();
        totalInButton.classList.add("selected");
    });

    totalOutButton.addEventListener("click", () => {
        resetButtons();
        totalOutButton.classList.add("selected");
    });

    vendings.forEach(container => {
        container.addEventListener("click", (event) => {
            event.target.classList.add("selected");
        })
    });

        [vendingButton, totalOutButton].forEach(button => button.classList.add("minimized"));
});


