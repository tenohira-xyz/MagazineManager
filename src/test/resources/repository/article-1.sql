INSERT INTO magazine(name, number, update_time)
VALUES('テスト雑誌', 'テスト号数', CURRENT_TIMESTAMP);

UPDATE magazine SET magazine_id = 1;

INSERT INTO article(magazine_id, section, title, start_page, update_time)
VALUES(
	1,
	'テストセクション1-1',
	'テストタイトル1-1',
	1,
	CURRENT_TIMESTAMP);

INSERT INTO article(magazine_id, section, title, start_page, update_time)
VALUES(
	1,
	'テストセクション1-2',
	'テストタイトル1-2',
	10,
	CURRENT_TIMESTAMP);

INSERT INTO article(magazine_id, section, title, start_page, update_time)
VALUES(
	1,
	'□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■',
	'□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■□□□□□□□□□■',
	999,
	CURRENT_TIMESTAMP);
