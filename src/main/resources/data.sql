INSERT INTO public.technical (id, name, email, mobile_phone, address, province, locality, coordinates)
    VALUES(1, 'Pedrito Perez', 'pedrito@gmail.com', '653893120', 'Puente Colgante 48', 'Valladolid', 'Valladolid', '41.653943518386086, -4.72507778931894');


INSERT INTO public.customers (id, customer_code, is_enabled, name, province, coordinates, email, landline_phone,
 mobile_phone, address, description, zip_code, work_schedule, locality, country, web_page, technical_id)
VALUES (1, 'A20251', true, 'Magdiel', 'Valladolid', '41.653943518386086, -4.72507778931894', 'magdielsh30@gmail.com',
'653893120', '653893120', 'Puente Colgante 48', 'Nuevo Cliente', 47006, '8-2pm', 'Valladolid', 'España', 'www.magdiel.com', 1),
       (2, 'B20251', true, 'Mailyn', 'Valladolid', '41.653943518386086, -4.72507778931894', 'mailyn@gmail.com',
 '624877083', '624877083', 'Puente Colgante 48', 'Nuevo Cliente', 47006, '8-2pm', 'Valladolid', 'España', 'www.mailyn.com', 1);


INSERT INTO public.type_machine (id, type_machine)
VALUES (1 , 'Split'), (2, 'Multisplit'), (3, 'Por conductos'), (4, 'Portátil'), (5, 'Solar split'), (6, 'De ventana'),
       (7, 'Fancoil'), (8, 'Fancoil inverter');


INSERT INTO public.machine (id, identifier, serial_number, brand, model, customer_id, type_machine_id)
VALUES (1, 1, '123456789B', 'Midea', 'B451258M', 1, 1),
       (2, 2, '123456789A', 'Midea', 'G4512287K', 1, 2),
       (3, 1, '123456789C', 'Midea', 'B451258M', 2, 1),
       (4, 2, '123456789T', 'Midea', 'G4512287K', 2, 2);


INSERT INTO public.type_services (id, type_service)
VALUES (1 , 'Instalacion'), (2, 'Limpieza de los filtros de aire'), (3, 'Limpieza de las bobinas del evaporador y del condensador'),
       (4, 'Verificación y ajuste del nivel de refrigerante'), (5, 'Verificación y ajuste del termostato'), (6, 'Verificación y ajuste del flujo de aire');


INSERT INTO public.installations_services (id, code_installation, start_date, end_date, final_reason, quantity_equipments, type_services_id, customer_id, technical_id)
VALUES (1, '2025-A20251', '2025-01-01 00:00:00', '2025-02-01 00:00:00', 'OK', 2, 2, 1, 1),
       (2, '2025-B20251', '2025-01-01 00:00:00', '2025-02-01 00:00:00', 'OK', 2, 3, 2, 1);


INSERT INTO public.installations_services_machine (installations_services_id , machine_id)
VALUES (1, 1), (1, 2), (2, 3), (2, 4);


INSERT INTO public.incidence (id, incidence_code, incident_type, description, opening_date, closing_date, is_operational, incidence_solution, closed_by, installation_service_id)
VALUES (1, '2025-A20251-001', 'TECHNICAL_VISIT', 'Visita Correcta', '2025-01-01 00:00:00', '2025-01-01 00:00:00', false, '', 'magdiel.santana', 1);


INSERT INTO public.visits (id, visit_date, start_time, end_time, description, state_visit)
VALUES (1, '2025-01-01 00:00:00', '08:00:00', '10:00:00', 'Revision Anual', 'EXECUTED');


INSERT INTO public.visit_installations_services (visits_id, installations_services_id)
VALUES (1, 1);


INSERT INTO public.visit_technical (visits_id, technical_id)
VALUES (1, 1);