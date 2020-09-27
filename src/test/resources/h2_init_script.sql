CREATE SCHEMA labank;
SET SCHEMA labank;

DROP TABLE IF EXISTS agency;
CREATE TABLE agency (
  agency_number NUMBER NOT NULL PRIMARY KEY
);

 DROP TABLE IF EXISTS bearer;
 CREATE TABLE bearer (
  bearer_document VARCHAR(14) NOT NULL PRIMARY KEY,
  bearer_name VARCHAR(50) NOT NULL,
  bearer_type NUMBER NOT NULL  
);

DROP TABLE IF EXISTS account;
CREATE TABLE account(
	account_number VARCHAR(50) NOT NULL PRIMARY KEY,
	account_balance double NOT NULL,
	fk_agency_number NUMBER NOT NULL,
	fk_bearer_document VARCHAR(50) NOT NULL,
	foreign key (fk_agency_number) references agency(agency_number),
	foreign key (fk_bearer_document) references bearer(bearer_document)	
);

DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction(
	transaction_id NUMBER AUTO_INCREMENT  PRIMARY KEY,
	transaction_amount DOUBLE NOT NULL,
	transaction_date DATE NOT NULL,
	fk_account_number VARCHAR(50) NOT NULL,
	foreign key (fk_account_number) references account(account_number)	
);

INSERT INTO labank.agency (agency_number) VALUES
  (4), (7);

INSERT INTO labank.bearer(bearer_name, bearer_type, bearer_document) VALUES
('Lopes',   4, '39746667056'),
('Luis', 7, '69997868000112');

INSERT INTO labank.account (account_number, account_balance, fk_agency_number, fk_bearer_document) VALUES
('02451097', 250.00, 4, '39746667056'),
('031818952', 4500.00, 7, '69997868000112');

INSERT INTO labank.transaction (transaction_amount, transaction_date, fk_account_number) VALUES 
(250.00, '2020-09-01', '02451097'),
(-6000.00, '2020-09-01', '031818952');
