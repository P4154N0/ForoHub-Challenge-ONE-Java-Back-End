-- Luego creamos la tabla que depende de usuarios
create table topicos (
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(300) not null,
    fecha_de_creacion datetime not null,
    status varchar(100) not null,
    curso varchar(100) not null,
    usuario_id bigint not null, -- Cambiamos autor_id por usuario_id para ser coherentes
    primary key (id),
    -- Creamos la llave foránea de una vez
    constraint fk_topicos_usuario_id foreign key (usuario_id) references usuarios(id)
);