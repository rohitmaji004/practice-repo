document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("policyForm");
  const successMessage = document.getElementById("successMessage");

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    if (!form.checkValidity()) {
      form.classList.add("was-validated");
      return;
    }

    // Collect form values
    const policyData = {
      policyName: document.getElementById("policyName").value,
      policyType: document.getElementById("policyType").value,
      coverageAmount: document.getElementById("coverageAmount").value,
      premiumAmount: document.getElementById("premiumAmount").value,
      description: document.getElementById("description").value,
      startDate: document.getElementById("startDate").value,
      endDate: document.getElementById("endDate").value,
    };

    // Show success message with summary
    successMessage.classList.remove("d-none");
    successMessage.innerHTML = `
      <h6 class="fw-bold mb-2">✅ Policy Added Successfully!</h6>
      <div class="card bg-light border-0">
        <div class="card-body p-2">
          <p class="mb-1"><strong>Name:</strong> ${policyData.policyName}</p>
          <p class="mb-1"><strong>Type:</strong> ${policyData.policyType}</p>
          <p class="mb-1"><strong>Coverage:</strong> ₹${policyData.coverageAmount}</p>
          <p class="mb-1"><strong>Premium:</strong> ₹${policyData.premiumAmount}</p>
          <p class="mb-1"><strong>Start:</strong> ${policyData.startDate}</p>
          <p class="mb-0"><strong>End:</strong> ${policyData.endDate}</p>
        </div>
      </div>
    `;

    // Reset form
    form.reset();
    form.classList.remove("was-validated");
  });
});
