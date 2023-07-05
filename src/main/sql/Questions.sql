create database questionsdb;

use questionsdb;

CREATE TABLE questions (
  id INT PRIMARY KEY AUTO_INCREMENT,
  category VARCHAR(255),
  difficulty VARCHAR(255),
  option_1 VARCHAR(255),
  option_2 VARCHAR(255),
  option_3 VARCHAR(255),
  question_title VARCHAR(255),
  right_answer VARCHAR(255)
);


select * from questions where category = 'Science';
select * from quiz;

select * from quiz_questions;

SELECT * FROM questions where category = 'Science' order by rand() limit 5;

INSERT INTO questions (category, difficulty, option_1, option_2, option_3, question_title, right_answer)
VALUES
  ('Science', 'Easy', 'A', 'B', 'C', 'What is the capital of France?', 'C'),
  ('Mathematics', 'Medium', '2', '4', '6', 'What is the square root of 16?', '4'),
  ('History', 'Hard', '1776', '1789', '1804', 'In which year was the United States Declaration of Independence signed?', '1776'),
  ('Geography', 'Easy', 'Asia', 'Europe', 'Africa', 'Which continent is Egypt located in?', 'Africa'),
  ('Sports', 'Medium', 'Ronaldo', 'Messi', 'Neymar', 'Who won the FIFA Ballon d\'Or in 2020?', 'Messi'),
  ('Science', 'Hard', 'Electron', 'Proton', 'Neutron', 'What is the subatomic particle with a negative charge?', 'Electron'),
  ('Mathematics', 'Easy', '7', '8', '9', 'What is 4 + 4?', '8'),
  ('History', 'Medium', 'World War I', 'World War II', 'Vietnam War', 'Which war ended in 1945?', 'World War II'),
  ('Geography', 'Hard', 'Mount Everest', 'K2', 'Kilimanjaro', 'Which is the highest peak in the world?', 'Mount Everest'),
  ('Sports', 'Easy', 'Basketball', 'Football', 'Tennis', 'Which sport is played with a round ball?', 'Football'),
  ('Science', 'Medium', 'Gravity', 'Magnetism', 'Friction', 'What force keeps objects on the ground?', 'Gravity'),
  ('Mathematics', 'Hard', 'Pi', 'Phi', 'Euler\'s number', 'Which mathematical constant is approximately equal to 3.14159?', 'Pi'),
  ('History', 'Easy', '1492', '1776', '1914', 'In which year did Christopher Columbus discover America?', '1492'),
  ('Geography', 'Medium', 'Australia', 'Russia', 'Canada', 'Which country is known as the "Land Down Under"?', 'Australia'),
  ('Sports', 'Hard', 'Super Bowl', 'World Series', 'Stanley Cup', 'Which trophy is awarded to the champion of the NFL?', 'Super Bowl'),
  ('Science', 'Easy', 'Water', 'Carbon dioxide', 'Oxygen', 'Which molecule has the chemical formula H2O?', 'Water');
