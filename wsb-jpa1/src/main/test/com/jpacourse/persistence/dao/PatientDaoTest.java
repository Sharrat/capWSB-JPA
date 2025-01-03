package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class PatientDaoTest {
    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @Test
    public void testShouldCorrectlyCreateVisitAndCascade() {
        //given
        DoctorEntity doctor = doctorDao.findOne(1L);
        assertThat(doctor).isNotNull();
        PatientEntity patient = patientDao.findOne(1L);
        assertThat(patient).isNotNull();
        LocalDateTime DateTime = LocalDateTime.now();
        String description = "Description";
        int VisitsSize = patient.getVisits().size();
        //when
        VisitEntity result = patientDao.createVisit(1L, 1L, DateTime, description);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getPatient()).isEqualTo(patient);
        assertThat(result.getDoctor()).isEqualTo(doctor);
        assertThat(result.getTime()).isEqualTo(DateTime);
        assertThat(patient.getVisits().contains(result)).isTrue();
        assertThat(patient.getVisits().size()).isEqualTo(VisitsSize + 1);
        assertThat(result.getDescription()).isEqualTo(description);
    }
}