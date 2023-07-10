CREATE TABLE fee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount BIGINT,
    currency VARCHAR(255),
    person_id BIGINT,
    refId VARCHAR(255),
    paidDate DATE,
    paymentType VARCHAR(255) not null default 'CASH',
    FOREIGN KEY (person_id) REFERENCES persons (id)
);


INSERT INTO fee (amount, currency, person_id, refId, paidDate, paymentType) VALUES
(1000, 'USD', 1, 'REF001', '2023-07-01', 'Card'),
(500, 'EUR', 2, 'REF002', '2023-07-02', 'Cash'),
(750, 'GBP', 3, 'REF003', '2023-07-03', 'Card'),
(1200, 'USD', 4, 'REF004', '2023-07-04', 'Bank Transfer'),
(900, 'CAD', 5, 'REF005', '2023-07-05', 'Card'),
(1500, 'USD', 6, 'REF006', '2023-07-06', 'Cash'),
(800, 'GBP', 7, 'REF007', '2023-07-07', 'Card'),
(950, 'EUR', 8, 'REF008', '2023-07-08', 'Bank Transfer'),
(1100, 'USD', 9, 'REF009', '2023-07-09', 'Card'),
(600, 'CAD', 10, 'REF010', '2023-07-10', 'Cash'),
(1300, 'USD', 11, 'REF011', '2023-07-11', 'Card'),
(850, 'GBP', 12, 'REF012', '2023-07-12', 'Bank Transfer'),
(1000, 'USD', 13, 'REF013', '2023-07-13', 'Card'),
(700, 'EUR', 14, 'REF014', '2023-07-14', 'Cash'),
(1400, 'USD', 15, 'REF015', '2023-07-15', 'Card'),
(950, 'CAD', 16, 'REF016', '2023-07-16', 'Bank Transfer');
