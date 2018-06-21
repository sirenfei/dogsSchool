create table if not exists tb_dogs (
  `id` int(11) unsigned NOT NULL primary key AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL
);

insert into tb_dogs( name) values ( 'Zidane');
insert into tb_dogs(name) values ( 'Maradona');
insert into tb_dogs(name) values ( 'Messi');
