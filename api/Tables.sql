create table categories(
c_id int not null primary key identity(1,1),
c_en varchar(MAX) not null,
c_ru varchar(MAX) not null,
c_ch varchar(MAX) not null
)

create table words(
w_id int not null primary key identity(1,1),
c_id int not null,
w_en varchar(MAX) not null,
w_ru varchar(MAX) not null,
w_ch varchar(MAX) not null,
w_ge varchar(MAX) not null,
image1_link varchar(MAX) not null,
image2_link varchar(MAX) not null,
image3_link varchar(MAX) not null,
image4_link varchar(MAX) not null,
CONSTRAINT fk_categories FOREIGN KEY(c_id)
	REFERENCES categories(c_id)
)

create table dbinfo(
d_id int not null primary key identity(1,1),
version_num int not null,
comment varchar(MAX) null
)

insert into dbinfo(version_num, comment)
values(1,'init')