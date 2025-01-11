package com.jpacourse.persistence.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PATIENT")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String telephoneNumber;

    private String email;

    @Column(nullable = false)
    private String patientNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private Boolean Vip;

    @Column()
    private Integer height; // w centymetrach

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false) // Relacja jednostronna ze strony dziecka z address
    private AddressEntity patientAddress;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    // Relacja dwustronna z visit
    private List<VisitEntity> visits = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public AddressEntity getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(AddressEntity patientAddress) {
        this.patientAddress = patientAddress;
    }

    public List<VisitEntity> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitEntity> visits) {
        this.visits = visits;
    }

    public Boolean isVip() {
        return Vip;
    }

    public void setVip(Boolean vip) {
        Vip = vip;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
