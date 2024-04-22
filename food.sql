--게시글과 댓글 번호 시퀀스
create sequence BOARDSEQ start with 1 increment by 1;
create sequence REPLYSEQ start with 1 increment by 1;

-- 커뮤니티 게시글목록
create table COM_BOARD(
    SEQ NUMBER(5) PRIMARY KEY,
    TITLE VARCHAR2(200),
    WRITER VARCHAR2(20),
    REGDATE DATE DEFAULT SYSDATE,
    CNT NUMBER(5) DEFAULT 0,
    IMAGE varchar2(500),
    CONSTRAINT FK_SEQ1 FOREIGN KEY(WRITER) references 회원게시판의 회원아이디
);

-- 커뮤니티 게시글 클릭시 상세정보
create table COM_BOARD_DETAIL(
    BOARD_NUM NUMBER(5) PRIMARY KEY,
    D_TITLE VARCHAR2(200),
    D_WRITER VARCHAR2(20),
    INGREDIENT VARCHAR2(500),
    RECIPE VARCHAR2(5000),
    D_REGDATE DATE DEFAULT SYSDATE,
    D_CNT NUMBER(5) DEFAULT 0,
    D_IMAGE varchar2(500),
    CONSTRAINT FK_SEQ2 FOREIGN KEY(BOARD_NUM) references BOARD(SEQ),
    CONSTRAINT FK_ID1 FOREIGN KEY(D_WRITER) references 회원게시판의 회원아이디
);

-- 커뮤니티 게시글의 댓글
create table REPLY (
    REPLYNUM NUMBER PRIMARY KEY,
    BOARDNUM NUMBER NOT NULL,
    NICKNAME VARCHAR2(30) NOT NULL,
    CONTENT VARCHAR2(600) NOT NULL,
    USERID VARCHAR2(30) NOT NULL,
    CONSTRAINT FK_ID2 FOREIGN KEY(USERID) REFERENCES 회원게시판의 회원아이디,
    CONSTRAINT FK_SEQ3 FOREIGN KEY(BOARDNUM) references BOARD(SEQ)
    );
    
    

