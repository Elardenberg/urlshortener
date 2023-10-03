create table statistics(
    id SERIAL primary key,
    clickdate_time timestamp not null,
    shortenedurl_id bigint not null,
    foreign key(shortenedurl_id) references shortenedurl(id)
)