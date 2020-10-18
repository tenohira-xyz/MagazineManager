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

