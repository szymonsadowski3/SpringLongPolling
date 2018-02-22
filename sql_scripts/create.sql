CREATE TABLE app_user
(
  userId   INT AUTO_INCREMENT
    PRIMARY KEY,
  username VARCHAR(255) NULL,
  hashed   TEXT         NULL,
  CONSTRAINT app_user_username_uindex
  UNIQUE (username)
)
  ENGINE = InnoDB;

CREATE TABLE notification
(
  notificationId INT AUTO_INCREMENT
PRIMARY KEY,
  content        TEXT                                NULL,
  importance     INT                                 NULL,
  authorName     VARCHAR(255)                        NOT NULL,
  title          VARCHAR(255)                        NULL,
  createdOn      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL
)
  ENGINE = InnoDB;

