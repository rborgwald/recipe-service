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

DROP TABLE IF EXISTS recipe;
DROP TABLE IF EXISTS protein_type_lu;
DROP TABLE IF EXISTS preparation_type_lu;
DROP TABLE IF EXISTS meal_type_lu;
DROP TABLE IF EXISTS cuisine_type_lu;
DROP SEQUENCE IF EXISTS hibernate_sequence;