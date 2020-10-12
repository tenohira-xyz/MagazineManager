# MagazineManager
雑誌の目次や索引を管理するためのwebシステム

## 環境
- Java: 11

## 起動方法
コマンドラインで以下のコマンドを実行する。  

```
java -jar -Dspring.profiles.active=release MagazineManager-0.1.0-SNAPSHOT.jar
```
ブラウザで `http://localhost:8080/`にアクセスする。  

終了するにはコマンドライン上で`Ctrl + C`を入力する。  

## 管理者アカウントについて
開発中のため、以下のアカウントで固定している。  

- ユーザ名：admin
- パスワード：password

今後ユーザ管理画面を追加してパスワード変更等に対応予定。  

## 設計書等
開発用のドキュメントはこちら。  
[Document](https://tenohira-xyz.github.io/MagazineManager-docs/#/)  

後でWiki等に移動する。  

- [メモ](doc/note.md)
- [システムの概要](doc/overview.md)
- [画面遷移図](doc/transition-image.md)
- [画面表示項目](doc/io-definition.md)
- [DBに格納する内容の洗い出し](doc/db-item.md)
- [操作部品の動作を定義](doc/action.md)
    - [ログイン画面](doc/action-login.md)
    - [雑誌一覧画面](doc/action-list.md)
    - [雑誌新規登録画面](doc/action-register.md)
    - [雑誌詳細画面](doc/action-detail.md)
    - [目次画面](doc/action-contents.md)
    - [索引画面](doc/action-index.md)
- [テーブル設計](doc/db-design.md)
- [プロジェクトのファイル構成](doc/project-structure.md)
- [処理内容の詳細化](doc/detail-design.md)
- [CRUDの定義](doc/crud.md)
- [DTOの中身の定義](doc/dto-item.md)
- [SQLクエリの設計](doc/sql-query.md)
- [エラーメッセージの定義](doc/error-message.md)
- [例外の定義](doc/exception.md)
- [サービスクラスの定義](doc/service.md)
- [テスト項目](doc/test-item.md)
- [テストデータ](doc/test-sql.md)
- [結合テスト](doc/link-test.md)
    - [テスト結果](doc/lt-result.md)
- [改善点など](doc/improvement.md)
