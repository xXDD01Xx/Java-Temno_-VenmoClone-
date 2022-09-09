BEGIN TRANSACTION;

-- ROLLBACK;

DROP TABLE IF EXISTS account, tenmo_user CASCADE;
DROP TABLE IF EXISTS transfer CASCADE;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id;
DROP SEQUENCE IF EXISTS seq_transfer_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
    INCREMENT BY 1
    START WITH 2001
    NO MAXVALUE;

CREATE SEQUENCE seq_transfer_id
    INCREMENT BY 1
    START WITH 3001
    NO MAXVALUE;


CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

--TESTING
--INSERT INTO account ( balance) VALUES ( 1000);

--CREATE TABLE transfer_status (
--    transfer_status_id int NOT NULL DEFAULT nextval('seq_transfer_status_id'),
--    transfer_status_type varchar(15) NOT NULL,
--    CONSTRAINT transfer_status_id PRIMARY KEY (transfer_status_id)
--);

--TESTING
-- INSERT INTO transfer_status (transfer_status_type) VALUES ('PENDING');
-- INSERT INTO transfer_status (transfer_status_type) VALUES ('APPROVED');
-- INSERT INTO transfer_status (transfer_status_type) VALUES ('REJECTED');


--CREATE TABLE transfer_type (
--    transfer_type_id int NOT NULL DEFAULT nextVal('seq_transfer_type_id'),
--    transfer_type_name varchar(15) NOT NULL,
--    CONSTRAINT transfer_type_id PRIMARY KEY (transfer_type_id)
--);

--TESTING
-- INSERT INTO transfer_type (transfer_type_name) VALUES ('SEND');
-- INSERT INTO transfer_type (transfer_type_name) VALUES ('RECEIVE');
--INSERT INTO transfer_type (transfer_type_name) VALUES ('REQUEST');


CREATE TABLE transfer (
    transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
    transfer_status varchar(20) NOT NULL,
    from_account int NOT NULL,
    to_account int NOT NULL,
    amount decimal(13,2) NOT NULL,
--transfer_type_id int NOT NULL,
--may need to include the userID for transfers

    CONSTRAINT PK_transfers PRIMARY KEY (transfer_id),
	CONSTRAINT FK_transfer_from_account FOREIGN KEY (from_account) REFERENCES account (account_id),
	CONSTRAINT FK_transfer_to_account FOREIGN KEY (to_account) REFERENCES account (account_id),
	CONSTRAINT CK_transfer_minimum CHECK ( amount >0 )
--    CONSTRAINT FK_transfer_status_id FOREIGN KEY (transfer_status_id) REFERENCES transfer_status (transfer_status_id),

    --    CONSTRAINT FK_transfer_type FOREIGN KEY (transfer_type_id) REFERENCES transfer_type (transfer_type_id),
--    --May or may not work...
--    CONSTRAINT CK_transfer_not_greater_than_balance CHECK (amount <= (SELECT balance FROM account WHERE account_id = transfer.from_account)),
--    CONSTRAINT CK_transfer_not_same_account CHECK ( from_account<>to_account )
);

COMMIT;
