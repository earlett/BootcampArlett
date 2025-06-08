CREATE DATABASE IF NOT EXISTS eviecardealership;
USE eviecardealership;
-- 1. Dealerships Table
CREATE TABLE IF NOT EXISTS Dealerships (
    DealershipID INT PRIMARY KEY NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Address VARCHAR(50) NOT NULL,
    Phone VARCHAR(12) NOT NULL
);

INSERT INTO Dealerships (DealershipID, Name, Address, Phone)
VALUES
(1, 'City Auto Mall', '123 Main St, Springfield', '555-123-4567'),
(2, 'Highway Motors', '456 Elm St, Shelbyville', '555-987-6543'),
(3, 'Premium Cars Co.', '789 Oak Ave, Capital City', '555-246-8101'),
(4, 'Budget Wheels', '321 Pine Rd, Ogdenville', '555-369-1470'),
(5, 'Luxury Drives', '654 Maple Blvd, North Haverbrook', '555-741-8529');

-- 2. Vehicle Table
CREATE TABLE IF NOT EXISTS Vehicle (
    VIN INT PRIMARY KEY NOT NULL,
    Make VARCHAR(25) NOT NULL,
    Model VARCHAR(25) NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    YearMade INT NOT NULL,
    HasSold BIT DEFAULT 0
);

INSERT INTO Vehicle (VIN, Make, Model, Price, YearMade, HasSold)
VALUES
(10001, 'Toyota', 'Camry', 25000.00, 2022, 0),
(10002, 'Honda', 'Civic', 22000.00, 2023, 1),
(10003, 'Ford', 'F-150', 32000.00, 2021, 0),
(10004, 'Chevrolet', 'Malibu', 21000.00, 2020, 1),
(10005, 'Tesla', 'Model 3', 42000.00, 2023, 0),
(10006, 'BMW', 'X5', 55000.00, 2022, 1),
(10007, 'Audi', 'A4', 47000.00, 2021, 0),
(10008, 'Hyundai', 'Elantra', 19000.00, 2022, 1),
(10009, 'Kia', 'Sorento', 28000.00, 2023, 0),
(10010, 'Nissan', 'Altima', 23000.00, 2020, 0);

-- 3. Inventory Table
CREATE TABLE IF NOT EXISTS Inventory (
    DealershipID INT NOT NULL,
    VIN INT NOT NULL,
    PRIMARY KEY (DealershipID, VIN),
    FOREIGN KEY (DealershipID) REFERENCES Dealerships(DealershipID),
    FOREIGN KEY (VIN) REFERENCES Vehicle(VIN)
);

INSERT INTO Inventory (DealershipID, VIN)
VALUES
(1, 10001),
(1, 10002),
(2, 10003),
(2, 10004),
(3, 10005),
(3, 10006),
(4, 10007),
(4, 10008),
(5, 10009),
(5, 10010);

-- 4. Customers Table
CREATE TABLE IF NOT EXISTS Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FullName VARCHAR(100) NOT NULL,
    Phone VARCHAR(15),
    Email VARCHAR(100),
    Address VARCHAR(150)
);

INSERT INTO Customers (FullName, Phone, Email, Address)
VALUES
('Emily Carter', '555-223-8899', 'emily.carter@mail.com', '12 River St, Springfield'),
('Michael Torres', '555-778-1122', 'm.torres@mail.com', '89 Oak Dr, Shelbyville'),
('Sarah Lee', '555-663-4411', 'sarah.lee@mail.com', '22 Maple Blvd, Capital City'),
('Brian Walker', '555-334-7788', 'brianw@mail.com', '77 Lake Rd, Ogdenville');

-- 5. SalesContracts Table
CREATE TABLE IF NOT EXISTS SalesContracts (
    SIN INT AUTO_INCREMENT PRIMARY KEY,
    VIN INT NOT NULL,
    DealershipID INT NOT NULL,
    CustomerID INT NOT NULL,
    SaleDate DATE NOT NULL,
    SalePrice DECIMAL(10,2) NOT NULL,
    WarrantyIncluded BIT DEFAULT 0,
    PaymentMethod VARCHAR(20),
    FOREIGN KEY (VIN) REFERENCES Vehicle(VIN),
    FOREIGN KEY (DealershipID) REFERENCES Dealerships(DealershipID),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

INSERT INTO SalesContracts (VIN, DealershipID, CustomerID, SaleDate, SalePrice, WarrantyIncluded, PaymentMethod)
VALUES
(10002, 1, 1, '2025-03-15', 21500.00, 1, 'Credit'),
(10004, 2, 2, '2025-04-02', 20000.00, 0, 'Cash'),
(10006, 3, 3, '2025-04-20', 54000.00, 1, 'Financing'),
(10008, 4, 4, '2025-05-01', 18500.00, 0, 'Credit');


-- 6. LeaseContracts Table
CREATE TABLE IF NOT EXISTS Lease_Contracts (
    CIN INT AUTO_INCREMENT PRIMARY KEY,
    VIN INT NOT NULL,
    DealershipID INT NOT NULL,
    CustomerID INT NOT NULL,
    LeaseStart DATE NOT NULL,
    LeaseEnd DATE NOT NULL,
    MonthlyPayment DECIMAL(10,2) NOT NULL,
    MileageLimit INT,
    PaymentMethod VARCHAR(20),
    FOREIGN KEY (VIN) REFERENCES Vehicle(VIN),
    FOREIGN KEY (DealershipID) REFERENCES Dealerships(DealershipID),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

INSERT INTO Lease_Contracts (VIN, DealershipID, CustomerID, LeaseStart, LeaseEnd, MonthlyPayment, MileageLimit, PaymentMethod)
VALUES
(10001, 1, 1, '2025-01-01', '2028-01-01', 399.99, 36000, 'Credit'),
(10005, 3, 2, '2025-03-15', '2027-03-15', 549.99, 30000, 'Financing');

SELECT d.*
FROM Dealerships d
JOIN Inventory i ON d.DealershipID = i.DealershipID
WHERE i.VIN = 10002;

SELECT v.*
FROM Vehicle v
JOIN Inventory i ON v.VIN = i.VIN
WHERE i.DealershipID = 1;

SELECT * FROM Vehicle
WHERE VIN = 10003;

SELECT d.*
FROM Dealerships d
JOIN Inventory i ON d.DealershipID = i.DealershipID
WHERE i.VIN = 10003;

SELECT DISTINCT d.*
FROM Dealerships d
JOIN Inventory i ON d.DealershipID = i.DealershipID
JOIN Vehicle v ON v.VIN = i.VIN
WHERE v.Make = 'Ford' AND v.Model = 'F-150';

SELECT 
    sc.SIN,
    sc.SaleDate,
    sc.SalePrice,
    sc.PaymentMethod,
    c.FullName AS Customer,
    v.Make,
    v.Model
FROM SalesContracts sc
JOIN Customers c ON sc.CustomerID = c.CustomerID
JOIN Vehicle v ON sc.VIN = v.VIN
WHERE sc.DealershipID = 2
  AND sc.SaleDate BETWEEN '2025-01-01' AND '2025-06-30';
