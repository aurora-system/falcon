insert into users(id,username,password,email,first_name,last_name) values(1,'admin','$2y$11$6TP/e9ycvO4DwriSjfGip.6OVPNKuRArNyb4phwx9Kk3WPsdVjWUq','cjp@tuta.io','Sidyey','Pi') on conflict (id) do nothing;
insert into users(id,username,password,email,first_name,last_name) values(2,'user','$2y$11$6TP/e9ycvO4DwriSjfGip.6OVPNKuRArNyb4phwx9Kk3WPsdVjWUq','cjp@tutanota.de','LA','Lang') on conflict (id) do nothing;

insert into user_roles(user_id,roles) values(1,'ROLE_ADMIN') on conflict (id) do nothing;
insert into user_roles(user_id,roles) values(2,'ROLE_USER') on conflict (id) do nothing;
