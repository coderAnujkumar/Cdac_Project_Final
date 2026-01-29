package com.csp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.csp.entity.Application;
import com.csp.entity.Citizen;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    List<Application> findByCitizenCitizenId(int citizenId);

    List<Application> findByStatus(String status);

    // ✅ FILTER BY STATUS + SERVICE TYPE
    List<Application> findByStatusAndServiceType(String status, String serviceType);

    List<Application> findByStatusOrderByApplicationDateAsc(String status);

    @Query("SELECT a FROM Application a JOIN FETCH a.citizen WHERE a.applicationId = :id")
    Application findByIdWithCitizen(@Param("id") int id);

    long countByStatus(String status);
    Optional<Application> findTopByCitizen_CitizenIdAndServiceTypeAndStatusOrderByApplicationDateDesc(
            int citizenId, String serviceType, String status);
    boolean existsByCitizenAndServiceTypeAndStatusIn(
            Citizen citizen,
            String serviceType,
            List<String> statuses);
}
