CREATE SCHEMA labank;
SET SCHEMA labank;

DROP TABLE IF EXISTS agency;
CREATE TABLE agency (
  agency_number NUMBER NOT NULL PRIMARY KEY
);
 
INSERT INTO agency (nm_agency) VALUES
  (1),
  (2),
  (3);

 DROP TABLE IF EXISTS bearer;
 CREATE TABLE bearer (
  bearer_document VARCHAR(14) NOT NULL PRIMARY KEY
  bearer_name VARCHAR(50) NOT NULL,
  bearer_type NUMBER NOT NULL  
);

INSERT INTO bearer(bearer_name, bearer_type, bearer_document) VALUES
('Tiago',   1, '39744197056'),
('Leticia', 0, '69995668000112'),
('Michel', 0, '13647542000100');

DROP TABLE IF EXISTS account;
CREATE TABLE account(
	account_number VARCHAR(50) NOT NULL PRIMARY KEY,
	account_balance double NOT NULL,
	fk_agency_number NUMBER NOT NULL,
	fk_bearer_document VARCHAR(50) NOT NULL,
	foreign key (fk_agency_number) references agency(agency_number),
	foreign key (fk_bearer_document) references bearer(bearer_document)	
);

INSERT INTO account (account_number, account_balance, fk_agency_number, fk_bearer_document) VALUES
('02451027', 250.00, 1, '39744197056'),
('031818652', 4500.00, 2, '69995668000112'),
('87893706112', 8000.00, 3, '13647542000100');


DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction(
	transaction_id NUMBER AUTO_INCREMENT  PRIMARY KEY,
	transaction_value DOUBLE NOT NULL,
	transaction_date DATE NOT NULL,
	fk_account_number VARCHAR(50) NOT NULL,
	foreign key (fk_account_number) references account(account_number)	
);

INSERT INTO transaction (transaction_value, transaction_date, fk_account_number) VALUES 
(250.00, '2020-09-01', '39744197056'),
(-2000.00, '2020-09-01', '39744197056'),
(2000.00, '2020-09-09', '39744197056'),
(2000.00, '2020-08-02', '13647542000100'),
(2000.00, '2020-07-01', '13647542000100'),
(-2000.00, '2020-09-01', '69995668000112'),
(2000.00, '2020-01-30', '69995668000112');