DROP TABLE IF EXISTS user_publishing_house_t;
DROP TABLE IF EXISTS publication_t;
DROP TABLE IF EXISTS publishing_house_t;
DROP TABLE IF EXISTS user_t;

CREATE TABLE user_t
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(15) NOT NULL UNIQUE,
    password BYTEA NOT NULL,
    email VARCHAR NOT NULL,
    role VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE publishing_house_t
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    title VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    main_image VARCHAR,
    subscripts_count INT DEFAULT 0 NOT NULL,
    view_count INT DEFAULT 0 NOT NULL ,
    subscription_price_usd INT DEFAULT 0 NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE publication_t
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    title VARCHAR NOT NULL,
    content VARCHAR NOT NULL,
    main_image VARCHAR,
    publishing_house_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_publishing_house
        FOREIGN KEY (publishing_house_id)
            REFERENCES publishing_house_t (id)
                ON DELETE CASCADE
);


CREATE TABLE user_publishing_house_t
(
    user_id BIGINT NOT NULL ,
    publishing_house_id BIGINT NOT NULL ,
    CONSTRAINT user_publishing_house_pk
        PRIMARY KEY (user_id, publishing_house_id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES user_t (id)
                ON DELETE CASCADE,
    CONSTRAINT fk_publishing_house
        FOREIGN KEY (publishing_house_id)
            REFERENCES publishing_house_t (id)
                ON DELETE CASCADE
);
