USE Shipe;
GO
create table shop (
	shop_id int identity(1,1) primary key,
	name nvarchar(100),
	address nvarchar(100),
	open_time time,
	close_time time
);
GO
create table users (
	user_id int identity(1,1) primary key,
	username varchar(30),
	password varchar(100),
	firstname nvarchar(30),
	lastname nvarchar(30),
	birth_date datetime,
	email varchar(50),
	type char,
	address nvarchar(100),
	phone varchar(15),
	created_date date default getdate(),
	constraint u_username unique (username)
);
GO
create table shipper (
	user_id int,
	plate_number varchar(15),
	vehicle nvarchar(20),
	vehicle_color varchar(10),
	is_active varchar(6) default 'TRUE'
);
GO
create table category (
	category_id int identity(1,1) primary key,
	name nvarchar(30),
	description nvarchar(200)
);
GO
create table items (
	item_id int identity(1,1) primary key,
	name nvarchar(50),
	description nvarchar(200),
	thumnail varchar(100),
	price int,
	category int,
	status char default 'N',
	created_date datetime default getdate(),
	updated_date datetime,
);
GO
create table review (
	review_id int identity(1,1) primary key,
	user_id int,
	type char,
	objectid int,
	item_id int,
	rating int,
	comment nvarchar(200),
	created_date datetime default getdate(),
	updated_date datetime
);
GO
create table bill (
	bill_id int identity(1,1) primary key,
	customer int,
	created_date datetime default getdate(),
	description nvarchar(100),
	total_price bigint,
	ship_charge int,
	accepted varchar(6) default '',
	status char default 'N',
	shipper int,
	deliver_time datetime,
	is_completed varchar(6) default 'FALSE'
);
GO
create table bill_detail (
	bill_id int,
	item int,
	amount int,
);
GO
create table payment (
	payment_id int identity(1,1) primary key,
	bill_id int,
	payment_date datetime default getdate(),
	type char default 'C',
	amount bigint,
	description nvarchar(100),
	customer int,
	receiver int
);
GO