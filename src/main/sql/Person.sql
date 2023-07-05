-- Create the table
CREATE TABLE persons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    mobile VARCHAR(20),
    email VARCHAR(255),
    address VARCHAR(255),
    company VARCHAR(255),
    is_active VARCHAR(5)
);

-- Insert dummy values
INSERT INTO persons (first_name, last_name, mobile, email, address, company, is_active)
VALUES
    ('John', 'Doe', '1234567890', 'john.doe@example.com', '123 Main St', 'Company A', 'true'),
    ('Jane', 'Smith', '9876543210', 'jane.smith@example.com', '456 Elm St', 'Company B', 'true'),
    ('Michael', 'Johnson', '5551234567', 'michael.johnson@example.com', '789 Oak St', 'Company C', 'false'),
    ('Emily', 'Brown', '4445678901', 'emily.brown@example.com', '321 Maple Ave', 'Company D', 'true'),
    ('David', 'Lee', '2223334444', 'david.lee@example.com', '987 Pine Dr', 'Company E', 'true'),
    ('Sarah', 'Taylor', '9998887777', 'sarah.taylor@example.com', '654 Birch Ln', 'Company F', 'false'),
    ('Robert', 'Anderson', '1112223333', 'robert.anderson@example.com', '789 Cedar Rd', 'Company G', 'true'),
    ('Olivia', 'Martinez', '7778889999', 'olivia.martinez@example.com', '234 Oakwood Blvd', 'Company H', 'true'),
    ('William', 'Hernandez', '3332221111', 'william.hernandez@example.com', '432 Elmwood Dr', 'Company I', 'false'),
    ('Sophia', 'Garcia', '5554443333', 'sophia.garcia@example.com', '876 Pine St', 'Company J', 'true'),
    ('James', 'Lopez', '8889990000', 'james.lopez@example.com', '765 Maple Ln', 'Company K', 'true'),
    ('Ava', 'King', '6665554444', 'ava.king@example.com', '345 Oak Ave', 'Company L', 'false'),
    ('John', 'Scott', '2223334444', 'john.scott@example.com', '123 Pine Rd', 'Company M', 'true'),
    ('Emma', 'Green', '7778889999', 'emma.green@example.com', '567 Elmwood Blvd', 'Company N', 'true'),
    ('Daniel', 'Hall', '1112223333', 'daniel.hall@example.com', '987 Cedar Dr', 'Company O', 'false'),
    ('Mia', 'Adams', '5554443333', 'mia.adams@example.com', '543 Oakwood Ln', 'Company P', 'true');
