package com.insurance.general_insurance.ProductCatalogue;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyDTO {
    
	
    private String policyName;
    private String policyType;
    private Double coverageAmount;
    private Double premiumAmount;
    private String description;
    
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName)
	{
		this.policyName=policyName;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public Double getCoverageAmount() {
		return coverageAmount;
	}
	public void setCoverageAmount(Double coverageAmount) {
		this.coverageAmount = coverageAmount;
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
    
    // Add more fields as per your requirement.
    
}
