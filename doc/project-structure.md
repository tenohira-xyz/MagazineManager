# プロジェクトのフォルダ構成

## Javaプログラム
src/main/java

### ルートパッケージ
xyz.tenohira.magazinemanager

### コントローラクラス
xyz.tenohira.magazinemanager.controller

|論理名|物理名|
|:--|:--| 
|ログイン画面|LoginController|
|雑誌一覧画面|MagazineListController|
|雑誌新規登録画面|RegisterController|
|雑誌詳細画面|DetailController|
|目次画面|ContentController|
|索引画面|IndexController|

### サービスクラス
xyz.tenohira.magazinemanager.service

|論理名|物理名|
|:--|:--| 
|ログイン画面|-|
|雑誌一覧画面|MagazineListService|
|雑誌新規登録画面|RegisterService|
|雑誌詳細画面|DetailService|
|目次画面|ContentService|
|索引画面|IndexService|

### ドメインクラス（DTO）
xyz.tenohira.magazinemanager.domain.model

|論理名|物理名|
|:--|:--| 
|雑誌|Magazine|
|記事|Article|
|キーワード|Keyword|
|雑誌一覧フォーム|MagazineListForm|
|雑誌詳細フォーム|DetailForm|
|目次フォーム|ContentForm|
|索引フォーム|IndexForm|

### リポジトリクラス（DAO）
#### インターフェイス
xyz.tenohira.magazinemanager.domain.repository

|論理名|物理名|
|:--|:--| 
|雑誌|MagazineDao|
|目次|ArticleDao|
|索引|KeywordDao|

#### 実装クラス
xyz.tenohira.magazinemanager.domain.repository.impl

|論理名|物理名|
|:--|:--| 
|雑誌|MagazineDaoImpl|
|目次|ArticleDaoImpl|
|索引|KeywordDaoImpl|

## リソース
src/main/resources

### ビュー

#### 業務画面
templates

|論理名|物理名|
|:--|:--| 
|ログイン画面|login.html|
|雑誌一覧画面|magazineList.html|
|雑誌新規登録画面|register.html|
|雑誌詳細画面|detail.html|
|目次画面|content.html|
|索引画面|index.html|

#### エラー画面
templates/error

|論理名|物理名|
|:--|:--| 
|共通エラー画面|error.html|
|アクセス不可エラー画面|deny.html|
|セッション切れエラー画面|session.html|
|雑誌未指定エラー画面|unspecified.html|
|雑誌非存在エラー画面|not-exist.html|
|雑誌登録済エラー画面|registered.html|
