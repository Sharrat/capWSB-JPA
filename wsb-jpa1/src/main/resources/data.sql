-- Dodawanie adresów
insert into address (id, address_line1, address_line2, city, postal_code)
values (1, 'xx', 'yy', 'city', '62-030');
insert into address (id, address_line1, address_line2, city, postal_code)
values (2, 'aa', 'bb', 'city2', '63-040');
insert into address (id, address_line1, address_line2, city, postal_code)
values (3, 'cc', 'dd', 'city3', '64-050');
insert into address (id, address_line1, address_line2, city, postal_code)
values (4, 'ee', 'ff', 'city4', '65-060');
insert into address (id, address_line1, address_line2, city, postal_code)
values (5, 'gg', 'hh', 'city5', '66-070');

-- Dodawanie doktorów
insert into doctor (id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id)
values (1, '123', 'adam@gmail.com', 'Adam', 'Nych', 'DENTIST', '123456789', 1);
insert into doctor (id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id)
values (2, '124', 'barbara@gmail.com', 'Barbara', 'Smith', 'GP', '223456789', 2);
insert into doctor (id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id)
values (3, '125', 'charles@gmail.com', 'Charles', 'Brown', 'SURGEON', '323456789', 3);
insert into doctor (id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id)
values (4, '126', 'diane@gmail.com', 'Diane', 'Taylor', 'OCULIST', '423456789', 4);
insert into doctor (id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id)
values (5, '127', 'edward@gmail.com', 'Edward', 'Wilson', 'DERMATOLOGIST', '523456789', 5);

-- Dodawanie pacjentów
insert into patient (id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id, vip)
values (1, to_date('1996-12-12', 'yyyy-mm-dd'), 'milosz.mikulski@gmail.com', 'Milosz', 'Mikulski', '123', '123456789',
        1, true);
insert into patient (id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id, vip)
values (2, to_date('1985-05-20', 'yyyy-mm-dd'), 'anna.kowalska@gmail.com', 'Anna', 'Kowalska', '124', '223456789', 2,
        false);
insert into patient (id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id, vip)
values (3, to_date('1990-03-15', 'yyyy-mm-dd'), 'john.doe@gmail.com', 'John', 'Doe', '125', '323456789', 3, true);
insert into patient (id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id, vip)
values (4, to_date('2000-07-07', 'yyyy-mm-dd'), 'susan.lee@gmail.com', 'Susan', 'Lee', '126', '423456789', 4, false);
insert into patient (id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id, vip)
values (5, to_date('1975-11-11', 'yyyy-mm-dd'), 'michael.king@gmail.com', 'Michael', 'King', '127', '523456789', 5,
        true);

-- Dodawanie wizyt
insert into visit (id, description, time, patient_id, doctor_id)
values (1, 'Checkup', to_timestamp('2024-06-24 10:00:00', 'yyyy-mm-dd hh24:mi:ss'), 1, 1);
insert into visit (id, description, time, patient_id, doctor_id)
values (2, 'Consultation', to_timestamp('2024-07-15 12:30:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 2);
insert into visit (id, description, time, patient_id, doctor_id)
values (3, 'Surgery', to_timestamp('2024-08-10 14:00:00', 'yyyy-mm-dd hh24:mi:ss'), 3, 3);
insert into visit (id, description, time, patient_id, doctor_id)
values (4, 'Eye Test', to_timestamp('2024-09-05 09:00:00', 'yyyy-mm-dd hh24:mi:ss'), 4, 4);
insert into visit (id, description, time, patient_id, doctor_id)
values (5, 'Skin Check', to_timestamp('2024-10-20 11:00:00', 'yyyy-mm-dd hh24:mi:ss'), 5, 5);

-- Dodawanie zabiegów medycznych
insert into medical_treatment (id, description, type, visit_id)
values (1, 'Basic Ultrasound', 'USG', 1);
insert into medical_treatment (id, description, type, visit_id)
values (2, 'Routine ECG', 'EKG', 2);
insert into medical_treatment (id, description, type, visit_id)
values (3, 'Chest X-Ray', 'RTG', 3);
insert into medical_treatment (id, description, type, visit_id)
values (4, 'Vision Screening', 'USG', 4);
insert into medical_treatment (id, description, type, visit_id)
values (5, 'Dermatological Examination', 'RTG', 5);

-- Nowe adresy dla nowych pacjentów
insert into address (id, address_line1, address_line2, city, postal_code)
values (6, 'Street 6', 'Apt 6', 'city6', '67-080');
insert into address (id, address_line1, address_line2, city, postal_code)
values (7, 'Street 7', 'Apt 7', 'city7', '68-090');
insert into address (id, address_line1, address_line2, city, postal_code)
values (8, 'Street 8', 'Apt 8', 'city8', '69-100');

-- Nowi pacjenci z tym samym nazwiskiem ale różnym wzrostem
insert into patient (id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id, vip, version, height)
values (6, to_date('1980-01-01', 'yyyy-mm-dd'), 'james.smith@gmail.com', 'James', 'Smith', '128', '623456789', 6, false, 0, 175);
insert into patient (id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id, vip, version, height)
values (7, to_date('1982-02-02', 'yyyy-mm-dd'), 'mary.smith@gmail.com', 'Mary', 'Smith', '129', '723456789', 7, true, 0, 165);
insert into patient (id, date_of_birth, email, first_name, last_name, patient_number, telephone_number, address_id, vip, version, height)
values (8, to_date('1984-03-03', 'yyyy-mm-dd'), 'robert.smith@gmail.com', 'Robert', 'Smith', '130', '823456789', 8, false, 0, 180);

-- Aktualizacja starych pacjentów bez wzrostu
update patient set height = 170, version = 0 where id = 1;
update patient set height = 165, version = 0 where id = 2;
update patient set height = 175, version = 0 where id = 3;
update patient set height = 168, version = 0 where id = 4;
update patient set height = 182, version = 0 where id = 5;

-- Dodajemy kilka nowych wizyt
insert into visit (id, description, time, patient_id, doctor_id)
values (6, 'Follow-up', to_timestamp('2024-11-15 10:00:00', 'yyyy-mm-dd hh24:mi:ss'), 1, 1);
insert into visit (id, description, time, patient_id, doctor_id)
values (7, 'Annual Check', to_timestamp('2024-12-20 14:30:00', 'yyyy-mm-dd hh24:mi:ss'), 1, 2);
insert into visit (id, description, time, patient_id, doctor_id)
values (8, 'Emergency', to_timestamp('2024-12-25 09:15:00', 'yyyy-mm-dd hh24:mi:ss'), 1, 3);
insert into visit (id, description, time, patient_id, doctor_id)
values (9, 'Consultation', to_timestamp('2024-11-10 11:00:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 1);
insert into visit (id, description, time, patient_id, doctor_id)
values (10, 'Follow-up', to_timestamp('2024-11-25 15:45:00', 'yyyy-mm-dd hh24:mi:ss'), 2, 1);
