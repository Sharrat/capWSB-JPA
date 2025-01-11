package com.jpacourse.persistence.dao.impl;

import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public VisitEntity createVisit(Long patientId, Long doctorId, LocalDateTime time, String description) {

        PatientEntity patient = em.find(PatientEntity.class, patientId);
        DoctorEntity doctor = em.find(DoctorEntity.class, doctorId);

        VisitEntity visit = VisitMapper.mapToEntity(patient, doctor, description, time);
        patient.getVisits().add(visit);
        em.persist(visit);
        em.merge(patient);

        return visit;
    }

    @Override
    public List<PatientEntity> findByLastName(String lastName) {
        TypedQuery<PatientEntity> query = em.createQuery(
                "SELECT p FROM PatientEntity p WHERE p.lastName = :lastName",
                PatientEntity.class);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithMoreVisitsThan(int visitCount) {
        TypedQuery<PatientEntity> query = em.createQuery(
                "SELECT DISTINCT p FROM PatientEntity p WHERE SIZE(p.visits) > :visitCount",
                PatientEntity.class);
        query.setParameter("visitCount", visitCount);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findByHeightGreaterThan(Integer height) {
        TypedQuery<PatientEntity> query = em.createQuery(
                "SELECT p FROM PatientEntity p WHERE p.height > :height",
                PatientEntity.class);
        query.setParameter("height", height);
        return query.getResultList();
    }
}
