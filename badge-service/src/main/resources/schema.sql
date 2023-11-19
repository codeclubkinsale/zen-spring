CREATE TABLE IF NOT EXISTS Badge (
    id INT NOT NULL,
    name varchar(250) NOT NULL,
    description text NOT NULL,
    image varchar(250) NOT NULL,
    version int,
    PRIMARY KEY (id)
);