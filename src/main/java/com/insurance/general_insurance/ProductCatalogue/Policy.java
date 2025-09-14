package com.insurance.general_insurance.ProductCatalogue;

import com.insurance.general_insurance.user.entity.User;
import com.insurance.general_insurance.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
public class Policy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String policyName;

	@Enumerated(EnumType.STRING)
	private PolicyType policyType;

	private Double insuredDeclaredValue; // Renamed from coverageAmount
	private Double premiumAmount;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;

	@ElementCollection(targetClass = Addon.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "policy_addons", joinColumns = @JoinColumn(name = "policy_id"))
	@Enumerated(EnumType.STRING)
	private Set<Addon> addons;

	@Enumerated(EnumType.STRING)
	private RenewalFrequency renewalFrequency;


	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public PolicyType getPolicyType() {
		return policyType;
	}

	public void setPolicyType(PolicyType policyType) {
		this.policyType = policyType;
	}

	public Double getInsuredDeclaredValue() {
		return insuredDeclaredValue;
	}

	public void setInsuredDeclaredValue(Double insuredDeclaredValue) {
		this.insuredDeclaredValue = insuredDeclaredValue;
	}

	public Double getPremiumAmount() {
		return premiumAmount;
	}

	public void setPremiumAmount(Double premiumAmount) {
		this.premiumAmount = premiumAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Set<Addon> getAddons() {
		return addons;
	}

	public void setAddons(Set<Addon> addons) {
		this.addons = addons;
	}

	public RenewalFrequency getRenewalFrequency() {
		return renewalFrequency;
	}

	public void setRenewalFrequency(RenewalFrequency renewalFrequency) {
		this.renewalFrequency = renewalFrequency;
	}
}