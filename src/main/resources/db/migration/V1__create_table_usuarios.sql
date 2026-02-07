-- Primero creamos la tabla que no depende de nadie
create table usuarios (
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    apellido varchar(100) not null,
    email varchar(100) not null unique,
    password varchar(255) not null,
    primary key (id)
);