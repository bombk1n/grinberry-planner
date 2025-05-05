CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE user_entity_roles (
                                   user_entity_id BIGINT NOT NULL,
                                   roles VARCHAR(255) NOT NULL CHECK (roles IN ('USER','ADMIN')),
                                   PRIMARY KEY (user_entity_id, roles),
                                   CONSTRAINT fk_user FOREIGN KEY (user_entity_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE tasks (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description VARCHAR(255),
                       status VARCHAR(255) CHECK (status IN ('IN_PROGRESS','COMPLETED')),
                       created_at TIMESTAMP NOT NULL,
                       deadline TIMESTAMP,
                       user_id BIGINT,
                       CONSTRAINT fk_user_task FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);