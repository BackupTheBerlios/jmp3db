# MySQL-Front Dump 2.5
#
# Host: localhost   Database: jmp3db
# --------------------------------------------------------
# Server version 4.0.16-nt


#
# Table structure for table 'albums'
#

CREATE TABLE albums (
  id int(3) NOT NULL default '0',
  name varchar(100) default '0',
  artist int(3) default '0',
  year int(3) default '0',
  genre int(3) default '0',
  complete int(1) default '0',
  cover int(3) default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;



#
# Table structure for table 'artists'
#

CREATE TABLE artists (
  id int(3) NOT NULL default '0',
  name varchar(100) default '0',
  genre int(3) default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;



#
# Table structure for table 'cover'
#

CREATE TABLE cover (
  id int(3) NOT NULL default '0',
  cover varchar(100) default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;



#
# Table structure for table 'genres'
#

CREATE TABLE genres (
  id int(3) NOT NULL default '0',
  number int(3) default NULL,
  genretext varchar(100) default NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;



#
# Table structure for table 'songs'
#

CREATE TABLE songs (
  id int(3) NOT NULL default '0',
  title varchar(100) default '0',
  artist int(3) default NULL,
  album int(3) default NULL,
  year int(3) default NULL,
  genre int(3) default '0',
  trackno int(3) default '0',
  bitrate int(3) default '0',
  length int(3) default '0',
  filesize int(10) default '0',
  filename varchar(200) default '0',
  lastmodified int(15) default '0',
  hashcode int(100) default '0',
  PRIMARY KEY  (id),
  KEY id (id)
) TYPE=MyISAM;



#
# Table structure for table 'years'
#

CREATE TABLE years (
  id int(3) NOT NULL default '0',
  number int(4) default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;

