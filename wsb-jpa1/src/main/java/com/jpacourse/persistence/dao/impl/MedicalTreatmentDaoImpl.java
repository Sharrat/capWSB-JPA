package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.MedicalTreatmentDao;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import org.springframework.stereotype.Repository;

@Repository
public class MedicalTreatmentDaoImpl extends AbstractDao<MedicalTreatmentEntity, Long> implements MedicalTreatmentDao {
}
