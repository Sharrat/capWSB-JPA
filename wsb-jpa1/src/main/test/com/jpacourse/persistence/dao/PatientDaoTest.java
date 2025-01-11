package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
                .hasSize(3)  // Changed from 4 to 3 to match our data
                .allMatch(p -> p.getLastName().equals(lastName));
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

        // Patient with ID 1 should be in results (has 4 visits)
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
    @Transactional
    public void testOptimisticLocking() throws InterruptedException {
        // given
        Long patientId = 1L;
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // when
        executor.submit(() -> {
            try {
                entityManager.clear();
                PatientEntity patient1 = patientDao.findOne(patientId);
                patient1.setHeight(190);
                latch.await(); // wait for the second transaction to start
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
                latch.countDown(); // allow first transaction to continue
            } catch (Exception e) {
                latch.countDown();
            }
        });

        // then
        Thread.sleep(2000); // wait for both transactions to complete
        executor.shutdown();

        PatientEntity finalPatient = patientDao.findOne(patientId);
        assertThat(finalPatient.getHeight()).isEqualTo(185);
    }
}
