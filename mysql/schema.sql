use testdb;

CREATE TABLE blocker_relationship (
  requestor_email varchar(256) NOT NULL,
  target_email varchar(256) NOT NULL,
  PRIMARY KEY (requestor_email, target_email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE follower_relationship (
  requestor_email varchar(256) NOT NULL,
  target_email varchar(256) NOT NULL,
  PRIMARY KEY (requestor_email, target_email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE friend_relationship (
  requestor_email varchar(256) NOT NULL,
  target_email varchar(256) NOT NULL,
  PRIMARY KEY (requestor_email, target_email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;