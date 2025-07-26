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

CREATE TABLE if_rakuten_card (
  if_rakuten_card_seq serial,
  usage_date DATE,
  merchant_product_name text,
  customer_nm text,
  payment_method text,
  usage_amount numeric(8, 0),
  payment_fee numeric(8, 0),
  total_payment_amount numeric(8, 0),
  payment_month text,
  monthly_payment_amount numeric(8, 0),
  monthly_carryover_balance numeric(8, 0),
  new_signup_flag text,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (if_rakuten_card_seq)
);

COMMENT ON TABLE if_rakuten_card IS '楽天カードインターフェース';

COMMENT ON COLUMN if_rakuten_card.if_rakuten_card_seq IS '楽天カードインターフェースSeq';

COMMENT ON COLUMN if_rakuten_card.usage_date IS '利用日';

COMMENT ON COLUMN if_rakuten_card.merchant_product_name IS '利用店名・商品名';

COMMENT ON COLUMN if_rakuten_card.customer_nm IS '利用者';

COMMENT ON COLUMN if_rakuten_card.payment_method IS '支払方法';

COMMENT ON COLUMN if_rakuten_card.usage_amount IS '利用金額';

COMMENT ON COLUMN if_rakuten_card.payment_fee IS '支払手数料';

COMMENT ON COLUMN if_rakuten_card.total_payment_amount IS '支払総額';

COMMENT ON COLUMN if_rakuten_card.payment_month IS '支払月';

COMMENT ON COLUMN if_rakuten_card.monthly_payment_amount IS 'N月支払金額';

COMMENT ON COLUMN if_rakuten_card.monthly_carryover_balance IS 'N月繰越残高';

COMMENT ON COLUMN if_rakuten_card.new_signup_flag IS '新規サイン';

COMMENT ON COLUMN if_rakuten_card.created_at IS '登録日時';

COMMENT ON COLUMN if_rakuten_card.updated_at IS '更新日時';

CREATE TRIGGER if_rakuten_card_upd_trigger BEFORE UPDATE ON public.if_rakuten_card FOR EACH ROW EXECUTE PROCEDURE set_update_time();

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

CREATE TABLE category_mapping_config (
  category_mapping_config_seq serial,
  mapping_key_nm text,
  category_cd integer,
  linking_excluded_flg CHARACTER VARYING(1) DEFAULT '0',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (category_mapping_config_seq),
  FOREIGN KEY (category_cd) REFERENCES category(category_cd)
);

COMMENT ON TABLE category_mapping_config IS '連携データカテゴリ振り分け設定';

COMMENT ON COLUMN category_mapping_config.category_mapping_config_seq IS '連携データカテゴリ振り分け設定Seq';

COMMENT ON COLUMN category_mapping_config.mapping_key_nm IS '振り分けキー名称';

COMMENT ON COLUMN category_mapping_config.category_cd IS 'カテゴリコード';

COMMENT ON COLUMN category_mapping_config.linking_excluded_flg IS '連携対象外フラグ';

COMMENT ON COLUMN category_mapping_config.created_at IS '登録日時';

COMMENT ON COLUMN category_mapping_config.updated_at IS '更新日時';

CREATE TRIGGER category_mapping_config_upd_trigger BEFORE UPDATE ON public.category_mapping_config FOR EACH ROW EXECUTE PROCEDURE set_update_time();

CREATE TABLE recurring_config (
  recurring_config_seq serial,
  recurring_nm text,
  execution_interval_type CHARACTER VARYING(1),
  category_cd INTEGER,
  store_cd INTEGER,
  amount numeric(8, 0),
  remarks text,
  linking_data_type INTEGER DEFAULT 0,
  active_flg  CHARACTER VARYING(1) DEFAULT '1',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (recurring_config_seq),
  FOREIGN KEY (category_cd) REFERENCES category(category_cd),
  FOREIGN KEY (store_cd) REFERENCES store(store_cd),
  FOREIGN KEY (linking_data_type) REFERENCES linking_data(linking_data_type)
);

COMMENT ON TABLE recurring_config IS '繰り返し入力設定';

COMMENT ON COLUMN recurring_config.recurring_config_seq IS '繰り返し入力設定Seq';

COMMENT ON COLUMN recurring_config.recurring_nm IS '繰り返し入力名称';

COMMENT ON COLUMN recurring_config.execution_interval_type IS '実行間隔種別（1：毎月1日）';

COMMENT ON COLUMN recurring_config.category_cd IS 'カテゴリコード';

COMMENT ON COLUMN recurring_config.store_cd IS '店コード';

COMMENT ON COLUMN recurring_config.amount IS '金額';

COMMENT ON COLUMN recurring_config.remarks IS '備考';

COMMENT ON COLUMN recurring_config.linking_data_type IS '連携データタイプ（固定値0：繰り返し入力設定）';

COMMENT ON COLUMN recurring_config.active_flg IS '有効フラグ';

COMMENT ON COLUMN recurring_config.created_at IS '登録日時';

COMMENT ON COLUMN recurring_config.updated_at IS '更新日時';

CREATE TRIGGER recurring_config_upd_trigger BEFORE UPDATE ON public.recurring_config FOR EACH ROW EXECUTE PROCEDURE set_update_time();

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

COMMENT ON COLUMN v_household_account_book.3ing_data_type IS '連携データタイプ';

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

