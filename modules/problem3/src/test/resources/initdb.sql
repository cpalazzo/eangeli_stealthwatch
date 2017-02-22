DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS organization;
CREATE TABLE members(
                             ID INTEGER NOT NULL,
                             NAME VARCHAR(256),
                             ADDRESS VARCHAR(256),
                             PHONE VARCHAR(256),
                             AGE INTEGER
                           );
CREATE TABLE organization(
                             ID INTEGER NOT NULL,
                             MEMBER_ID INTEGER NOT NULL,
                             LOCATION VARCHAR(256),
                             DUES DECIMAL
                           );
insert into members values (1,'Alvin','1 Treehouse Lane', '1234567890', 42);
insert into members values (2,'Simon','1 Treehouse Lane', '1234567890', 45);
insert into members values (3,'Theodore','1 Treehouse Lane', '1234567890', 46);
insert into members values (4,'Dave','1 Treehouse Lane', '1234567890', 50);
insert into members values (5,'Britney','2 Treehouse Lane', '2345678901', 35);
insert into organization values (1,1,'north', 2.00);
insert into organization values (1,2,'south', 2.00);
insert into organization values (1,3,'east', 0.00);
insert into organization values (1,4,'west', 2.00);
insert into organization values (2,1,'north', 0.99);
insert into organization values (3,1,'north', 0.99);
insert into organization values (2,4,'under the stairs', 0.99);
insert into organization values (5,6,'south', 3.14);