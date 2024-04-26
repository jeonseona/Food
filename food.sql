
-- 로그인 페이지
ALTER TABLE member_data ADD (no_data NUMBER); 
UPDATE member_data SET no_data = ROWNUM;
ALTER TABLE member_data ADD CONSTRAINT pk_member_data PRIMARY KEY (no_data);

CREATE TABLE MEMBER(
    member_num number,
    ID VARCHAR(30)  PRIMARY KEY,
    NAME VARCHAR(50)  NOT NULL ,
    nickname VARCHAR(50) NOT NULL unique ,
    email VARCHAR(50) NOT NULL ,
    PASSWORD VARCHAR(30) NOT NULL,
    code CHAR(1) DEFAULT 0 ,   -- 0 : 일반회원, 1 : 관리자
    height VARCHAR2(30) ,   -- 키
    weight VARCHAR2(30) ,   -- 몸무게
    gender VARCHAR2(20),        -- input-radio : 성별
    goal VARCHAR2(30),           -- checkbox : 목표
CONSTRAINT fk_id1 FOREIGN KEY(member_num) REFERENCES Member_data(no_data)
);


-- 관리자 페이지
-- 관리자 게시판(음식레시피, Q&A, 1:1문의)
CREATE TABLE admin_board (
    userid VARCHAR2(30),     -- 회원게시판 외래키
    usercode CHAR(1) DEFAULT 1 NOT NULL,
    boardcode CHAR(1) NOT NULL,    -- 코드0 : 레시피, 코드1:Q&A     
    nickname VARCHAR2(20), 
    boardnum NUMBER(30) NOT NULL, -- 기본키
    title VARCHAR2(30) NOT NULL,
    content VARCHAR2(500) NOT NULL,
    image VARCHAR2(500),
    tag VARCHAR2(500),
    regdate DATE DEFAULT SYSDATE,
    count NUMBER(30) DEFAULT 0,  
    PRIMARY KEY (boardnum),
    CONSTRAINT fk_id4 FOREIGN KEY (userid) REFERENCES MEMBER(ID)
);



-- 커뮤니티 페이지
-- 커뮤니티 레시피 데이터 테이블 수정
ALTER TABLE com_recipe ADD (idx NUMBER); -- 인덱스 컬럼 생성
UPDATE com_recipe SET idx = ROWNUM; -- 인덱스컬럼에 값 부여
ALTER TABLE com_recipe ADD CONSTRAINT pk_com_recipe PRIMARY KEY (idx); -- 인덱스 컬럼을 커뮤니티 레시피 데이터의 primary키로 합니다.


--게시글과 댓글 번호 시퀀스
CREATE SEQUENCE boardseq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE replyseq START WITH 1 INCREMENT BY 1;


-- 커뮤니티 게시글목록
CREATE TABLE com_board(
    seq NUMBER PRIMARY KEY,
    title VARCHAR2(200),
    writer VARCHAR2(20),
    writer_ID NUMBER(20),
    regdate DATE DEFAULT sysdate,
    cnt NUMBER DEFAULT 0,
    image VARCHAR2(500),
    CONSTRAINT fk_id_1 FOREIGN KEY(writer_ID) REFERENCES Member_data(no_data),
    CONSTRAINT fk_idx_1 FOREIGN KEY(seq) REFERENCES COM_RECIPE(IDX)   
);

-- 커뮤니티 게시글 클릭시 상세정보
CREATE TABLE com_board_detail(
    board_num NUMBER PRIMARY KEY,
    RECIPE_NUM NUMBER,
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
    CONSTRAINT fk_idx2 FOREIGN KEY(RECIPE_NUM) REFERENCES COM_RECIPE(IDX)
);


-- 커뮤니티 게시글의 댓글
CREATE TABLE reply (
    replynum NUMBER PRIMARY KEY,
    boardnum NUMBER NOT NULL,
    reply_name VARCHAR2(50) NOT NULL, 
    CONTENT VARCHAR2(2000) NOT NULL,
    userid NUMBER NOT NULL,
    CONSTRAINT fk_id3 FOREIGN KEY(userid) REFERENCES Member_data(no_data),
    CONSTRAINT fk_seq_3 FOREIGN KEY(boardnum) REFERENCES com_board(seq)
);




-- 고객센터 페이지 / 기존의 QnA 테이블은 관리자에서 필요로 하기 때문에 삭제하였습니다 !

-- 1:1 문의 테이블 생성
CREATE TABLE inquiries (
    inquiry_id NUMBER(5) PRIMARY KEY,
    NAME VARCHAR(50), 
    email VARCHAR(100), 
    subject VARCHAR(100),
    message VARCHAR(100),
    created_at TIMESTAMP DEFAULT current_timestamp,
    comments VARCHAR(200),
    CONSTRAINT fk_inquiry_id FOREIGN KEY (inquiry_id) REFERENCES Member_data(no_data)
);

create sequence inq_SEQ start with 1 increment by 1;




-- 마이페이지 MyPage 
CREATE TABLE mypage (
    m_seq NUMBER,       -- 마이페이지에서 내가쓴 게시글목록 조회
    m_num number, 
    m_id VARCHAR2(30),     
    m_pw VARCHAR(30),
    m_email VARCHAR2(60),
    m_nickname VARCHAR2(30),
    m_height VARCHAR(30),
    m_weight VARCHAR(30), 
    m_gender VARCHAR2(20), 
    m_goal VARCHAR2(300),  
    bmi NUMBER PRIMARY KEY,
    food_preference CHAR(1) DEFAULT 'y',      -- 음식선호도 : 'y'는 선호 | 'n'은 불호
    allergy_food CHAR(1) DEFAULT 'n',       -- 알레르기 음식 : 'n'은 없음 | 'y'는 있음
    CONSTRAINT fk_seq9 FOREIGN KEY(m_seq) REFERENCES com_board(seq), 
    CONSTRAINT fk_id5 FOREIGN KEY(m_num) REFERENCES Member_data(no_data)
);
















