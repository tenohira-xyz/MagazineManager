/* ユーザマスタのデータ */
INSERT INTO m_user(name, password, authority)
VALUES('admin', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_ADMIN');

/* 雑誌テーブルのデータ */
INSERT INTO magazine(name, number, publisher, issue_date, update_time)
VALUES('テスト雑誌1', 'No.1', '出版社1', '2020-01-01', CURRENT_TIMESTAMP);
INSERT INTO magazine(name, number, publisher, issue_date, update_time)
VALUES('テスト雑誌2', 'No.2', '出版社2', '2020-02-01', CURRENT_TIMESTAMP);
INSERT INTO magazine(name, number, publisher, issue_date, update_time)
VALUES('テスト雑誌3', 'No.3', '出版社3', '2020-03-01', CURRENT_TIMESTAMP);

/* 記事テーブルのデータ */
INSERT INTO article(magazine_id, section, title, start_page, update_time)
VALUES(1, 'セクション1', 'タイトルAAA', 1, CURRENT_TIMESTAMP);
INSERT INTO article(magazine_id, section, title, start_page, update_time)
VALUES(1, 'セクション2', 'タイトルBBB', 2, CURRENT_TIMESTAMP);

/* キーワードテーブルのデータ */
INSERT INTO keyword(magazine_id, word, start_page, update_time)
VALUES(1, 'キーワード1', 1, CURRENT_TIMESTAMP);
INSERT INTO keyword(magazine_id, word, start_page, update_time)
VALUES(1, 'キーワード2', 2, CURRENT_TIMESTAMP);
