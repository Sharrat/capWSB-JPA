insert into address (id, address_line1, address_line2, city, postal_code)
            values (1, 'xx', 'yy', 'city', '62-030');
insert into doctor (id, doctor_number, email, first_name, last_name, specialization, telephone_number)
            values (1, '123', 'Adam@gmail.com', 'Adam', 'Nych', 'Dentist', '123456789');
insert into medical_treatment (id, description, type)
            values (1, 'xx', 'yy');
insert into patient (id, date_of_birth, email, first_name, last_name, patient_number, telephone_number)
            values (1, to_date('1996-12-12','yyyy-mm-dd'), 'Badam@gmail.com', 'Milosz', 'Mikulski', '123', '123456789');
insert into visit (id, description, time)
            values (1, 'xx',  '2024-06-24 00:00:00');