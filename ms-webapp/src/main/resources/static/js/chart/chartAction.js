import { initializeChart } from "../../chartJS/src/acquisitions.js";

document.addEventListener("DOMContentLoaded", () => {
    const vendingButton = document.querySelector(".charts_vendings_btn");
    const totalInButton = document.querySelector(".charts_totalIn_btn");
    const customerButtons = document.querySelectorAll(".charts_vendings_customer_btn");
    const vendingOptionsContainers = document.querySelectorAll(".vending-options");
    const chartContainer = document.querySelector(".Container_chart_graph");
    const baseUrl = window.location.origin;

    vendingButton.addEventListener("click", () => {
        vendingButton.classList.add("selected");
        totalInButton.classList.remove("selected");

        customerButtons.forEach(button => {
            button.classList.remove("hidden", "selected");
        });

        vendingOptionsContainers.forEach(container => container.classList.add("hidden"));
    });

    totalInButton.addEventListener("click", () => {
        totalInButton.classList.add("selected");
        vendingButton.classList.remove("selected");

        customerButtons.forEach(button => button.classList.add("hidden"));
        vendingOptionsContainers.forEach(container => container.classList.add("hidden"));

        const url = new URL(`/chartJS/ajax/stockIn`, baseUrl);
        fetch(url)
            .then(response => response.text())
            .then(html => {
                chartContainer.innerHTML = html;
                initializeChart('bar');
            })
            .catch(error => console.error("Erreur lors du chargement des données du graphique:", error));
    });

    customerButtons.forEach((customerButton) => {
        customerButton.addEventListener("click", () => {
            customerButtons.forEach(button => button.classList.remove("selected"));
            customerButton.classList.add("selected");

            vendingOptionsContainers.forEach(container => {
                container.classList.add("hidden");
                container.querySelectorAll(".charts_vendings_customer_vending_btn").forEach(btn => btn.classList.remove("selected"));
            });

            const customerId = customerButton.getAttribute("data-customer-id");
            vendingOptionsContainers.forEach((container) => {
                if (container.getAttribute("data-customer-id") === customerId) {
                    container.classList.remove("hidden");
                }
            });
        });
    });

    vendingOptionsContainers.forEach(container => {
        container.addEventListener("click", (event) => {
            const vendingButton = event.target;
            if (vendingButton.classList.contains("charts_vendings_customer_vending_btn")) {
                container.querySelectorAll(".charts_vendings_customer_vending_btn").forEach(btn => {
                    btn.classList.remove("selected");
                });

                vendingButton.classList.add("selected");

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
});
