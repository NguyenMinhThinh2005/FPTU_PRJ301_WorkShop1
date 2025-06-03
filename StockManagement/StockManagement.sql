CREATE DATABASE FinanceMarketManagement;
GO

USE FinanceMarketManagement;
GO

CREATE TABLE tblUsers (
    userID VARCHAR(50) NOT NULL PRIMARY KEY,
    fullName NVARCHAR(255) NOT NULL,
    roleID VARCHAR(5) NOT NULL,
    password VARCHAR(50) NOT NULL
);
GO

CREATE TABLE tblStocks (
    ticker CHAR(6) NOT NULL PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    sector NVARCHAR(50) NOT NULL,
    price FLOAT NOT NULL CHECK (price > 0),
    status BIT NOT NULL
);
GO

CREATE TABLE tblTransactions (
    id INT IDENTITY(1,1) PRIMARY KEY,
    userID VARCHAR(50) NOT NULL,
    ticker CHAR(6) NOT NULL,
    type VARCHAR(10) NOT NULL CHECK (type IN ('buy', 'sell')),
    quantity INT NOT NULL CHECK (quantity > 0),
    price FLOAT NOT NULL CHECK (price > 0),
    status VARCHAR(20) NOT NULL CHECK (status IN ('pending', 'executed')),
    CONSTRAINT FK_Transactions_Users FOREIGN KEY (userID) REFERENCES tblUsers(userID),
    CONSTRAINT FK_Transactions_Stocks FOREIGN KEY (ticker) REFERENCES tblStocks(ticker)
);
GO

CREATE TABLE tblAlerts (
    alertID INT IDENTITY(1,1) PRIMARY KEY,
    userID VARCHAR(50) NOT NULL,
    ticker CHAR(6) NOT NULL,
    threshold FLOAT NOT NULL CHECK (threshold > 0),
    direction VARCHAR(10) NOT NULL CHECK (direction IN ('increase', 'decrease')),
    status VARCHAR(20) NOT NULL DEFAULT 'inactive',
    CONSTRAINT FK_Alerts_Users FOREIGN KEY (userID) REFERENCES tblUsers(userID),
    CONSTRAINT FK_Alerts_Stocks FOREIGN KEY (ticker) REFERENCES tblStocks(ticker)
);
GO

INSERT INTO tblUsers (userID, fullName, roleID, password) VALUES
('user001', 'Nguyen Van A', 'AD', '123456'),
('user002', 'Tran Thi B', 'NV', 'abcdef'),
('user003', 'Le Minh C', 'NV', 'qwerty');

INSERT INTO tblStocks (ticker, name, sector, price, status) VALUES
('VNM', 'Vietnam Dairy Products JSC', 'Food & Beverage', 85.5, 1),
('HPG', 'Hoa Phat Group JSC', 'Metals & Mining', 28.0, 1),
('VIC', 'Vingroup JSC', 'Real Estate', 70.2, 1),
('ACB', 'Asia Commercial Bank', 'Banking', 24.7, 1),
('FPT', 'FPT Corporation', 'Information Technology', 105.0, 1);

INSERT INTO tblTransactions (userID, ticker, type, quantity, price, status) VALUES
('user001', 'VNM', 'buy', 100, 85.0, 'executed'),
('user002', 'HPG', 'buy', 200, 27.5, 'executed'),
('user001', 'VIC', 'sell', 50, 71.0, 'executed'),
('user003', 'ACB', 'buy', 300, 24.5, 'pending'),
('user002', 'FPT', 'buy', 150, 104.5, 'executed'),
('user003', 'HPG', 'sell', 100, 28.2, 'pending');

INSERT INTO tblAlerts (userID, ticker, threshold, direction, status) VALUES
('user001', 'VNM', 90.0, 'increase', 'active'),
('user002', 'HPG', 30.0, 'increase', 'inactive'),
('user001', 'VIC', 65.0, 'decrease', 'active'),
('user003', 'ACB', 25.5, 'increase', 'pending'),
('user002', 'FPT', 110.0, 'increase', 'active'),
('user001', 'HPG', 27.0, 'decrease', 'inactive');