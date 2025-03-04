REVOKE CONNECT ON DATABASE kp FROM kp;
REVOKE USAGE ON SCHEMA kp FROM kp;
REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA kp FROM kp;
--REVOKE ALL PRIVILEGES ON TABLE kp.users FROM kp;

DROP SCHEMA IF EXISTS kp CASCADE;
\c postgres
DROP DATABASE IF EXISTS kp;
DROP ROLE kp;

CREATE ROLE kp LOGIN
  ENCRYPTED PASSWORD 'kp'
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

CREATE DATABASE kp
 WITH OWNER = kp
  template=template0
  ENCODING = 'UTF8'
  TABLESPACE = pg_default
  LC_COLLATE = 'C'
  LC_CTYPE = 'C'
  CONNECTION LIMIT = -1;

\c kp
REVOKE CREATE ON SCHEMA public FROM PUBLIC;

CREATE SCHEMA kp AUTHORIZATION kp;

REVOKE CONNECT ON DATABASE kp FROM PUBLIC;
GRANT CONNECT ON DATABASE kp TO kp;

GRANT USAGE ON SCHEMA kp TO kp;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA kp TO kp;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA kp TO kp;
