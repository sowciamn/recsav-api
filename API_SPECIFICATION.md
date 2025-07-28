
# API仕様書

## 概要

このドキュメントは、家計簿WebアプリのAPI仕様を定義します。

## エンドポイント一覧

| メソッド | パス | 説明 |
| --- | --- | --- |
| GET | /api/assets | 資産の一覧を取得する |
| POST | /api/assets | 資産を登録する |
| PUT | /api/assets/{assetYearMonth}/{depositAccountCd} | 資産を更新する |
| DELETE | /api/assets/{assetYearMonth}/{depositAccountCd} | 資産を削除する |
| GET | /api/categories | カテゴリの一覧を取得する |
| POST | /api/categories | カテゴリを登録する |
| PUT | /api/categories/{categoryCd} | カテゴリを更新する |
| DELETE | /api/categories/{categoryCd} | カテゴリを削除する |
| GET | /api/dashboard | ダッシュボードの情報を取得する |
| GET | /api/deposit-accounts | 預金口座の一覧を取得する |
| POST | /api/deposit-accounts | 預金口座を登録する |
| PUT | /api/deposit-accounts/{depositAccountCd} | 預金口座を更新する |
| DELETE | /api/deposit-accounts/{depositAccountCd} | 預金口座を削除する |
| GET | /api/fixed-costs | 固定費の一覧を取得する |
| POST | /api/fixed-costs | 固定費を登録する |
| PUT | /api/fixed-costs/{fixedCostSeq} | 固定費を更新する |
| DELETE | /api/fixed-costs/{fixedCostSeq} | 固定費を削除する |
| GET | /api/household-account-books | 家計簿の一覧を取得する |
| POST | /api/household-account-books | 家計簿を登録する |
| PUT | /api/household-account-books/{habSeq} | 家計簿を更新する |
| DELETE | /api/household-account-books/{habSeq} | 家計簿を削除する |
| GET | /api/investment-pls | 投資損益の一覧を取得する |
| POST | /api/investment-pls | 投資損益を登録する |
| PUT | /api/investment-pls/{investmentPlYearMonth}/{depositAccountCd} | 投資損益を更新する |
| DELETE | /api/investment-pls/{investmentPlYearMonth}/{depositAccountCd} | 投資損益を削除する |
| GET | /api/stores | 店の一覧を取得する |
| POST | /api/stores | 店を登録する |
| PUT | /api/stores/{storeCd} | 店を更新する |
| DELETE | /api/stores/{storeCd} | 店を削除する |
| GET | /api/category-mapping-configs | 連携データカテゴリ振り分け設定の一覧を取得する |
| POST | /api/category-mapping-configs | 連携データカテゴリ振り分け設定を登録する |
| PUT | /api/category-mapping-configs/{categoryMappingConfigSeq} | 連携データカテゴリ振り分け設定を更新する |
| DELETE | /api/category-mapping-configs/{categoryMappingConfigSeq} | 連携データカテゴリ振り分け設定を削除する |
| GET | /api/recurring-configs | 繰り返し入力設定の一覧を取得する |
| POST | /api/recurring-configs | 繰り返し入力設定を登録する |
| PUT | /api/recurring-configs/{recurringConfigSeq} | 繰り返し入力設定を更新する |
| DELETE | /api/recurring-configs/{recurringConfigSeq} | 繰り返し入力設定を削除する |

## API詳細

### 資産 (Asset)

#### GET /api/assets

資産の一覧を取得します。

- **クエリパラメータ**
  - `yearMonth` (string, optional): 年月 (例: 2023-07)

- **レスポンス (200 OK)**

```json
[
  {
    "assetYearMonth": "2023-07-01",
    "depositAccountCd": "001",
    "assetAmount": 1000000,
    "assetRemarks": "給与振込",
    "createdAt": "2023-07-09T12:00:00Z",
    "updatedAt": "2023-07-09T12:00:00Z"
  }
]
```

#### POST /api/assets

新しい資産を登録します。

- **リクエスト**

```json
{
  "assetYearMonth": "2023-08-01",
  "depositAccountCd": "001",
  "assetAmount": 1200000,
  "assetRemarks": "ボーナス"
}
```

- **レスポンス (201 Created)**

```json
{
  "assetYearMonth": "2023-08-01",
  "depositAccountCd": "001",
  "assetAmount": 1200000,
  "assetRemarks": "ボーナス",
  "createdAt": "2023-07-10T10:00:00Z",
  "updatedAt": "2023-07-10T10:00:00Z"
}
```

#### PUT /api/assets/{assetYearMonth}/{depositAccountCd}

指定した資産を更新します。

- **パスパラメータ**
  - `assetYearMonth` (string, required): 資産年月 (例: 2023-07-01)
  - `depositAccountCd` (string, required): 預金口座コード

- **リクエスト**

```json
{
  "assetAmount": 1100000,
  "assetRemarks": "臨時収入"
}
```

- **レスポンス (200 OK)**

```json
{
  "assetYearMonth": "2023-07-01",
  "depositAccountCd": "001",
  "assetAmount": 1100000,
  "assetRemarks": "臨時収入",
  "createdAt": "2023-07-09T12:00:00Z",
  "updatedAt": "2023-07-10T11:00:00Z"
}
```

#### DELETE /api/assets/{assetYearMonth}/{depositAccountCd}

指定した資産を削除します。

- **パスパラメータ**
  - `assetYearMonth` (string, required): 資産年月 (例: 2023-07-01)
  - `depositAccountCd` (string, required): 預金口座コード

- **レスポンス (204 No Content)**

### カテゴリ (Category)

#### GET /api/categories

カテゴリの一覧を取得します。

- **レスポンス (200 OK)**

```json
[
  {
    "categoryCd": 1,
    "categoryNm": "給与",
    "categoryType": "1",
    "displayOrder": 1
  }
]
```

#### POST /api/categories

新しいカテゴリを登録します。

- **リクエスト**

```json
{
  "categoryNm": "食費",
  "categoryType": "2",
  "displayOrder": 2
}
```

- **レスポンス (201 Created)**

```json
{
  "categoryCd": 2,
  "categoryNm": "食費",
  "categoryType": "2",
  "displayOrder": 2
}
```

#### PUT /api/categories/{categoryCd}

指定したカテゴリを更新します。

- **パスパラメータ**
  - `categoryCd` (integer, required): カテゴリコード

- **リクエスト**

```json
{
  "categoryNm": "外食費",
  "displayOrder": 3
}
```

- **レスポンス (200 OK)**

```json
{
  "categoryCd": 2,
  "categoryNm": "外食費",
  "categoryType": "2",
  "displayOrder": 3
}
```

#### DELETE /api/categories/{categoryCd}

指定したカテゴリを削除します。

- **パスパラメータ**
  - `categoryCd` (integer, required): カテゴリコード

- **レスポンス (204 No Content)**

### ダッシュボード (Dashboard)

#### GET /api/dashboard

ダッシュボードの情報を取得します。

- **クエリパラメータ**
  - `yearMonth` (string, required): 年月 (例: 2023-07)

- **レスポンス (200 OK)**

```json
{
  "summary": {
    "depositTotal": 500000,
    "expenseTotal": 300000,
    "balance": 200000
  },
  "categoryExpenses": [
    {
      "categoryNm": "食費",
      "amount": 100000
    },
    {
      "categoryNm": "家賃",
      "amount": 150000
    }
  ]
}
```

### 預金口座 (Deposit Account)

#### GET /api/deposit-accounts

預金口座の一覧を取得します。

- **レスポンス (200 OK)**

```json
[
  {
    "depositAccountCd": "001",
    "depositAccountNm": "メインバンク",
    "depositUsage": "生活費用",
    "investmentAccountFlg": "0",
    "createdAt": "2023-07-09T12:00:00Z",
    "updatedAt": "2023-07-09T12:00:00Z"
  }
]
```

#### POST /api/deposit-accounts

新しい預金口座を登録します。

- **リクエスト**

```json
{
  "depositAccountCd": "002",
  "depositAccountNm": "サブバンク",
  "depositUsage": "貯金用",
  "investmentAccountFlg": "0"
}
```

- **レスポンス (201 Created)**

```json
{
  "depositAccountCd": "002",
  "depositAccountNm": "サブバンク",
  "depositUsage": "貯金用",
  "investmentAccountFlg": "0",
  "createdAt": "2023-07-10T10:00:00Z",
  "updatedAt": "2023-07-10T10:00:00Z"
}
```

#### PUT /api/deposit-accounts/{depositAccountCd}

指定した預金口座を更新します。

- **パスパラメータ**
  - `depositAccountCd` (string, required): 預金口座コード

- **リクエスト**

```json
{
  "depositAccountNm": "投資用バンク",
  "investmentAccountFlg": "1"
}
```

- **レスポンス (200 OK)**

```json
{
  "depositAccountCd": "002",
  "depositAccountNm": "投資用バンク",
  "depositUsage": "貯金用",
  "investmentAccountFlg": "1",
  "createdAt": "2023-07-10T10:00:00Z",
  "updatedAt": "2023-07-10T11:00:00Z"
}
```

#### DELETE /api/deposit-accounts/{depositAccountCd}

指定した預金口座を削除します。

- **パスパラメータ**
  - `depositAccountCd` (string, required): 預金口座コード

- **レスポンス (204 No Content)**

### 固定費 (Fixed Cost)

#### GET /api/fixed-costs

固定費の一覧を取得します。

- **レスポンス (200 OK)**

```json
[
  {
    "fixedCostSeq": 1,
    "categoryCd": 3,
    "fixedCostDetails": "家賃",
    "displayOrder": 1,
    "remarks": "毎月25日引き落とし",
    "january": 80000,
    "february": 80000,
    "march": 80000,
    "april": 80000,
    "may": 80000,
    "june": 80000,
    "july": 80000,
    "august": 80000,
    "september": 80000,
    "october": 80000,
    "november": 80000,
    "december": 80000,
    "createdAt": "2023-07-09T12:00:00Z",
    "updatedAt": "2023-07-09T12:00:00Z"
  }
]
```

#### POST /api/fixed-costs

新しい固定費を登録します。

- **リクエスト**

```json
{
  "categoryCd": 4,
  "fixedCostDetails": "光熱費",
  "displayOrder": 2,
  "remarks": "",
  "january": 10000,
  "february": 12000,
  "march": 11000,
  "april": 9000,
  "may": 8000,
  "june": 7000,
  "july": 8000,
  "august": 9000,
  "september": 10000,
  "october": 11000,
  "november": 12000,
  "december": 13000
}
```

- **レスポンス (201 Created)**

```json
{
  "fixedCostSeq": 2,
  "categoryCd": 4,
  "fixedCostDetails": "光熱費",
  "displayOrder": 2,
  "remarks": "",
  "january": 10000,
  "february": 12000,
  "march": 11000,
  "april": 9000,
  "may": 8000,
  "june": 7000,
  "july": 8000,
  "august": 9000,
  "september": 10000,
  "october": 11000,
  "november": 12000,
  "december": 13000,
  "createdAt": "2023-07-10T10:00:00Z",
  "updatedAt": "2023-07-10T10:00:00Z"
}
```

#### PUT /api/fixed-costs/{fixedCostSeq}

指定した固定費を更新します。

- **パスパラメータ**
  - `fixedCostSeq` (long, required): 固定費SEQ

- **リクエスト**

```json
{
  "fixedCostDetails": "家賃（更新）",
  "remarks": "備考更新"
}
```

- **レスポンス (200 OK)**

```json
{
  "fixedCostSeq": 1,
  "categoryCd": 3,
  "fixedCostDetails": "家賃（更新）",
  "displayOrder": 1,
  "remarks": "備考更新",
  "january": 80000,
  "february": 80000,
  "march": 80000,
  "april": 80000,
  "may": 80000,
  "june": 80000,
  "july": 80000,
  "august": 80000,
  "september": 80000,
  "october": 80000,
  "november": 80000,
  "december": 80000,
  "createdAt": "2023-07-09T12:00:00Z",
  "updatedAt": "2023-07-10T11:00:00Z"
}
```

#### DELETE /api/fixed-costs/{fixedCostSeq}

指定した固定費を削除します。

- **パスパラメータ**
  - `fixedCostSeq` (long, required): 固定費SEQ

- **レスポンス (204 No Content)**

### 家計簿 (Household Account Book)

#### GET /api/household-account-books

家計簿の一覧を取得します。

- **クエリパラメータ**
  - `yearMonth` (string, optional): 年月 (例: 2023-07)

- **レスポンス (200 OK)**

```json
[
  {
    "habSeq": 1,
    "actualDate": "2023-07-09",
    "categoryCd": 2,
    "storeCd": 1,
    "amount": 3000,
    "remarks": "夕食の買い物",
    "linkingDataType": null,
    "createdAt": "2023-07-09T18:00:00Z",
    "updatedAt": "2023-07-09T18:00:00Z"
  }
]
```

#### POST /api/household-account-books

新しい家計簿を登録します。

- **リクエスト**

```json
{
  "actualDate": "2023-07-10",
  "categoryCd": 2,
  "storeCd": 2,
  "amount": 1500,
  "remarks": "昼食"
}
```

- **レスポンス (201 Created)**

```json
{
  "habSeq": 2,
  "actualDate": "2023-07-10",
  "categoryCd": 2,
  "storeCd": 2,
  "amount": 1500,
  "remarks": "昼食",
  "linkingDataType": null,
  "createdAt": "2023-07-10T12:00:00Z",
  "updatedAt": "2023-07-10T12:00:00Z"
}
```

#### PUT /api/household-account-books/{habSeq}

指定した家計簿を更新します。

- **パスパラメータ**
  - `habSeq` (long, required): 家計簿SEQ

- **リクエスト**

```json
{
  "amount": 2000,
  "remarks": "昼食（変更）"
}
```

- **レスポンス (200 OK)**

```json
{
  "habSeq": 2,
  "actualDate": "2023-07-10",
  "categoryCd": 2,
  "storeCd": 2,
  "amount": 2000,
  "remarks": "昼食（変更）",
  "linkingDataType": null,
  "createdAt": "2023-07-10T12:00:00Z",
  "updatedAt": "2023-07-10T13:00:00Z"
}
```

#### DELETE /api/household-account-books/{habSeq}

指定した家計簿を削除します。

- **パスパラメータ**
  - `habSeq` (long, required): 家計簿SEQ

- **レスポンス (204 No Content)**

### 投資損益 (Investment P/L)

#### GET /api/investment-pls

投資損益の一覧を取得します。

- **クエリパラメータ**
  - `yearMonth` (string, optional): 年月 (例: 2023-07)

- **レスポンス (200 OK)**

```json
[
  {
    "investmentPlYearMonth": "2023-07-01",
    "depositAccountCd": "002",
    "investmentPlAmount": 50000,
    "investmentPlRemarks": "株式評価益",
    "createdAt": "2023-07-09T12:00:00Z",
    "updatedAt": "2023-07-09T12:00:00Z"
  }
]
```

#### POST /api/investment-pls

新しい投資損益を登録します。

- **リクエスト**

```json
{
  "investmentPlYearMonth": "2023-08-01",
  "depositAccountCd": "002",
  "investmentPlAmount": -10000,
  "investmentPlRemarks": "株式評価損"
}
```

- **レスポンス (201 Created)**

```json
{
  "investmentPlYearMonth": "2023-08-01",
  "depositAccountCd": "002",
  "investmentPlAmount": -10000,
  "investmentPlRemarks": "株式評価損",
  "createdAt": "2023-07-10T10:00:00Z",
  "updatedAt": "2023-07-10T10:00:00Z"
}
```

#### PUT /api/investment-pls/{investmentPlYearMonth}/{depositAccountCd}

指定した投資損益を更新します。

- **パスパラメータ**
  - `investmentPlYearMonth` (string, required): 投資損益年月 (例: 2023-07-01)
  - `depositAccountCd` (string, required): 預金口座コード

- **リクエスト**

```json
{
  "investmentPlAmount": 60000,
  "investmentPlRemarks": "備考更新"
}
```

- **レスポンス (200 OK)**

```json
{
  "investmentPlYearMonth": "2023-07-01",
  "depositAccountCd": "002",
  "investmentPlAmount": 60000,
  "investmentPlRemarks": "備考更新",
  "createdAt": "2023-07-09T12:00:00Z",
  "updatedAt": "2023-07-10T11:00:00Z"
}
```

#### DELETE /api/investment-pls/{investmentPlYearMonth}/{depositAccountCd}

指定した投資損益を削除します。

- **パスパラメータ**
  - `investmentPlYearMonth` (string, required): 投資損益年月 (例: 2023-07-01)
  - `depositAccountCd` (string, required): 預金口座コード

- **レスポンス (204 No Content)**

### 店 (Store)

#### GET /api/stores

店の一覧を取得します。

- **レスポンス (200 OK)**

```json
[
  {
    "storeCd": 1,
    "storeNm": "スーパーA",
    "storeAddress": "東京都新宿区…",
    "createdAt": "2023-07-09T12:00:00Z",
    "updatedAt": "2023-07-09T12:00:00Z"
  }
]
```

#### POST /api/stores

新しい店を登録します。

- **リクエスト**

```json
{
  "storeNm": "コンビニB",
  "storeAddress": "東京都渋谷区…"
}
```

- **レスポンス (201 Created)**

```json
{
  "storeCd": 2,
  "storeNm": "コンビニB",
  "storeAddress": "東京都渋谷区…",
  "createdAt": "2023-07-10T10:00:00Z",
  "updatedAt": "2023-07-10T10:00:00Z"
}
```

#### PUT /api/stores/{storeCd}

指定した店を更新します。

- **パスパラメータ**
  - `storeCd` (integer, required): 店コード

- **リクエスト**

```json
{
  "storeNm": "ドラッグストアC",
  "storeAddress": "東京都豊島区…"
}
```

- **レスポンス (200 OK)**

```json
{
  "storeCd": 2,
  "storeNm": "ドラッグストアC",
  "storeAddress": "東京都豊島区…",
  "createdAt": "2023-07-10T10:00:00Z",
  "updatedAt": "2023-07-10T11:00:00Z"
}
```

#### DELETE /api/stores/{storeCd}

指定した店を削除します。

- **パスパラメータ**
  - `storeCd` (integer, required): 店コード

- **レスポンス (204 No Content)**

### 連携データカテゴリ振り分け設定 (Category Mapping Config)

#### GET /api/category-mapping-configs

連携データカテゴリ振り分け設定の一覧を取得します。

- **レスポンス (200 OK)**

```json
[
  {
    "categoryMappingConfigSeq": 1,
    "mappingKeyNm": "キー",
    "categoryCd": 1,
    "linkingExcludedFlg": "0",
    "createdAt": "2023-07-09T12:00:00Z",
    "updatedAt": "2023-07-09T12:00:00Z"
  }
]
```

#### POST /api/category-mapping-configs

新しい連携データカテゴリ振り分け設定を登録します。

- **リクエスト**

```json
{
  "mappingKeyNm": "キー2",
  "categoryCd": 2,
  "linkingExcludedFlg": "1"
}
```

- **レスポンス (201 Created)**

```json
{
  "categoryMappingConfigSeq": 2,
  "mappingKeyNm": "キー2",
  "categoryCd": 2,
  "linkingExcludedFlg": "1",
  "createdAt": "2023-07-10T10:00:00Z",
  "updatedAt": "2023-07-10T10:00:00Z"
}
```

#### PUT /api/category-mapping-configs/{categoryMappingConfigSeq}

指定した連携データカテゴリ振り分け設定を更新します。

- **パスパラメータ**
  - `categoryMappingConfigSeq` (integer, required): 連携データカテゴリ振り分け設定SEQ

- **リクエスト**

```json
{
  "mappingKeyNm": "キー更新",
  "categoryCd": 3,
  "linkingExcludedFlg": "0"
}
```

- **レスポンス (200 OK)**

```json
{
  "categoryMappingConfigSeq": 1,
  "mappingKeyNm": "キー更新",
  "categoryCd": 3,
  "linkingExcludedFlg": "0",
  "createdAt": "2023-07-09T12:00:00Z",
  "updatedAt": "2023-07-10T11:00:00Z"
}
```

#### DELETE /api/category-mapping-configs/{categoryMappingConfigSeq}

指定した連携データカテゴリ振り分け設定を削除します。

- **パスパラメータ**
  - `categoryMappingConfigSeq` (integer, required): 連携データカテゴリ振り分け設定SEQ

- **レスポンス (204 No Content)**

### 繰り返し入力設定 (Recurring Config)

#### GET /api/recurring-configs

繰り返し入力設定の一覧を取得します。

- **レスポンス (200 OK)**

```json
[
  {
    "recurringConfigSeq": 1,
    "recurringNm": "家賃",
    "executionIntervalType": "1",
    "categoryCd": 1,
    "storeCd": 1,
    "amount": 80000,
    "remarks": "毎月1日",
    "linkingDataType": 0,
    "activeFlg": "1",
    "createdAt": "2023-07-09T12:00:00Z",
    "updatedAt": "2023-07-09T12:00:00Z"
  }
]
```

#### POST /api/recurring-configs

新しい繰り返し入力設定を登録します。

- **リクエスト**

```json
{
  "recurringNm": "サブスク",
  "executionIntervalType": "1",
  "categoryCd": 2,
  "storeCd": 2,
  "amount": 1000,
  "remarks": "毎月1日",
  "linkingDataType": 0,
  "activeFlg": "1"
}
```

- **レスポンス (201 Created)**

```json
{
  "recurringConfigSeq": 2,
  "recurringNm": "サブスク",
  "executionIntervalType": "1",
  "categoryCd": 2,
  "storeCd": 2,
  "amount": 1000,
  "remarks": "毎月1日",
  "linkingDataType": 0,
  "activeFlg": "1",
  "createdAt": "2023-07-10T10:00:00Z",
  "updatedAt": "2023-07-10T10:00:00Z"
}
```

#### PUT /api/recurring-configs/{recurringConfigSeq}

指定した繰り返し入力設定を更新します。

- **パスパラメータ**
  - `recurringConfigSeq` (integer, required): 繰り返し入力設定SEQ

- **リクエスト**

```json
{
  "recurringNm": "家賃（更新）",
  "amount": 85000
}
```

- **レスポンス (200 OK)**

```json
{
  "recurringConfigSeq": 1,
  "recurringNm": "家賃（更新）",
  "executionIntervalType": "1",
  "categoryCd": 1,
  "storeCd": 1,
  "amount": 85000,
  "remarks": "毎月1日",
  "linkingDataType": 0,
  "activeFlg": "1",
  "createdAt": "2023-07-09T12:00:00Z",
  "updatedAt": "2023-07-10T11:00:00Z"
}
```

#### DELETE /api/recurring-configs/{recurringConfigSeq}

指定した繰り返し入力設定を削除します。

- **パスパラメータ**
  - `recurringConfigSeq` (integer, required): 繰り返し入力設定SEQ

- **レスポンス (204 No Content)**

