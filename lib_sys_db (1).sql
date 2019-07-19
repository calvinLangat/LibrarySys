-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 19, 2019 at 03:03 PM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lib_sys_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `Title` varchar(50) NOT NULL,
  `Author` varchar(50) NOT NULL,
  `SerialNo` int(11) NOT NULL,
  `PublishDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`Title`, `Author`, `SerialNo`, `PublishDate`) VALUES
('The Bold', 'James Bond', 2001, '2000-10-12'),
('Introduction to Comp', 'Mbuvi Wachi', 2002, '2001-09-15'),
('Cargo', 'Mr. Wild', 2003, '2012-12-12'),
('My Life', 'John Doe', 2004, '2009-08-22');

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--

CREATE TABLE `borrow` (
  `BorrowID` varchar(20) NOT NULL,
  `IDnumber` int(20) NOT NULL,
  `SerialNo` int(20) NOT NULL,
  `DateBurrowed` date NOT NULL,
  `DateDue` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `borrow`
--

INSERT INTO `borrow` (`BorrowID`, `IDnumber`, `SerialNo`, `DateBurrowed`, `DateDue`) VALUES
('3001', 1001, 2001, '2019-07-11', '2019-07-21'),
('3002', 1004, 2002, '2019-07-11', '2019-07-22');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `FirstName` varchar(20) NOT NULL,
  `LastName` varchar(20) NOT NULL,
  `Gender` varchar(8) NOT NULL,
  `IDNumber` int(20) NOT NULL,
  `PhoneNumber` varchar(11) NOT NULL,
  `Address` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`FirstName`, `LastName`, `Gender`, `IDNumber`, `PhoneNumber`, `Address`) VALUES
('Calvin', 'Langat', 'Male', 1001, '789123456', 'Nairobi'),
('John', 'Kadivane', 'Male', 1002, '0712345678', 'Thika RD'),
('Brian', 'Mbogi', 'Male', 1003, '0713567432', 'Kiambu'),
('Humphrey', 'Bett', 'Male', 1004, '0987654', 'Mombasa'),
('Daisy', 'Kimari', 'Female', 563452, '0712456732', 'usiu');

-- --------------------------------------------------------

--
-- Table structure for table `users_passwords`
--

CREATE TABLE `users_passwords` (
  `user` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users_passwords`
--

INSERT INTO `users_passwords` (`user`, `password`) VALUES
('user', 'pass');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`SerialNo`);

--
-- Indexes for table `borrow`
--
ALTER TABLE `borrow`
  ADD PRIMARY KEY (`BorrowID`),
  ADD UNIQUE KEY `BorrowID` (`BorrowID`),
  ADD KEY `SerialNo` (`SerialNo`),
  ADD KEY `IDnumber` (`IDnumber`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`IDNumber`);

--
-- Indexes for table `users_passwords`
--
ALTER TABLE `users_passwords`
  ADD UNIQUE KEY `user` (`user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `IDNumber` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=563453;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`IDnumber`) REFERENCES `customers` (`IDNumber`),
  ADD CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`SerialNo`) REFERENCES `books` (`SerialNo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
