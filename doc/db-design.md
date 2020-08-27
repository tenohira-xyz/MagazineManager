# テーブル設計

## 雑誌
|項目名|型|桁数|備考|
|:--|:--|:--|:--|
|雑誌ID|INT||主キー|
|雑誌名|VARCHAR|30|NOT NULL|
|号数|VARCHAR|20|NOT NULL|
|出版社|VARCHAR|20||
|発行日|DATE|||
|更新日付|TIMESTAMP||NOT NULL|

## 記事
|項目名|型|桁数|備考|
|:--|:--|:--|:--|
|雑誌ID|INT||外部キー|
|セクション|VARCHAR|30||
|タイトル|VARCHAR|50||
|開始ページ|INT||NOT NULL|
|更新日付|TIMESTAMP||NOT NULL|

## キーワード
|項目名|型|桁数|備考|
|:--|:--|:--|:--|
|雑誌ID|INT||外部キー|
|キーワード|VARCHAR|50|NOT NULL|
|開始ページ|INT||NOT NULL|
|更新日付|TIMESTAMP||NOT NULL|
