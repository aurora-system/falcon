    
insert ignore into users(id,username,password,email,first_name,last_name) values
  (1,'admin','$2y$11$6TP/e9ycvO4DwriSjfGip.6OVPNKuRArNyb4phwx9Kk3WPsdVjWUq','cjp@tuta.io','Sidyey','Pi'),
  (2,'user','$2y$11$6TP/e9ycvO4DwriSjfGip.6OVPNKuRArNyb4phwx9Kk3WPsdVjWUq','cjp@tutanota.de','LA','Lang');
insert ignore into role(id,name) values 
  (1,'ROLE_ADMIN'),(2,'ROLE_USER');
insert ignore into users_roles(user_id,role_id) values (1,1),(2,2);

insert ignore into product_category(id,name) values (1,'Body and Paint');
insert ignore into product(id,category_id,name,brand,for_vehicle,color,aqui_price,srp,other_details,supplier_name,stock_level,threshold) values
  (1,1,'Bumper1','Brand X','Vios','Bronze Mica Metallic',2000,2500,'brand new','Secret Supplier',20,10),
  (2,1,'Bumper2','Brand X','Vios','Bronze Mica Metallic',2000,2500,'brand new','Secret Supplier',20,10),
  (3,1,'Bumper3','Brand X','Vios','Bronze Mica Metallic',2000,2500,'brand new','Secret Supplier',20,10),
  (4,1,'Bumper4','Brand X','Vios','Bronze Mica Metallic',2000,2500,'brand new','Secret Supplier',20,10),
  (5,1,'Bumper5','Brand X','Vios','Bronze Mica Metallic',2000,2500,'brand new','Secret Supplier',20,10);
  
insert ignore into in_out_history(id,trans_date,trans_type,product_id,product_count,updated_count) values
  (1,'2020-07-01','IN',1,5,25),
  (2,'2020-07-01','OUT',1,5,20);