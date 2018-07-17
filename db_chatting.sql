-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 17, 2018 at 11:13 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_chatting`
--

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `connection_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT '(invited or joining request user id)',
  `connection_status` int(1) NOT NULL DEFAULT '0' COMMENT '1 . Invited 2. RequestJoin 3. CancellInvited 4. CancellJoiningRequest',
  `connection_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`connection_id`, `room_id`, `user_id`, `connection_status`, `connection_date`) VALUES
(34, 14, 3, 1, '2018-07-17 20:59:15'),
(35, 14, 4, 1, '2018-07-17 20:59:15'),
(36, 15, 5, 1, '2018-07-17 21:00:42'),
(37, 15, 3, 1, '2018-07-17 21:00:42'),
(38, 15, 4, 1, '2018-07-17 21:01:54'),
(39, 15, 4, 1, '2018-07-17 21:02:16');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `message_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  `message_content` text NOT NULL,
  `msg_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`message_id`, `room_id`, `message_content`, `msg_date`, `user_id`) VALUES
(126, 14, 'hello%20', '2018-07-17 20:59:15', 3),
(127, 14, 'hello', '2018-07-17 21:00:26', 4),
(128, 15, 'hello', '2018-07-17 21:00:42', 5),
(129, 15, 'yes%20', '2018-07-17 21:02:17', 4);

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `notify_id` int(11) NOT NULL,
  `to_user` int(11) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `notify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`notify_id`, `to_user`, `title`, `content`, `notify_date`) VALUES
(11, 5, 'Friend Request Accepted', 'Sharif Accepted your friend request!', '2018-07-17 18:00:00'),
(12, 5, 'Friend Request Accepted', 'Sakib Accepted your friend request!', '2018-07-17 18:00:00'),
(13, 3, 'Friend Request Accepted', 'Sharif Accepted your friend request!', '2018-07-17 18:00:00'),
(14, 4, 'SakibGroup ', 'Sakib added you to a group !', '2018-07-17 18:00:00'),
(15, 4, 'zahid islamGroup ', 'zahid islam added you to a group !', '2018-07-17 18:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `room_id` int(11) NOT NULL,
  `room_name` text NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`room_id`, `room_name`, `created_date`, `created_by`, `active`) VALUES
(14, 'Group\n: SakibSharif', '2018-07-17 20:59:15', 3, 0),
(15, 'Group\n: zahid islamSakibSharifSharif', '2018-07-17 21:00:42', 5, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `email` varchar(40) NOT NULL,
  `image` text NOT NULL,
  `contact_number` int(16) NOT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` tinyint(1) DEFAULT '1',
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `full_name`, `email`, `image`, `contact_number`, `create_date`, `active`, `password`) VALUES
(1, 'Rashed Khan', 'rashed@gmail.com', 'sds', 34535345, '2018-07-01 19:52:36', 1, 'e10adc3949ba59abbe56e057f20f883e'),
(2, 'Saon Khan', 'saon@gmail.com', 'sdf', 4325353, '2018-07-02 16:31:33', 1, 'e10adc3949ba59abbe56e057f20f883e'),
(3, 'Sakib', 'sk@gmail.com', 'df', 4535, '2018-07-02 16:31:33', 1, 'e10adc3949ba59abbe56e057f20f883e'),
(4, 'Sharif', 'shr@gmail.com', 'eer', 435345, '2018-07-02 16:31:33', 1, 'e10adc3949ba59abbe56e057f20f883e'),
(5, 'zahid islam', 'zahidrofique12@gmail.com', 'img', 1865336889, '2018-07-03 16:21:16', 1, 'e10adc3949ba59abbe56e057f20f883e');

-- --------------------------------------------------------

--
-- Table structure for table `user_friend`
--

CREATE TABLE `user_friend` (
  `user_friend_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  `request_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1 . Requested 2. Accepted 3. Cancelled  4. Sent',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_friend`
--

INSERT INTO `user_friend` (`user_friend_id`, `user_id`, `friend_id`, `request_status`, `create_date`) VALUES
(57, 5, 4, 2, '2018-07-17 19:41:50'),
(58, 5, 3, 2, '2018-07-17 19:42:17'),
(59, 3, 4, 2, '2018-07-17 19:42:37');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`connection_id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`message_id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`notify_id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`room_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_friend`
--
ALTER TABLE `user_friend`
  ADD PRIMARY KEY (`user_friend_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `connection_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=130;
--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `notify_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `room_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `user_friend`
--
ALTER TABLE `user_friend`
  MODIFY `user_friend_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
