CREATE TABLE IF NOT EXISTS Street (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    postal_code INT NOT NULL
    );


CREATE TABLE IF NOT EXISTS House (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    build_date DATE NOT NULL,
    num_floors INT NOT NULL,
    type VARCHAR(255) NOT NULL,
    street_id INT,
    FOREIGN KEY (street_id) REFERENCES Street(id) ON DELETE SET NULL
    );