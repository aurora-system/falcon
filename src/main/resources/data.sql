insert into users(id,username,password,email,first_name,last_name) values(1,'admin','$2y$11$6TP/e9ycvO4DwriSjfGip.6OVPNKuRArNyb4phwx9Kk3WPsdVjWUq','cjp@tuta.io','Sidyey','Pi') on conflict (id) do nothing;
insert into users(id,username,password,email,first_name,last_name) values(2,'user','$2y$11$6TP/e9ycvO4DwriSjfGip.6OVPNKuRArNyb4phwx9Kk3WPsdVjWUq','cjp@tutanota.de','LA','Lang') on conflict (id) do nothing;
insert into role(id,name) values(1,'ROLE_ADMIN') on conflict (id) do nothing;
insert into role(id,name) values(2,'ROLE_USER') on conflict (id) do nothing;
insert into users_roles(user_id,role_id) values(1,1) on conflict (id) do nothing;
insert into users_roles(user_id,role_id) values(2,2) on conflict (id) do nothing;

insert into product_category(id,name) values(1,'Body and Paint') on conflict (id) do nothing;

insert into product(id,category_id,name,brand,for_vehicle,color,aqui_price,srp,other_details,supplier_name,stock_level,threshold) values(1,1,'Bumper1','Brand X','Vios','Bronze Mica Metallic',2000,2500,'brand new','Secret Supplier',20,10) on conflict (id) do nothing;
insert into product(id,category_id,name,brand,for_vehicle,color,aqui_price,srp,other_details,supplier_name,stock_level,threshold) values(2,1,'Bumper2','Brand X','Vios','Bronze Mica Metallic',2000,2500,'brand new','Secret Supplier',20,10) on conflict (id) do nothing;
insert into product(id,category_id,name,brand,for_vehicle,color,aqui_price,srp,other_details,supplier_name,stock_level,threshold) values(3,1,'Bumper3','Brand X','Vios','Bronze Mica Metallic',2000,2500,'brand new','Secret Supplier',20,10) on conflict (id) do nothing;
insert into product(id,category_id,name,brand,for_vehicle,color,aqui_price,srp,other_details,supplier_name,stock_level,threshold) values(4,1,'Bumper4','Brand X','Vios','Bronze Mica Metallic',2000,2500,'brand new','Secret Supplier',20,10) on conflict (id) do nothing;
insert into product(id,category_id,name,brand,for_vehicle,color,aqui_price,srp,other_details,supplier_name,stock_level,threshold) values(5,1,'Bumper5','Brand X','Vios','Bronze Mica Metallic',2000,2500,'brand new','Secret Supplier',20,10) on conflict (id) do nothing;
  
insert into in_out_history(id,trans_date,trans_type,product_id,product_count,updated_count) values(1,'2020-07-01','IN',1,5,25) on conflict (id) do nothing;
insert into in_out_history(id,trans_date,trans_type,product_id,product_count,updated_count) values(2,'2020-07-01','OUT',1,5,20) on conflict (id) do nothing;