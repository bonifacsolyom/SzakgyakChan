DROP SEQUENCE BOARD_SEQ;
DROP SEQUENCE THREAD_SEQ;
DROP SEQUENCE REPLY_SEQ;
DROP SEQUENCE NOTIF_SEQ;
DROP SEQUENCE RUSER_SEQ;
DROP SEQUENCE META_SEQ;
DROP TABLE META_INFO CASCADE CONSTRAINTS;
DROP TABLE BOARD CASCADE CONSTRAINTS;
DROP TABLE BOARD_THREADS CASCADE CONSTRAINTS;
DROP TABLE COMMENT_NOTIFICATION CASCADE CONSTRAINTS;
DROP TABLE REPLY CASCADE CONSTRAINTS;
DROP TABLE REPLY_USERS_DOWNVOTED CASCADE CONSTRAINTS;
DROP TABLE REPLY_USERS_UPVOTED CASCADE CONSTRAINTS;
DROP TABLE THREAD CASCADE CONSTRAINTS;
DROP TABLE THREAD_REPLIES CASCADE CONSTRAINTS;
DROP TABLE RUSER CASCADE CONSTRAINTS;
DROP TABLE RUSER_COMMENT_NOTIFICATIONS CASCADE CONSTRAINTS;
DROP TABLE RUSER_REPLIES CASCADE CONSTRAINTS;
DROP TABLE RUSER_THREADS CASCADE CONSTRAINTS;
DROP TABLE RUSER_VOTE_NOTIFICATIONS CASCADE CONSTRAINTS;
DROP TABLE VOTE_NOTIFICATION CASCADE CONSTRAINTS;


