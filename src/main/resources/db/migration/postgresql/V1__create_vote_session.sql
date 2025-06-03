create table pauta
(
    id          serial primary key,
    title       varchar(100) not null,
    description text         not null,
    creation_date timestamp default now(),
    update_date timestamp default now()
);

create table vote_session
(
    id             serial primary key,
    pauta_id       int       not null,
    positive_votes int       not null default 0,
    negative_votes int       not null default 0,
    total_votes    int       not null default 0,
    session_start  timestamp not null default now(),
    session_end    timestamp,
    foreign key (pauta_id) references pauta (id)
);

create table vote
(
    vote_id         serial not null,
    vote_session_id int    not null,
    positive_vote   bool   not null,
    primary key (vote_session_id, vote_id),
    foreign key (vote_session_id) references vote_session (id)
);
