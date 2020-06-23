package com.cognizant.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cognizant.sales.domain.SalesOrder;

@Repository
public interface SalesRepository extends JpaRepository<SalesOrder,Long>{

}
