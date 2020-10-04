DROP TABLE IF EXISTS user_publishing_house_t;
DROP TABLE IF EXISTS publication_t;
DROP TABLE IF EXISTS publishing_house_t;
DROP TABLE IF EXISTS user_t;

CREATE TABLE user_t
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(15) NOT NULL UNIQUE,
    password VARCHAR(15) NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    role VARCHAR NOT NULL,
    balance INT DEFAULT 0 NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE publishing_house_t
(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR NOT NULL UNIQUE,
    description VARCHAR NOT NULL,
    main_image VARCHAR,
    subscription_price_usd INT DEFAULT 0 NOT NULL,
    theme VARCHAR NOT NULL,
    type_field VARCHAR NOT NULL,
    PRIMARY KEY (id)
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
