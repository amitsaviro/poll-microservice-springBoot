
DROP TABLE IF EXISTS poll;
DROP TABLE IF EXISTS user_poll;

CREATE TABLE poll (
    id int(11) unsigned NOT NULL AUTO_INCREMENT,
    title varchar(300) NOT NULL DEFAULT '',
    first_answer varchar(300) NOT NULL DEFAULT '',
    second_answer varchar(300) NOT NULL DEFAULT '',
    third_answer varchar(300) NOT NULL DEFAULT '',
    fourth_answer varchar(300) NOT NULL DEFAULT '',
    PRIMARY KEY (id)
);
CREATE TABLE user_poll (
    id int(11) unsigned NOT NULL AUTO_INCREMENT,
    user_id BIGINT(11) NOT NULL,
    poll_id BIGINT(11) NOT NULL,
    answer varchar(300) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (poll_id) REFERENCES poll (id)
);




