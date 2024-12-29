package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTOpatient;
import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.VisitDao;
import com.jpacourse.persistence.entity.*;
import com.jpacourse.persistence.enums.TreatmentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Mock
    private PatientDao patientDao;

    @Mock
    private DoctorDao doctorDao;

    @Mock
    private VisitDao visitDao;

    @Transactional
    @Test
    public void testShouldDeletePatientWithCorrectCascade() {
        // given
        PatientTO patient = patientService.findById(1L);
        assertThat(patient).isNotNull();
        assertThat(patient.getVisits()).isNotEmpty();
        ArrayList<DoctorEntity> DoctorsBeforeDelete = new ArrayList<>(doctorDao.findAll());
        //when
        patientService.delete(1L);
        // then
        PatientTO patientAfterDelete = patientService.findById(1L);
        assertThat(patientAfterDelete).isNull();
        assertThat(visitDao.findByPatientId(1L)).isEmpty();
        ArrayList<DoctorEntity> DoctorsAfterDelete = new ArrayList<>(doctorDao.findAll());
        assertThat(DoctorsBeforeDelete).isEqualTo(DoctorsAfterDelete);
    }

    @Transactional
    @Test
    public void testFindPatientByID_ReturnsCorrectStructure() {
        // given
        Long patientId = 1L;

        // Mock danych adresu
        AddressEntity address = new AddressEntity();
        address.setId(1L);
        address.setAddressLine1("xx");
        address.setAddressLine2("yy");
        address.setCity("city");
        address.setPostalCode("62-030");

        // Mock danych lekarza
        DoctorEntity doctor = new DoctorEntity();
        doctor.setId(1L);
        doctor.setFirstName("Adam");
        doctor.setLastName("Nych");

        // Mock danych wizyty
        VisitEntity visit = new VisitEntity();
        visit.setId(1L);
        visit.setDescription("Checkup");
        visit.setTime(LocalDateTime.of(2024, 6, 24, 10, 0));
        visit.setDoctor(doctor);

        // Mock danych zabiegów medycznych
        MedicalTreatmentEntity treatment = new MedicalTreatmentEntity();
        treatment.setId(1L);
        treatment.setDescription("Basic Ultrasound");
        treatment.setType(TreatmentType.USG);
        visit.setTreatments(List.of(treatment));

        // Mock danych pacjenta
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientId);
        patientEntity.setFirstName("Milosz");
        patientEntity.setLastName("Mikulski");
        patientEntity.setTelephoneNumber("123456789");
        patientEntity.setEmail("milosz.mikulski@gmail.com");
        patientEntity.setPatientNumber("123");
        patientEntity.setDateOfBirth(LocalDate.of(1996, 12, 12));
        patientEntity.setPatientAddress(address);
        patientEntity.setVisits(List.of(visit));
        patientEntity.setVip(true);

        // Mock zachowania Dao
        when(patientDao.findOne(patientId)).thenReturn(patientEntity);

        // when
        PatientTO patient = patientService.findById(1L);

        // then
        assertThat(patient).isNotNull();
        assertThat(patient.getId()).isEqualTo(1L);
        assertThat(patient.getFirstName()).isEqualTo("Milosz");
        assertThat(patient.getLastName()).isEqualTo("Mikulski");
        assertThat(patient.getTelephoneNumber()).isEqualTo("123456789");
        assertThat(patient.getEmail()).isEqualTo("milosz.mikulski@gmail.com");
        assertThat(patient.getPatientNumber()).isEqualTo("123");
        assertThat(patient.getDateOfBirth()).isEqualTo(LocalDate.of(1996, 12, 12));
        assertThat(patient.isVip()).isTrue();

        // Weryfikacja adresu pacjenta
        AddressEntity addressReturned = patient.getPatientAddress();
        assertThat(addressReturned).isNotNull();
        assertThat(addressReturned.getId()).isEqualTo(1L);
        assertThat(addressReturned.getAddressLine1()).isEqualTo("xx");
        assertThat(addressReturned.getAddressLine2()).isEqualTo("yy");
        assertThat(addressReturned.getCity()).isEqualTo("city");
        assertThat(addressReturned.getPostalCode()).isEqualTo("62-030");

        // Weryfikacja wizyt
        assertThat(patient.getVisits()).isNotEmpty();
        assertThat(patient.getVisits()).hasSize(1);

        VisitTOpatient visitReturned = patient.getVisits().get(0);
        assertThat(visitReturned.getId()).isEqualTo(1L);
        assertThat(visitReturned.getTime()).isEqualTo(LocalDateTime.of(2024, 6, 24, 10, 0));
        assertThat(visitReturned.getDoctorFirstName()).isEqualTo("Adam");
        assertThat(visitReturned.getDoctorLastName()).isEqualTo("Nych");
        assertThat(visitReturned.getTreatmentTypes()).containsExactly("USG");
    }
}