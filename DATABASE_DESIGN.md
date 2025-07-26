# データベース設計

このドキュメントは、`recsav-api` アプリケーションのデータベース構造を定義します。

## ER図

```mermaid
erDiagram
    household_account_book {
        INT hab_seq PK
        DATE actual_date
        INT category_cd FK
        INT store_cd FK
        NUMERIC amount
        TEXT remarks
        INT linking_data_type FK
    }

    category {
        INT category_cd PK
        VARCHAR category_nm
        VARCHAR category_type
        INT display_order
        VARCHAR fixed_category_flg
    }

    store {
        INT store_cd PK
        VARCHAR store_nm
        TEXT store_address
    }

    linking_data {
        INT linking_data_type PK
        VARCHAR linking_data_nm
        DATE last_linking_date
    }

    budget {
        INT budget_seq PK
        DATE budget_year_month
        INT category_cd FK
        NUMERIC budget_amount
        TEXT budget_remarks
    }

    asset {
        DATE asset_year_month PK
        VARCHAR deposit_account_cd PK FK
        NUMERIC asset_amount
        TEXT asset_remarks
    }

    deposit_account {
        VARCHAR deposit_account_cd PK
        VARCHAR deposit_account_nm
        TEXT deposit_usage
        VARCHAR investment_account_flg
    }

    investment_pl {
        DATE investment_pl_year_month PK
        VARCHAR deposit_account_cd PK FK
        NUMERIC investment_pl_amount
        TEXT investment_pl_remarks
    }

    fixed_cost {
        INT fixed_cost_seq PK
        INT category_cd FK
        TEXT fixed_cost_details
        INT display_order
        TEXT remarks
        NUMERIC january
        NUMERIC february
        NUMERIC march
        NUMERIC april
        NUMERIC may
        NUMERIC june
        NUMERIC july
        NUMERIC august
        NUMERIC september
        NUMERIC october
        NUMERIC november
        NUMERIC december
    }

    category_mapping_config {
        INT category_mapping_config_seq PK
        TEXT mapping_key_nm
        INT category_cd FK
        VARCHAR linking_excluded_flg
    }

    recurring_config {
        INT recurring_config_seq PK
        TEXT recurring_nm
        VARCHAR execution_interval_type
        INT category_cd FK
        INT store_cd FK
        NUMERIC amount
        TEXT remarks
        INT linking_data_type FK
        VARCHAR active_flg
    }

    if_rakuten_card {
        INT if_rakuten_card_seq PK
        DATE usage_date
        TEXT merchant_product_name
        TEXT customer_nm
        TEXT payment_method
        NUMERIC usage_amount
        NUMERIC payment_fee
        NUMERIC total_payment_amount
        TEXT payment_month
        NUMERIC monthly_payment_amount
        NUMERIC monthly_carryover_balance
        TEXT new_signup_flag
    }

    household_account_book }|--|| category : "belongs to"
    household_account_book }|--|| store : "belongs to"
    household_account_book }|--|| linking_data : "belongs to"
    budget }|--|| category : "belongs to"
    asset }|--|| deposit_account : "relates to"
    investment_pl }|--|| deposit_account : "relates to"
    fixed_cost }|--|| category : "belongs to"
    category_mapping_config }|--|| category : "belongs to"
    recurring_config }|--|| category : "belongs to"
    recurring_config }|--|| store : "belongs to"
    recurring_config }|--|| linking_data : "belongs to"
```

## テーブル定義

### `if_rakuten_card` (楽天カードインターフェース)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `if_rakuten_card_seq` | `serial` | 楽天カードインターフェースSeq |
| `usage_date` | `DATE` | 利用日 |
| `merchant_product_name` | `text` | 利用店名・商品名 |
| `customer_nm` | `text` | 利用者 |
| `payment_method` | `text` | 支払方法 |
| `usage_amount` | `numeric(8, 0)` | 利用金額 |
| `payment_fee` | `numeric(8, 0)` | 支払手数料 |
| `total_payment_amount` | `numeric(8, 0)` | 支払総額 |
| `payment_month` | `text` | 支払月 |
| `monthly_payment_amount` | `numeric(8, 0)` | N月支払金額 |
| `monthly_carryover_balance` | `numeric(8, 0)` | N月繰越残高 |
| `new_signup_flag` | `text` | 新規サイン |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

### `category` (カテゴリ)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `category_cd` | `serial` | カテゴリコード |
| `category_nm` | `CHARACTER VARYING(100)` | カテゴリ名 |
| `category_type` | `CHARACTER VARYING(2)` | カテゴリ種別（1：収入、2：支出） |
| `display_order` | `INTEGER` | 表示順 |
| `fixed_category_flg` | `CHARACTER VARYING(1)` | 固定費カテゴリーフラグ |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

### `store` (店)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `store_cd` | `serial` | 店コード |
| `store_nm` | `CHARACTER VARYING(100)` | 店名 |
| `store_address` | `text` | 店住所 |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

### `deposit_account` (預金口座)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `deposit_account_cd` | `CHARACTER VARYING(3)` | 預金口座コード |
| `deposit_account_nm` | `CHARACTER VARYING(100)` | 預金口座名 |
| `deposit_usage` | `text` | 預金用途 |
| `investment_account_flg` | `CHARACTER VARYING(1)` | 投資口座フラグ |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

### `linking_data` (連携データ)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `linking_data_type` | `serial` | 連携データタイプ |
| `linking_data_nm` | `CHARACTER VARYING(100)` | 連携データ名 |
| `last_linking_date` | `DATE` | 最終連携日 |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

### `household_account_book` (家計簿)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `hab_seq` | `serial` | 家計簿Seq |
| `actual_date` | `DATE` | 実績日 |
| `category_cd` | `INTEGER` | カテゴリコード |
| `store_cd` | `INTEGER` | 店コード |
| `amount` | `numeric(8, 0)` | 金額 |
| `remarks` | `text` | 備考 |
| `linking_data_type` | `INTEGER` | 連携データタイプ |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

### `budget` (予算)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `budget_seq` | `serial` | 予算Seq |
| `budget_year_month` | `DATE` | 予算年月 |
| `category_cd` | `INTEGER` | カテゴリコード |
| `budget_amount` | `numeric(8, 0)` | 予算金額 |
| `budget_remarks` | `text` | 予算備考 |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

### `asset` (資産)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `asset_year_month` | `DATE` | 資産年月 |
| `deposit_account_cd` | `CHARACTER VARYING(3)` | 預金口座コード |
| `asset_amount` | `numeric(8, 0)` | 資産金額 |
| `asset_remarks` | `text` | 資産備考 |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

### `investment_pl` (投資損益)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `investment_pl_year_month` | `DATE` | 投資損益年月 |
| `deposit_account_cd` | `CHARACTER VARYING(3)` | 預金口座コード |
| `investment_pl_amount` | `numeric(8, 0)` | 投資損益金額 |
| `investment_pl_remarks` | `text` | 投資損益備考 |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

### `fixed_cost` (固定費)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `fixed_cost_seq` | `serial` | 固定費SEQ |
| `category_cd` | `INTEGER` | カテゴリコード |
| `fixed_cost_details` | `TEXT` | 固定費内訳 |
| `display_order` | `INTEGER` | 表示順 |
| `remarks` | `TEXT` | 備考 |
| `january` | `numeric(8, 0)` | 1月 |
| `february` | `numeric(8, 0)` | 2月 |
| `march` | `numeric(8, 0)` | 3月 |
| `april` | `numeric(8, 0)` | 4月 |
| `may` | `numeric(8, 0)` | 5月 |
| `june` | `numeric(8, 0)` | 6月 |
| `july` | `numeric(8, 0)` | 7月 |
| `august` | `numeric(8, 0)` | 8月 |
| `september` | `numeric(8, 0)` | 9月 |
| `october` | `numeric(8, 0)` | 10月 |
| `november` | `numeric(8, 0)` | 11月 |
| `december` | `numeric(8, 0)` | 12月 |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

### `category_mapping_config` (連携データカテゴリ振り分け設定)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `category_mapping_config_seq` | `serial` | 連携データカテゴリ振り分け設定Seq |
| `mapping_key_nm` | `text` | 振り分けキー名称 |
| `category_cd` | `integer` | カテゴリコード |
| `linking_excluded_flg` | `CHARACTER VARYING(1)` | 連携対象外フラグ |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

### `recurring_config` (繰り返し入力設定)

| カラム名 | データ型 | 説明 |
| --- | --- | --- |
| `recurring_config_seq` | `serial` | 繰り返し入力設定Seq |
| `recurring_nm` | `text` | 繰り返し入力名称 |
| `execution_interval_type` | `CHARACTER VARYING(1)` | 実行間隔種別（1：毎月1日） |
| `category_cd` | `INTEGER` | カテゴリコード |
| `store_cd` | `INTEGER` | 店コード |
| `amount` | `numeric(8, 0)` | 金額 |
| `remarks` | `text` | 備考 |
| `linking_data_type` | `INTEGER` | 連携データタイプ（固定値0：繰り返し入力設定） |
| `active_flg` | `CHARACTER VARYING(1)` | 有効フラグ |
| `created_at` | `TIMESTAMP` | 登録日時 |
| `updated_at` | `TIMESTAMP` | 更新日時 |

## ビュー定義

### `v_household_account_book` (家計簿ビュー)

| カラム名 | 説明 |
| --- | --- |
| `hab_seq` | 家計簿Seq |
| `year` | 年 |
| `actual_date` | 実績日 |
| `category_type` | カテゴリ種別（1：収入、2：支出） |
| `display_category_type_nm` | 表示用カテゴリ種別名（1：収入、2：支出（固定費）、3：支出（変動費）） |
| `category_cd` | カテゴリコード |
| `category_nm` | カテゴリ名 |
| `display_category_nm` | 表示用カテゴリ名 |
| `display_income_category_nm` | 表示用カテゴリ名（収入） |
| `store_cd` | 店コード |
| `store_nm` | 店名 |
| `amount` | 金額 |
| `display_amount` | 表示用金額（支出は負数） |
| `remarks` | 備考 |
| `linking_data_type` | 連携データタイプ |

### `v_asset` (資産ビュー)

| カラム名 | 説明 |
| --- | --- |
| `year` | 年 |
| `asset_year_month` | 資産年月 |
| `deposit_account_type` | 預金口座種別（0：生活費・貯金口座、1：投資口座） |
| `display_deposit_account_type_nm` | 表示用預金口座種別名（0：生活費・貯金口座、1：投資口座） |
| `deposit_account_cd` | 預金口座コード |
| `deposit_account_nm` | 預金口座名 |
| `display_deposit_account_nm` | 表示用預金口座名 |
| `asset_amount` | 資産金額 |
| `asset_remarks` | 資産備考 |

### `v_investment_pl` (投資損益ビュー)

| カラム名 | 説明 |
| --- | --- |
| `year` | 年 |
| `investment_pl_year_month` | 投資損益年月 |
| `deposit_account_type` | 預金口座種別（0：生活費・貯金口座、1：投資口座） |
| `display_deposit_account_type_nm` | 表示用預金口座種別名（0：生活費・貯金口座、1：投資口座） |
| `deposit_account_cd` | 預金口座コード |
| `deposit_account_nm` | 預金口座名 |
| `display_deposit_account_nm` | 表示用預金口座名 |
| `investment_pl_amount` | 投資損益金額 |
| `investment_pl_remarks` | 投資損益備考 |

### `v_fixed_cost` (固定費ビュー)

| カラム名 | 説明 |
| --- | --- |
| `display_category_nm` | カテゴリ名 |
| `fixed_cost_details` | 固定費内訳 |
| `remarks` | 備考 |
| `month` | 月 |
| `fixed_cost_amount` | 固定費金額 |

### `v_month_calen` (年月カレンダービュー)

| カラム名 | 説明 |
| --- | --- |
| `ym` | 年月 |