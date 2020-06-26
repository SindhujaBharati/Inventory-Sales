package com.cognizant.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cognizant.sales.domain.SalesOrderEntity;

@Repository
public interface SalesRepository extends JpaRepository<SalesOrderEntity,Long>{

}
