


----------------------------------  ROLE  ----------------------------------
INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO role (id, name) VALUES (2, 'FARMER');



----------------------------------  USER  ----------------------------------
INSERT INTO user (id, first_name, last_name, email, password, enabled) VALUES (1, 'admin',     'admin',     'admin@yopmail.com',       '$2a$10$1kQmZRmgjO8RdhbekEicj.VY7XpIuvWKzIi4MMvoJaODFwbGQXA0i', true);
INSERT INTO user (id, first_name, last_name, email, password, enabled) VALUES (2, 'sebastian', 'silva',     'sesilvavi@gmail.com',     '$2a$10$1kQmZRmgjO8RdhbekEicj.VY7XpIuvWKzIi4MMvoJaODFwbGQXA0i', true);
INSERT INTO user (id, first_name, last_name, email, password, enabled) VALUES (3, 'sebastian', 'silva',     'sebas@yopmail.com',       '$2a$10$1kQmZRmgjO8RdhbekEicj.VY7XpIuvWKzIi4MMvoJaODFwbGQXA0i', true);
INSERT INTO user (id, first_name, last_name, email, password, enabled) VALUES (4, 'soloadmin', 'soloadmin', 'soloadmin@yopmail.com',   '$2a$10$1kQmZRmgjO8RdhbekEicj.VY7XpIuvWKzIi4MMvoJaODFwbGQXA0i', true);



--------------------------------  USER_ROLE  --------------------------------
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 1);



--------------------------------  FERTILIZER  --------------------------------
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (1, 'fertilizer1', 'fertilizer1', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (2, 'fertilizer2', 'fertilizer2', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (3, 'fertilizer3', 'fertilizer3', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (4, 'fertilizer4', 'fertilizer4', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (5, 'fertilizer5', 'fertilizer5', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (6, 'fertilizer6', 'fertilizer6', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (7, 'fertilizer7', 'fertilizer7', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (8, 'fertilizer8', 'fertilizer8', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (9, 'fertilizer9', 'fertilizer9', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (10, 'fertilizer10', 'fertilizer10', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (11, 'fertilizer11', 'fertilizer11', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (12, 'fertilizer12', 'fertilizer12', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (13, 'fertilizer13', 'fertilizer13', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (14, 'fertilizer14', 'fertilizer14', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (15, 'fertilizer15', 'fertilizer15', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (16, 'fertilizer16', 'fertilizer16', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (17, 'fertilizer17', 'fertilizer17', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (18, 'fertilizer18', 'fertilizer18', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (19, 'fertilizer19', 'fertilizer19', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (20, 'fertilizer20', 'fertilizer20', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (21, 'fertilizer21', 'fertilizer21', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (22, 'fertilizer22', 'fertilizer22', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (23, 'fertilizer23', 'fertilizer23', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (24, 'fertilizer24', 'fertilizer24', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (25, 'fertilizer25', 'fertilizer25', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (26, 'fertilizer26', 'fertilizer26', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (27, 'fertilizer27', 'fertilizer27', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (28, 'fertilizer28', 'fertilizer28', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (29, 'fertilizer29', 'fertilizer29', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (30, 'fertilizer30', 'fertilizer30', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (31, 'fertilizer31', 'fertilizer31', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (32, 'fertilizer32', 'fertilizer32', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (33, 'fertilizer33', 'fertilizer33', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (34, 'fertilizer34', 'fertilizer34', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (35, 'fertilizer35', 'fertilizer35', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (36, 'fertilizer36', 'fertilizer36', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (37, 'fertilizer37', 'fertilizer37', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (38, 'fertilizer38', 'fertilizer38', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (39, 'fertilizer39', 'fertilizer39', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (40, 'fertilizer40', 'fertilizer40', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (41, 'fertilizer41', 'fertilizer41', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (42, 'fertilizer42', 'fertilizer42', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (43, 'fertilizer43', 'fertilizer43', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (44, 'fertilizer44', 'fertilizer44', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (45, 'fertilizer45', 'fertilizer45', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (46, 'fertilizer46', 'fertilizer46', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (47, 'fertilizer47', 'fertilizer47', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (48, 'fertilizer48', 'fertilizer48', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (49, 'fertilizer49', 'fertilizer49', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (50, 'fertilizer50', 'fertilizer50', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (51, 'fertilizer51', 'fertilizer51', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (52, 'fertilizer52', 'fertilizer52', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (53, 'fertilizer53', 'fertilizer53', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (54, 'fertilizer54', 'fertilizer54', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (55, 'fertilizer55', 'fertilizer55', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (56, 'fertilizer56', 'fertilizer56', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (57, 'fertilizer57', 'fertilizer57', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (58, 'fertilizer58', 'fertilizer58', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (59, 'fertilizer59', 'fertilizer59', 34.5);
INSERT INTO fertilizer (id, name, brand, price_per_gram) VALUES (60, 'fertilizer60', 'fertilizer60', 34.5);



--------------------------------  PESTICIDE  --------------------------------
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (1, 'pesticide1', 'pesticide1', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (2, 'pesticide2', 'pesticide2', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (3, 'pesticide3', 'pesticide3', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (4, 'pesticide4', 'pesticide4', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (5, 'pesticide5', 'pesticide5', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (6, 'pesticide6', 'pesticide6', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (7, 'pesticide7', 'pesticide7', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (8, 'pesticide8', 'pesticide8', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (9, 'pesticide9', 'pesticide9', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (10, 'pesticide10', 'pesticide10', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (11, 'pesticide11', 'pesticide11', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (12, 'pesticide12', 'pesticide12', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (13, 'pesticide13', 'pesticide13', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (14, 'pesticide14', 'pesticide14', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (15, 'pesticide15', 'pesticide15', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (16, 'pesticide16', 'pesticide16', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (17, 'pesticide17', 'pesticide17', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (18, 'pesticide18', 'pesticide18', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (19, 'pesticide19', 'pesticide19', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (20, 'pesticide20', 'pesticide20', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (21, 'pesticide21', 'pesticide21', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (22, 'pesticide22', 'pesticide22', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (23, 'pesticide23', 'pesticide23', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (24, 'pesticide24', 'pesticide24', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (25, 'pesticide25', 'pesticide25', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (26, 'pesticide26', 'pesticide26', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (27, 'pesticide27', 'pesticide27', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (28, 'pesticide28', 'pesticide28', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (29, 'pesticide29', 'pesticide29', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (30, 'pesticide30', 'pesticide30', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (31, 'pesticide31', 'pesticide31', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (32, 'pesticide32', 'pesticide32', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (33, 'pesticide33', 'pesticide33', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (34, 'pesticide34', 'pesticide34', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (35, 'pesticide35', 'pesticide35', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (36, 'pesticide36', 'pesticide36', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (37, 'pesticide37', 'pesticide37', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (38, 'pesticide38', 'pesticide38', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (39, 'pesticide39', 'pesticide39', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (40, 'pesticide40', 'pesticide40', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (41, 'pesticide41', 'pesticide41', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (42, 'pesticide42', 'pesticide42', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (43, 'pesticide43', 'pesticide43', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (44, 'pesticide44', 'pesticide44', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (45, 'pesticide45', 'pesticide45', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (46, 'pesticide46', 'pesticide46', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (47, 'pesticide47', 'pesticide47', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (48, 'pesticide48', 'pesticide48', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (49, 'pesticide49', 'pesticide49', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (50, 'pesticide50', 'pesticide50', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (51, 'pesticide51', 'pesticide51', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (52, 'pesticide52', 'pesticide52', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (53, 'pesticide53', 'pesticide53', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (54, 'pesticide54', 'pesticide54', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (55, 'pesticide55', 'pesticide55', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (56, 'pesticide56', 'pesticide56', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (57, 'pesticide57', 'pesticide57', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (58, 'pesticide58', 'pesticide58', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (59, 'pesticide59', 'pesticide59', 34.5);
INSERT INTO pesticide (id, name, brand, price_per_gram) VALUES (60, 'pesticide60', 'pesticide60', 34.5);



--------------------------------  CROP_TYPE  --------------------------------
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (1,  'TEMPLADO',     'Trigo',     100, 120, 2, 30, 1, 45, 1,  1);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (2,  'TEMPLADO',     'Manzana',   100, 120, 2, 30, 1, 45, 1,  1);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (3,  'TEMPLADO',     'Avena',     100, 120, 2, 30, 1, 45, 2,  1);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (4,  'TEMPLADO',     'Cebada',    100, 120, 2, 30, 1, 45, 3,  1);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (5,  'TEMPLADO',     'Remolacha', 100, 120, 2, 30, 1, 45, 4,  2);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (6,  'TROPICAL', 'Banano',    100, 120, 2, 30, 1, 45, 2,  2);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (7,  'TROPICAL', 'Papaya',    100, 120, 2, 30, 1, 45, 1,  3);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (8,  'TROPICAL', 'Mango',     100, 120, 2, 30, 1, 45, 5,  7);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (9,  'TROPICAL', 'Piña',      100, 120, 2, 30, 1, 45, 6,  8);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (10, 'TROPICAL', 'Coco',      100, 120, 2, 30, 1, 45, 7,  9);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (11, 'FRIO',     'Fresa',     100, 120, 2, 30, 1, 45, 8,  10);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (12, 'FRIO',     'Uva',       100, 120, 2, 30, 1, 45, 9,  3);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (13, 'FRIO',     'Arándano',  100, 120, 2, 30, 1, 45, 10, 4);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (14, 'FRIO',     'Col',       100, 120, 2, 30, 1, 45, 1,  5);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (15, 'FRIO',     'Zanahoria', 100, 120, 2, 30, 1, 45, 1,  6);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (16, 'CALIDO',      'Guayaba',   100, 120, 2, 30, 1, 45, 1,  7);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (17, 'CALIDO',      'Maíz',      100, 120, 2, 30, 1, 45, 1,  8);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (18, 'CALIDO',      'Sandía',    100, 120, 2, 30, 1, 45, 1,  9);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (19, 'CALIDO',      'Chile',     100, 120, 2, 30, 1, 45, 1,  10);
INSERT INTO crop_type (id, weather, name, plant_quantity_per_square_meter, harvest_time, fertilizer_quantity_per_plant, fertilizer_frequency, pesticide_quantity_per_plant, pesticide_frequency, fertilizer_id, pesticide_id) VALUES (20, 'CALIDO',      'Tomate',    100, 120, 2, 30, 1, 45, 1,  11);



---------------------------------  PROJECT  ---------------------------------
INSERT INTO project (id, crop_type_id, name, status, start_date, end_date, municipality, total_budget, image_url) VALUES(1,  1,  'Proyecto 1',  'CREATED', '2023-01-01', '2023-12-31', 'Municipio 1',  10000, 'assets/images/bg-01.jpg');
INSERT INTO project (id, crop_type_id, name, status, start_date, end_date, municipality, total_budget, image_url) VALUES(2,  2,  'Proyecto 2',  'CREATED', '2023-01-01', '2023-12-31', 'Municipio 2',  10000, 'assets/images/info.jpg');
INSERT INTO project (id, crop_type_id, name, status, start_date, end_date, municipality, total_budget, image_url) VALUES(3,  3,  'Proyecto 3',  'CREATED', '2023-01-01', '2023-12-31', 'Municipio 3',  10000, 'assets/images/bg-verde-01.avif');
INSERT INTO project (id, crop_type_id, name, status, start_date, end_date, municipality, total_budget, image_url) VALUES(4,  4,  'Proyecto 4',  'CREATED', '2023-01-01', '2023-12-31', 'Municipio 4',  10000, 'assets/images/bg-verde-02.avif');
INSERT INTO project (id, crop_type_id, name, status, start_date, end_date, municipality, total_budget, image_url) VALUES(5,  1,  'Proyecto 5',  'CREATED', '2023-01-01', '2023-12-31', 'Municipio 5',  10000, 'assets/images/bg-verde-03.jpg');
INSERT INTO project (id, crop_type_id, name, status, start_date, end_date, municipality, total_budget, image_url) VALUES(6,  1,  'Proyecto 6',  'CREATED', '2023-01-01', '2023-12-31', 'Municipio 6',  10000, 'assets/images/bg-verde-04.jpg');
INSERT INTO project (id, crop_type_id, name, status, start_date, end_date, municipality, total_budget, image_url) VALUES(7,  1,  'Proyecto 7',  'CREATED', '2023-01-01', '2023-12-31', 'Municipio 7',  10000, 'assets/images/bg-verde-05.jpg');
INSERT INTO project (id, crop_type_id, name, status, start_date, end_date, municipality, total_budget, image_url) VALUES(8,  8,  'Proyecto 8',  'CREATED', '2023-01-01', '2023-12-31', 'Municipio 8',  10000, 'assets/images/bg-verde-06.avif');
INSERT INTO project (id, crop_type_id, name, status, start_date, end_date, municipality, total_budget, image_url) VALUES(9,  2,  'Proyecto 9',  'CLOSED',  '2023-01-01', '2023-12-31', 'Municipio 9',  10000, 'assets/images/bg-verde-07.jpg');
INSERT INTO project (id, crop_type_id, name, status, start_date, end_date, municipality, total_budget, image_url) VALUES(10, 10, 'Proyecto 10', 'CREATED', '2023-01-01', '2023-12-31', 'Municipio 10', 10000, 'assets/images/bg-verde-08.jpg');



---------------------------  PROJECT_APPLICATION ---------------------------
INSERT INTO project_application (id, project_id, applicant_id, application_status, application_date, review_date, admin_comment) VALUES (1, 1, 2, 'ACCEPTED', '2023-01-02', '2023-03-03', 'Aprobado');
INSERT INTO project_application (id, project_id, applicant_id, application_status, application_date, review_date, admin_comment) VALUES (2, 1, 2, 'ACCEPTED', '2023-01-02', '2023-03-03', 'Aprobado');
INSERT INTO project_application (id, project_id, applicant_id, application_status, application_date, review_date, admin_comment) VALUES (3, 1, 2, 'REJECTED', '2023-01-02', NULL,         'Revisión pendiente');
INSERT INTO project_application (id, project_id, applicant_id, application_status, application_date, review_date, admin_comment) VALUES (4, 1, 2, 'PENDING',  '2023-01-02', NULL,         'Rechazado');


----------------------------------  CROP ----------------------------------
INSERT INTO crop (id, user_id, project_id, project_application_id, name, status, start_date, end_date, expected_expense, assigned_budget, sale_value, area) VALUES (1, 2, 1, 1, 'Finca la quinciañera', 'CREATED', '2023-01-01', '2023-06-01', 500.00, 1000.00, 1200.00, 100);
INSERT INTO crop (id, user_id, project_id, project_application_id, name, status, start_date, end_date, expected_expense, assigned_budget, sale_value, area) VALUES (2, 2, 1, 2, 'Finca la veterana',     'CLOSED', '2023-02-01', '2023-07-20', 300.00, 900.00,  950.00,  200);
--INSERT INTO crop (user_id, project_id, name, status, start_date, end_date, expected_expense, assigned_budget, sale_value, area) VALUES (3, 3, 'Cultivo de Cebada',    'CREATED', '2023-03-01', NULL,         200.00, 800.00, 850.00, 150);
--INSERT INTO crop (user_id, project_id, name, status, start_date, end_date, expected_expense, assigned_budget, sale_value, area) VALUES (1, 1, 'Cultivo de Soya',      'CLOSED',  '2023-01-15', '2023-08-10', 400.00, 1100.00, 1300.00, 180);
--INSERT INTO crop (user_id, project_id, name, status, start_date, end_date, expected_expense, assigned_budget, sale_value, area) VALUES (2, 2, 'Cultivo de Arroz',     'CREATED', '2023-01-20', '2023-09-30', 600.00, 1200.00, 1250.00, 160);
--INSERT INTO crop (user_id, project_id, name, status, start_date, end_date, expected_expense, assigned_budget, sale_value, area) VALUES (3, 3, 'Cultivo de Sorgo',     'CLOSED',  '2023-02-10', '2023-08-15', 350.00, 950.00, 1000.00, 190);
--INSERT INTO crop (user_id, project_id, name, status, start_date, end_date, expected_expense, assigned_budget, sale_value, area) VALUES (1, 1, 'Cultivo de Alfalfa',   'CREATED', '2023-03-05', '2023-08-25', 250.00, 850.00, 900.00, 120);
--INSERT INTO crop (user_id, project_id, name, status, start_date, end_date, expected_expense, assigned_budget, sale_value, area) VALUES (2, 2, 'Cultivo de Lentejas',  'CLOSED',  '2023-03-20', '2023-10-05', 450.00, 1050.00, 1150.00, 130);
--INSERT INTO crop (user_id, project_id, name, status, start_date, end_date, expected_expense, assigned_budget, sale_value, area) VALUES (3, 3, 'Cultivo de Garbanzos', 'CREATED', '2023-04-01', NULL,         500.00, 1000.00, 1100.00, 140);
--INSERT INTO crop (user_id, project_id, name, status, start_date, end_date, expected_expense, assigned_budget, sale_value, area) VALUES (1, 1, 'Cultivo de Papas',     'CLOSED',  '2023-05-01', '2023-10-20', 550.00, 1150.00, 1180.00, 170);
