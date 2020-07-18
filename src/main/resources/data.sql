insert into user(id,username,password,email,first_name,last_name) values
  (1,'admin','$2y$11$6TP/e9ycvO4DwriSjfGip.6OVPNKuRArNyb4phwx9Kk3WPsdVjWUq','cjp@tuta.io','Sidyey','Pi');
insert into role(id,name) values (1,'ROLE_ADMIN');
insert into users_roles(user_id,role_id) values(1,1);

insert into product_category(id,name) values (1,'Body and Paint');
insert into product(id,category_id,name,brand,for_vehicle,color,aqui_price,srp,other_details,supplier_name,stock_level,threshold) values
  (1,1,'Bumper','Brand X','Vios','Bronze Mica Metallic',2000,2500,'brand new','Secret Supplier',20,10)