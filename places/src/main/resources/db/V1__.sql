-- =========================================
-- TABLE : zones
-- =========================================
CREATE TABLE zone (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      hourly_rate DOUBLE PRECISION NOT NULL
);

ALTER TABLE zone
    ADD CONSTRAINT uc_zone_name UNIQUE (name);


-- =========================================
-- TABLE : parking_spot
-- =========================================
CREATE TABLE parking_spot (
                              id BIGSERIAL PRIMARY KEY,
                              code VARCHAR(255) NOT NULL,
                              type VARCHAR(255) NOT NULL,
                              active BOOLEAN NOT NULL,
                              zone_id BIGINT,
                              CONSTRAINT fk_parking_spot_zone
                                  FOREIGN KEY (zone_id) REFERENCES zone (id)
);

ALTER TABLE parking_spot
    ADD CONSTRAINT uc_parking_spot_code UNIQUE (code);


-- =========================================
-- TABLE : sensor
-- =========================================
CREATE TABLE sensor (
                        id BIGSERIAL PRIMARY KEY,
                        reference VARCHAR(255) NOT NULL,
                        type VARCHAR(255),
                        active BOOLEAN NOT NULL,
                        parking_spot_id BIGINT,
                        CONSTRAINT fk_sensor_parking_spot
                            FOREIGN KEY (parking_spot_id) REFERENCES parking_spot (id)
);

ALTER TABLE sensor
    ADD CONSTRAINT uc_sensor_reference UNIQUE (reference);


-- =========================================
-- TABLE : availability
-- =========================================
CREATE TABLE availability (
                              id BIGSERIAL PRIMARY KEY,
                              parking_spot_id BIGINT,
                              status VARCHAR(255) NOT NULL,
                              created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                              CONSTRAINT fk_availability_parking_spot
                                  FOREIGN KEY (parking_spot_id) REFERENCES parking_spot (id)
);
