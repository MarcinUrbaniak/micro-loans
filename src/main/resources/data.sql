insert into user_account(id, email, first_name, last_name) values
(200,'urbanim@o2.pl','Anna','Wolniak'),
(300,'adres@o2.pl','Wojciech','Wolski'),
(400,'krzysztof@o2.pl','Krzysztof','Urbaniak');

insert into loan(id, amount, end_date, is_deferral, loan_period, start_date, user_account_id) VALUES
(400,3000,'2020-05-01',false,34,'2020-01-04', 200),
(500,3000,'2020-05-01',false,34,'2020-01-04', 200),
(600,3000,'2020-05-01',false,34,'2020-01-04', 300);

insert into loan_application(id, application_day, application_time, ip_address, loan_id, user_id) VALUES
(700,'2020-04-05','16:22','225.225.0.0',400,200);