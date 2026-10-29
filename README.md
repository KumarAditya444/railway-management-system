# RailConnect — Railway Management System

A full-stack railway reservation project built with **Java 17, Spring Boot, JDBC, MySQL, React, JavaScript, HTML and CSS**.

## Features
- Search trains by source, destination and travel date
- View train schedules, fares and available seats
- Book tickets with generated PNR and passenger details
- Track a booking by PNR
- Cancel a booking (seat inventory is restored)
- Admin dashboard: create, update and delete trains; view bookings
- Validation, structured API errors, transactional booking and unit tests
- Responsive React interface

> This is a portfolio/demo application. The demo admin endpoints are not protected by authentication. Add Spring Security and role-based access before deploying it for real users or real passenger data.

## Requirements
- Java 17+
- Maven 3.9+
- Node.js 18+
- MySQL 8+

## 1. Create the database
Run the SQL in `database/schema.sql` in MySQL Workbench or the MySQL CLI. It creates `railway_db`, tables, and sample trains.

## 2. Configure backend
Set environment variables (recommended):

```bash
# Windows PowerShell
$env:DB_URL="jdbc:mysql://localhost:3306/railway_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"

# macOS/Linux
export DB_URL='jdbc:mysql://localhost:3306/railway_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true'
export DB_USERNAME='root'
export DB_PASSWORD='your_mysql_password'
```

Then run:

```bash
cd backend
mvn spring-boot:run
```
Backend runs at `http://localhost:8080`.

## 3. Run frontend
In another terminal:

```bash
cd frontend
npm install
npm run dev
```
Open the Vite URL shown (normally `http://localhost:5173`).

## Demo API
- `GET /api/trains?source=Delhi&destination=Mumbai&date=2026-11-01`
- `GET /api/trains` — list trains
- `POST /api/trains` — create train (admin)
- `PUT /api/trains/{id}` — update train (admin)
- `DELETE /api/trains/{id}` — delete train (admin)
- `POST /api/bookings` — book ticket
- `GET /api/bookings/{pnr}` — track booking
- `GET /api/bookings` — list bookings (admin)
- `PATCH /api/bookings/{pnr}/cancel` — cancel booking

Example booking JSON:

```json
{
  "trainId": 1,
  "travelDate": "2026-11-01",
  "passengerName": "Aarav Sharma",
  "age": 24,
  "gender": "Male",
  "email": "aarav@example.com",
  "phone": "9876543210"
}
```

## Tests
```bash
cd backend
mvn test
```

## Project structure
```
railway-management-system/
├── backend/                 # Java REST API, JDBC repositories, validation and tests
├── database/schema.sql      # MySQL schema and sample records
└── frontend/                # React UI, HTML entry point, CSS and JavaScript
```
