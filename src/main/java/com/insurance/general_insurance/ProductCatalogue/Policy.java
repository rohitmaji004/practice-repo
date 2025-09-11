package com.insurance.general_insurance.ProductCatalogue;
import jakarta.persistence.*;
import java.time.*;
import com.insurance.general_insurance.user.entity.User;
import com.insurance.general_insurance.vehicle.entity.Vehicle;

@Entity
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    private String policyName;
    private String policyType;
    private Double coverageAmount;
    private Double premiumAmount;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.EAGER) // Eager load the user
    @JoinColumn(name = "user_id")
    private User user;  // Do I really need this?? I mean why does policy need to have a User Object
    //It can be mapped to may users right???Or is it just for the mapping and connecting databases
    
    @ManyToOne(fetch = FetchType.EAGER) // Eager load the vehicle
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Long getId()
    {
    	return id;
    }
    public void setId(Long id)
    {
    	this.id=id;
    }
	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
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

	

    // Getters and Setters
}
