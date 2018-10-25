create table profile (
	name varchar(255) not null,
	constraint profile_name_pk primary key (name)
);

create table field_permissions (
	profile_name varchar(255) not null,
	editable smallint not null,
	field varchar(255) not null,
	readable smallint not null,
	constraint field_permissions_fk foreign key (profile_name) references profile (name)
);