package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class PatientDaoTest {
    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testShouldFindPatientsByLastName() {
        // given
        String lastName = "Smith";

        // when
        List<PatientEntity> patients = patientDao.findByLastName(lastName);

        // then
        assertThat(patients)
                .isNotEmpty()
                .hasSize(3)
                .allMatch(p -> p.getLastName().equals(lastName));
    }

    @Test
    @Transactional
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

    @Test
    @Transactional
    public void testShouldFindPatientsWithMoreVisitsThan() {
        // given
        int visitThreshold = 2;

        // when
        List<PatientEntity> patients = patientDao.findPatientsWithMoreVisitsThan(visitThreshold);

        // then
        assertThat(patients).isNotEmpty()
                .allMatch(p -> p.getVisits().size() > visitThreshold);
        assertThat(patients).anyMatch(p -> p.getId().equals(1L));
    }

    @Test
    @Transactional
    public void testShouldFindPatientsByHeight() {
        // given
        int heightThreshold = 175;

        // when
        List<PatientEntity> patients = patientDao.findByHeightGreaterThan(heightThreshold);

        // then
        assertThat(patients).isNotEmpty()
                .allMatch(p -> p.getHeight() > heightThreshold);
    }

    @Test
    public void testOptimisticLocking() throws InterruptedException {
        // given
        Long patientId = 2L;
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // when
        executor.submit(() -> {
            try {
                entityManager.clear();
                PatientEntity patient1 = patientDao.findOne(patientId);
                patient1.setHeight(190);
                latch.await();
                assertThrows(ObjectOptimisticLockingFailureException.class,
                        () -> patientDao.update(patient1));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.submit(() -> {
            try {
                entityManager.clear();
                PatientEntity patient2 = patientDao.findOne(patientId);
                patient2.setHeight(185);
                patientDao.update(patient2);
                latch.countDown();
            } catch (Exception e) {
                latch.countDown();
            }
        });

        // then
        Thread.sleep(2000);
        executor.shutdown();

        PatientEntity finalPatient = patientDao.findOne(patientId);
        assertThat(finalPatient.getHeight()).isEqualTo(185);
    }
}
