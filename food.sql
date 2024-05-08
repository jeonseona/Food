-- 로그인 페이지
-- member_data 테이블에 code 컬럼과 goal 컬럼 추가
ALTER TABLE member_data ADD (code CHAR(1) DEFAULT '0', goal VARCHAR2(30));
-- member_data 테이블에 BMI 컬럼 추가
ALTER TABLE member_data ADD BMI FLOAT DEFAULT O;
-- 멤버테이블 삭제해주세요!
drop table member;


ALTER TABLE member_data ADD CONSTRAINT pk_member_id unique (id);

-- 관리자 페이지
-- 관리자 게시판(음식레시피, Q&A, 1:1문의)
-- 수정 사항 적용 / BoardCode컬럼 삭제, 
CREATE TABLE admin_recipe_board (
    USERID VARCHAR2(255) NOT NULL,     -- USERID를 통해서 USERCODE, NICKNAME 참조
    RECIPE_BOARDNUM NUMBER PRIMARY KEY,
    FB_INDEX NUMBER,  -- FB_INDEX를 통해서 F_IMAGE 참조
    TITLE VARCHAR2(30) NOT NULL,
    CONTENT VARCHAR2(500) NOT NULL,
    TAG VARCHAR2(100),
    COUNT NUMBER(30) DEFAULT 0,
    IMAGES VARCHAR2(500),
    REGDATE DATE DEFAULT SYSDATE,
    EDITDATE DATE DEFAULT SYSDATE,
    CONSTRAINT fk_id_3 FOREIGN KEY(USERID) REFERENCES MEMBER_data(id),
    CONSTRAINT fk_f_index FOREIGN KEY(FB_INDEX) REFERENCES FOOD_RECIPE(IDX)
);

-- 관리자 음식DB  -----실행 XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
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

-- 테이블들은 추가사항
-- 수정사항 적용 / QnA_detail -> qna보드페이지로 수정
CREATE TABLE admin_qna_board (
    qna_boardnum NUMBER PRIMARY KEY,
    question VARCHAR(255),
    answer VARCHAR(255),
    regdate DATE DEFAULT SYSDATE
);


-- 커뮤니티 페이지
-- 커뮤니티 레시피 데이터 테이블 수정
ALTER TABLE com_recipe ADD (idx NUMBER); -- 인덱스 컬럼 생성
UPDATE com_recipe SET idx = ROWNUM; -- 인덱스컬럼에 값 부여
ALTER TABLE com_recipe ADD CONSTRAINT pk_com_recipe PRIMARY KEY (idx); -- 인덱스 컬럼을 커뮤니티 레시피 데이터의 primary키로 합니다.


--게시글과 댓글 번호 시퀀스
CREATE SEQUENCE boardseq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE replyseq START WITH 1 INCREMENT BY 1;





-- 커뮤니티 게시글의 댓글
CREATE TABLE reply (
    replynum NUMBER PRIMARY KEY,
    boardnum NUMBER, --com_board_Detail 참고
    CONTENT VARCHAR2(2000) NOT NULL,
    userid NUMBER NOT NULL, -- Member_data 참고
    CONSTRAINT fk_id3 FOREIGN KEY(userid) REFERENCES Member_data(no_data),
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

--추천시스템 목록페이지 삭제 

--  추천시스템 상세페이지   -------실행 XXXXXXXXXXXXXXXXXXXX
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

CREATE TABLE recommend_history(
    history_id VARCHAR(100) PRIMARY KEY,
    recommend_food number,    -- 추천받은 음식(food_seq)에서 NAME, IMAGES, CALORIES 조회
    recommend_date DATE DEFAULT SYSDATE,
    h_no_data number,
    CONSTRAINT fk_history_id FOREIGN KEY (h_no_data) REFERENCES Member_data(no_data),
    CONSTRAINT fk_recommend_food FOREIGN KEY (recommend_food) REFERENCES food_recipe(idx)
);

-- 5/7 커뮤니티 시퀀스추가
CREATE SEQUENCE com_recipeseq START WITH 44 INCREMENT BY 1;

--5/8 커뮤니티 테이블 수정 ( 추천수 컬럼추가)
-- 커뮤니티 게시글 클릭시 상세정보
CREATE TABLE com_board_detail(
    seq NUMBER PRIMARY KEY	,
    RECIPE_NUM NUMBER, -- com_recipe 참고1
    d_writer_no   number,  --meber_data 참고1
    d_regdate DATE DEFAULT sysdate,
    cnt NUMBER DEFAULT 0,
    goodpoint number DEFAULT 0,
    CONSTRAINT fk_seq1 FOREIGN KEY(d_writer_no) REFERENCES Member_Data(no_data),  
    CONSTRAINT fk_idx2 FOREIGN KEY(RECIPE_NUM) REFERENCES COM_RECIPE(IDX)
);

-- 5/8 1:1 문의 테이블 수정 (이름, 이메일, 답변 상태 컬럼추가)
ALTER TABLE inquiries
ADD name VARCHAR(100)
ADD email VARCHAR(100);

ALTER TABLE inquiries
ADD status VARCHAR(100);
