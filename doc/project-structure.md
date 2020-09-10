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
|目次画面|ContentListController|
|索引画面|IndexListController|

### サービスクラス
xyz.tenohira.magazinemanager.service

|論理名|物理名|
|:--|:--| 
|ログイン画面|-|
|雑誌一覧画面|MagazineListService|
|雑誌新規登録画面|RegisterService|
|雑誌詳細画面|DetailService|
|目次画面|ContentListService|
|索引画面|IndexListService|

### ドメインクラス（DTO）
xyz.tenohira.magazinemanager.domain.model

|論理名|物理名|
|:--|:--| 
|雑誌|Magazine|
|目次|Content|
|索引|Index|
|雑誌一覧フォーム|MagazineListForm|
|雑誌詳細フォーム|DetailForm|
|目次フォーム|ContentListForm|
|索引フォーム|IndexListForm|

### リポジトリクラス（DAO）
#### インターフェイス
xyz.tenohira.magazinemanager.domain.repository

|論理名|物理名|
|:--|:--| 
|雑誌|MagazineDao|
|目次|ContentDao|
|索引|IndexDao|

#### 実装クラス
xyz.tenohira.magazinemanager.domain.repository.impl

|論理名|物理名|
|:--|:--| 
|雑誌|MagazineDaoImpl|
|目次|ContentDaoImpl|
|索引|IndexDaoImpl|

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
|目次画面|contentsList.html|
|索引画面|indexList.html|

#### エラー画面
templates/error

|論理名|物理名|
|:--|:--| 
|共通エラー画面|error.html|
|雑誌未指定エラー画面|unspecified.html|
|雑誌非存在エラー画面|notExist.html|
|雑誌登録済エラー画面|registered.html|
