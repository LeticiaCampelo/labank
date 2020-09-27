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
