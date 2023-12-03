-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- member Table Create SQL
-- 테이블 생성 SQL - member
CREATE TABLE member
(
    `member_id`     INT             NOT NULL    AUTO_INCREMENT,
    `email`         VARCHAR(50)     NOT NULL,
    `nickname`      VARCHAR(50)     NOT NULL,
    `thumbnail`     VARCHAR(255)    NOT NULL,
    `role`          VARCHAR(50)     NOT NULL,
    `oauth_domain`  VARCHAR(50)     NOT NULL,
    `oauth_id`      VARCHAR(255)    NOT NULL,
    `created_at`    DATETIME        NOT NULL,
    `updated_at`    DATETIME        NOT NULL,
    PRIMARY KEY (member_id)
);

-- Unique Index 설정 SQL - member(oauth_id, oauth_domain)
CREATE UNIQUE INDEX UQ_member_1
    ON member(oauth_id, oauth_domain);


-- place Table Create SQL
-- 테이블 생성 SQL - place
CREATE TABLE place
(
    `place_id`    INT              NOT NULL    AUTO_INCREMENT,
    `name`        VARCHAR(255)     NOT NULL,
    `introduce`   VARCHAR(1000)    NULL,
    `address`     VARCHAR(255)     NOT NULL,
    `lat`         DOUBLE           NOT NULL,
    `lng`         DOUBLE           NOT NULL,
    `thumbnail`   VARCHAR(255)     NULL,
    `place_type`  VARCHAR(50)      NOT NULL,
    `created_at`  DATETIME         NOT NULL,
    `updated_at`  DATETIME         NOT NULL,
    PRIMARY KEY (place_id)
);

-- Unique Index 설정 SQL - place(place_type, name, address)
CREATE UNIQUE INDEX UQ_place_1
    ON place(place_type, name, address);


-- plan Table Create SQL
-- 테이블 생성 SQL - plan
CREATE TABLE plan
(
    `plan_id`     INT             NOT NULL    AUTO_INCREMENT,
    `member_id`   INT             NOT NULL,
    `title`       VARCHAR(255)    NOT NULL,
    `created_at`  DATETIME        NOT NULL,
    `updated_at`  DATETIME        NOT NULL,
    PRIMARY KEY (plan_id)
);

-- Foreign Key 설정 SQL - plan(member_id) -> member(member_id)
ALTER TABLE plan
    ADD CONSTRAINT FK_plan_member_id_member_member_id FOREIGN KEY (member_id)
        REFERENCES member (member_id) ON DELETE CASCADE ON UPDATE CASCADE;

-- Foreign Key 삭제 SQL - plan(member_id)
-- ALTER TABLE plan
-- DROP FOREIGN KEY FK_plan_member_id_member_member_id;


-- record Table Create SQL
-- 테이블 생성 SQL - record
CREATE TABLE record
(
    `record_id`     INT         NOT NULL    AUTO_INCREMENT,
    `place_id`      INT         NULL        COMMENT '장소는 없을 수 있음',
    `plan_id`       INT         NOT NULL,
    `content`       TEXT        NULL,
    `is_highlight`  TINYINT     NULL,
    `created_at`    DATETIME    NULL,
    `updated_at`    DATETIME    NULL,
    PRIMARY KEY (record_id)
);

-- Foreign Key 설정 SQL - record(plan_id) -> plan(plan_id)
ALTER TABLE record
    ADD CONSTRAINT FK_record_plan_id_plan_plan_id FOREIGN KEY (plan_id)
        REFERENCES plan (plan_id) ON DELETE CASCADE ON UPDATE CASCADE;

-- Foreign Key 삭제 SQL - record(plan_id)
-- ALTER TABLE record
-- DROP FOREIGN KEY FK_record_plan_id_plan_plan_id;

-- Foreign Key 설정 SQL - record(place_id) -> place(place_id)
ALTER TABLE record
    ADD CONSTRAINT FK_record_place_id_place_place_id FOREIGN KEY (place_id)
        REFERENCES place (place_id) ON DELETE SET NULL ON UPDATE CASCADE;

-- Foreign Key 삭제 SQL - record(place_id)
-- ALTER TABLE record
-- DROP FOREIGN KEY FK_record_place_id_place_place_id;


-- DailyYoutube Table Create SQL
-- 테이블 생성 SQL - DailyYoutube
CREATE TABLE daily_youtube
(
    `daily_youtube_id`  INT             NOT NULL    AUTO_INCREMENT,
    `code`              VARCHAR(255)    NOT NULL,
    PRIMARY KEY (daily_youtube_id)
);


-- record_image Table Create SQL
-- 테이블 생성 SQL - record_image
CREATE TABLE record_image
(
    `record_image_id`  INT             NOT NULL    AUTO_INCREMENT,
    `record_id`        INT             NOT NULL,
    `url`              VARCHAR(255)    NOT NULL,
    `created_at`       VARCHAR(50)     NOT NULL,
    `updated_at`       VARCHAR(50)     NOT NULL,
    PRIMARY KEY (record_image_id)
);

-- Foreign Key 설정 SQL - record_image(record_id) -> record(record_id)
ALTER TABLE record_image
    ADD CONSTRAINT FK_record_image_record_id_record_record_id FOREIGN KEY (record_id)
        REFERENCES record (record_id) ON DELETE CASCADE ON UPDATE CASCADE;

-- Foreign Key 삭제 SQL - record_image(record_id)
-- ALTER TABLE record_image
-- DROP FOREIGN KEY FK_record_image_record_id_record_record_id;


-- destination Table Create SQL
-- 테이블 생성 SQL - destination
CREATE TABLE destination
(
    `destination_id`  INT             NOT NULL    AUTO_INCREMENT,
    `plan_id`         INT             NOT NULL,
    `place_id`        INT             NOT NULL,
    `visited_at`      DATETIME        NOT NULL,
    `comment`         VARCHAR(255)    NULL,
    `created_at`      DATETIME        NOT NULL,
    `updated_at`      DATETIME        NOT NULL,
    PRIMARY KEY (destination_id)
);

-- Unique Index 설정 SQL - destination(plan_id, place_id, visited_at)
CREATE UNIQUE INDEX UQ_destination_1
    ON destination(plan_id, place_id, visited_at);

-- Foreign Key 설정 SQL - destination(plan_id) -> plan(plan_id)
ALTER TABLE destination
    ADD CONSTRAINT FK_destination_plan_id_plan_plan_id FOREIGN KEY (plan_id)
        REFERENCES plan (plan_id) ON DELETE CASCADE ON UPDATE CASCADE;

-- Foreign Key 삭제 SQL - destination(plan_id)
-- ALTER TABLE destination
-- DROP FOREIGN KEY FK_destination_plan_id_plan_plan_id;

-- Foreign Key 설정 SQL - destination(place_id) -> place(place_id)
ALTER TABLE destination
    ADD CONSTRAINT FK_destination_place_id_place_place_id FOREIGN KEY (place_id)
        REFERENCES place (place_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - destination(place_id)
-- ALTER TABLE destination
-- DROP FOREIGN KEY FK_destination_place_id_place_place_id;


