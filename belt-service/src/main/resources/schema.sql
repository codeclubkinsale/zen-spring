CREATE TABLE IF NOT EXISTS Belt (
                                    id INT NOT NULL,
                                    name varchar(250) NOT NULL,
                                    description varchar(250) NOT NULL,
    image_url text NOT NULL,
    version int,
    PRIMARY KEY (id)
    );