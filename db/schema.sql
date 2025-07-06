/**************************************/
/*             CREATE FUNCTION           */
/**************************************/
CREATE OR REPLACE FUNCTION set_update_time() RETURNS trigger AS '
  begin
    new.updated_at := ''now'';
    return new;
  end;
' LANGUAGE plpgsql;

/**************************************/
/*             CREATE TABLE           */
/**************************************/

CREATE TABLE if_zaim (
  if_zaim_seq serial,
  if_zaim_date DATE,
  if_zaim_method text,
  if_zaim_category text,
  if_zaim_category_detail text,
  if_zaim_payment_source text,
  if_zaim_deposit text,
  if_zaim_item text,
  if_zaim_remarks text,
  if_zaim_store text,
  if_zaim_currency text,
  if_zaim_income_amount numeric(8, 0),
  if_zaim_expense_amount numeric(8, 0),
  if_zaim_transfer_amount numeric(8, 0),
  if_zaim_balance_amount numeric(8, 0),
  if_zaim_before_amount numeric(8, 0),
  if_zaim_aggregation_settings text,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (if_zaim_seq)
);

COMMENT ON TABLE if_zaim IS 'Zaimインターフェース';

COMMENT ON COLUMN if_zaim.if_zaim_seq IS 'ZaimインターフェースSeq';

COMMENT ON COLUMN if_zaim.if_zaim_date IS '日付';

COMMENT ON COLUMN if_zaim.if_zaim_method IS '方法';

COMMENT ON COLUMN if_zaim.if_zaim_category IS 'カテゴリ';

COMMENT ON COLUMN if_zaim.if_zaim_category_detail IS 'カテゴリの内訳';

COMMENT ON COLUMN if_zaim.if_zaim_payment_source IS '支払元';

COMMENT ON COLUMN if_zaim.if_zaim_deposit IS '入金先';

COMMENT ON COLUMN if_zaim.if_zaim_item IS '品目';

COMMENT ON COLUMN if_zaim.if_zaim_remarks IS 'メモ';

COMMENT ON COLUMN if_zaim.if_zaim_store IS 'お店';

COMMENT ON COLUMN if_zaim.if_zaim_currency IS '通貨';

COMMENT ON COLUMN if_zaim.if_zaim_income_amount IS '収入';

COMMENT ON COLUMN if_zaim.if_zaim_expense_amount IS '支出';

COMMENT ON COLUMN if_zaim.if_zaim_transfer_amount IS '振替';

COMMENT ON COLUMN if_zaim.if_zaim_balance_amount IS '残高調整';

COMMENT ON COLUMN if_zaim.if_zaim_before_amount IS '通貨変換前の金額';

COMMENT ON COLUMN if_zaim.if_zaim_aggregation_settings IS '集計の設定';

COMMENT ON COLUMN if_zaim.created_at IS '登録日時';

COMMENT ON COLUMN if_zaim.updated_at IS '更新日時';

CREATE TRIGGER if_zaim_upd_trigger BEFORE UPDATE ON public.if_zaim FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TABLE category (
  category_cd serial,
  category_nm CHARACTER VARYING(100),
  category_type CHARACTER VARYING(2),
  display_order INTEGER,
  fixed_category_flg CHARACTER VARYING(1),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (category_cd)
);

COMMENT ON TABLE category IS 'カテゴリ';

COMMENT ON COLUMN category.category_cd IS 'カテゴリコード';

COMMENT ON COLUMN category.category_nm IS 'カテゴリ名';

COMMENT ON COLUMN category.category_type IS 'カテゴリ種別（1：収入、2：支出）';

COMMENT ON COLUMN category.display_order IS '表示順';

COMMENT ON COLUMN category.fixed_category_flg IS '固定費カテゴリーフラグ';

COMMENT ON COLUMN category.created_at IS '登録日時';

COMMENT ON COLUMN category.updated_at IS '更新日時';

CREATE TRIGGER category_upd_trigger BEFORE UPDATE ON public.category FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TABLE store (
  store_cd serial,
  store_nm CHARACTER VARYING(100),
  store_address text,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (store_cd)
);

COMMENT ON TABLE store IS '店';

COMMENT ON COLUMN store.store_cd IS '店コード';

COMMENT ON COLUMN store.store_nm IS '店名';

COMMENT ON COLUMN store.store_address IS '店住所';

COMMENT ON COLUMN store.created_at IS '登録日時';

COMMENT ON COLUMN store.updated_at IS '更新日時';

CREATE TRIGGER store_upd_trigger BEFORE UPDATE ON public.store FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TABLE deposit_account (
  deposit_account_cd CHARACTER VARYING(3),
  deposit_account_nm CHARACTER VARYING(100),
  deposit_usage text,
  investment_account_flg CHARACTER VARYING(1) DEFAULT '0',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (deposit_account_cd)
);

COMMENT ON TABLE deposit_account IS '預金口座';

COMMENT ON COLUMN deposit_account.deposit_account_cd IS '預金口座コード';

COMMENT ON COLUMN deposit_account.deposit_account_nm IS '預金口座名';

COMMENT ON COLUMN deposit_account.deposit_usage IS '預金用途';

COMMENT ON COLUMN deposit_account.investment_account_flg IS '投資口座フラグ';

COMMENT ON COLUMN deposit_account.created_at IS '登録日時';

COMMENT ON COLUMN deposit_account.updated_at IS '更新日時';

CREATE TRIGGER deposit_account_upd_trigger BEFORE UPDATE ON public.deposit_account FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TABLE linking_data (
  linking_data_type serial,
  linking_data_nm CHARACTER VARYING(100),
  last_linking_date DATE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (linking_data_type)
);

COMMENT ON TABLE linking_data IS '連携データ';

COMMENT ON COLUMN linking_data.linking_data_type IS '連携データタイプ';

COMMENT ON COLUMN linking_data.linking_data_nm IS '連携データ名';

COMMENT ON COLUMN linking_data.last_linking_date IS '最終連携日';

COMMENT ON COLUMN linking_data.created_at IS '登録日時';

COMMENT ON COLUMN linking_data.updated_at IS '更新日時';

CREATE TRIGGER linking_data_upd_trigger BEFORE UPDATE ON public.linking_data FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TABLE household_account_book (
  hab_seq serial,
  actual_date DATE,
  category_cd INTEGER,
  store_cd INTEGER,
  amount numeric(8, 0),
  remarks text,
  linking_data_type INTEGER,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (hab_seq),
  FOREIGN KEY (category_cd) REFERENCES category(category_cd),
  FOREIGN KEY (store_cd) REFERENCES store(store_cd),
  FOREIGN KEY (linking_data_type) REFERENCES linking_data(linking_data_type)
);

COMMENT ON TABLE household_account_book IS '家計簿';

COMMENT ON COLUMN household_account_book.hab_seq IS '家計簿Seq';

COMMENT ON COLUMN household_account_book.actual_date IS '実績日';

COMMENT ON COLUMN household_account_book.category_cd IS 'カテゴリコード';

COMMENT ON COLUMN household_account_book.store_cd IS '店コード';

COMMENT ON COLUMN household_account_book.amount IS '金額';

COMMENT ON COLUMN household_account_book.remarks IS '備考';

COMMENT ON COLUMN household_account_book.linking_data_type IS '連携データタイプ';

COMMENT ON COLUMN household_account_book.created_at IS '登録日時';

COMMENT ON COLUMN household_account_book.updated_at IS '更新日時';

CREATE TRIGGER hab_upd_trigger BEFORE UPDATE ON public.household_account_book FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TABLE budget (
  budget_seq serial,
  budget_year_month DATE,
  category_cd INTEGER,
  budget_amount numeric(8, 0),
  budget_remarks text,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (budget_seq),
  FOREIGN KEY (category_cd) REFERENCES category(category_cd)
);

COMMENT ON TABLE budget IS '予算';

COMMENT ON COLUMN budget.budget_seq IS '予算Seq';

COMMENT ON COLUMN budget.budget_year_month IS '予算年月';

COMMENT ON COLUMN budget.category_cd IS 'カテゴリコード';

COMMENT ON COLUMN budget.budget_amount IS '予算金額';

COMMENT ON COLUMN budget.budget_remarks IS '予算備考';

COMMENT ON COLUMN budget.created_at IS '登録日時';

COMMENT ON COLUMN budget.updated_at IS '更新日時';

CREATE TRIGGER budget_upd_trigger BEFORE UPDATE ON public.budget FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TABLE asset (
  asset_year_month DATE,
  deposit_account_cd CHARACTER VARYING(3),
  asset_amount numeric(8, 0),
  asset_remarks text,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (asset_year_month, deposit_account_cd),
  FOREIGN KEY (deposit_account_cd) REFERENCES deposit_account(deposit_account_cd)
);

COMMENT ON TABLE asset IS '資産';

COMMENT ON COLUMN asset.asset_year_month IS '資産年月';

COMMENT ON COLUMN asset.deposit_account_cd IS '預金口座コード';

COMMENT ON COLUMN asset.asset_amount IS '資産金額';

COMMENT ON COLUMN asset.asset_remarks IS '資産備考';

COMMENT ON COLUMN asset.created_at IS '登録日時';

COMMENT ON COLUMN asset.updated_at IS '更新日時';

CREATE TRIGGER asset_upd_trigger BEFORE UPDATE ON public.asset FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TABLE investment_pl (
  investment_pl_year_month DATE,
  deposit_account_cd CHARACTER VARYING(3),
  investment_pl_amount numeric(8, 0),
  investment_pl_remarks text,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (investment_pl_year_month, deposit_account_cd),
  FOREIGN KEY (deposit_account_cd) REFERENCES deposit_account(deposit_account_cd)
);

COMMENT ON TABLE investment_pl IS '投資損益';

COMMENT ON COLUMN investment_pl.investment_pl_year_month IS '投資損益年月';

COMMENT ON COLUMN investment_pl.deposit_account_cd IS '預金口座コード';

COMMENT ON COLUMN investment_pl.investment_pl_amount IS '投資損益金額';

COMMENT ON COLUMN investment_pl.investment_pl_remarks IS '投資損益備考';

COMMENT ON COLUMN investment_pl.created_at IS '登録日時';

COMMENT ON COLUMN investment_pl.updated_at IS '更新日時';

CREATE TRIGGER investment_pl_upd_trigger BEFORE UPDATE ON public.investment_pl FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TABLE fixed_cost (
  fixed_cost_seq serial,
  category_cd INTEGER,
  fixed_cost_details TEXT,
  display_order INTEGER,
  remarks TEXT,
  january numeric(8, 0),
  february numeric(8, 0),
  march numeric(8, 0),
  april numeric(8, 0),
  may numeric(8, 0),
  june numeric(8, 0),
  july numeric(8, 0),
  august numeric(8, 0),
  september numeric(8, 0),
  october numeric(8, 0),
  november numeric(8, 0),
  december numeric(8, 0),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (fixed_cost_seq),
  FOREIGN KEY (category_cd) REFERENCES category(category_cd)
);

COMMENT ON TABLE fixed_cost IS '固定費';

COMMENT ON COLUMN fixed_cost.fixed_cost_seq IS '固定費SEQ';

COMMENT ON COLUMN fixed_cost.category_cd IS 'カテゴリコード';

COMMENT ON COLUMN fixed_cost.fixed_cost_details IS '固定費内訳';

COMMENT ON COLUMN fixed_cost.display_order IS '表示順';

COMMENT ON COLUMN fixed_cost.remarks IS '備考';

COMMENT ON COLUMN fixed_cost.january IS '1月';

COMMENT ON COLUMN fixed_cost.february IS '2月';

COMMENT ON COLUMN fixed_cost.march IS '3月';

COMMENT ON COLUMN fixed_cost.april IS '4月';

COMMENT ON COLUMN fixed_cost.may IS '5月';

COMMENT ON COLUMN fixed_cost.june IS '6月';

COMMENT ON COLUMN fixed_cost.july IS '7月';

COMMENT ON COLUMN fixed_cost.august IS '8月';

COMMENT ON COLUMN fixed_cost.september IS '9月';

COMMENT ON COLUMN fixed_cost.october IS '10月';

COMMENT ON COLUMN fixed_cost.november IS '11月';

COMMENT ON COLUMN fixed_cost.december IS '12月';

COMMENT ON COLUMN fixed_cost.created_at IS '登録日時';

COMMENT ON COLUMN fixed_cost.updated_at IS '更新日時';

CREATE TRIGGER fixed_cost_upd_trigger BEFORE UPDATE ON public.fixed_cost FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TABLE m_user (
  id serial,
  name CHARACTER VARYING(100) NOT NULL,
  email CHARACTER VARYING(100) NOT NULL,
  roles CHARACTER VARYING(100) NOT NULL,
  password CHARACTER VARYING(150) NOT NULL,
  is_active BOOLEAN NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

COMMENT ON TABLE m_user IS 'ユーザマスタ';

COMMENT ON COLUMN m_user.name IS 'ユーザ名';

COMMENT ON COLUMN m_user.email IS 'e-mail';

COMMENT ON COLUMN m_user.roles IS 'ロール';

COMMENT ON COLUMN m_user.password IS 'パスワード';

COMMENT ON COLUMN m_user.is_active IS '有効';

COMMENT ON COLUMN m_user.created_at IS '登録日時';

COMMENT ON COLUMN m_user.updated_at IS '更新日時';

CREATE TRIGGER m_user_upd_trigger BEFORE UPDATE ON public.m_user FOR EACH ROW EXECUTE PROCEDURE set_update_time();

/**************************************/
/*             CREATE VIEW           */
/**************************************/
CREATE OR REPLACE VIEW v_household_account_book AS
SELECT
    hab.hab_seq
  , CAST(extract(year from hab.actual_date) as character varying) AS year
  , hab.actual_date
  , c.category_type
  , CASE
      WHEN c.category_type = '1' THEN '10_収入'
      WHEN c.category_type = '2' AND c.fixed_category_flg = '1' THEN '20_支出（固定費）'
      WHEN c.category_type = '2' AND c.fixed_category_flg = '0' THEN '20_支出（変動費）'
      ELSE NULL
    END AS display_category_type_nm
  , hab.category_cd
  , c.category_nm
  , CAST(c.display_order AS character varying) || '_' || c.category_nm AS display_category_nm
  , CASE
      WHEN c.category_type = '1' AND (hab.category_cd = '1' OR hab.category_cd = '3') THEN '10_勇輝'
      WHEN c.category_type = '1' AND (hab.category_cd = '2' OR hab.category_cd = '4') THEN '20_麻衣'
      WHEN c.category_type = '1' THEN '30_その他'
      ELSE NULL
    END AS display_income_category_nm
  , hab.store_cd
  , s.store_nm
  , hab.amount
  , CASE
      WHEN c.category_type = '2' THEN COALESCE(hab.amount, 0) * -1
      ELSE COALESCE(hab.amount, 0)
    END AS display_amount
  , hab.remarks
  , hab.linking_data_type 
FROM household_account_book hab 
 LEFT JOIN category c 
   ON c.category_cd = hab.category_cd
 LEFT JOIN store s 
   ON s.store_cd = hab.store_cd
ORDER BY
    hab.actual_date
  , c.category_type
  , c.display_order;
;

COMMENT ON VIEW v_household_account_book IS '家計簿ビュー';

COMMENT ON COLUMN v_household_account_book.hab_seq IS '家計簿Seq';

COMMENT ON COLUMN v_household_account_book.year IS '年';

COMMENT ON COLUMN v_household_account_book.actual_date IS '実績日';

COMMENT ON COLUMN v_household_account_book.category_type IS 'カテゴリ種別（1：収入、2：支出）';

COMMENT ON COLUMN v_household_account_book.display_category_type_nm IS '表示用カテゴリ種別名（1：収入、2：支出（固定費）、3：支出（変動費））';

COMMENT ON COLUMN v_household_account_book.category_cd IS 'カテゴリコード';

COMMENT ON COLUMN v_household_account_book.category_nm IS 'カテゴリ名';

COMMENT ON COLUMN v_household_account_book.display_category_nm IS '表示用カテゴリ名';

COMMENT ON COLUMN v_household_account_book.display_income_category_nm IS '表示用カテゴリ名（収入）';

COMMENT ON COLUMN v_household_account_book.store_cd IS '店コード';

COMMENT ON COLUMN v_household_account_book.store_nm IS '店名';

COMMENT ON COLUMN v_household_account_book.amount IS '金額';

COMMENT ON COLUMN v_household_account_book.display_amount IS '表示用金額（支出は負数）';

COMMENT ON COLUMN v_household_account_book.remarks IS '備考';

COMMENT ON COLUMN v_household_account_book.linking_data_type IS '連携データタイプ';

CREATE OR REPLACE VIEW v_asset AS
SELECT
    CAST(extract(year from a.asset_year_month) as character varying) AS year
  , a.asset_year_month
  , da.investment_account_flg AS deposit_account_type
  , CASE
      WHEN da.investment_account_flg = '0' THEN '10_生活費・貯金口座'
      WHEN da.investment_account_flg = '1' THEN '20_投資口座'
      ELSE NULL
    END AS display_deposit_account_type_nm
  , a.deposit_account_cd
  , da.deposit_account_nm
  , a.deposit_account_cd || '_' || da.deposit_account_nm AS display_deposit_account_nm
  , a.asset_amount
  , a.asset_remarks
FROM asset a 
 LEFT JOIN deposit_account da 
   ON da.deposit_account_cd = a.deposit_account_cd
ORDER BY
    a.asset_year_month
  , da.investment_account_flg
  , a.deposit_account_cd
;

COMMENT ON VIEW v_asset IS '資産ビュー';

COMMENT ON COLUMN v_asset.year IS '年';

COMMENT ON COLUMN v_asset.asset_year_month IS '資産年月';

COMMENT ON COLUMN v_asset.deposit_account_type IS '預金口座種別（0：生活費・貯金口座、1：投資口座）';

COMMENT ON COLUMN v_asset.display_deposit_account_type_nm IS '表示用預金口座種別名（0：生活費・貯金口座、1：投資口座）';

COMMENT ON COLUMN v_asset.deposit_account_cd IS '預金口座コード';

COMMENT ON COLUMN v_asset.deposit_account_nm IS '預金口座名';

COMMENT ON COLUMN v_asset.display_deposit_account_nm IS '表示用預金口座名';

COMMENT ON COLUMN v_asset.asset_amount IS '資産金額';

COMMENT ON COLUMN v_asset.asset_remarks IS '資産備考';

CREATE OR REPLACE VIEW v_investment_pl AS
SELECT
    CAST(extract(year from ip.investment_pl_year_month) as character varying) AS year
  , ip.investment_pl_year_month
  , da.investment_account_flg AS deposit_account_type
  , CASE
      WHEN da.investment_account_flg = '0' THEN '10_生活費・貯金口座'
      WHEN da.investment_account_flg = '1' THEN '20_投資口座'
      ELSE NULL
    END AS display_deposit_account_type_nm
  , ip.deposit_account_cd
  , da.deposit_account_nm
  , ip.deposit_account_cd || '_' || da.deposit_account_nm AS display_deposit_account_nm
  , ip.investment_pl_amount
  , ip.investment_pl_remarks
FROM investment_pl ip
 LEFT JOIN deposit_account da 
   ON da.deposit_account_cd = ip.deposit_account_cd
ORDER BY
    ip.investment_pl_year_month
  , da.investment_account_flg
  , ip.deposit_account_cd
;

COMMENT ON VIEW v_investment_pl IS '投資損益ビュー';

COMMENT ON COLUMN v_investment_pl.year IS '年';

COMMENT ON COLUMN v_investment_pl.investment_pl_year_month IS '投資損益年月';

COMMENT ON COLUMN v_investment_pl.deposit_account_type IS '預金口座種別（0：生活費・貯金口座、1：投資口座）';

COMMENT ON COLUMN v_investment_pl.display_deposit_account_type_nm IS '表示用預金口座種別名（0：生活費・貯金口座、1：投資口座）';

COMMENT ON COLUMN v_investment_pl.deposit_account_cd IS '預金口座コード';

COMMENT ON COLUMN v_investment_pl.deposit_account_nm IS '預金口座名';

COMMENT ON COLUMN v_investment_pl.display_deposit_account_nm IS '表示用預金口座名';

COMMENT ON COLUMN v_investment_pl.investment_pl_amount IS '投資損益金額';

COMMENT ON COLUMN v_investment_pl.investment_pl_remarks IS '投資損益備考';

CREATE OR REPLACE VIEW v_fixed_cost AS 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '01月' AS MONTH
  , fc.january AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
UNION ALL 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '02月' AS MONTH
  , fc.february AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
UNION ALL 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '03月' AS MONTH
  , fc.march AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
UNION ALL 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '04月' AS MONTH
  , fc.april AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
UNION ALL 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '05月' AS MONTH
  , fc.may AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
UNION ALL 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '06月' AS MONTH
  , fc.june AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
UNION ALL 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '07月' AS MONTH
  , fc.july AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
UNION ALL 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '08月' AS MONTH
  , fc.august AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
UNION ALL 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '09月' AS MONTH
  , fc.september AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
UNION ALL 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '10月' AS MONTH
  , fc.october AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
UNION ALL 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '11月' AS MONTH
  , fc.november AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
UNION ALL 
SELECT
    c.display_order || '_' || c.category_nm AS display_category_nm
  , fc.fixed_cost_details
  , fc.remarks
  , '12月' AS MONTH
  , fc.december AS fixed_cost_amount 
FROM fixed_cost fc 
 INNER JOIN category c 
    ON fc.category_cd = c.category_cd
    
;

COMMENT ON VIEW v_fixed_cost IS '固定費ビュー';

COMMENT ON COLUMN v_fixed_cost.display_category_nm IS 'カテゴリ名';

COMMENT ON COLUMN v_fixed_cost.fixed_cost_details IS '固定費内訳';

COMMENT ON COLUMN v_fixed_cost.remarks IS '備考';

COMMENT ON COLUMN v_fixed_cost.month IS '月';

COMMENT ON COLUMN v_fixed_cost.fixed_cost_amount IS '固定費金額';

CREATE OR REPLACE VIEW v_month_calen AS 
SELECT
    to_char( 
    ((generate_series) ::DATE) ::TIMESTAMP WITH TIME ZONE
    , 'yyyy-mm' ::text
  ) AS ym 
FROM
  generate_series( 
    ('2009-04-01' ::DATE) ::TIMESTAMP WITH TIME ZONE
    , (CURRENT_DATE) ::TIMESTAMP WITH TIME ZONE
    , '1 mon' ::INTERVAL
  ) generate_series(generate_series)
;

COMMENT ON VIEW v_month_calen IS '年月カレンダービュー';

COMMENT ON COLUMN v_month_calen.ym IS '年月';

