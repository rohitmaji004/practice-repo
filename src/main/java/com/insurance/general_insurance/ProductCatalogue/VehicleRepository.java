package com.insurance.general_insurance.ProductCatalogue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>{

}
