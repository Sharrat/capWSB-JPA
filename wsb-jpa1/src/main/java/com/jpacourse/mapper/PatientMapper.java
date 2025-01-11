package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.entity.PatientEntity;

import java.util.ArrayList;

public final class PatientMapper {

    public static PatientTO mapToTO(final PatientEntity patientEntity) {
        if (patientEntity == null) {
            return null;
        }
        final PatientTO patientTO = new PatientTO();
        patientTO.setId(patientEntity.getId());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setPatientAddress(patientEntity.getPatientAddress());
        patientTO.setVisits(VisitMapper.mapToTOpatientList(patientEntity.getVisits()));
        patientTO.setVip(patientEntity.isVip());
        patientTO.setHeight(patientEntity.getHeight());

        return patientTO;
    }

    public static PatientEntity mapToEntity(final PatientTO patientTO) {
        if (patientTO == null) {
            return null;
        }
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientTO.getId());
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        patientEntity.setPatientAddress(patientTO.getPatientAddress());
        patientEntity.setVisits(new ArrayList<>());
        patientEntity.setVip(patientTO.isVip());
        patientEntity.setHeight(patientTO.getHeight());
        return patientEntity;
    }
}
