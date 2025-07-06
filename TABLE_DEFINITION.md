# テーブル定義書

## 1. if_zaim (Zaimインターフェース)

- **説明:** Zaimからのデータを一時的に格納するテーブル

| カラム名 | データ型 | 主キー | 説明 |
| :--- | :--- | :--- | :--- |
| if_zaim_seq | serial | PK | ZaimインターフェースSeq |
| if_zaim_date | date | | 日付 |
| if_zaim_method | text | | 方法 |
| if_zaim_category | text | | カテゴリ |
| if_zaim_category_detail | text | | カテゴリの内訳 |
| if_zaim_payment_source | text | | 支払元 |
| if_zaim_deposit | text | | 入金先 |
| if_zaim_item | text | | 品目 |
| if_zaim_remarks | text | | メモ |
| if_zaim_store | text | | お店 |
| if_zaim_currency | text | | 通貨 |
| if_zaim_income_amount | numeric(8, 0) | | 収入 |
| if_zaim_expense_amount | numeric(8, 0) | | 支出 |
| if_zaim_transfer_amount | numeric(8, 0) | | 振替 |
| if_zaim_balance_amount | numeric(8, 0) | | 残高調整 |
| if_zaim_before_amount | numeric(8, 0) | | 通貨変換前の金額 |
| if_zaim_aggregation_settings | text | | 集計の設定 |
| created_at | timestamp | | 登録日時 |
| updated_at | timestamp | | 更新日時 |

## 2. category (カテゴリ)

- **説明:** 収入・支出のカテゴリを管理するテーブル

| カラム名 | データ型 | 主キー | 説明 |
| :--- | :--- | :--- | :--- |
| category_cd | serial | PK | カテゴリコード |
| category_nm | varchar(100) | | カテゴリ名 |
| category_type | varchar(2) | | カテゴリ種別（1：収入、2：支出） |
| display_order | integer | | 表示順 |
| fixed_category_flg | varchar(1) | | 固定費カテゴリーフラグ |
| created_at | timestamp | | 登録日時 |
| updated_at | timestamp | | 更新日時 |

## 3. store (店)

- **説明:** 支払いや収入のあった店舗を管理するテーブル

| カラム名 | データ型 | 主キー | 説明 |
| :--- | :--- | :--- | :--- |
| store_cd | serial | PK | 店コード |
| store_nm | varchar(100) | | 店名 |
| store_address | text | | 店住所 |
| created_at | timestamp | | 登録日時 |
| updated_at | timestamp | | 更新日時 |

## 4. deposit_account (預金口座)

- **説明:** 預金口座の情報を管理するテーブル

| カラム名 | データ型 | 主キー | 説明 |
| :--- | :--- | :--- | :--- |
| deposit_account_cd | varchar(3) | PK | 預金口座コード |
| deposit_account_nm | varchar(100) | | 預金口座名 |
| deposit_usage | text | | 預金用途 |
| investment_account_flg | varchar(1) | | 投資口座フラグ |
| created_at | timestamp | | 登録日時 |
| updated_at | timestamp | | 更新日時 |

## 5. linking_data (連携データ)

- **説明:** 外部サービスとの連携情報を管理するテーブル

| カラム名 | データ型 | 主キー | 説明 |
| :--- | :--- | :--- | :--- |
| linking_data_type | serial | PK | 連携データタイプ |
| linking_data_nm | varchar(100) | | 連携データ名 |
| last_linking_date | date | | 最終連携日 |
| created_at | timestamp | | 登録日時 |
| updated_at | timestamp | | 更新日時 |

## 6. household_account_book (家計簿)

- **説明:** 日々の収入・支出を記録するテーブル

| カラム名 | データ型 | 主キー | 外部キー | 説明 |
| :--- | :--- | :--- | :--- | :--- |
| hab_seq | serial | PK | | 家計簿Seq |
| actual_date | date | | | 実績日 |
| category_cd | integer | | category(category_cd) | カテゴリコード |
| store_cd | integer | | store(store_cd) | 店コード |
| amount | numeric(8, 0) | | | 金額 |
| remarks | text | | | 備考 |
| linking_data_type | integer | | linking_data(linking_data_type) | 連携データタイプ |
| created_at | timestamp | | | 登録日時 |
| updated_at | timestamp | | | 更新日時 |

## 7. budget (予算)

- **説明:** 月ごとの予算を管理するテーブル

| カラム名 | データ型 | 主キー | 外部キー | 説明 |
| :--- | :--- | :--- | :--- | :--- |
| budget_seq | serial | PK | | 予算Seq |
| budget_year_month | date | | | 予算年月 |
| category_cd | integer | | category(category_cd) | カテゴリコード |
| budget_amount | numeric(8, 0) | | | 予算金額 |
| budget_remarks | text | | | 予算備考 |
| created_at | timestamp | | | 登録日時 |
| updated_at | timestamp | | | 更新日時 |

## 8. asset (資産)

- **説明:** 月ごとの資産状況を管理するテーブル

| カラム名 | データ型 | 主キー | 外部キー | 説明 |
| :--- | :--- | :--- | :--- | :--- |
| asset_year_month | date | PK | | 資産年月 |
| deposit_account_cd | varchar(3) | PK | deposit_account(deposit_account_cd) | 預金口座コード |
| asset_amount | numeric(8, 0) | | | 資産金額 |
| asset_remarks | text | | | 資産備考 |
| created_at | timestamp | | | 登録日時 |
| updated_at | timestamp | | | 更新日時 |

## 9. investment_pl (投資損益)

- **説明:** 月ごとの投資損益を管理するテーブル

| カラム名 | データ型 | 主キー | 外部キー | 説明 |
| :--- | :--- | :--- | :--- | :--- |
| investment_pl_year_month | date | PK | | 投資損益年月 |
| deposit_account_cd | varchar(3) | PK | deposit_account(deposit_account_cd) | 預金口座コード |
| investment_pl_amount | numeric(8, 0) | | | 投資損益金額 |
| investment_pl_remarks | text | | | 投資損益備考 |
| created_at | timestamp | | | 登録日時 |
| updated_at | timestamp | | | 更新日時 |

## 10. fixed_cost (固定費)

- **説明:** 毎月発生する固定費を管理するテーブル

| カラム名 | データ型 | 主キー | 外部キー | 説明 |
| :--- | :--- | :--- | :--- | :--- |
| fixed_cost_seq | serial | PK | | 固定費SEQ |
| category_cd | integer | | category(category_cd) | カテゴリコード |
| fixed_cost_details | text | | | 固定費内訳 |
| display_order | integer | | | 表示順 |
| remarks | text | | | 備考 |
| january | numeric(8, 0) | | | 1月 |
| february | numeric(8, 0) | | | 2月 |
| march | numeric(8, 0) | | | 3月 |
| april | numeric(8, 0) | | | 4月 |
| may | numeric(8, 0) | | | 5月 |
| june | numeric(8, 0) | | | 6月 |
| july | numeric(8, 0) | | | 7月 |
| august | numeric(8, 0) | | | 8月 |
| september | numeric(8, 0) | | | 9月 |
| october | numeric(8, 0) | | | 10月 |
| november | numeric(8, 0) | | | 11月 |
| december | numeric(8, 0) | | | 12月 |
| created_at | timestamp | | | 登録日時 |
| updated_at | timestamp | | | 更新日時 |

## 11. m_user (ユーザマスタ)

- **説明:** アプリケーションの利用者を管理するテーブル

| カラム名 | データ型 | 主キー | 説明 |
| :--- | :--- | :--- | :--- |
| id | serial | PK | ユーザID |
| name | varchar(100) | | ユーザ名 |
| email | varchar(100) | | e-mail |
| roles | varchar(100) | | ロール |
| password | varchar(150) | | パスワード |
| is_active | boolean | | 有効 |
| created_at | timestamp | | 登録日時 |
| updated_at | timestamp | | 更新日時 |
