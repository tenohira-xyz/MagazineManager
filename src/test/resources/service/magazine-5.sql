INSERT INTO magazine(magazine_id, name, number, publisher, issue_date, update_time)
VALUES(
	1,
	'テスト雑誌5-1',
	'テスト号数5-1',
	'テスト出版社5-1',
	DATE '2020-01-01',
	CURRENT_TIMESTAMP);

INSERT INTO article(magazine_id, section, title, start_page, update_time)
VALUES(
	1,
	'テストセクション5-1',
	'テストタイトル5-1',
	1,
	CURRENT_TIMESTAMP);

INSERT INTO keyword(magazine_id, word, start_page, update_time)
VALUES(
	1,
	'テスト単語5-1',
	1,
	CURRENT_TIMESTAMP);
