
create table usuarios(

    id_usuario bigint not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null,
    login varchar(100) not null unique,
    contrasena varchar(100) not null,
    activo tinyint not null,

    primary key(id_usuario)

);
