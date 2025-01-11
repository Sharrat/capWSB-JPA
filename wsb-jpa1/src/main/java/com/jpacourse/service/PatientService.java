package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTOpatient;

import java.util.List;

public interface PatientService {
    public PatientTO findById(final Long id);

    void delete(final Long id);

    List<VisitTOpatient> findVisitsByPatientId(Long patientId);
}
