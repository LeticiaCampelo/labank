 
INSERT INTO labank.agency (agency_number) VALUES
  (1), (2), (3);

INSERT INTO labank.bearer(bearer_name, bearer_type, bearer_document) VALUES
('Tiago',   1, '39744197056'),
('Leticia', 0, '69995668000112'),
('Michel', 0, '13647542000100');

INSERT INTO labank.account (account_number, account_balance, fk_agency_number, fk_bearer_document) VALUES
('02451027', 250.00, 1, '39744197056'),
('031818652', 4500.00, 2, '69995668000112'),
('87893706112', 8000.00, 3, '13647542000100');

INSERT INTO labank.transaction (transaction_amount, transaction_date, fk_account_number) VALUES 
(250.00, '2020-09-01', '02451027'),
(-2000.00, '2020-09-01', '02451027'),
(2000.00, '2020-09-09', '02451027'),
(2000.00, '2020-08-02', '87893706112'),
(2000.00, '2020-07-01', '87893706112'),
(-2000.00, '2020-09-01', '031818652'),
(2000.00, '2020-01-30', '031818652');

