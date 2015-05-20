CREATE TABLE users
(
  id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
  login VARCHAR(100) NOT NULL,
  encrypted_password VARCHAR(1000) NOT NULL,
  registration_date TIMESTAMP NOT NULL,
  avatar_filename VARCHAR(100),
  PRIMARY KEY (id)
); 

CREATE TABLE nicks
(
  id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
  nickname VARCHAR(30) NOT NULL,
  user_id INTEGER NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE channels
(
  id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  topic VARCHAR(128) NOT NULL,
  description VARCHAR(1024),
  PRIMARY KEY (id) 
);

CREATE TABLE modes
(
  id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  attributes VARCHAR(20),
  channel_id INTEGER NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (channel_id) REFERENCES channels(id)
);

CREATE TABLE nicks_in_channel
(
  nick_id INTEGER NOT NULL,
  channel_id INTEGER NOT NULL,
  PRIMARY KEY (nick_id, channel_id),
  FOREIGN KEY (nick_id) REFERENCES nicks(id),
  FOREIGN KEY (channel_id) REFERENCES channels(id)
);

CREATE TABLE logs
(
  id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
  datetime TIMESTAMP NOT NULL,
  msg VARCHAR(4096),
  nick_id INTEGER NOT NULL,
  channel_id INTEGER NOT NULL,
  target_nick_id INTEGER NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (nick_id) REFERENCES nicks(id),
  FOREIGN KEY (channel_id) REFERENCES channels(id),
  FOREIGN KEY (target_nick_id) REFERENCES nicks(id)
);
