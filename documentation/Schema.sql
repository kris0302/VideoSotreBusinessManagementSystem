create table user(
	email varchar(100) not null,
    password varchar(30) not null,
    firstname varchar(30),
    lastname varchar(30),
    address varchar(200) not null,
    province varchar(5) not null,
    rewardpoints int default 0,
    amountDue double default 0,
    cart varchar(200),
    latefee float,
    primary key (email)
);

create table admin(
	email varchar(100) not null,
    password varchar(100) not null,
    primary key (email)
);
create table operator(
	email varchar(100) not null,
    password varchar(100) not null,
    cart varchar(200),
    primary key (email)
);
create table warehouseadmin(
	email varchar(100) not null,
    password varchar(100) not null,
    primary key (email)
);


create table movie(
	title varchar(100) not null,
    cat varchar(30) not null,
    director varchar(50),
    actors varchar(200),
    description varchar(300),
    price double not null,
    availablity int not null,
    primary key (title)
);


create table orders(
	id varchar(30) not null,
    title varchar(100) not null,
    customer varchar(100) not null,
    orderdate date not null,
    totalamount float not null,
    status varchar(30) not null,
    address varchar(200) not null,
    province varchar(20) not null,
    latedays int not null,
    warehouse varchar(200) not null,
    delivery varchar(20) not null,
    primary key (id)
);

drop table admin;
drop table operator;
drop table warehouseadmin;
drop table movie;
drop table orders;
drop table user;

select * from admin;
select * from operator;
select * from warehouseadmin;
select * from movie;
select * from orders;
select * from user;
