SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Database votingDB
-- -----------------------------------------------------
CREATE DATABASE IF NOT EXISTS `votingDB` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs ;
USE `votingDB` ;

-- -----------------------------------------------------
-- Table `votingDB`.`candidates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `votingDB`.`candidates` (
  `cid` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  PRIMARY KEY (`cid`),
  INDEX `cand_lastname_idx` (`lastname` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `votingDB`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `votingDB`.`users` (
  `uid` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` CHAR(60) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `dob` DATE NULL,
  `hasVoted` TINYINT NULL,
  `votedCid` INT NULL,
  PRIMARY KEY (`uid`),
  INDEX `fk_users_cand_id_idx` (`votedCid` ASC) VISIBLE,
  INDEX `username_idx` (`username` ASC) VISIBLE,
  INDEX `user_lastname_idx` (`lastname` ASC) VISIBLE,
  INDEX `email_idx` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_users_cand_id`
    FOREIGN KEY (`votedCid`)
    REFERENCES `votingDB`.`candidates` (`cid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE USER 'votingUser' IDENTIFIED BY 'voting';
GRANT ALL ON `votingDB`.* TO 'votingUser';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
