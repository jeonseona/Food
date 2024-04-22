-- 관리자 페이지
-- 관리자 게시판(음식레시피, Q&A, 1:1문의)
CREATE TABLE admin_board(
    userid VARCHAR2(30),     -- 회원게시판 id(외래키 지정)
    usercode CHAR(1),        -- 회원게시판 회원권한코드(외래키 지정)
    boardcode CHAR(1) NOT NULL,  -- 기본키
    boardnum VARCHAR2(30) NOT NULL,
    nickname VARCHAR2(20),    -- 회원게시판 nickname(외래키 지정)
    title VARCHAR(30) NOT NULL,
    CONTENT VARCHAR2(500) NOT NULL,
    image VARCHAR2(500),
    TAG VARCHAR2(500),
    COMMENTs VARCHAR2(100),
    regdate DATE DEFAULT sysdate,
    COUNT VARCHAR2(30) DEFAULT 0,   
    PRIMARY KEY(boardnum),
    CONSTRAINT fk_id4 FOREIGN KEY(userid) REFERENCES MEMBER(ID),-- 회원게시판 id
    CONSTRAINT fk_usercode FOREIGN KEY(usercode) REFERENCES MEMBER(code),-- 회원게시판 회원권한코드
    CONSTRAINT fk_nickname1 FOREIGN KEY(nickname) REFERENCES MEMBER(nickname)-- 회원게시판 닉네임
);


-- 커뮤니티 페이지
--게시글과 댓글 번호 시퀀스
CREATE SEQUENCE boardseq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE replyseq START WITH 1 INCREMENT BY 1;



-- 커뮤니티 게시글목록
CREATE TABLE com_board(
    seq NUMBER PRIMARY KEY,
    title VARCHAR2(200),
    writer VARCHAR2(20),
    regdate DATE DEFAULT sysdate,
    cnt NUMBER DEFAULT 0,
    image VARCHAR2(500),
    CONSTRAINT fk_id1 FOREIGN KEY(writer) REFERENCES MEMBER(ID)
);

-- 커뮤니티 게시글 클릭시 상세정보
CREATE TABLE com_board_detail(
    board_num NUMBER PRIMARY KEY,
    d_title VARCHAR2(200),
    d_writer VARCHAR2(20),
    ingredient VARCHAR2(500),
    d_category varchar2(100),
    d_regdate DATE DEFAULT sysdate,
    d_cnt NUMBER DEFAULT 0,
    d_image VARCHAR2(500),
    d_manual01 varchar2(500),
    d_manual02 varchar2(500),
    d_manual03 varchar2(500),
    d_manual04 varchar2(500),
    d_manual05 varchar2(500),
    d_manual06 varchar2(500),
    d_manual_img01 varchar2(500),
    d_manual_img02 varchar2(500),
    d_manual_img03 varchar2(500),
    d_manual_img04 varchar2(500),
    d_manual_img05 varchar2(500),
    d_manual_img06 varchar2(500),
    CONSTRAINT fk_seq1 FOREIGN KEY(board_num) REFERENCES com_board(seq),  
    CONSTRAINT fk_id2 FOREIGN KEY(d_writer) REFERENCES MEMBER(ID)
);


-- 커뮤니티 게시글의 댓글
CREATE TABLE reply (
    replynum NUMBER PRIMARY KEY,
    boardnum NUMBER NOT NULL,
    reply_name VARCHAR2(50) NOT NULL, 
    CONTENT VARCHAR2(2000) NOT NULL,
    userid VARCHAR2(30) NOT NULL,
    CONSTRAINT fk_id3 FOREIGN KEY(userid) REFERENCES MEMBER(ID),
    CONSTRAINT fk_seq_3 FOREIGN KEY(boardnum) REFERENCES com_board(seq), 
    CONSTRAINT fk_name FOREIGN KEY(reply_name) REFERENCES member(nickname)
);




-- 고객센터 페이지
-- 질문 테이블 생성
CREATE TABLE questions (
    question_id NUMBER(5) PRIMARY KEY,
    question_text VARCHAR(200)
);

-- 답변 테이블 생성
CREATE TABLE answers (
    answer_id NUMBER(5) PRIMARY KEY,
    question_id NUMBER(5),
    answer_text VARCHAR(200),
    CONSTRAINT fk_qid1 FOREIGN KEY (question_id) REFERENCES questions(question_id)
);

-- 1:1 문의 테이블 생성
CREATE TABLE inquiries (
    inquiry_id NUMBER(5) PRIMARY KEY,
    NAME VARCHAR(50), 
    email VARCHAR(100), 
    subject VARCHAR(100),
    message VARCHAR(100),
    created_at TIMESTAMP DEFAULT current_timestamp
    CONSTRAINT fk_name1 FOREIGN KEY(name) REFERENCES member(name),
    CONSTRAINT fk_email1 FOREIGN KEY(email) REFERENCES member(email)
);

CREATE SEQUENCE questionsseq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE inquiriesseq START WITH 1 INCREMENT BY 1;

-- 로그인 페이지
CREATE TABLE MEMBER(
    ID VARCHAR(30)  PRIMARY KEY,
    NAME VARCHAR(50)  NOT NULL ,
    nickname VARCHAR(50) NOT NULL unique ,
    email VARCHAR(50) NOT NULL ,
    PASSWORD VARCHAR(30) NOT NULL,
    code CHAR(1) DEFAULT 0 ,   -- 0 : 일반회원, 1 : 관리자
    height VARCHAR2(30) ,   -- 키
    weight VARCHAR2(30) ,   -- 몸무게
    gender VARCHAR2(20),        -- input-radio : 성별
    goal VARCHAR2(30)           -- checkbox : 목표
);



-- 마이페이지 MyPage 
CREATE TABLE mypage (
    m_id VARCHAR2(30),     
    m_pw VARCHAR(30),
    m_email VARCHAR2(60),  
    m_name VARCHAR2(20),   
    m_height VARCHAR(30),
    m_weight VARCHAR(30), 
    m_gender VARCHAR2(20), 
    m_goal VARCHAR2(300),  
    bmi NUMBER PRIMARY KEY,
    
    CONSTRAINT fk_id5 FOREIGN KEY(m_id) REFERENCES MEMBER(ID),
    CONSTRAINT fk_pw1 FOREIGN KEY(m_pw) REFERENCES MEMBER(PASSWORD),
    CONSTRAINT fk_email2 FOREIGN KEY(m_email) REFERENCES MEMBER(email),
    CONSTRAINT fk_name2 FOREIGN KEY(m_name) REFERENCES MEMBER(NAME),
    CONSTRAINT fk_height1 FOREIGN KEY(m_height) REFERENCES MEMBER(height),
    CONSTRAINT fk_weight1 FOREIGN KEY(m_weight) REFERENCES MEMBER(weight),
    CONSTRAINT fk_gender1 FOREIGN KEY(m_gender) REFERENCES MEMBER(gender),
    CONSTRAINT fk_goal1 FOREIGN KEY(m_goal) REFERENCES MEMBER(goal)
);

















