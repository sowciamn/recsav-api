# 家計簿API (recsav-api)

これは、日々の収支、資産、予算などを管理するための家計簿アプリケーションのバックエンドAPIです。

## 主な機能

- **収支管理:** 日々の収入と支出を記録・管理します。
- **資産管理:** 預金口座ごとの資産状況を月次で記録します。
- **投資損益管理:** 投資口座の損益を月次で記録します。
- **固定費管理:** 家賃や光熱費などの固定費を管理します。
- **予算管理:** カテゴリごとに月次の予算を設定します。
- **ダッシュボード:** 指定した年月のサマリー（収入、支出、収支、資産状況など）を表示します。
- **マスタ管理:** カテゴリ、店舗、預金口座の情報を管理します。

## 使用技術

- **言語:** Java
- **フレームワーク:** Spring Boot
- **ORM:** MyBatis
- **ビルドツール:** Maven
- **データベース:** PostgreSQL

## セットアップ手順

### 1. 前提条件

- JDK 17 以上
- Maven
- PostgreSQL

### 2. インストール

1.  **リポジトリをクローンします。**
    ```bash
    git clone https://github.com/your-username/recsav-api.git
    cd recsav-api
    ```

2.  **データベースをセットアップします。**
    - PostgreSQLでデータベースを作成します。
    - `db/schema.sql` を実行して、テーブルとビューを作成します。

3.  **アプリケーションの設定を更新します。**
    `src/main/resources/application.properties` ファイルを開き、ご自身のデータベース接続情報（URL、ユーザー名、パスワード）に合わせて内容を更新してください。
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

4.  **アプリケーションをビルドします。**
    ```bash
    ./mvnw clean install
    ```

### 3. アプリケーションの実行

以下のコマンドでアプリケーションを起動します。
```bash
./mvnw spring-boot:run
```
デフォルトでは、`http://localhost:8080` で起動します。

## ドキュメント

- **[データベース設計書](DATABASE_DESIGN.md):** ER図、テーブル定義、ビュー定義など。
- **[API仕様書](API_SPECIFICATION.md):** 各APIエンドポイントの詳細。
- **[テーブル定義書](TABLE_DEFINITION.md):** 各テーブルのカラム詳細。

## ライセンス

このプロジェクトは MIT License の下で公開されています。詳細は [LICENSE](LICENSE) ファイルをご覧ください。
