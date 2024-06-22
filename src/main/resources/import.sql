-- Insertar registros en la tabla 'domicilio'
INSERT INTO domicilios (calle, numero, localidad, provincia) VALUES ('Calle 1', 123, 'Localidad 1', 'Provincia 1');
INSERT INTO domicilios (calle, numero, localidad, provincia) VALUES ('Calle 2', 456, 'Localidad 2', 'Provincia 2');
INSERT INTO domicilios (calle, numero, localidad, provincia) VALUES ('Calle 3', 789, 'Localidad 3', 'Provincia 3');

-- Insertar registros en la tabla 'pacientes'
-- Asegúrate de reemplazar los valores de 'domicilio_id' con los IDs correctos de los registros insertados en la tabla 'domicilio'
INSERT INTO pacientes (nombre, apellido, cedula, fecha_ingreso, domicilio_id, correo) VALUES ('Pool Petter', 'Hijuela', '00000001', '2024-06-17', 1, 'poolpetter@example.com');
INSERT INTO pacientes (nombre, apellido, cedula, fecha_ingreso, domicilio_id, correo) VALUES ('Luna', 'Montenegro', '00000002', '2024-06-15', 2, 'lunam@example.com');
INSERT INTO pacientes (nombre, apellido, cedula, fecha_ingreso, domicilio_id, correo) VALUES ('Oliver', 'Ramírez', '00000003', '2024-06-16', 3, 'oliverr@example.com');
INSERT INTO pacientes (nombre, apellido, cedula, fecha_ingreso, domicilio_id, correo) VALUES ('Aria', 'González', '00000004', '2024-06-20', 3, 'ariag@example.com');
INSERT INTO pacientes (nombre, apellido, cedula, fecha_ingreso, domicilio_id, correo) VALUES ('Max', 'Vargas', '00000005', '2024-06-21', 1, 'maxv@example.com');


