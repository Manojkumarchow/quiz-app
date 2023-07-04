-- Create the table
CREATE TABLE room (
    id INT AUTO_INCREMENT PRIMARY KEY,
    room_no BIGINT,
    limit_value BIGINT
);
ALTER TABLE room_persons DROP FOREIGN KEY FK47cgjs4vchcqnxcjbo6hkxajd;
ALTER TABLE room_persons DROP FOREIGN KEY FK47cgjs4vchcqnxcjbo6hkxajd;
drop table room_persons;
ALTER TABLE room_persons MODIFY COLUMN room_id INT NOT NULL;
ALTER TABLE room_persons ADD CONSTRAINT FK47cgjs4vchcqnxcjbo6hkxajd FOREIGN KEY (room_id) REFERENCES room (id);

commit;


-- Insert records
INSERT INTO room (room_no, limit_value)
VALUES
    (101, 5),
    (102, 4),
    (103, 2),
    (104, 3),
    (105, 6),
    (106, 4),
    (107, 3),
    (108, 5),
    (109, 2),
    (110, 4),
    (111, 3),
    (112, 6),
    (113, 5),
    (114, 4),
    (115, 2),
    (116, 3);
