create table shortenedURL(
    id SERIAL primary key,
    fullURL varchar(200) not null,
    shortURL varchar(10) not null
)