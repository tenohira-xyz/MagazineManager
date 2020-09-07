# MagazineManager
雑誌の目次を管理するためのwebシステム

## メモ

### 開発の優先度
1. 閲覧機能
1. 登録機能
1. UIを整える
1. 検索機能
1. エクスポート機能
1. 画像登録機能
1. 画像閲覧機能

### 設計のToDo
<!--
- [x] ~~ユースケース図を作成する~~
- [x] ~~ユースケース記述を作成する~~
- [x] 機能（ユースケース）を洗い出す
- [x] 画面遷移図を作成する
- [x] 画面一覧を作成する
- [x] 画面の入出力項目を洗い出す
- [x] DBの登録項目を洗い出す
- [x] テーブル定義を作成する
- [x] 画面の入出力項目の入力チェックを定義する
- [x] メソッドを洗い出す
- [ ] 画面の入出力項目に対応するDTOの内容を洗い出す
- [ ] DBの各テーブルに対応するDTOを洗い出す
- [ ] テーブルごとのCRUD操作を洗い出す
- [ ] リポジトリクラス（DAO）を設計する
- [ ] メソッドのロジックを設計する
- [ ] クラス図を作成する
- [ ] クラス図を整理する
-->

※基本的に各チェック項目ごとにページを作成する。  
※一通り設計し終わってから、ドキュメントを整理する。  
　（上記ページの分割・統合含む）  

#### 要件定義～基本設計
- [x] システムの概要
- [x] 機能抽出
- [x] 画面表示項目の洗い出し
    - [x] 入出力項目
    - [x] 操作部品（ボタン／リンク）
- [ ] UI設計
- [ ] DBに格納する内容の洗い出し

#### 詳細設計
- [ ] 操作部品の動作を定義
    - [ ] 操作をサーバとクライアントのどちらで処理するか
    - [ ] 画面遷移先
    - [ ] 処理内容の概要
    - [ ] エラー発生時の動作
- [ ] テーブル設計
- [ ] DTOの中身の定義
    - [ ] View→DB、DB→Viewに必要な項目の洗い出し
    - [ ] View-Controller、Controller-Service-DAO間の最低2つを画面ごとに定義
- [ ] 処理内容の詳細化
    - [ ] 正常系
    - [ ] 異常系
- [ ] CRUD定義
- [ ] SQLクエリの設計


## 設計書等
<!--
- [要件定義](doc/requirement-definition.md)
- [画面一覧](doc/page-list.md)
- [入出力項目定義](doc/io-definition.md)
- [テーブル設計](doc/db-design.md)
- [詳細設計](doc/detail-design.md)
-->
- [システムの概要](doc/overview.md)
- [画面表示項目](doc/io-definition.md)
