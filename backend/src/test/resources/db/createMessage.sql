INSERT INTO person (id, created_at, email, firstname, lastname, password)
VALUES ('124e4567-e89b-12d3-a456-426614174000', '2024-08-02 12:00:00', 'example@example.com', 'John', 'Doe', 'password123');

INSERT INTO message(id, content, created_at, subject, person_id) VALUES ('543e4567-e89b-12d3-a456-456614171234', 'Test Content', '2024-09-02 12:00:00', 'Test Subject', '123e4567-e89b-12d3-a456-426614174000');