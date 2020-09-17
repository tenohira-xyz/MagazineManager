/* ユーザマスタ */
CREATE TABLE IF NOT EXISTS m_user (
	user_id VARCHAR(50) PRIMARY KEY,
	password VARCHAR(255),
	authority VARCHAR(20)
);

/* 雑誌テーブル */
CREATE TABLE IF NOT EXISTS magazine (
	magazine_id INT auto_increment PRIMARY KEY,
	magazine_name VARCHAR(30) NOT NULL,
	number VARCHAR(20) NOT NULL,
	publisher VARCHAR(20),
	issue_date DATE,
	update_time TIMESTAMP NOT NULL
);

/* 目次テーブル */
CREATE TABLE IF NOT EXISTS contents (
	magazine_id INT,
	section VARCHAR(30),
	title VARCHAR(50),
	start_page INT NOT NULL,
	update_time TIMESTAMP NOT NULL,
	FOREIGN KEY (magazine_id) REFERENCES magazine(magazine_id)
);

/* 索引テーブル */
CREATE TABLE IF NOT EXISTS index (
	magazine_id INT,
	keyword VARCHAR(50) NOT NULL,
	start_page INT NOT NULL,
	update_time TIMESTAMP NOT NULL,
	FOREIGN KEY (magazine_id) REFERENCES magazine(magazine_id)
);
