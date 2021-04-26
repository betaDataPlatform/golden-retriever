CREATE SEQUENCE IF NOT EXISTS timescale_seq;

CREATE TABLE metric
(
    id   serial,
    name TEXT NOT NULL
);

ALTER TABLE metric
    ADD CONSTRAINT "PK_metric"
        PRIMARY KEY (id);
CREATE
UNIQUE INDEX "IDX_metric_name" ON metric (name);

CREATE TABLE tag
(
    id       serial,
    tag_json TEXT NOT NULL
);
ALTER TABLE tag
    ADD CONSTRAINT "PK_tag"
        PRIMARY KEY (id);

CREATE
UNIQUE INDEX "IDX_tag_json" ON tag (tag_json);

CREATE TABLE metric_tag
(
    metric_id INTEGER NOT NULL,
    tag_name  TEXT    NOT NULL,
    tag_value TEXT    NOT NULL,
    tag_id    INTEGER NOT NULL
);

ALTER TABLE metric_tag
    ADD CONSTRAINT "PK_metric_tag"
        PRIMARY KEY (metric_id, tag_name, tag_value, tag_id);

ALTER TABLE metric_tag
    ADD CONSTRAINT "FK_metric_tag_metric_id"
        FOREIGN KEY (metric_id)
            REFERENCES metric (id)
            ON DELETE CASCADE;

ALTER TABLE metric_tag
    ADD CONSTRAINT "FK_metric_tag_tag_id"
        FOREIGN KEY (tag_id)
            REFERENCES tag (id)
            ON DELETE CASCADE;

CREATE TABLE data_point
(
    event_time TIMESTAMP        NOT NULL,
    metric_id  INTEGER          NOT NULL,
    tag_id     INTEGER          NOT NULL,
    value      DOUBLE PRECISION NOT NULL
);

ALTER TABLE data_point
    ADD CONSTRAINT "PK_data_point"
        PRIMARY KEY (event_time, metric_id, tag_id);

CREATE
INDEX "IXFK_data_point_metric" ON data_point (metric_id);

CREATE
INDEX "IXFK_data_point_tag" ON data_point (tag_id);

SELECT create_hypertable('data_point', 'event_time');