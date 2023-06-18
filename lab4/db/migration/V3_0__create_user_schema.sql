CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    CONSTRAINT unique_username UNIQUE (username),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    accountNonExpired BOOLEAN NOT NULL,
    accountNonLocked BOOLEAN NOT NULL,
    credentialsNonExpired BOOLEAN NOT NULL,
    enabled BOOLEAN NOT NULL,
    street_id INT UNIQUE,
    FOREIGN KEY (street_id) REFERENCES street (id),
    CHECK ((role <> 'ROLE_ADMIN' AND street_id IS NOT NULL) OR (role = 'ROLE_ADMIN' AND street_id IS NULL))
    );