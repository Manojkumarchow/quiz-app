CREATE TABLE column_headers (
  id INT PRIMARY KEY,
  header_name VARCHAR(255),
  header_value VARCHAR(255)
);


INSERT INTO column_headers (id, header_name, header_value)
VALUES
  (1, 'Category', 'Category'),
  (2, 'Difficulty', 'Difficulty'),
  (3, 'Question Title', 'Question Title'),
  (4, 'Option 1', 'Option 1'),
  (5, 'Option 2', 'Option 2'),
  (6, 'Option 3', 'Option 3'),
  (7, 'Correct Answer', 'Correct Answer');
