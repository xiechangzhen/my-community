create table user
(
    id bigint auto_increment primary key not null,
    account_id varchar(100),
    avatar_url VARCHAR (100),
    name varchar(50),
    token varchar(36),
    gmt_create bigint,
    gmt_modified bigint
) engine=innodb ,charset=utf8;