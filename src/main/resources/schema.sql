DROP TABLE IF EXISTS kp.specification CASCADE;
DROP TABLE IF EXISTS kp.unit CASCADE;
DROP TABLE IF EXISTS kp.currency_list CASCADE;
DROP TABLE IF EXISTS kp.tax_rate CASCADE;
DROP TABLE IF EXISTS kp.tax_type CASCADE;
DROP TABLE IF EXISTS kp.specification_group CASCADE;
DROP TABLE IF EXISTS kp.balance_type CASCADE;
DROP TABLE IF EXISTS kp.account_info CASCADE;
DROP TABLE IF EXISTS kp.account_and_balance CASCADE;
DROP TABLE IF EXISTS kp.shop CASCADE;
DROP TABLE IF EXISTS kp.third_class_by_order CASCADE;
DROP TABLE IF EXISTS kp.third_class CASCADE;
DROP TABLE IF EXISTS kp.second_class_by_order CASCADE;
DROP TABLE IF EXISTS kp.second_class CASCADE;
DROP TABLE IF EXISTS kp.first_class CASCADE;
DROP TABLE IF EXISTS kp.users_ext CASCADE;
DROP TABLE IF EXISTS kp.users CASCADE;
DROP TABLE IF EXISTS SPRING_SESSION_ATTRIBUTES;
DROP TABLE IF EXISTS SPRING_SESSION;
DROP SEQUENCE IF EXISTS kp.specification_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.unit_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.currency_list_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.tax_rate_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.tax_type_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.specification_group_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.balance_type_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.account_info_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.account_and_balance_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.shop_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.third_class_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.second_class_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.first_class_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.users_ext_seq CASCADE;
DROP SEQUENCE IF EXISTS kp.users_seq CASCADE;

CREATE TABLE SPRING_SESSION
(
    PRIMARY_ID            CHAR(36) NOT NULL,
    SESSION_ID            CHAR(36) NOT NULL,
    CREATION_TIME         BIGINT   NOT NULL,
    LAST_ACCESS_TIME      BIGINT   NOT NULL,
    MAX_INACTIVE_INTERVAL INT      NOT NULL,
    EXPIRY_TIME           BIGINT   NOT NULL,
    PRINCIPAL_NAME        VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES
(
    SESSION_PRIMARY_ID CHAR(36)     NOT NULL,
    ATTRIBUTE_NAME     VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES    BYTEA        NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION (PRIMARY_ID) ON DELETE CASCADE
);

CREATE SEQUENCE IF NOT EXISTS kp.users_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.users -- ユーザテーブル
(
    username                VARCHAR(255)         NOT NULL UNIQUE,    -- ユーザ名
    user_id                 BIGINT
                                    DEFAULT nextval('kp.users_seq'), -- ユーザID
    password                VARCHAR(255)         NOT NULL,           -- パスワード
    role_name               VARCHAR(255)         NOT NULL,           -- 権限
    enabled                 BOOLEAN DEFAULT TRUE NOT NULL,           -- 有効フラグ (true: 有効, false: 無効)
    account_non_expired     BOOLEAN DEFAULT TRUE NOT NULL,           -- アカウント期限切れフラグ (true: 有効, false: 無効)
    account_non_locked      BOOLEAN DEFAULT TRUE NOT NULL,           -- アカウントロックフラグ (true: 有効, false: 無効)
    credentials_non_expired BOOLEAN DEFAULT TRUE NOT NULL,           -- 資格情報期限切れフラグ (true: 有効, false: 無効)
    PRIMARY KEY (user_id)
);

CREATE SEQUENCE IF NOT EXISTS kp.users_ext_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.users_ext -- ユーザ拡張テーブル
(
    user_id      BIGINT       NOT NULL UNIQUE, -- ユーザID
    last_name    VARCHAR(255) NOT NULL,        -- 苗字 (任意)
    first_name   VARCHAR(255) NOT NULL,        -- 名前 (任意)
    email        VARCHAR(255) NOT NULL,        -- メールアドレス
    birth_day    DATE         NOT NULL,        -- 誕生日
    phone_number VARCHAR(255) NOT NULL,        -- 電話番号
    entry_date   TIMESTAMPTZ  NOT NULL,        -- 登録日時
    update_date  TIMESTAMPTZ,                  -- 更新日時
    PRIMARY KEY (user_id)
);

CREATE SEQUENCE IF NOT EXISTS kp.first_class_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.first_class -- 支出入分類テーブル
(
    user_id          BIGINT       NOT NULL,    -- ユーザID
    first_class_id   BIGINT
        DEFAULT nextval('kp.first_class_seq'), -- 支出入分類ID
    first_class_name VARCHAR(128) NOT NULL,    -- 支出入分類名
    PRIMARY KEY (user_id, first_class_id),
    FOREIGN KEY (user_id)
        REFERENCES kp.users (user_id)
);

CREATE SEQUENCE IF NOT EXISTS kp.second_class_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.second_class -- 費目大分類テーブル
(
    user_id           BIGINT                NOT NULL,                 -- ユーザID
    second_class_id   BIGINT
                              DEFAULT nextval('kp.second_class_seq'), -- 費目大分類ID
    first_class_id    BIGINT                NOT NULL,                 -- 支出入分類ID
    second_class_name VARCHAR(128)          NOT NULL,                 -- 費目大分類名
    disabled          BOOLEAN DEFAULT FALSE NOT NULL,                 -- 無効フラグ (true: 無効, false: 有効)
    PRIMARY KEY (user_id, second_class_id, first_class_id, disabled),
    FOREIGN KEY (user_id, first_class_id)
        REFERENCES kp.first_class (user_id, first_class_id)
);

CREATE TABLE IF NOT EXISTS kp.second_class_by_order -- 費目大分類ソートテーブル
(
    user_id         BIGINT                NOT NULL, -- ユーザID
    second_class_id BIGINT                NOT NULL, -- 費目大分類ID
    first_class_id  BIGINT                NOT NULL, -- 支出入分類ID,
    order_number    BIGINT,                         -- ソート順 (任意)
    disabled        BOOLEAN DEFAULT FALSE NOT NULL, -- 無効フラグ (true: 無効, false: 有効)
    PRIMARY KEY (user_id, second_class_id, first_class_id, order_number, disabled),
    FOREIGN KEY (user_id, second_class_id, first_class_id, disabled)
        REFERENCES kp.second_class (user_id, second_class_id, first_class_id, disabled)
);

CREATE SEQUENCE IF NOT EXISTS kp.third_class_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.third_class -- 費目小分類テーブル
(
    user_id          BIGINT                NOT NULL,            -- ユーザID
    third_class_id   BIGINT  DEFAULT
                                 nextval('kp.third_class_seq'), -- 費目小分類ID
    second_class_id  BIGINT                NOT NULL,            -- 費目大分類ID
    first_class_id   BIGINT                NOT NULL,            -- 支出入分類ID
    third_class_name VARCHAR(128)          NOT NULL,            -- 費目小分類名
    disabled         BOOLEAN DEFAULT FALSE NOT NULL,            -- 無効フラグ (true: 無効, false: 有効)
    PRIMARY KEY (user_id, third_class_id, second_class_id, first_class_id, disabled),
--     FOREIGN KEY (user_id, first_class_id)
--         REFERENCES kp.first_class (user_id, first_class_id),
    FOREIGN KEY (user_id, second_class_id, first_class_id, disabled)
        REFERENCES kp.second_class (user_id, second_class_id, first_class_id, disabled)
);

CREATE TABLE IF NOT EXISTS kp.third_class_by_order -- 費目小分類ソートテーブル
(
    user_id         BIGINT                NOT NULL, -- ユーザID
    third_class_id  BIGINT                NOT NULL, -- 費目小分類ID
    second_class_id BIGINT                NOT NULL, -- 費目大分類ID
    first_class_id  BIGINT                NOT NULL, -- 支出入分類ID
    order_number    BIGINT                NOT NULL, -- ソート順
    disabled        BOOLEAN DEFAULT FALSE NOT NULL, -- 無効フラグ (true: 無効, false: 有効)
    PRIMARY KEY (user_id, third_class_id, second_class_id, first_class_id, order_number, disabled),
    FOREIGN KEY (user_id, third_class_id, second_class_id, first_class_id, disabled)
        REFERENCES kp.third_class (user_id, third_class_id, second_class_id, first_class_id, disabled)
);


CREATE SEQUENCE IF NOT EXISTS kp.shop_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.shop -- 店舗情報テーブル
(
    shop_id         BIGINT DEFAULT
                               nextval('kp.shop_seq'), -- 店舗ID
    shop_name       VARCHAR(255) NOT NULL,             -- 店舗名
    branch_name     VARCHAR(255),                      -- 支店名 (任意)
    shop_url        VARCHAR(255),                      -- URL (任意)
    phone_number    VARCHAR(255),                      -- 電話番号 (任意)
    email           VARCHAR(255),                      -- メールアドレス (任意)
    open_shop_date  DATE,                              -- 開店日 (任意)
    close_shop_date DATE,                              -- 閉店日 (任意)
    shop_memo       TEXT,                              -- メモ (任意) 1000文字まで
    entry_date      TIMESTAMPTZ  NOT NULL,             -- 登録日時
    update_date     TIMESTAMPTZ,                       -- 更新日時
    PRIMARY KEY (shop_id)
);

CREATE SEQUENCE IF NOT EXISTS kp.account_and_balance_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.account_and_balance -- 口座収支複合キーテーブル
(
    account_and_balance_id BIGINT DEFAULT
                                      nextval('kp.account_and_balance_seq'), -- 口座収支複合キーID
    account_source_id      BIGINT,                                           -- 口座支出元ID
    account_destination_id BIGINT,                                           -- 収支収入先ID
    entry_date             TIMESTAMPTZ NOT NULL,                             -- 登録日時
    update_date            TIMESTAMPTZ,                                      -- 更新日時
    PRIMARY KEY (account_and_balance_id)
);

CREATE SEQUENCE IF NOT EXISTS kp.account_info_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.account_info -- 口座情報テーブル
(
    account_id   BIGINT DEFAULT
                            nextval('kp.account_info_seq'), -- 口座ID
    account_name VARCHAR(255) NOT NULL,                     -- 口座名
    branch_name  VARCHAR(255),                              -- 支店名 (任意)
    entry_date   TIMESTAMPTZ  NOT NULL,                     -- 登録日時
    update_date  TIMESTAMPTZ,                               -- 更新日時
    PRIMARY KEY (account_id)
);
CREATE SEQUENCE IF NOT EXISTS kp.balance_type_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.balance_type -- 収支テーブル
(
    balance_type_id   BIGINT DEFAULT
                                 nextval('kp.balance_type_seq'), -- 収支ID
    balance_type_name VARCHAR(2) NOT NULL,                       -- 収支名
    PRIMARY KEY (balance_type_id)
);

CREATE SEQUENCE IF NOT EXISTS kp.specification_group_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.specification_group -- 明細グループテーブル
(
    specification_group_id     BIGINT  DEFAULT
                                           nextval('kp.specification_group_seq'), -- 明細グループID
    user_id                    BIGINT                NOT NULL,                    -- ユーザID
    shop_id                    BIGINT                NOT NULL,                    -- 店舗ID
    receiving_and_payment_date DATE                  NOT NULL,                    -- 受取支払日
    receiving_and_payment_time TIME                  NOT NULL,                    -- 受取支払時間
    balance_type_id            BIGINT                NOT NULL,                    -- 受取支払種別ID (支出 / 収入 / 振替)
    account_and_balance_id     BIGINT,                                            -- 口座ID (任意) (支出 / 振替: 送金元)
    group_memo                 TEXT,                                              -- メモ (任意) 1000文字まで
    deleted                    BOOLEAN DEFAULT FALSE NOT NULL,                    -- 削除フラグ (true: 削除, false: 有効)
    entry_date                 TIMESTAMPTZ           NOT NULL,                    -- 登録日時
    update_date                TIMESTAMPTZ,                                       -- 更新日時
    PRIMARY KEY (specification_group_id, user_id),
--    FOREIGN KEY (user_id) REFERENCES kp.users (user_id),
    FOREIGN KEY (shop_id) REFERENCES kp.shop (shop_id),
    FOREIGN KEY (account_and_balance_id)
        REFERENCES kp.account_and_balance (account_and_balance_id),
    FOREIGN KEY (balance_type_id) REFERENCES kp.balance_type (balance_type_id)
);

CREATE SEQUENCE IF NOT EXISTS kp.tax_type_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.tax_type -- 消費税種別テーブル
(
    tax_type_id   BIGINT DEFAULT
                             nextval('kp.tax_type_seq'), -- 消費税種別ID
    tax_type_name VARCHAR(128) NOT NULL,                 -- 消費税種別
    PRIMARY KEY (tax_type_id)
);

CREATE SEQUENCE IF NOT EXISTS kp.tax_rate_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.tax_rate -- 消費税率テーブル
(
    tax_rate_id BIGINT DEFAULT
                           nextval('kp.tax_rate_seq'), -- 消費税率ID
    tax_rate    BIGINT NOT NULL,                       -- 消費税率
    PRIMARY KEY (tax_rate_id)
);

CREATE SEQUENCE IF NOT EXISTS kp.currency_list_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.currency_list -- 通貨テーブル
(
    currency_id   BIGINT DEFAULT
                             nextval('kp.currency_list_seq'), -- 通貨ID
    currency_name VARCHAR(3) NOT NULL,                        -- 通貨名
    PRIMARY KEY (currency_id)
);

CREATE SEQUENCE IF NOT EXISTS kp.unit_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.unit -- 単位テーブル
(
    unit_id   BIGINT DEFAULT
                         nextval('kp.unit_seq'), -- 単位ID
    unit_name VARCHAR(16) NOT NULL,              -- 単位名
    PRIMARY KEY (unit_id)
);

CREATE SEQUENCE IF NOT EXISTS kp.specification_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS kp.specification -- 明細テーブル
(
    specification_group_id BIGINT                NOT NULL, -- 明細グループID
    specification_id       BIGINT                NOT NULL, -- 明細ID
--        DEFAULT nextval('kp.specification_seq'),                  -- 明細ID
    user_id                BIGINT                NOT NULL, -- ユーザID
    brand_name             VARCHAR(255),                   -- 商品名
    price                  BIGINT                NOT NULL, -- 価格
    currency_id            BIGINT,                         -- 通貨名 (任意 : USD, EUR, ...)
    quantity               BIGINT                NOT NULL, -- 数量
    unit_id                BIGINT  DEFAULT 1     NOT NULL, -- 単位
    tax_type_id            BIGINT  DEFAULT 1     NOT NULL, -- 消費税種別ID
    tax_rate_id            BIGINT  DEFAULT 1     NOT NULL, -- 消費税率ID
    tax                    BIGINT  DEFAULT 0,              -- 消費税額 (任意)
    spec_memo              TEXT,                           -- メモ (任意) 1000文字まで
    deleted                BOOLEAN DEFAULT FALSE NOT NULL, -- 削除フラグ (true: 削除, false: 有効)
    entry_date             TIMESTAMPTZ           NOT NULL, -- 登録日時
    update_date            TIMESTAMPTZ,                    -- 更新日時
    PRIMARY KEY (specification_group_id, specification_id, user_id),
    FOREIGN KEY (specification_group_id, user_id)
        REFERENCES kp.specification_group (specification_group_id, user_id),
    FOREIGN KEY (tax_type_id) REFERENCES kp.tax_type (tax_type_id),
    FOREIGN KEY (tax_rate_id) REFERENCES kp.tax_rate (tax_rate_id),
    FOREIGN KEY (currency_id) REFERENCES kp.currency_list (currency_id),
    FOREIGN KEY (unit_id) REFERENCES kp.unit (unit_id)
);
