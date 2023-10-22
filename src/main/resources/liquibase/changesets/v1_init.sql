create table if not exists users(
    id bigint not null primary key generated by default as identity ,
    chat_id bigint not null ,
    name varchar(50) not null,
    username varchar(50),
    bot_state int not null default 0,
    constraint unique_name_chat_id unique (chat_id, name)
);

create table if not exists orders(
    id bigint not null primary key generated by default as identity ,
    chat_id bigint not null,
    value varchar,
    comment varchar(400),
    departure varchar(20),
    destination varchar(20),
    confirmed boolean default false,
    sended boolean default false,
    with_owner boolean,
    type varchar(20) not null
);