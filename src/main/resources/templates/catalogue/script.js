// Sample data
let policies = [
  { id: 101, description: "Comprehensive Car Insurance", coverage: 800000, type: "Comprehensive" },
  { id: 102, description: "Third-Party Bike Insurance", coverage: 200000, type: "Third-Party" },
  { id: 103, description: "Own Damage for Commercial Vehicle", coverage: 1200000, type: "Own Damage" },
  { id: 104, description: "Comprehensive SUV Insurance", coverage: 1500000, type: "Comprehensive" }
];

function renderTable(data) {
  const tbody = document.querySelector("#policiesTable tbody");
  tbody.innerHTML = "";
  data.forEach(p => {
    tbody.innerHTML += `
      <tr>
        <td>${p.id}</td>
        <td>${p.description}</td>
        <td>₹${p.coverage.toLocaleString()}</td>
        <td>${p.type}</td>
        <td class="text-center">
          <button class="btn btn-sm btn-outline-primary me-1"><i class="bi bi-pencil"></i></button>
          <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
        </td>
      </tr>`;
  });
}

function renderKPIs() {
  document.getElementById("kpiCustomers").textContent = "842";
  document.getElementById("kpiPolicies").textContent = policies.length;
  document.getElementById("kpiRevenue").textContent = "₹ " + (policies.length * 5000).toLocaleString();
}

// Filter functionality
document.getElementById("filterForm").addEventListener("submit", e => {
  e.preventDefault();
  const type = document.getElementById("typeFilter").value;
  const min = parseInt(document.getElementById("minCoverage").value) || 0;
  const max = parseInt(document.getElementById("maxCoverage").value) || Infinity;

  let filtered = policies.filter(p =>
    (type === "all" || p.type === type) &&
    p.coverage >= min &&
    p.coverage <= max
  );

  renderTable(filtered);
});

document.getElementById("resetFilters").addEventListener("click", () => {
  document.getElementById("typeFilter").value = "all";
  document.getElementById("minCoverage").value = "";
  document.getElementById("maxCoverage").value = "";
  renderTable(policies);
});

// Init
renderTable(policies);
renderKPIs();
