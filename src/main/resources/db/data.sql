TRUNCATE TABLE event CASCADE;
TRUNCATE TABLE ticket CASCADE;
TRUNCATE TABLE guest CASCADE;

INSERT INTO event(event_id, title, event_description, start_time, end_time, location) VALUES
(200, 'Music show', 'Afro fusion', '2024-06-03T04:30', '2024-06-03T07:30', 'Eko Convention Centre'),
(201, 'Music show', 'Afro fusion', '2024-06-03T04:30', '2024-06-03T07:30', 'Eko Convention Centre'),
(203, 'Music show', 'Afro fusion', '2024-06-03T04:30', '2024-06-03T07:30', 'Eko Convention Centre');

INSERT INTO ticket(id, amount, category,ticket_status, ticket_number) VALUES
(100, 25000.00, 'VVIP', 'AVAILABLE','234435676'),
(101, 25000.00, 'VIP','AVAILABLE', '234435676'),
(102, 25000.00, 'REGULAR','AVAILABLE', '234435676');

INSERT INTO guest(guest_id, user_name, email, number_of_tickets) VALUES
(300, 'Johnson', 'Johnson@gmail.com', 1),
(301, 'Johnson', 'Johnson1@gmail.com', 1),
(303, 'Johnson', 'Johnson2@gmail.com', 1);
