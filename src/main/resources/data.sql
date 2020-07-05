insert into user(id,username,password,email,first_name,last_name) values
  (1,'admin','$2y$11$6TP/e9ycvO4DwriSjfGip.6OVPNKuRArNyb4phwx9Kk3WPsdVjWUq','cjp@tuta.io','Sidyey','Pi');
insert into role(id,name) values (1,'ROLE_ADMIN');
insert into users_roles(user_id,role_id) values(1,1);