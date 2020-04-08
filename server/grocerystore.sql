-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 08, 2020 at 08:06 AM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id11971473_grocerystore`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `UserName` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `updationDate` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `UserName`, `Password`, `updationDate`) VALUES
(1, 'admin', '827ccb0eea8a706c4c34a16891f84e7b', '2018-05-25 05:51:25');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `categry` varchar(100) NOT NULL,
  `cateimg` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `categry`, `cateimg`) VALUES
(35, 'Milk', '20200404040405.png'),
(36, 'Fresh Bread & Rolls', '20200404040432.png'),
(37, 'Salty Snacks', '20200404040420.png'),
(38, 'Bottled Water', '20200404040411.png'),
(40, 'Shortening & Oil', '20200404040431.png'),
(41, 'Rice', '20200404040437.png'),
(42, 'Egg', '20200404040407.png'),
(43, 'Household Cleaner', '20200404040415.png'),
(44, 'Toothpaste', '20200404040410.png'),
(45, 'Household Cleaner', '20200404040425.png');

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `id` int(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `category` varchar(100) NOT NULL,
  `description` varchar(500) NOT NULL,
  `attribute` varchar(100) NOT NULL,
  `currency` varchar(100) NOT NULL DEFAULT 'Rs.',
  `discount` varchar(100) NOT NULL,
  `price` varchar(50) NOT NULL,
  `image` varchar(200) NOT NULL,
  `homepage` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`id`, `name`, `category`, `description`, `attribute`, `currency`, `discount`, `price`, `image`, `homepage`) VALUES
(24, 'Toned Milk', 'Milk', 'Goodlife Smart Homogenised Double Toned Milk UHT processed milk with Min 1.5% fat and Min 9.0% SNF fortified with vitamins A and D. Suitable for preparing tea/coffee, milk shakes and milk delights for people leading a fitness conscious lifestyle.', 'ml Pouch', 'Rs.', '69', '25', '20200404040448000000.png', 'YES'),
(25, 'Nandini GoodLife Toned Milk, 1 L Carton', 'Milk', 'Goodlife Cow milk is UHT processed milk with Min.3.% fat and Min. 8.5% SNF. Suitable for all generation.', '1L', 'Rs.', '', '53', '20200404040404000000.png', 'YES'),
(26, 'Fresho Ooty Potato - Medium, 1 kg', 'Vegetables', 'Whether mashed, baked or roasted, people often consider potatoes as comfort food. It is an important food staple and the number one vegetable crop in the world. Potatoes are available year-round as they are harvested somewhere every month of the year.', '1KG', 'Rs.', '', '35', '20200404040457000000.png', 'YES'),
(27, 'Potato Onion Tomato 1 kg Each, Combo 3 Items', 'Vegetables', 'Potato Onion Tomato 1 kg Each, Combo 3 Items', 'Kg', 'Rs.', '', '100', '20200404040428000000.png', 'YES'),
(28, 'Fresho Onion, 1 kg', 'Vegetables', 'Onion is a vegetable which is almost like a staple in Indian food. This is also known to be one of the essential ingredients of raw salads. They come in different colours like white, red or yellow and are quite in demand in cold salads and hot soups. You can dice, slice or cut it in rings and put it in burgers and sandwiches. Onions emit a sharp flavour and fragrance once they are fried; it is due to the sulphur compound in the vegetable.', '1Kg', 'Rs.', '', '35', '20200404040444000000.png', 'YES'),
(29, 'White Big Bread Slices - Safe, Preservative Free, 400 g', 'Fresh Bread & Rolls', 'Freshly Baked bread is one of life\'s greatest simple pleasures and at Bigbasket we decided to give you just that. We start baking our bread once your order using the finest ingredients available. Use Fresho White bread\'s Big Slices for making scrumptious jumbo sandwiches at home! These are also perfect for scooping up your favourite curry!', '400 g', 'Rs.', '35', '45', '20200405010402000000.png', 'NO');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `fname` varchar(100) NOT NULL,
  `lname` varchar(100) NOT NULL,
  `mobile` varchar(25) NOT NULL,
  `area` varchar(100) NOT NULL,
  `address` varchar(500) NOT NULL,
  `status` varchar(50) NOT NULL,
  `user_id` varchar(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `total` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `fname`, `lname`, `mobile`, `area`, `address`, `status`, `user_id`, `date`, `total`) VALUES
(17000074, ' Santosh Dash', ' ', '9778178337', 'Bengaluru', 'Test Address', 'Delivered', '32', '2020-04-07 10:37:09', '170'),
(17000075, 'Japathi prabhakar', ' ', '9908302604', 'New Delhi', 'fdd', 'Confirmed', '34', '2020-04-06 10:05:33', '575'),
(17000076, 'TestTestTestTestTest', ' ', '1234567892', 'Port Blair', 'g5f6f', 'Pending', '35', '2020-04-05 06:22:47', '203'),
(17000077, 'Rubel Das', ' ', '8436549563', 'Adra', 'fhhhhj', 'Pending', '36', '2020-04-05 07:32:50', '50'),
(17000078, 'Shiu Kumar', ' ', '6283631561', 'Sakti', 'vbb', 'Pending', '37', '2020-04-05 07:36:33', '35'),
(17000079, 'BlackHacker', ' ', '6649484694', 'Taliparamba', 'snns snssjaka', 'Confirmed', '39', '2020-04-05 21:35:38', '288'),
(17000080, 'deva raj', ' ', '9014888175', 'Srisailam Project (Right Flank Colony) Township', 'zbzbb', 'Pending', '41', '2020-04-06 06:48:09', '234'),
(17000081, 'Pruthbiraj Sarangi', ' ', '9090867757', 'Tarbha', 'vvvvbhb', 'Pending', '42', '2020-04-06 13:13:56', '50'),
(17000082, 'Pruthbiraj Sarangi', ' ', '9090867757', 'Bhubaneswar', 'vvvvbhb', 'Pending', '42', '2020-04-06 13:25:46', '45'),
(17000083, 'Sagar', ' ', '9969336649', 'Kagaznagar', 'ysus sjsi', 'Pending', '43', '2020-04-07 05:14:15', '310'),
(17000084, 'Shiu Kumar', ' ', '6283631561', 'Chandigarh', 'vbb', 'Pending', '37', '2020-04-07 06:24:29', '53'),
(17000085, 'Gokul Krishna', ' ', '9567351746', 'Port Blair', 'ghgcjk', 'Pending', '44', '2020-04-07 11:29:54', '45'),
(17000086, 'Art', ' ', '0912345678', 'Hyderabad', 'dhh ', 'Pending', '45', '2020-04-07 12:41:59', '268'),
(17000087, 'Praveen Kumar', ' ', '8637862842', 'Ranchi', 'kfjdkdldkdkdkfkfkfk', 'Pending', '46', '2020-04-08 05:50:22', '35'),
(17000088, 'Praveen Kumar', ' ', '8637862842', 'Dhanbad', 'kfjdkdldkdkdkfkfkfk', 'Pending', '46', '2020-04-08 07:43:27', '264');

-- --------------------------------------------------------

--
-- Table structure for table `orderslist`
--

CREATE TABLE `orderslist` (
  `id` int(50) NOT NULL,
  `orderid` varchar(50) NOT NULL,
  `itemname` varchar(500) NOT NULL,
  `itemquantity` varchar(100) NOT NULL,
  `attribute` varchar(100) NOT NULL,
  `currency` varchar(100) NOT NULL,
  `itemImage` varchar(250) DEFAULT NULL,
  `itemprice` varchar(30) NOT NULL,
  `itemtotal` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orderslist`
--

INSERT INTO `orderslist` (`id`, `orderid`, `itemname`, `itemquantity`, `attribute`, `currency`, `itemImage`, `itemprice`, `itemtotal`) VALUES
(2018136, '17000074', 'Fresho Onion, 1 kg', '2', '1Kg', 'Rs.', '20200404040444000000.png', '35', '70.0'),
(2018137, '17000074', 'Potato Onion Tomato 1 kg Each, Combo 3 Items', '1', 'Kg', 'Rs.', '20200404040428000000.png', '100', '100.0'),
(2018138, '17000075', 'Toned Milk', '4', 'ml Pouch', 'Rs.', '20200404040448000000.png', '25', '100.0'),
(2018139, '17000075', 'Fresho Ooty Potato - Medium, 1 kg', '3', '1KG', 'Rs.', '20200404040457000000.png', '35', '105.0'),
(2018140, '17000075', 'Potato Onion Tomato 1 kg Each, Combo 3 Items', '3', 'Kg', 'Rs.', '20200404040428000000.png', '100', '300.0'),
(2018141, '17000075', 'Fresho Onion, 1 kg', '2', '1Kg', 'Rs.', '20200404040444000000.png', '35', '70.0'),
(2018142, '17000076', 'Toned Milk', '6', 'ml Pouch', 'Rs.', '20200404040448000000.png', '25', '150.0'),
(2018143, '17000076', 'Nandini GoodLife Toned Milk, 1 L Carton', '1', '1L', 'Rs.', '20200404040404000000.png', '53', '53.0'),
(2018144, '17000077', 'Toned Milk', '2', 'ml Pouch', 'Rs.', '20200404040448000000.png', '25', '50.0'),
(2018145, '17000078', 'Fresho Onion, 1 kg', '1', '1Kg', 'Rs.', '20200404040444000000.png', '35', '35.0'),
(2018146, '17000079', 'penis', '3', 'kg', 'Rs.', '20200405090448000000.png', '96', '288.0'),
(2018147, '17000080', 'Toned Milk', '3', 'ml Pouch', 'Rs.', '20200404040448000000.png', '25', '75.0'),
(2018148, '17000080', 'Nandini GoodLife Toned Milk, 1 L Carton', '3', '1L', 'Rs.', '20200404040404000000.png', '53', '159.0'),
(2018149, '17000081', 'Toned Milk', '2', 'ml Pouch', 'Rs.', '20200404040448000000.png', '25', '50.0'),
(2018150, '17000082', 'White Big Bread Slices - Safe, Preservative Free, 400 g', '1', '400 g', 'Rs.', '20200405010402000000.png', '45', '45.0'),
(2018151, '17000083', 'White Big Bread Slices - Safe, Preservative Free, 400 g', '3', '400 g', 'Rs.', '20200405010402000000.png', '45', '135.0'),
(2018152, '17000083', 'Fresho Onion, 1 kg', '3', '1Kg', 'Rs.', '20200404040444000000.png', '35', '105.0'),
(2018153, '17000083', 'Fresho Ooty Potato - Medium, 1 kg', '2', '1KG', 'Rs.', '20200404040457000000.png', '35', '70.0'),
(2018154, '17000084', 'Nandini GoodLife Toned Milk, 1 L Carton', '1', '1L', 'Rs.', '20200404040404000000.png', '53', '53'),
(2018155, '17000085', 'White Big Bread Slices - Safe, Preservative Free, 400 g', '1', '400 g', 'Rs.', '20200405010402000000.png', '45', '45.0'),
(2018156, '17000086', 'White Big Bread Slices - Safe, Preservative Free, 400 g', '1', '400 g', 'Rs.', '20200405010402000000.png', '45', '45.0'),
(2018157, '17000086', 'Fresho Onion, 1 kg', '1', '1Kg', 'Rs.', '20200404040444000000.png', '35', '35.0'),
(2018158, '17000086', 'Potato Onion Tomato 1 kg Each, Combo 3 Items', '1', 'Kg', 'Rs.', '20200404040428000000.png', '100', '100.0'),
(2018159, '17000086', 'Fresho Ooty Potato - Medium, 1 kg', '1', '1KG', 'Rs.', '20200404040457000000.png', '35', '35.0'),
(2018160, '17000086', 'Nandini GoodLife Toned Milk, 1 L Carton', '1', '1L', 'Rs.', '20200404040404000000.png', '53', '53.0'),
(2018161, '17000087', 'Fresho Onion, 1 kg', '1', '1Kg', 'Rs.', '20200404040444000000.png', '35', '35.0'),
(2018162, '17000088', 'Nandini GoodLife Toned Milk, 1 L Carton', '3', '1L', 'Rs.', '20200404040404000000.png', '53', '159.0'),
(2018163, '17000088', 'Fresho Ooty Potato - Medium, 1 kg', '3', '1KG', 'Rs.', '20200404040457000000.png', '35', '105.0');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `fname` varchar(100) DEFAULT NULL,
  `lname` varchar(50) DEFAULT NULL,
  `area` varchar(100) DEFAULT NULL,
  `address` text DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `zip` varchar(11) DEFAULT NULL,
  `mobile` varchar(30) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `fname`, `lname`, `area`, `address`, `state`, `city`, `zip`, `mobile`, `email`, `password`) VALUES
(32, ' Santosh Dash', '', NULL, 'Test Address', 'Karnataka', 'Bengaluru', '756007', '9778178337', 'santoshnet2016@gmail.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
(33, 'Sample', '', NULL, NULL, NULL, NULL, NULL, '1234567890', NULL, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),
(34, 'Japathi prabhakar', '', NULL, 'fdd', 'Delhi', 'Delhi', '555', '9908302604', 'pavansms750@gmail.com', '40bff4da058e0e14d44abf72b81fb540dce9f32afed2cc9ebbb4f422cb5c768e'),
(35, 'TestTestTestTestTest', '', NULL, 'g5f6f', 'Andaman and Nicobar Islands', 'Port Blair', '133001', '1234567892', 'trst@124.com', 'd399a625764db15dd442a2b94d0875797db7d0d847541abcefc617cb09d8f3be'),
(36, 'Rubel Das', '', NULL, 'fhhhhj', 'West Bengal', 'Adra', '743348', '84365495', 'rubel.das@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),
(37, 'Shiu Kumar', '', NULL, 'vbb', 'Chandigarh', 'Chandigarh', '133301', '6283631561', 'shiukumar05@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),
(38, 'Madiha Urooj', '', NULL, NULL, NULL, NULL, NULL, '0333869757', NULL, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92'),
(39, 'BlackHacker', '', NULL, 'snns snssjaka', 'Kerala', 'Taliparamba', '649947', '6649484694', 'kahsbsjsh@gmail.com', '0c2206f8df3bc78576eaf04ddf74b1a0216237b6592cc83a94ddcbce75a2d8b2'),
(40, 'Ali Raza', '', NULL, NULL, NULL, NULL, NULL, '3086229947', NULL, '723709e8a2de5d325144654efcbf545f73e60933a2b96c4c7b34ce41d5138510'),
(41, 'deva raj', '', NULL, 'zbzbb', 'Andhra Pradesh', 'Srisailam Project (Right Flank Colony) Township', '500018', '9014888175', 'devaraj.ssp@gmail.com', '65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5'),
(42, 'Pruthbiraj Sarangi', '', NULL, 'vvvvbhb', 'Odisha', 'Bhubaneswar', '752104', '9090867757', 'fghhjko@gmail.com', 'e0bc60c82713f64ef8a57c0c40d02ce24fd0141d5cc3086259c19b1e62a62bea'),
(43, 'Sagar', '', NULL, 'ysus sjsi', 'Telangana', 'Kagaznagar', '400012', '9969336649', 'sagar@gamail.com', '87ecbdc56705d28be1e03c2d78377676ae9e8e5a4deae3528c63adc3836fad86'),
(44, 'Gokul Krishna', '', NULL, 'ghgcjk', 'Andaman and Nicobar Islands', 'Port Blair', '639999', '9567351746', 'dragonbooster965@gmail.com', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225'),
(45, 'Art', '', NULL, 'dhh ', 'Telangana', 'Hyderabad', '558478', '0912345678', 'ddysu@gm.com', 'befa156f0283eb0062beb9b86e16a413e1cf8c5135e5518d5c4fa321ce0c7b6b'),
(46, 'Praveen Kumar', '', NULL, 'kfjdkdldkdkdkfkfkfk', 'Jharkhand', 'Dhanbad', '834008', '8637862842', 'decodingspace71@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orderslist`
--
ALTER TABLE `orderslist`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17000089;

--
-- AUTO_INCREMENT for table `orderslist`
--
ALTER TABLE `orderslist`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2018164;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
