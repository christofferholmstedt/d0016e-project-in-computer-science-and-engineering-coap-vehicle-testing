-- phpMyAdmin SQL Dump
-- version 3.4.5deb1
-- http://www.phpmyadmin.net
--
-- Skapad: 02 feb 2012 kl 11:07
-- Serverversion: 5.1.58
-- PHP-version: 5.3.6-13ubuntu3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Databas: `mulle`
--

-- --------------------------------------------------------

--
-- Tabellstruktur `data`
--

DROP TABLE IF EXISTS `data`;
CREATE TABLE IF NOT EXISTS `data` (
      `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'Increasing number for all data that is logged.',
      `type` varchar(255) NOT NULL,
      `value` varchar(255) NOT NULL,
      `datetime` datetime NOT NULL,
      `host` varchar(255) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=49 ;

    --
    -- Dumpning av Data i tabell `data`
    --

    INSERT INTO `data` (`id`, `type`, `value`, `datetime`, `host`) VALUES
    (48, 'testTypeFromDatabase', 'testValueFromDatabase', '1986-10-25 00:01:00', 'testHostFromDatabase');
    
