SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = recipe;

SET default_tablespace = '';

SET default_with_oids = false;

-- Add primary key to user_account.id
ALTER TABLE user_account ADD PRIMARY KEY (id);

-- Create recipe_list table
CREATE TABLE recipe_list
(
  id   VARCHAR(255) NOT NULL
    CONSTRAINT recipe_list_pkey
    PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX recipe_list_id_uindex
  ON recipe_list (id);

-- Create recipe_list_mapping table
CREATE TABLE recipe_list_mapping
(
  id VARCHAR(255) NOT NULL
    CONSTRAINT recipe_list_mapping_pkey
    PRIMARY KEY,
  recipe_id      VARCHAR(255) NOT NULL
    CONSTRAINT recipe_list_mapping_recipe_id_fk
    REFERENCES recipe,
  recipe_list_id VARCHAR(255) NOT NULL
    CONSTRAINT recipe_list_mapping_recipe_list_id_fk
    REFERENCES recipe_list
);

-- Create recipe_list_permission table
CREATE TABLE recipe_list_permission
(
  id             VARCHAR(255) NOT NULL
    CONSTRAINT recipe_list_permission_pkey
    PRIMARY KEY,
  recipe_list_id VARCHAR(255) NOT NULL
    CONSTRAINT list_permission_recipe_list_id_fk
    REFERENCES recipe_list,
  user_id        BIGSERIAL    NOT NULL
    CONSTRAINT list_permission_user_account_id_fk
    REFERENCES user_account
);