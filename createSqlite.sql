CREATE TABLE app_user
(
  userId   INTEGER
    PRIMARY KEY
  AUTOINCREMENT,
  username VARCHAR(255),
  hashed   TEXT
);

CREATE UNIQUE INDEX app_user_username_uindex
  ON app_user (username);

-- auto-generated definition
CREATE TABLE notification
(
  notificationId INTEGER,
  content        TEXT,
  importance     INTEGER,
  authorName     VARCHAR(255) NOT NULL,
  title          VARCHAR(255),
  createdOn      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX notification_notificationId_uindex
  ON notification (notificationId);
