package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {
    VisitEntity createVisit(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription);

    List<PatientEntity> findByLastName(String lastName);

    List<PatientEntity> findPatientsWithMoreVisitsThan(int visitCount);

    List<PatientEntity> findByHeightGreaterThan(Integer height);
}
