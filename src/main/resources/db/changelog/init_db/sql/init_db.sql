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

--
-- Name: cuisine_type_lu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE cuisine_type_lu (
    id integer NOT NULL,
    name character varying(2044) NOT NULL,
    description character varying NOT NULL
);


ALTER TABLE cuisine_type_lu OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO postgres;

--
-- Name: meal_type_lu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE meal_type_lu (
    id integer NOT NULL,
    name character varying(2044) NOT NULL,
    description character varying(2044) NOT NULL
);


ALTER TABLE meal_type_lu OWNER TO postgres;

--
-- Name: preparation_type_lu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE preparation_type_lu (
    id integer NOT NULL,
    name character varying(2044) NOT NULL,
    description character varying(2044) NOT NULL
);


ALTER TABLE preparation_type_lu OWNER TO postgres;

--
-- Name: protein_type_lu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE protein_type_lu (
    id integer NOT NULL,
    name character varying(2044) NOT NULL,
    description character varying(2044) NOT NULL
);


ALTER TABLE protein_type_lu OWNER TO postgres;

--
-- Name: recipe; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE recipe (
    id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    page integer,
    source character varying(255) NOT NULL,
    volume character varying(255),
    meal_type_id integer,
    cuisine_type_id integer,
    protein_type_id integer,
    preparation_type_id integer
);


ALTER TABLE recipe OWNER TO postgres;

--
-- Data for Name: cuisine_type_lu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO cuisine_type_lu (id, name, description) VALUES (1, 'AMERICAN', 'American');
INSERT INTO cuisine_type_lu (id, name, description) VALUES (2, 'MEXICAN', 'Mexican');
INSERT INTO cuisine_type_lu (id, name, description) VALUES (3, 'CHINESE', 'Chinese');
INSERT INTO cuisine_type_lu (id, name, description) VALUES (4, 'ITALIAN', 'Italian');
INSERT INTO cuisine_type_lu (id, name, description) VALUES (5, 'CAJUN', 'Cajun');
INSERT INTO cuisine_type_lu (id, name, description) VALUES (6, 'FRENCH', 'French');
INSERT INTO cuisine_type_lu (id, name, description) VALUES (7, 'INDIAN', 'Indian');


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 17, true);


--
-- Data for Name: meal_type_lu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO meal_type_lu (id, name, description) VALUES (3, 'DINNER', 'Dinner');
INSERT INTO meal_type_lu (id, name, description) VALUES (2, 'LUNCH', 'Lunch');
INSERT INTO meal_type_lu (id, name, description) VALUES (4, 'SNACK', 'Snack');
INSERT INTO meal_type_lu (id, name, description) VALUES (1, 'BREAKFAST', 'Breakfast');
INSERT INTO meal_type_lu (id, name, description) VALUES (5, 'DESSERT', 'Dessert');


--
-- Data for Name: preparation_type_lu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO preparation_type_lu (id, name, description) VALUES (1, 'GRILL', 'Grill');
INSERT INTO preparation_type_lu (id, name, description) VALUES (2, 'SOUP', 'Soup');
INSERT INTO preparation_type_lu (id, name, description) VALUES (3, 'STEW', 'Stew');
INSERT INTO preparation_type_lu (id, name, description) VALUES (4, 'CROCKPOT', 'Crockpot');
INSERT INTO preparation_type_lu (id, name, description) VALUES (5, 'BAKE', 'Bake');
INSERT INTO preparation_type_lu (id, name, description) VALUES (6, 'ROAST', 'Roast');
INSERT INTO preparation_type_lu (id, name, description) VALUES (7, 'ONE_POT', 'One Pot');


--
-- Data for Name: protein_type_lu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO protein_type_lu (id, name, description) VALUES (1, 'CHICKEN', 'Chicken');
INSERT INTO protein_type_lu (id, name, description) VALUES (3, 'PORK', 'Pork');
INSERT INTO protein_type_lu (id, name, description) VALUES (4, 'VENISON', 'Venison');
INSERT INTO protein_type_lu (id, name, description) VALUES (5, 'EGG', 'Egg');
INSERT INTO protein_type_lu (id, name, description) VALUES (6, 'TOFU', 'Tofu');
INSERT INTO protein_type_lu (id, name, description) VALUES (7, 'VEGETABLE', 'Vegetable');
INSERT INTO protein_type_lu (id, name, description) VALUES (8, 'FISH', 'Fish');
INSERT INTO protein_type_lu (id, name, description) VALUES (10, 'LOBSTER', 'Lobster');
INSERT INTO protein_type_lu (id, name, description) VALUES (9, 'SHRIMP', 'Shrimp');
INSERT INTO protein_type_lu (id, name, description) VALUES (2, 'BEEF', 'Beef');


--
-- Name: cuisine_type_lu cuisine_type_lu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cuisine_type_lu
    ADD CONSTRAINT cuisine_type_lu_pkey PRIMARY KEY (id);


--
-- Name: preparation_type_lu preparation_type_lu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY preparation_type_lu
    ADD CONSTRAINT preparation_type_lu_pkey PRIMARY KEY (id);


--
-- Name: protein_type_lu protein_type_lu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY protein_type_lu
    ADD CONSTRAINT protein_type_lu_pkey PRIMARY KEY (id);


--
-- Name: recipe recipe_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recipe
    ADD CONSTRAINT recipe_pkey PRIMARY KEY (id);


--
-- Name: meal_type_lu unique_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY meal_type_lu
    ADD CONSTRAINT unique_id PRIMARY KEY (id);


--
-- Name: recipe lnk_cuisine_type_lu_recipe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recipe
    ADD CONSTRAINT lnk_cuisine_type_lu_recipe FOREIGN KEY (cuisine_type_id) REFERENCES cuisine_type_lu(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: recipe lnk_meal_type_lu_recipe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recipe
    ADD CONSTRAINT lnk_meal_type_lu_recipe FOREIGN KEY (meal_type_id) REFERENCES meal_type_lu(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: recipe lnk_preparation_type_lu_recipe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recipe
    ADD CONSTRAINT lnk_preparation_type_lu_recipe FOREIGN KEY (preparation_type_id) REFERENCES preparation_type_lu(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: recipe lnk_protein_type_lu_recipe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY recipe
    ADD CONSTRAINT lnk_protein_type_lu_recipe FOREIGN KEY (protein_type_id) REFERENCES protein_type_lu(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE;
