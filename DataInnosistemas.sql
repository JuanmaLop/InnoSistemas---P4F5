--
-- PostgreSQL database dump
--

\restrict 6sx40ndaM0qmOnaK9cRnhdTHTGiJPews6ikNt0QpBTcR0GkVH6yZTJnGftRwPJE

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

--
-- Data for Name: auditoria_documento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.auditoria_documento (auditoria_id, accion, documento_id, fecha_accion, observaciones, usuario_id) FROM stdin;
1	DESHABILITADO	19	2025-11-17 21:29:53.711745-05	Documento marcado como deshabilitado	2
2	DESHABILITADO	20	2025-11-17 21:30:00.386619-05	Documento marcado como deshabilitado	2
3	HABILITADO	20	2025-11-17 21:32:30.586313-05	Documento restaurado	2
4	HABILITADO	21	2025-11-21 20:31:57.486879-05	Documento restaurado	6
5	DESHABILITADO	21	2025-11-21 20:32:07.867607-05	Documento marcado como deshabilitado	6
6	DESHABILITADO	22	2025-11-21 20:32:12.438729-05	Documento marcado como deshabilitado	6
7	HABILITADO	21	2025-11-21 20:32:16.552289-05	Documento restaurado	6
8	DESHABILITADO	21	2025-11-21 20:32:21.65605-05	Documento marcado como deshabilitado	6
9	HABILITADO	22	2025-11-21 20:32:26.422168-05	Documento restaurado	6
10	DESHABILITADO	25	2025-11-21 22:26:49.920608-05	Documento marcado como deshabilitado	6
11	DESHABILITADO	24	2025-11-21 22:26:54.046353-05	Documento marcado como deshabilitado	6
12	DESHABILITADO	20	2025-11-21 22:26:57.80739-05	Documento marcado como deshabilitado	6
13	HABILITADO	24	2025-11-21 22:27:08.433378-05	Documento restaurado	6
14	DESHABILITADO	24	2025-11-21 22:27:17.160921-05	Documento marcado como deshabilitado	6
15	HABILITADO	25	2025-11-21 22:27:23.535162-05	Documento restaurado	6
\.


--
-- Data for Name: carpeta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.carpeta (carpeta_id, estado, fecha_creacion, nombre, padre_id, proyecto_id, usuario_creador_id) FROM stdin;
1	Habilitado	2025-11-21 20:45:55.361434	Carpeta raiz proyecto 9	\N	9	6
2	Habilitado	2025-11-21 20:46:22.104629	Carpeta raiz para pruebas del proyecto 9	\N	9	6
3	Habilitado	2025-11-21 20:47:07.641067	Carpeta sub 2	2	9	6
4	Habilitado	2025-11-21 20:47:21.293575	Carpeta sub carpeta raiz	1	9	6
5	Habilitado	2025-11-21 20:47:32.003308	Carpeta sub carpeta raiz para super pruebas	1	9	6
6	Habilitado	2025-11-21 22:29:07.295782	Carpeta prueba final	\N	10	6
7	Habilitado	2025-11-21 22:29:12.372877	Carpeta prueba final 2	\N	10	6
8	Habilitado	2025-11-21 22:29:15.074752	Carpeta prueba final 3	\N	10	6
9	Habilitado	2025-11-21 22:29:48.207952	Carpeta sub prueba 1	6	11	6
10	Habilitado	2025-11-21 22:29:58.74899	Carpeta sub prueba 2	6	11	6
11	Habilitado	2025-11-21 22:30:03.707045	Carpeta sub prueba 3	6	11	6
12	Habilitado	2025-11-21 22:30:14.642092	Carpeta sub prueba 3	6	10	6
\.


--
-- Data for Name: equipo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.equipo (equipo_id, nombre, descripcion) FROM stdin;
1	EquipoPrueba	Equipo para realizar pruebas backend
2	EquipoPrueba2	Equipo #2 para realizar pruebas backend
3	EqupoPuntual	El mejor equipo de la vida
4	Equipo Bestia	Hola
5	Equipo Polola	Hola
6	Equipo Polopo	Hola
7	Equipo Pro Master	Equipo para comprobar lo pro
8	Equipo de prueba number 3	Equipo para realizar pruebas
9	Equipo Arquisoft	El mejor equipo
10	Ejemplo Equipo	Equipo ej
11	Equipo Super Requete Pro	SuperRequete
12	Equipo exámenes finales	Equipo para examenes finales
13	Equipo Prueba FINAL	Equipo para prueba final del proyecto Inno
\.


--
-- Data for Name: proyecto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.proyecto (proyecto_id, nombre, descripcion, equipo_id) FROM stdin;
1	ProyectoPrueba	Proyecto de prueba para backend	1
2	ProyectoPrueba2	Proyecto #2 de prueba para backend	2
3	Proyecto Integrador	Proyecto super pro	3
4	Proyecto Repro Master17	Habia una vez una iguanaaaa	6
5	Proyecto de investigación sobre abejas	Investigación sobre el día a día de las abejas	8
6	Proyecto prueba	mamsma	8
7	Proyecto Ejemplar 1	Ejemplo	10
8	Proyecto Super Requete Pro	Proyecto de equipo pro	11
9	Proyecto exámenes finales	Examenes finales del semestre	12
10	Proyecto Prueba final v1	Prueba final v1	13
11	Proyecto Prueba final v2	Prueba final v2	13
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (usuario_id, nombres, apellidos, contrasenia, correo, rol, token_invalid_before, id_usuario) FROM stdin;
3	Profesor	Prueba	$2a$10$JVk6n/Ng2AajE0tq9e4c5eQAnJ5Yz3iiOf5Kg9K/qMn/1.GLLjm0u	testprofesor@uni.edu	Profesor	\N	1
5	Estudiante	Prueba 2	$2a$10$JVk6n/Ng2AajE0tq9e4c5eQAnJ5Yz3iiOf5Kg9K/qMn/1.GLLjm0u	testest2@uni.edu	Estudiante	\N	3
2	Estudiante	Prueba	$2a$10$JVk6n/Ng2AajE0tq9e4c5eQAnJ5Yz3iiOf5Kg9K/qMn/1.GLLjm0u	testest@uni.edu	Estudiante	2025-11-05 20:07:35.415099-05	5
4	Admin	Prueba	$2a$10$JVk6n/Ng2AajE0tq9e4c5eQAnJ5Yz3iiOf5Kg9K/qMn/1.GLLjm0u	testadmin@uni.edu	Administrador	2025-11-07 22:42:53.525495-05	2
6	Estudiante	Prueba 3	$2a$10$JVk6n/Ng2AajE0tq9e4c5eQAnJ5Yz3iiOf5Kg9K/qMn/1.GLLjm0u	testest3@uni.edu	Estudiante	2025-11-21 20:34:12.600892-05	4
\.


--
-- Data for Name: documento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.documento (documento_id, titulo, ruta_doc, proyecto_id, usuario_id, fecha_modificacion, estado, carpeta_id) FROM stdin;
2	docejemplo_1_v2.pdf	docs/prueba/docejemplo_1_v2.pdf	1	2	2025-10-22	Habilitado	\N
4	DocumentoPrueba	68f99e597fb69be23e51f101	1	2	2025-10-22	Habilitado	\N
5	Documento Prueba 2	68f9a247ac004d430c96aa44	1	2	2025-10-22	Habilitado	\N
6	Documento Prueba 2	68f9a261ac004d430c96aa46	1	2	2025-10-22	Habilitado	\N
7	DocumentoPrueba	68fd3c424bd0e43016c6efb9	1	2	2025-10-25	Habilitado	\N
8	DocumentoPruebaEstudiante2	68fd4ca9e8b5dd7e96e21596	2	5	2025-10-25	Habilitado	\N
9	Doc definitivo usuario final	68fd6b839c90aca11ecdec6c	2	5	2025-10-25	Habilitado	\N
10	Prueba Proyecto Restringido	68fd73dc6f41bbc62051225f	2	2	2025-10-25	Habilitado	\N
11	Prueba Proyecto Restringido	68fd76086f41bbc620512269	1	2	2025-10-25	Habilitado	\N
14	Documento Prueba  Equipo 7 V4	68fee0764119e2355f9d50c9	4	3	2025-10-26	Habilitado	\N
15	Requerimientos del proyecto V2	690bf8f7bbe74b6e5a419ed3	1	2	2025-11-05	Habilitado	\N
1	docejemplo_1_v2.pdf	68f99e597fb69be23e51f101a	1	2	2025-10-22	Habilitado	\N
17	Documento Prueba	690eb6c496744041ef79eebf	3	5	2025-11-07	Habilitado	\N
16	Nueva act	690eb73b96744041ef79eec1	3	5	2025-11-07	Habilitado	\N
12	Nueva act	690eb74296744041ef79eec4	1	5	2025-11-07	Habilitado	\N
13	Nueva act	690eb74696744041ef79eec7	1	5	2025-11-07	Habilitado	\N
18	Ejemplo 2	690ebde396744041ef79eecc	7	2	2025-11-07	Habilitado	\N
19	Documento Prueba 10002	691bd454e3ff3aa6beae8698	5	2	2025-11-17	Deshabilitado	\N
21	Examen final Com v3	692111bbaced499f2220fa64	9	6	2025-11-21	Deshabilitado	2
22	Examen final DB	692111e6aced499f2220fa66	9	6	2025-11-21	Habilitado	5
23	Documento prueba final 	69212c6002dd7ea0a81f2446	10	6	2025-11-21	Habilitado	\N
20	Documento Pro Super	691bda67ac832118c67d4c6f	8	2	2025-11-17	Deshabilitado	\N
25	Documento prueba final 2 v2	69212d4202dd7ea0a81f244d	11	6	2025-11-21	Habilitado	6
24	Documento prueba final 	69212caf02dd7ea0a81f2448	11	6	2025-11-21	Deshabilitado	7
\.


--
-- Data for Name: notificacion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notificacion (notificacion_id, mensaje, accion_notificacion, fecha_creacion, proyecto_id, documento_id) FROM stdin;
1	Esto es una notificacion de prueba	Edicion documento	2025-09-25	1	1
2	Se ha subido un nuevo documento al proyecto ProyectoPrueba	Nuevo documento	2025-10-03	1	2
\.


--
-- Data for Name: usuario_equipo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario_equipo (usuario_id, equipo_id) FROM stdin;
4	1
5	1
2	1
6	1
5	2
3	3
5	3
6	3
4	4
5	4
4	5
4	6
3	6
6	6
2	7
5	7
6	7
2	8
2	9
5	9
5	10
6	10
2	10
3	11
4	11
5	11
6	11
2	11
4	12
3	12
6	12
2	13
3	13
6	13
\.


--
-- Data for Name: version_documento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.version_documento (versiond_id, detalles_cambios, documento_id, fecha_version, titulo_documento) FROM stdin;
1	Se le agrego un nuevo parrafo 	14	2025-10-26	Documento Prueba  Equipo 7
2	Se le agrego un nuevo parrafo 	14	2025-10-26	Documento Prueba  Equipo 7 V2
3	Se le agrego un nuevo parrafo 	14	2025-10-26	Documento Prueba  Equipo 7 V3
4	Se corrigieron algunos requerimientos	15	2025-11-05	Documento definicion de requerimientos
5	Cambio total	16	2025-11-07	Documento Prueba
6	Cambio total	12	2025-11-07	Documento Prueba Final
7	Cambio total	13	2025-11-07	Documento Prueba  Equipo 7
8	Cambios en estructura	18	2025-11-07	Documento prueba
9	Se sumo 1 al titulo	19	2025-11-17	Documento Prueba 10000
10	Se sumo 1 al titulo	19	2025-11-17	Documento Prueba 10001
11	Se sumo 2 al titulo	19	2025-11-17	Documento Prueba 10001
12	Cambios cambiosos	20	2025-11-17	Documento Re Pro
13	Se actualizo fecha	21	2025-11-21	Examen final Com
14	Se actualizo fecha por 2da vez	21	2025-11-21	Examen final Com v2
15	Se actualizo fecha	22	2025-11-21	Examen final BD
16	Cambios finales 	25	2025-11-21	Documento prueba final 2
\.


--
-- Name: auditoria_documento_auditoria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auditoria_documento_auditoria_id_seq', 15, true);


--
-- Name: carpeta_carpeta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.carpeta_carpeta_id_seq', 12, true);


--
-- Name: documento_documento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.documento_documento_id_seq', 25, true);


--
-- Name: equipo_equipo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.equipo_equipo_id_seq', 13, true);


--
-- Name: notificacion_notificacion_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notificacion_notificacion_id_seq', 2, true);


--
-- Name: proyecto_proyecto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.proyecto_proyecto_id_seq', 11, true);


--
-- Name: usuario_id_usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_usuario_seq', 5, true);


--
-- Name: usuario_usuario_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_usuario_id_seq', 6, true);


--
-- Name: version_documento_versiond_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.version_documento_versiond_id_seq', 16, true);


--
-- PostgreSQL database dump complete
--

\unrestrict 6sx40ndaM0qmOnaK9cRnhdTHTGiJPews6ikNt0QpBTcR0GkVH6yZTJnGftRwPJE

