DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS bank;
DROP TABLE IF EXISTS user_account_joint;
DROP TRIGGER IF EXISTS delete_account_with_user;

CREATE TABLE users(
	user_id integer PRIMARY KEY autoincrement,
	username text UNIQUE NOT null,
	password text NOT null
);

CREATE TABLE bank(
	account_id integer PRIMARY KEY autoincrement,
	account_type text NOT null,
	balance real NOT null DEFAULT 0.00
	
);

CREATE TABLE user_account_joint (
	user_id integer,
	account_id integer,
	PRIMARY KEY (user_id, account_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
	FOREIGN KEY (account_id) REFERENCES bank(account_id) ON DELETE CASCADE
);

INSERT INTO users (username, password) values ('admin', 1234);
INSERT INTO users (username, password) values ('admin2', 1234);
INSERT INTO bank (account_type, balance) VALUES ('CHECKING', 100.0);
INSERT INTO bank (account_type) VALUES ('SAVING');
INSERT INTO user_account_joint (user_id, account_id) VALUES (1,1);
INSERT INTO user_account_joint (user_id, account_id) VALUES (2,1);
INSERT INTO user_account_joint (user_id, account_id) VALUES (2,2);

CREATE TRIGGER delete_accound_with_user
BEFORE DELETE ON users
	FOR EACH ROW
	BEGIN
		DELETE FROM bank
		WHERE account_id IN (
			SELECT account_id FROM user_account_joint uaj WHERE uaj.user_id = OLD.user_id
		);
	END;


CREATE TRIGGER delete_bank_account
BEFORE DELETE ON bank
	FOR EACH ROW
	BEGIN
		DELETE FROM user_account_joint
		WHERE account_id = OLD.account_id;
	END;
