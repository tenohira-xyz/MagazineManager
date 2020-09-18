# SQLクエリ

## セキュリティ設定クラス
### ユーザ名とパスワードを取得するSQL
#### 取得項目
- ユーザ名
- パスワード
- 使用可否（一律`true`を設定する）
#### 取得元テーブル
- ユーザマスタ
#### 条件
- ユーザ名 = 入力フォームのユーザ名
#### SQL
SELECT name, password, true FROM m_user WHERE name = ?

### ユーザのロールを取得するSQL
#### 取得項目
- ユーザ名
- 権限
#### 取得元テーブル
- ユーザマスタ
#### 条件
- ユーザID = 入力フォームのユーザID
#### SQL
SELECT name, authority FROM m_user WHERE name = ?

## 雑誌リポジトリクラス
### 存在チェックメソッド（雑誌ID）
#### 取得項目
- 雑誌ID
#### 取得元テーブル
- 雑誌
#### 条件
- 雑誌ID = 引数.雑誌ID
### SQL
SELECT magazine_id FROM magazine WHERE magazine_id = ?

### 存在チェックメソッド（雑誌名、号数）
#### 取得項目
- 雑誌ID
#### 取得元テーブル
- 雑誌
#### 条件
- 雑誌名 = 引数.雑誌名
- 号数 = 引数.号数
#### SQL
SELECT magazine_id FROM magazine WHERE name = ? AND number = ?

### 単一レコード取得メソッド
#### 取得項目
- 雑誌名
- 号数
- 出版社
- 発行日
#### 取得元テーブル
- 雑誌
#### 条件
- 雑誌ID = 引数.雑誌ID
#### SQL
SELECT name, number, publisher, issue_date FROM magazine WHERE magazine_id = ?

### 複数レコード取得メソッド
#### 取得項目
- 雑誌ID
- 雑誌名
- 号数
#### 取得元テーブル
- 雑誌
#### 条件
なし
#### SQL
SELECT magazine_id, name, number FROM magazine

### 削除メソッド
#### 操作テーブル
- 雑誌
#### 条件
- 雑誌ID = 引数.雑誌ID
#### SQL
DELETE FROM magazine WHERE magazine_id = ?

### 登録メソッド
#### 操作テーブル
- 雑誌
#### 登録内容
- 雑誌名 = 引数.雑誌名
- 号数 = 引数.号数
- 更新日付 = 現在日時
#### SQL
INSERT INTO magazine(name, number, update_time) VALUES(?, ?, CURRENT_TIMESTAMP)

### 更新メソッド
#### 操作テーブル
- 雑誌
#### 条件
- 雑誌ID = 引数.雑誌ID
#### 登録内容
- 雑誌名 = 引数.雑誌名
- 号数 = 引数.号数
- 出版社 = 引数.出版社
- 発行日 = 引数.発行日
- 更新日付 = 現在日時
#### SQL
UPDATE magazine SET name = ?, number = ?, publisher = ?, issue_date = ?, update_time = CURRENT_TIMESTAMP WHERE magazine_id = ?

## 記事リポジトリクラス
### 複数レコード取得メソッド
#### 取得項目
- セクション
- タイトル
- 開始ページ
#### 取得元テーブル
- 記事
#### 条件
- 雑誌ID = 引数.雑誌ID
#### SQL
SELECT section, title, start_page FROM article WHERE magazine_id = ?

### 削除メソッド
#### 操作テーブル
- 記事
#### 条件
- 雑誌ID = 引数.雑誌ID
#### SQL
DELETE FROM article WHERE magazine_id = ?

### 登録メソッド
#### 操作テーブル
- 記事
#### 登録内容
- 雑誌ID = 引数.雑誌ID
- セクション = 引数.セクション
- タイトル = 引数.タイトル
- 開始ページ = 引数.開始ページ
- 更新日付 = 現在日時
#### SQL
INSERT INTO article(magazine_id, section, title, start_page, update_time) VALUES(?, ?, ?, ?, CURRENT_TIMESTAMP)

## キーワードリポジトリクラス
### 複数レコード取得メソッド
#### 取得項目
- キーワード
- 開始ページ
#### 取得元テーブル
- キーワード
#### 条件
- 雑誌ID = 引数.雑誌ID
#### SQL
SELECT word, start_page FROM keyword WHERE magazine_id = ?

### 削除メソッド
#### 操作テーブル
- キーワード
#### 条件
- 雑誌ID = 引数.雑誌ID
#### SQL
DELETE FROM keyword WHERE magazine_id = ?

### 登録メソッド
#### 操作テーブル
- キーワード
#### 登録内容
- 雑誌ID = 引数.雑誌ID
- キーワード = 引数.キーワード
- 開始ページ = 引数.開始ページ
- 更新日付 = 現在日時
#### SQL
INSERT INTO keyword(magazine_id, word, start_page, update_time) VALUES(?, ?, ?, CURRENT_TIMESTAMP)
