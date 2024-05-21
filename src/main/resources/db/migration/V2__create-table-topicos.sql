
create table topico(

    id_topico bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(100) not null,
    fechaCreacion DATE not null,
    status tinyint not null,
    curso_id bigint not null,
    primary key(id_topico),
    constraint fk_topico_curso_id foreign key(curso_id) references curso(id_curso)

);
