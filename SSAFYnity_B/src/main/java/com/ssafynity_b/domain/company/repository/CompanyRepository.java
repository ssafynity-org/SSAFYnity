package com.ssafynity_b.domain.company.repository;

import com.ssafynity_b.domain.company.entity.Company;
import com.ssafynity_b.domain.company.repository.custom.CompanyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long>, CompanyRepositoryCustom {
}
