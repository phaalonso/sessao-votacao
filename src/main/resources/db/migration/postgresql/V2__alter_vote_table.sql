drop table vote;

create table vote
(
    vote_session_id int        not null,
    associate_id    int        not null,
    vote            varchar(3) not null,
    primary key (vote_session_id, associate_id),
    foreign key (vote_session_id) references vote_session (id)
);
