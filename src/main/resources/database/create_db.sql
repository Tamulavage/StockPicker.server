#

DROP DATABASE if exists stocks;
CREATE DATABASE stocks;

USE stocks;

CREATE TABLE user
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
  CONSTRAINT pk_account PRIMARY KEY (id)
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
  stock_symbol_id integer,
  start_watch     date,
  end_watch       date,
  CONSTRAINT pk_watched_stocks PRIMARY KEY (id),
  CONSTRAINT fk_watched_stock FOREIGN KEY (stock_symbol_id) REFERENCES stock_symbol (id)
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
  CONSTRAINT fk_watched_stock FOREIGN KEY (stock_symbol_id) REFERENCES stock_symbol (id)
);


CREATE TABLE current_stocks
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
