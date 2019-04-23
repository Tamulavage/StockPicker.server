# mysql -u root -p < create_db.sql

DROP TABLE if exists stock_history;
DROP TABLE if exists watched_stocks;
DROP TABLE if exists account;
DROP TABLE if exists stocks_users_XREF;
DROP TABLE if exists  stock_transaction;
DROP TABLE if exists user_profiles;
DROP TABLE if exists stock_symbol;

CREATE TABLE user_profiles
(
  user_id    INTEGER     NOT NULL auto_increment,
  first_name varchar(50) NOT NULL,
  last_name  varchar(50) NOT NULL,
  username   varchar(50) NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (user_id)
);


CREATE TABLE account
(
  id      INTEGER NOT NULL auto_increment,
  balance double,
  user_id INTEGER,
  CONSTRAINT pk_account PRIMARY KEY (id),
  CONSTRAINT fk_account_profiles FOREIGN KEY (user_id) REFERENCES user_profiles (user_id)
);

CREATE TABLE stock_symbol
(
  id     INTEGER     NOT NULL auto_increment,
  symbol VARCHAR(10) NOT NULL,
  CONSTRAINT pk_stock_symbol PRIMARY KEY (id)
);

CREATE TABLE watched_stocks
(
  id              INTEGER NOT NULL auto_increment,
  user_id         integer,
  stock_symbol_id integer,
  start_watch     date,
  end_watch       date,
  CONSTRAINT pk_watched_stocks PRIMARY KEY (id),
  CONSTRAINT fk_watched_stock_sym FOREIGN KEY (stock_symbol_id) REFERENCES stock_symbol (id),
  CONSTRAINT fk_watched_stock_prof FOREIGN KEY (user_id) REFERENCES user_profiles (user_id)
);

CREATE TABLE stock_history
(
  id              INTEGER not null auto_increment,
  stock_symbol_Id integer,
  Open_amt        integer,
  close_amt       integer,
  high_amt        integer,
  low_amt         integer,
  volume          integer,
  time_frame_unit INTEGER,
  time_frame_Type varchar(10),
  CONSTRAINT pk_stock_history PRIMARY KEY (id),
  CONSTRAINT fk_stock_history_sym FOREIGN KEY (stock_symbol_id) REFERENCES stock_symbol (id)
);


CREATE TABLE stock_transaction
(
  id              INTEGER NOT NULL auto_increment,
  stock_symbol_id integer,
  buy_price       INTEGER,
  sell_price      INTEGER,
  buy_dt          date,
  sell_dt         date,
  qty             INTEGER,
  CONSTRAINT pk_current_stocks PRIMARY KEY (id)
);

CREATE TABLE stocks_users_XREF
(
  id      INTEGER NOT NULL auto_increment,
  user_id INTEGER NOT NULL,
  stock_id INTEGER NOT NULL,
  CONSTRAINT pk_stocks_users_XREF PRIMARY KEY (id),
  CONSTRAINT fk_stocks_users_user FOREIGN KEY (user_id) REFERENCES user_profiles (user_id),
  CONSTRAINT fk_stocks_users_stck FOREIGN KEY (stock_id) REFERENCES stock_transaction (id)
);