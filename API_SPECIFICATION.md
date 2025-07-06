# API仕様書

## 1. 資産 (Asset)

### `GET /api/assets`

- **説明:** 全ての資産情報を取得します。
- **レスポンス:** `200 OK`
  ```json
  [
    {
      "assetYearMonth": "2023-01",
      "depositAccountCd": "001",
      "assetAmount": 1000000,
      "assetRemarks": "給与振込"
    }
  ]
  ```

### `POST /api/assets`

- **説明:** 新しい資産情報を登録します。
- **リクエストボディ:**
  ```json
  {
    "assetYearMonth": "2023-02",
    "depositAccountCd": "001",
    "assetAmount": 1200000,
    "assetRemarks": "ボーナス"
  }
  ```
- **レスポンス:** `201 Created`

### `PUT /api/assets/{assetYearMonth}/{depositAccountCd}`

- **説明:** 既存の資産情報を更新します。
- **パスパラメータ:**
  - `assetYearMonth`: 資産年月 (例: `2023-01`)
  - `depositAccountCd`: 預金口座コード (例: `001`)
- **リクエストボディ:**
  ```json
  {
    "assetAmount": 1100000,
    "assetRemarks": "給与振込（修正）"
  }
  ```
- **レスポンス:** `200 OK`

### `DELETE /api/assets/{assetYearMonth}/{depositAccountCd}`

- **説明:** 既存の資産情報を削除します。
- **パスパラメータ:**
  - `assetYearMonth`: 資産年月 (例: `2023-01`)
  - `depositAccountCd`: 預金口座コード (例: `001`)
- **レスポンス:** `204 No Content`

## 2. カテゴリ (Category)

### `GET /api/categories`

- **説明:** 全てのカテゴリ情報を取得します。
- **レスポンス:** `200 OK`

### `POST /api/categories`

- **説明:** 新しいカテゴリ情報を登録します。
- **レスポンス:** `201 Created`

### `PUT /api/categories/{categoryCd}`

- **説明:** 既存のカテゴリ情報を更新します。
- **レスポンス:** `200 OK`

### `DELETE /api/categories/{categoryCd}`

- **説明:** 既存のカテゴリ情報を削除します。
- **レスポンス:** `204 No Content`

## 3. ダッシュボード (Dashboard)

### `GET /api/dashboard`

- **説明:** ダッシュボードの情報を取得します。
- **リクエストパラメータ:**
  - `yearMonth`: 年月 (例: `2023-01`)
- **レスポンス:** `200 OK`

## 4. 預金口座 (Deposit Account)

### `GET /api/deposit-accounts`

- **説明:** 全ての預金口座情報を取得します。
- **レスポンス:** `200 OK`

### `POST /api/deposit-accounts`

- **説明:** 新しい預金口座情報を登録します。
- **レスポンス:** `201 Created`

### `PUT /api/deposit-accounts/{depositAccountCd}`

- **説明:** 既存の預金口座情報を更新します。
- **レスポンス:** `200 OK`

### `DELETE /api/deposit-accounts/{depositAccountCd}`

- **説明:** 既存の預金口座情報を削除します。
- **レスポンス:** `204 No Content`

## 5. 固定費 (Fixed Cost)

### `GET /api/fixed-costs`

- **説明:** 全ての固定費情報を取得します。
- **レスポンス:** `200 OK`

### `POST /api/fixed-costs`

- **説明:** 新しい固定費情報を登録します。
- **レスポンス:** `201 Created`

### `PUT /api/fixed-costs/{fixedCostSeq}`

- **説明:** 既存の固定費情報を更新します。
- **レスポンス:** `200 OK`

### `DELETE /api/fixed-costs/{fixedCostSeq}`

- **説明:** 既存の固定費情報を削除します。
- **レスポンス:** `204 No Content`

## 6. 家計簿 (Household Account Book)

### `GET /api/household-account-books`

- **説明:** 全ての家計簿情報を取得します。
- **レスポンス:** `200 OK`

### `POST /api/household-account-books`

- **説明:** 新しい家計簿情報を登録します。
- **レスポンス:** `201 Created`

### `PUT /api/household-account-books/{habSeq}`

- **説明:** 既存の家計簿情報を更新します。
- **レスポンス:** `200 OK`

### `DELETE /api/household-account-books/{habSeq}`

- **説明:** 既存の家計簿情報を削除します。
- **レスポンス:** `204 No Content`

## 7. 投資損益 (Investment PL)

### `GET /api/investment-pls`

- **説明:** 全ての投資損益情報を取得します。
- **レスポンス:** `200 OK`

### `POST /api/investment-pls`

- **説明:** 新しい投資損益情報を登録します。
- **レスポンス:** `201 Created`

### `PUT /api/investment-pls/{investmentPlYearMonth}/{depositAccountCd}`

- **説明:** 既存の投資損益情報を更新します。
- **レスポンス:** `200 OK`

### `DELETE /api/investment-pls/{investmentPlYearMonth}/{depositAccountCd}`

- **説明:** 既存の投資損益情報を削除します。
- **レスポンス:** `204 No Content`

## 8. 店舗 (Store)

### `GET /api/stores`

- **説明:** 全ての店舗情報を取得します。
- **レスポンス:** `200 OK`

### `POST /api/stores`

- **説明:** 新しい店舗情報を登録します。
- **レスポンス:** `201 Created`

### `PUT /api/stores/{storeCd}`

- **説明:** 既存の店舗情報を更新します。
- **レスポンス:** `200 OK`

### `DELETE /api/stores/{storeCd}`

- **説明:** 既存の店舗情報を削除します。
- **レスポンス:** `204 No Content`
