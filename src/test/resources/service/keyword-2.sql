INSERT INTO magazine(name, number, update_time)
VALUES('テスト雑誌', 'テスト号数', CURRENT_TIMESTAMP);

UPDATE magazine SET magazine_id = 1;

INSERT INTO keyword(magazine_id, word, start_page, update_time)
VALUES(
	1,
	'テスト単語2-1',
	1,
	CURRENT_TIMESTAMP);

INSERT INTO keyword(magazine_id, word, start_page, update_time)
VALUES(
	1,
	'テスト単語2-2',
	2,
	CURRENT_TIMESTAMP);
