--
-- PostgreSQL database dump
--

\restrict MJ0OkATDwbSelZ81vzg4N3uLf50lMbEp5bDpfX6E7JadYIXanFD0hDSyLQBL6Xx

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

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
-- Name: documento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documento (
    documento_id integer NOT NULL,
    "titulo " character varying(80) NOT NULL,
    ruta_doc character varying(260) NOT NULL,
    proyecto_id integer NOT NULL,
    usuario_id integer NOT NULL
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
    nombre character varying NOT NULL,
    descripcion character varying(300)
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
    nombre character varying(80) NOT NULL,
    descripcion character varying(200),
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
    rol character varying(255) NOT NULL
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
-- Name: documento documento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documento
    ADD CONSTRAINT documento_pkey PRIMARY KEY (documento_id);


--
-- Name: equipo equipo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.equipo
    ADD CONSTRAINT equipo_pkey PRIMARY KEY (equipo_id);


--
-- Name: notificacion notificacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificacion
    ADD CONSTRAINT notificacion_pkey PRIMARY KEY (notificacion_id);


--
-- Name: proyecto proyecto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proyecto
    ADD CONSTRAINT proyecto_pkey PRIMARY KEY (proyecto_id);


--
-- Name: usuario_equipo usuario_equipo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_equipo
    ADD CONSTRAINT usuario_equipo_pkey PRIMARY KEY (usuario_id, equipo_id);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario_id);


--
-- Name: usuario ux_correo; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT ux_correo UNIQUE (correo);


--
-- Name: ux_usuario_correo; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX ux_usuario_correo ON public.usuario USING btree (correo);


--
-- Name: documento FK_documento_proyecto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documento
    ADD CONSTRAINT "FK_documento_proyecto" FOREIGN KEY (proyecto_id) REFERENCES public.proyecto(proyecto_id) NOT VALID;


--
-- Name: documento FK_documento_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documento
    ADD CONSTRAINT "FK_documento_usuario" FOREIGN KEY (usuario_id) REFERENCES public.usuario(usuario_id) NOT VALID;


--
-- Name: usuario_equipo FK_equipo_tm; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_equipo
    ADD CONSTRAINT "FK_equipo_tm" FOREIGN KEY (equipo_id) REFERENCES public.equipo(equipo_id);


--
-- Name: notificacion FK_notificacion_documento; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificacion
    ADD CONSTRAINT "FK_notificacion_documento" FOREIGN KEY (documento_id) REFERENCES public.documento(documento_id) NOT VALID;


--
-- Name: notificacion FK_notificacion_proyecto; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notificacion
    ADD CONSTRAINT "FK_notificacion_proyecto" FOREIGN KEY (proyecto_id) REFERENCES public.proyecto(proyecto_id) NOT VALID;


--
-- Name: proyecto FK_proyecto_equipo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proyecto
    ADD CONSTRAINT "FK_proyecto_equipo" FOREIGN KEY (equipo_id) REFERENCES public.equipo(equipo_id) NOT VALID;


--
-- Name: usuario_equipo FK_usuario_us; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_equipo
    ADD CONSTRAINT "FK_usuario_us" FOREIGN KEY (usuario_id) REFERENCES public.usuario(usuario_id);


--
-- PostgreSQL database dump complete
--

\unrestrict MJ0OkATDwbSelZ81vzg4N3uLf50lMbEp5bDpfX6E7JadYIXanFD0hDSyLQBL6Xx

