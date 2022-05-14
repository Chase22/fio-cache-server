create table cache
(
    path      text  not null,
    timestamp timestamptz  not null,
    data      text not null,

    primary key (path, timestamp)
)