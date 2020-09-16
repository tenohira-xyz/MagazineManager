# SQLクエリ

## ユーザ名とパスワードを取得するSQL
### 取得項目
- ユーザID: user_id
- パスワード: password
### 取得元テーブル
- ユーザマスタ: m_user
### 条件
- ユーザID = 入力フォームのユーザID
### SQL
SELECT user_id, password FROM m_user WHERE user_id = ?

## ユーザのロールを取得するSQL
### 取得項目
- ユーザID: user_id
- 権限: authority
### 取得元テーブル
- ユーザマスタ: m_user
### 条件
- ユーザID = 入力フォームのユーザID
### SQL
SELECT user_id, authority FROM m_user WHERE user_id = ?

## 雑誌リポジトリクラス
### 存在チェックメソッド（雑誌ID）
#### 取得項目
- 雑誌ID: magazine_id
#### 取得元テーブル
- 雑誌: magazine
#### 条件
- 雑誌ID = 引数.雑誌ID
### SQL
SELECT magazine_id FROM magazine WHERE magazine_id = ?

### 存在チェックメソッド（雑誌名、号数）
#### 取得項目
- 雑誌ID: magazine_id
#### 取得元テーブル
- 雑誌: magazine
#### 条件
- 雑誌名(magazine_name) = 引数.雑誌名
- 号数(number) = 引数.号数
### SQL
SELECT magazine_id FROM magazine WHERE magazine_name = ? AND number = ?

### 削除メソッド
#### 操作テーブル
- 雑誌: magazine
#### 条件
- 雑誌ID(magazine_id) = 引数.雑誌ID
### SQL
DELETE FROM magazine WHERE magazine_id = ?

### 登録メソッド
#### 操作テーブル
- 雑誌: magazine
#### 登録内容
- 雑誌名(magazine_name) = 引数.雑誌名
- 号数(number) = 引数.号数
- 更新日付(update_time) = 現在日時
### SQL
INSERT INTO magazine(magazine_name, number, update_time) VALUES(?, ?, CURRENT_TIMESTAMP)

### 更新メソッド
#### 操作テーブル
- 雑誌: magazine
#### 条件
- 雑誌ID(magazine_id) = 引数.雑誌ID
#### 登録内容
- 雑誌名(magazine_name) = 引数.雑誌名
- 号数(number) = 引数.号数
- 出版社(publisher) = 引数.出版社
- 発行日(issue_date) = 引数.発行日
- 更新日付(update_time) = 現在日時
### SQL
UPDATE magazine SET magazine_name = ?, number = ?, publisher = ?, issue_date = ?, update_time = CURRENT_TIMESTAMP WHERE magazine_id = ?

## 目次リポジトリクラス
### 削除メソッド
#### 操作テーブル
- 目次: contents
#### 条件
- 雑誌ID(magazine_id) = 引数.雑誌ID
### SQL
DELETE FROM contents WHERE magazine_id = ?

### 登録メソッド
#### 操作テーブル
- 目次: contents
#### 登録内容
- 雑誌ID(magazine_id) = 引数.雑誌ID
- セクション(section) = 引数.セクション
- タイトル(title) = 引数.タイトル
- 開始ページ(start_page) = 引数.開始ページ
- 更新日付(update_time) = 現在日時
### SQL
INSERT INTO contents(magazine_id, section, title, start_page, update_time) VALUES(?, ?, ?, ?, CURRENT_TIMESTAMP)

## 索引リポジトリクラス
### 削除メソッド
#### 操作テーブル
- 索引: index
#### 条件
- 雑誌ID(magazine_id) = 引数.雑誌ID
### SQL
DELETE FROM index WHERE magazine_id = ?

### 登録メソッド
#### 操作テーブル
- 索引: index
#### 登録内容
- 雑誌ID(magazine_id) = 引数.雑誌ID
- キーワード(keyword) = 引数.キーワード
- 開始ページ(start_page) = 引数.開始ページ
- 更新日付(update_time) = 現在日時
### SQL
INSERT INTO contents(magazine_id, keyword, start_page, update_time) VALUES(?, ?, ?, CURRENT_TIMESTAMP)
