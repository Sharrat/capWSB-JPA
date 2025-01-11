package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTOpatient;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {
    private final PatientDao patientDao;
    private final VisitDao visitDao;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao, VisitDao visitDao) {
        this.patientDao = patientDao;
        this.visitDao = visitDao;
    }

    @Override
    public PatientTO findById(Long id) {
        final PatientEntity entity = patientDao.findOne(id);
        return PatientMapper.mapToTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        patientDao.delete(id);
    }

    @Override
    public List<VisitTOpatient> findVisitsByPatientId(Long patientId) {
        return visitDao.findByPatientId(patientId)
                .stream()
                .map(VisitMapper::mapToTOpatient)
                .collect(Collectors.toList());
    }
}
