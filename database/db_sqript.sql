-- MySQL Script generated by MySQL Workbench
-- Thu Aug 26 03:52:10 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema epam-rest-basic
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema epam-rest-basic
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `epam-rest-basic` DEFAULT CHARACTER SET utf8 ;
USE `epam-rest-basic` ;

-- -----------------------------------------------------
-- Table `epam-rest-basic`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam-rest-basic`.`gift_certificate` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(250) NOT NULL,
  `price` DECIMAL UNSIGNED NOT NULL,
  `duration` INT NOT NULL,
  `create_date` TIMESTAMP NOT NULL,
  `last_update_date` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `epam-rest-basic`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam-rest-basic`.`tag` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `epam-rest-basic`.`gift_certificate_tag_link`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam-rest-basic`.`gift_certificate_tag_link` (
  `gift_certificate_id` BIGINT UNSIGNED NOT NULL,
  `tag_id` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`gift_certificate_id`, `tag_id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
