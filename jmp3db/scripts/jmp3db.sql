CREATE TABLE albums (
  id int(3) NOT NULL,
  name varchar(100),
  artist int(3),
  year int(3),
  genre int(3),
  complete int(1),
  cover int(3),
  PRIMARY KEY  (id)
);
CREATE TABLE artists (
  id int(3) NOT NULL,
  name varchar(100),
  genre int(3),
  PRIMARY KEY  (id)
);
CREATE TABLE cover (
  id int(3) NOT NULL,
  cover varchar(100),
  PRIMARY KEY  (id)
);
CREATE TABLE genres (
  id int(3) NOT NULL,
  number int(3) default NULL,
  genretext varchar(100) default NULL,
  PRIMARY KEY  (id)
);
CREATE TABLE songs (
  id int(3) NOT NULL,
  title varchar(100),
  artist int(3),
  album int(3),
  year int(3),
  genre int(3),
  trackno int(3),
  bitrate int(3),
  length int(3),
  filesize int(10),
  filename varchar(200),
  lastmodified int(15),
  hashcode int(100),
  PRIMARY KEY  (id)
);
CREATE TABLE years (
  id int(3) NOT NULL,
  number int(4),
  PRIMARY KEY  (id)
);