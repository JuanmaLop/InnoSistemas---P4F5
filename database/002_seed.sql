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

--
-- Data for Name: equipo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.equipo (equipo_id, nombre, descripcion) FROM stdin;
1	EquipoPrueba	Equipo para realizar pruebas backend
\.

--
-- Data for Name: proyecto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.proyecto (proyecto_id, nombre, descripcion, equipo_id) FROM stdin;
1	ProyectoPrueba	Proyecto de prueba para backend	1
\.

--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (usuario_id, nombres, apellidos, contrasenia, correo, rol) FROM stdin;
2	Estudiante	Prueba	$2a$10$S29byzLZhdwkJ/P0cfcJ0.R7xQfFIysk06Elfp12kt5e9S5oZGr/m	testest@uni.edu	Estudiante
3	Profesor	Prueba	$2a$10$wsrrY85Kb2UN7QJEn1VRXed4znnxKVycptOyKppCkelYLrlP1yXX.	testprofesor@uni.edu	Profesor
4	Admin	Prueba	$2a$10$H292RHo6xzcEAFiEn69Jc.vLx7sRqkvxTrj6Hh8t6pYhWIEL/69Sy	testadmin@uni.edu	Administrador
5	Estudiante	Prueba 2	$2a$10$aXGZmFM29/gBXwxR0aojquUTSU9cdsNMIpcFVezWgpOiVipQ88bwS	testest2@uni.edu	Estudiante
6	Estudiante	Prueba 3	$2a$10$kgEJGS9OhrlStVnEzijV8.3pp0JQxog8t6sHfEuk9cKsvtL5nq75K	testest3@uni.edu	Estudiante
\.

--
-- Data for Name: documento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.documento (titulo, ruta_doc, proyecto_id, usuario_id, fecha_modificacion) FROM stdin;
DocumentoPrueba	rutax	1	2	2025-11-01
\.

--
-- Data for Name: notificacion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notificacion (notificacion_id, mensaje, accion_notificacion, fecha_creacion, proyecto_id, documento_id) FROM stdin;
1	Esto es una notificacion de prueba	Edicion documento	2025-09-25	1	1
\.

--
-- Data for Name: usuario_equipo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario_equipo (usuario_id, equipo_id) FROM stdin;
2	1
4	1
5	1
6	1
\.

--
-- Sequence Sets
--

SELECT pg_catalog.setval('public.documento_documento_id_seq', 1, true);
SELECT pg_catalog.setval('public.equipo_equipo_id_seq', 1, true);
SELECT pg_catalog.setval('public.notificacion_notificacion_id_seq', 1, true);
SELECT pg_catalog.setval('public.proyecto_proyecto_id_seq', 1, true);
SELECT pg_catalog.setval('public.usuario_usuario_id_seq', 6, true);
