package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.dto.VisitTOpatient;
import com.jpacourse.persistence.dao.MedicalTreatmentDao;
import com.jpacourse.persistence.dao.impl.MedicalTreatmentDaoImpl;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class VisitMapper {

    private static MedicalTreatmentDao medicalTreatmentDao = new MedicalTreatmentDaoImpl();

    public static VisitTO mapToTO(final VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }

        VisitTO visitTO = new VisitTO();
        visitTO.setId(visitEntity.getId());
        visitTO.setDescription(visitEntity.getDescription());
        visitTO.setTime(visitEntity.getTime());
        visitTO.setDoctor(visitEntity.getDoctor());
        visitTO.setPatient(visitEntity.getPatient());
        visitTO.setTreatments(visitEntity.getTreatments());

        return visitTO;
    }

    public static List<VisitTO> mapToTOList(final List<VisitEntity> visitEntities) {
        if (visitEntities == null) {
            return null;
        }
        return visitEntities.stream()
                .map(VisitMapper::mapToTO)
                .collect(Collectors.toList());
    }

    public static VisitEntity mapToEntity(final PatientEntity patient, final DoctorEntity doctor, final String description, final LocalDateTime time) {

        VisitEntity visit = new VisitEntity();
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setDescription(description);
        visit.setTime(time);
        visit.setTreatments(new ArrayList<MedicalTreatmentEntity>());

        return visit;
    }

    public static VisitTOpatient mapToTOpatient(final VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }

        VisitTOpatient visitTO = new VisitTOpatient();
        visitTO.setId(visitEntity.getId());
        visitTO.setTime(visitEntity.getTime());
        visitTO.setDoctorFirstName(visitEntity.getDoctor().getFirstName());
        visitTO.setDoctorLastName(visitEntity.getDoctor().getLastName());
        List<String> Treatments = new ArrayList<>();
        for (MedicalTreatmentEntity treatment : visitEntity.getTreatments()) {
            Treatments.add(String.valueOf(treatment.getType()));
        }
        visitTO.setTreatmentTypes(Treatments);

        return visitTO;
    }

    public static List<VisitTOpatient> mapToTOpatientList(final List<VisitEntity> visitEntities) {
        if (visitEntities == null) {
            return null;
        }
        return visitEntities.stream()
                .map(VisitMapper::mapToTOpatient)
                .collect(Collectors.toList());
    }

}
