--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: versionDocumento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."versionDocumento" (
    versiond_id integer NOT NULL,
    documento_id integer NOT NULL,
    titulo_documento character varying(80) NOT NULL,
    fecha_version date NOT NULL,
    detalles_cambios character varying(300) NOT NULL
);

ALTER TABLE public."versionDocumento" OWNER TO postgres;

--
-- Name: cambioDocumento_cambiod_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."versionDocumento" ALTER COLUMN versiond_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."cambioDocumento_cambiod_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

--
-- Name: documento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documento (
    documento_id integer NOT NULL,
    titulo character varying(255) NOT NULL,
    ruta_doc character varying(255) NOT NULL,
    proyecto_id integer NOT NULL,
    usuario_id integer NOT NULL,
    fecha_modificacion date NOT NULL
);

ALTER TABLE public.documento OWNER TO postgres;

--
-- Name: documento_documento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.documento ALTER COLUMN documento_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.documento_documento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

--
-- Name: equipo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.equipo (
    equipo_id integer NOT NULL,
    nombre character varying(255) NOT NULL,
    descripcion character varying(255)
);

ALTER TABLE public.equipo OWNER TO postgres;

--
-- Name: equipo_equipo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.equipo ALTER COLUMN equipo_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.equipo_equipo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

--
-- Name: notificacion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notificacion (
    notificacion_id integer NOT NULL,
    mensaje character varying(200) NOT NULL,
    accion_notificacion character varying(50) NOT NULL,
    fecha_creacion date NOT NULL,
    proyecto_id integer NOT NULL,
    documento_id integer NOT NULL
);

ALTER TABLE public.notificacion OWNER TO postgres;

--
-- Name: notificacion_notificacion_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.notificacion ALTER COLUMN notificacion_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.notificacion_notificacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

--
-- Name: proyecto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.proyecto (
    proyecto_id integer NOT NULL,
    nombre character varying(255) NOT NULL,
    descripcion character varying(255),
    equipo_id integer NOT NULL
);

ALTER TABLE public.proyecto OWNER TO postgres;

--
-- Name: proyecto_proyecto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.proyecto ALTER COLUMN proyecto_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.proyecto_proyecto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    usuario_id integer NOT NULL,
    nombres character varying(255) NOT NULL,
    apellidos character varying(255) NOT NULL,
    contrasenia character varying(255) NOT NULL,
    correo character varying(255) NOT NULL,
    rol character varying(255) NOT NULL,
    token_invalid_before timestamp(6) with time zone
);

ALTER TABLE public.usuario OWNER TO postgres;

--
-- Name: usuario_equipo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario_equipo (
    usuario_id integer NOT NULL,
    equipo_id integer NOT NULL
);

ALTER TABLE public.usuario_equipo OWNER TO postgres;

--
-- Name: usuario_usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.usuario ALTER COLUMN usuario_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.usuario_usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

--
-- Name: version_documento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.version_documento (
    versiond_id integer NOT NULL,
    detalles_cambios character varying(255) NOT NULL,
    documento_id integer NOT NULL,
    fecha_version date NOT NULL,
    titulo_documento character varying(255) NOT NULL
);

ALTER TABLE public.version_documento OWNER TO postgres;

--
-- Name: version_documento_versiond_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.version_documento ALTER COLUMN versiond_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.version_documento_versiond_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

--
-- Primary Keys
--

ALTER TABLE ONLY public."versionDocumento"
    ADD CONSTRAINT "cambioDocumento_pkey" PRIMARY KEY (versiond_id);

ALTER TABLE ONLY public.documento
    ADD CONSTRAINT documento_pkey PRIMARY KEY (documento_id);

ALTER TABLE ONLY public.equipo
    ADD CONSTRAINT equipo_pkey PRIMARY KEY (equipo_id);

ALTER TABLE ONLY public.notificacion
    ADD CONSTRAINT notificacion_pkey PRIMARY KEY (notificacion_id);

ALTER TABLE ONLY public.proyecto
    ADD CONSTRAINT proyecto_pkey PRIMARY KEY (proyecto_id);

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario_id);

ALTER TABLE ONLY public.usuario_equipo
    ADD CONSTRAINT usuario_equipo_pkey PRIMARY KEY (usuario_id, equipo_id);

ALTER TABLE ONLY public.version_documento
    ADD CONSTRAINT version_documento_pkey PRIMARY KEY (versiond_id);

--
-- Unique Constraints
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT ux_correo UNIQUE (correo);

CREATE UNIQUE INDEX ux_usuario_correo ON public.usuario USING btree (correo);

--
-- Foreign Keys
--

ALTER TABLE ONLY public."versionDocumento"
    ADD CONSTRAINT "FK_cambioDoc_documento" FOREIGN KEY (documento_id) REFERENCES public.documento(documento_id) NOT VALID;

ALTER TABLE ONLY public.documento
    ADD CONSTRAINT "FK_documento_proyecto" FOREIGN KEY (proyecto_id) REFERENCES public.proyecto(proyecto_id) NOT VALID;

ALTER TABLE ONLY public.documento
    ADD CONSTRAINT "FK_documento_usuario" FOREIGN KEY (usuario_id) REFERENCES public.usuario(usuario_id) NOT VALID;

ALTER TABLE ONLY public.usuario_equipo
    ADD CONSTRAINT "FK_equipo_tm" FOREIGN KEY (equipo_id) REFERENCES public.equipo(equipo_id);

ALTER TABLE ONLY public.notificacion
    ADD CONSTRAINT "FK_notificacion_documento" FOREIGN KEY (documento_id) REFERENCES public.documento(documento_id) NOT VALID;

ALTER TABLE ONLY public.notificacion
    ADD CONSTRAINT "FK_notificacion_proyecto" FOREIGN KEY (proyecto_id) REFERENCES public.proyecto(proyecto_id) NOT VALID;

ALTER TABLE ONLY public.proyecto
    ADD CONSTRAINT "FK_proyecto_equipo" FOREIGN KEY (equipo_id) REFERENCES public.equipo(equipo_id) NOT VALID;

ALTER TABLE ONLY public.usuario_equipo
    ADD CONSTRAINT "FK_usuario_us" FOREIGN KEY (usuario_id) REFERENCES public.usuario(usuario_id);
