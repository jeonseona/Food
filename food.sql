
-- 로그인 페이지
-- member_data 테이블에 code 컬럼과 goal 컬럼 추가
ALTER TABLE member_data ADD (code CHAR(1) DEFAULT '0', goal VARCHAR2(30));
-- 멤버테이블 삭제해주세요!
drop table member;
);



-- 관리자 페이지
-- 관리자 게시판(음식레시피, Q&A, 1:1문의)
-- ERD작성용은 IMAGE칼럼 삭제
CREATE TABLE admin_board (
    USERID VARCHAR2(30) NOT NULL,     -- USERID를 통해서 USERCODE, NICKNAME 참조
    BOARDNUM NUMBER PRIMARY KEY,
    BOARDCODE NUMBER(1) DEFAULT 0,
    FB_INDEX NUMBER,  -- FB_INDEX를 통해서 F_IMAGE 참조
    TITLE VARCHAR2(30) NOT NULL,
    CONTENT VARCHAR2(500) NOT NULL,
    TAG VARCHAR2(100),
    COUNT NUMBER(30) DEFAULT 0,
    REGDATE DATE DEFAULT SYSDATE,
    EDITDATE DATE DEFAULT SYSDATE,
    
    CONSTRAINT fk_id FOREIGN KEY(USERID) REFERENCES MEMBER(ID)
    CONSTRAINT fk_f_index FOREIGN KEY(FB_INDEX) REFERENCES FOOD_RECEIPE(F_INDEX)
);

-- 관리자 음식DB
-- ERD 작성용 예시 테이블입니다.(실제 테이블은 .CSV파일 임포트해서 사용)
CREATE TABLE FOOD_RECIPE (
    F_INDEX NUMBER PRIMARY KEY,
    F_NAME VARCHAR2(50) NOT NULL,
    F_IMAGE VARCHAR2(500),
    F_CATEGORY VARCHAR2(300),
    F_KEYWORDS VARCHAR2(300) NOT NULL,
    F_INGREDIENT VARCHAR2(500),
    F_CALORIES NUMBER NOT NULL,
    F_FAT NUMBER,
    F_CHOLESTEROL NUMBER,
    F_CARBOHYDRATE NUMBER,
    F_FIBER NUMBER,
    F_SUGAR NUMBER,
    F_PROTEIN NUMBER,
    F_MANUAL VARCHAR2(500)
);



-- 커뮤니티 페이지
-- 커뮤니티 레시피 데이터 테이블 수정
ALTER TABLE com_recipe ADD (idx NUMBER); -- 인덱스 컬럼 생성
UPDATE com_recipe SET idx = ROWNUM; -- 인덱스컬럼에 값 부여
ALTER TABLE com_recipe ADD CONSTRAINT pk_com_recipe PRIMARY KEY (idx); -- 인덱스 컬럼을 커뮤니티 레시피 데이터의 primary키로 합니다.


--게시글과 댓글 번호 시퀀스
CREATE SEQUENCE boardseq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE replyseq START WITH 1 INCREMENT BY 1;


CREATE TABLE com_board(
    board_num NUMBER primary key, -- com_board_detail 참고1
    recipe_num number ,
    CONSTRAINT fk_id_11 FOREIGN KEY(board_num) REFERENCES com_board_detail(seq),
    CONSTRAINT fk_idx_1 FOREIGN KEY(recipe_num) REFERENCES COM_RECIPE(IDX)   
);


-- 커뮤니티 게시글 클릭시 상세정보
CREATE TABLE com_board_detail(
    seq NUMBER PRIMARY KEY	,
    RECIPE_NUM NUMBER, -- com_recipe 참고1
    d_writer_no   number,  --meber_data 참고1
    d_regdate DATE DEFAULT sysdate,
    cnt NUMBER DEFAULT 0,
    CONSTRAINT fk_seq1 FOREIGN KEY(d_writer_no) REFERENCES MemberData(no_data),  
    CONSTRAINT fk_idx2 FOREIGN KEY(RECIPE_NUM) REFERENCES COM_RECIPE(IDX)
);




-- 커뮤니티 게시글의 댓글
CREATE TABLE reply (
    replynum NUMBER PRIMARY KEY,
    boardnum NUMBER, --com_board_Detail 참고
    CONTENT VARCHAR2(2000) NOT NULL,
    userid NUMBER NOT NULL, -- Member_data 참고
    CONSTRAINT fk_id3 FOREIGN KEY(userid) REFERENCES Memberdata(no_data),
    CONSTRAINT fk_seq_3 FOREIGN KEY(boardnum) REFERENCES com_board_detail(seq)
);



-- 고객센터 페이지 / 기존의 QnA 테이블은 관리자에서 필요로 하기 때문에 삭제하였습니다 !

-- 1:1 문의 테이블 생성
CREATE TABLE inquiries (
    inquiry_id NUMBER(5) PRIMARY KEY,
    subject VARCHAR(100),
    message VARCHAR(100),
    created_at TIMESTAMP DEFAULT current_timestamp,
    comments VARCHAR(200),
    CONSTRAINT fk_inquiry_id FOREIGN KEY (inquiry_id) REFERENCES Member_data(no_data)
);

create sequence inq_SEQ start with 1 increment by 1;

-- 마이페이지(삭제) : 외래키로 받아오는 정보뿐이라 삭제했어요.

-- 여기서부터 추천시스템 페이지 DB입니다. 모두 실행시켜주세요
ALTER TABLE food_recipe ADD CONSTRAINT pk_food_recipe PRIMARY KEY (idx);

---  추천시스템 목록 페이지
create table recommend_list(
    food_number number primary key,
    food_title varchar2(128),
    food_img varchar2(4000),
    CONSTRAINT fk_foodnum FOREIGN KEY(food_number) REFERENCES food_recipe(idx)
);


--  추천시스템 상세페이지
create table recommend_detail(
    food_seq	NUMBER,
    NAME	VARCHAR2(128),
    IMAGES	VARCHAR2(4000),
    RECIPECATEGORY	VARCHAR2(26),
    KEYWORDS	VARCHAR2(256),
    RECIPEINGREDIENTPARTS	VARCHAR2(1024),
    CALORIES	NUMBER(38,1),
    FATCONTENT	NUMBER(38,1),
    SATURATEDFATCONTENT	NUMBER(38,1),
    CHOLESTEROLCONTENT	NUMBER(38,1),
    SODIUMCONTENT	NUMBER(38,1),
    CARBOHYDRATECONTENT	NUMBER(38,1),
    FIBERCONTENT	NUMBER(38,1),
    SUGARCONTENT	NUMBER(38,1),
    PROTEINCONTENT	NUMBER(38,1),
    RECIPEINSTRUCTIONS	VARCHAR2(4000),
    CONSTRAINT fk_foodseq FOREIGN KEY(food_seq) REFERENCES food_recipe(idx)
);

-- 테이블들은 추가사항
CREATE TABLE qna_detail (
    id INT PRIMARY KEY,
    admin_board_id INT NOT NULL,
    questionDetail VARCHAR(255),
    answerDetail VARCHAR(255),
    FOREIGN KEY (admin_board_id) REFERENCES admin_board (boardnum)
);

-- 1:1 문의 게시글 목록 테이블 생성
CREATE TABLE inquiry_list (
    inquiry_id NUMBER(5) PRIMARY KEY,
    name VARCHAR(50), 
    email VARCHAR(100), 
    subject VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20)
);

-- inquiry_id 시퀀스 생성
CREATE SEQUENCE inquiry_list_SEQ START WITH 1 INCREMENT BY 1;  













