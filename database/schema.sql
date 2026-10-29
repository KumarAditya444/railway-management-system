CREATE DATABASE IF NOT EXISTS railway_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE railway_db;

CREATE TABLE IF NOT EXISTS trains (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  train_number VARCHAR(12) NOT NULL UNIQUE,
  train_name VARCHAR(100) NOT NULL,
  source_station VARCHAR(80) NOT NULL,
  destination_station VARCHAR(80) NOT NULL,
  departure_time TIME NOT NULL,
  arrival_time TIME NOT NULL,
  duration VARCHAR(30) NOT NULL,
  fare DECIMAL(10,2) NOT NULL,
  total_seats INT NOT NULL,
  available_seats INT NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT chk_fare CHECK (fare >= 0),
  CONSTRAINT chk_seats CHECK (total_seats >= 0 AND available_seats >= 0 AND available_seats <= total_seats)
);

CREATE TABLE IF NOT EXISTS bookings (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  pnr VARCHAR(16) NOT NULL UNIQUE,
  train_id BIGINT NOT NULL,
  travel_date DATE NOT NULL,
  passenger_name VARCHAR(100) NOT NULL,
  age INT NOT NULL,
  gender VARCHAR(20) NOT NULL,
  email VARCHAR(120) NOT NULL,
  phone VARCHAR(15) NOT NULL,
  status ENUM('CONFIRMED','CANCELLED') NOT NULL DEFAULT 'CONFIRMED',
  fare DECIMAL(10,2) NOT NULL,
  booked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_booking_train FOREIGN KEY (train_id) REFERENCES trains(id),
  INDEX idx_booking_pnr (pnr),
  INDEX idx_booking_date (travel_date)
);

INSERT IGNORE INTO trains (id, train_number, train_name, source_station, destination_station, departure_time, arrival_time, duration, fare, total_seats, available_seats, active) VALUES
(1, '12002', 'Bhopal Shatabdi', 'New Delhi', 'Bhopal', '06:00:00', '14:00:00', '8h 00m', 1450.00, 120, 120, TRUE),
(2, '12952', 'Mumbai Rajdhani', 'New Delhi', 'Mumbai Central', '16:25:00', '08:15:00', '15h 50m', 2200.00, 100, 100, TRUE),
(3, '12802', 'Purushottam Express', 'New Delhi', 'Puri', '22:20:00', '04:15:00', '29h 55m', 980.00, 160, 160, TRUE),
(4, '12019', 'Howrah Rajdhani', 'Howrah', 'New Delhi', '16:50:00', '09:55:00', '17h 05m', 1850.00, 100, 100, TRUE),
(5, '12627', 'Karnataka Express', 'Bengaluru', 'New Delhi', '19:20:00', '10:30:00', '39h 10m', 1250.00, 180, 180, TRUE),
(6, '12864', 'YPR Howrah Express', 'Bengaluru', 'Howrah', '19:30:00', '06:30:00', '35h 00m', 1100.00, 160, 160, TRUE);
