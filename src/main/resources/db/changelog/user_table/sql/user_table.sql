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

CREATE TABLE user_account (
    id bigserial NOT NULL,
    username character varying NOT NULL,
    password character varying NOT NULL
);

INSERT INTO user_account (id, username, password) VALUES (1, 'rborgwald', '$2a$10$W5ZU6AeVBR5UABt6uRYeq.1iBUV.xwtk9ELnwfVMoAysdZXIebw2m');
INSERT INTO user_account (id, username, password) VALUES (2, 'jufkes', '$2a$10$X4qWks/jhBBgyOjxXpgKLujdlQOzfk/xTLSHFyYgE8tRxVfTAXjwG');