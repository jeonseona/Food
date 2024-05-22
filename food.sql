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


-- 작성자 중복가능 5/13
ALTER TABLE com_board_detail DROP CONSTRAINT UK_66CJHOET1KTDRW43D3FWHXB6H;
ALTER TABLE reply DROP CONSTRAINT SYS_C007947;
ALTER TABLE reply DROP CONSTRAINT SYS_C007969;

ALTER TABLE reply DROP CONSTRAINT FKSECNEYRDUDMKVV7N14LPA4BG1;
ALTER TABLE REply
ADD CONSTRAINT FKSECNEYRDUDMKVV7N14LPA4BG1
FOREIGN KEY (seq)
REFERENCES com_Board_DEtail (seq)
ON DELETE CASCADE;


--------------------------------------------------------------
-- 05.20 : 체중변화 기록용 테이블
CREATE TABLE WEIGHT_RECORD (
    RE_ID NUMBER GENERATED BY DEFAULT AS IDENTITY,
    RE_DATE DATE DEFAULT SYSDATE,
    RE_WEIGHT NUMBER(10, 2) NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT SYSDATE,    -- 안쓰고 있는데 지울까요?
    UPDATED_AT TIMESTAMP DEFAULT SYSDATE,    -- 안쓰고 있는데 지울까요?
    NO_DATA NUMBER NOT NULL,
    CONSTRAINT WEIGHT_RECORD_PK PRIMARY KEY (RE_ID),
    CONSTRAINT WEIGHT_RECORD_FK FOREIGN KEY (NO_DATA) REFERENCES MEMBER_DATA(NO_DATA)
);

------------------ 5/22 데이터 입력 및 시퀀스등
CREATE SEQUENCE community_SEQ_sec START WITH 1 INCREMENT BY 1;
drop sequence community_seq;

ALTER TABLE community_Board DROP CONSTRAINT UK_TAOXC3UGGLPH7P7ROWV0H3S9A;

INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(2, 27, '이번 달 독서 클럽에서 읽은 책은 정말 흥미로웠어요. 내용이 풍부하고 다양한 주제를 다루어 생각할 거리가 많았어요. 다음 모임이 벌써 기대됩니다.', TO_TIMESTAMP('2024-01-14 12:15:43.123', 'YYYY-MM-DD HH24:MI:SS.FF'), 21, '독서 클럽 모임', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(3, 91, '우리 집 고양이가 새로운 장난감을 좋아해요. 하루 종일 그 장난감을 가지고 놀아서 보는 내내 즐거웠어요. 반려동물과 함께하는 시간은 언제나 소중해요.', TO_TIMESTAMP('2023-10-29 11:23:45.789', 'YYYY-MM-DD HH24:MI:SS.FF'), 8, '반려동물 이야기', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(4, 29, '오늘 점심으로 먹은 파스타가 정말 맛있었어요. 특별한 소스와 신선한 재료 덕분에 집에서 먹는 것 같지 않았어요. 여러분도 꼭 한번 만들어보세요.', TO_TIMESTAMP('2024-03-05 09:30:21.564', 'YYYY-MM-DD HH24:MI:SS.FF'), 15, '오늘의 점심 메뉴', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(5, 25, '매일 아침 조깅을 시작했는데, 기분이 좋아요. 상쾌한 아침 공기와 함께 달리다 보면 하루가 활기차게 시작되는 것 같아요. 건강에도 좋고 추천합니다.', TO_TIMESTAMP('2024-02-26 11:40:14.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 37, '운동 루틴 공유!!', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(6, 84, '새로 시작한 취미가 정말 재미있어요. 이번에는 수채화를 그려봤는데, 색감과 표현이 마음에 들어서 뿌듯했어요. 여러분도 새로운 취미를 찾아보세요.', TO_TIMESTAMP('2023-11-29 21:39:01.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 49, '새로 시작한 취미', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(7, 34, '거실을 새롭게 꾸며봤어요. 분위기가 확 달라져서 집에 있는 시간이 더 즐거워졌어요. 작은 변화로도 큰 행복을 느낄 수 있다는 것을 깨달았어요.', TO_TIMESTAMP('2023-09-17 08:42:14.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 10, '집 꾸미기 프로젝트', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(8, 62, '어제 본 영화가 정말 감동적이었어요. 스토리와 연기가 훌륭해서 보는 내내 눈을 뗄 수 없었어요. 여러분께도 꼭 추천드리고 싶은 영화입니다.', TO_TIMESTAMP('2023-10-26 17:43:48.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 31, '영화 감상 후기', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(9, 78, '오늘은 날씨가 참 좋네요. 그래서 오랜만에 공원으로 산책을 다녀왔어요. 맑은 하늘과 따뜻한 햇살이 마음까지 따뜻하게 해주는 것 같아요.', TO_TIMESTAMP('2024-02-01 15:29:48.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 13, '산책을 다녀왔어요', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(10, 48, '주말에 가까운 곳으로 여행을 다녀왔어요. 짧은 시간이었지만, 자연 속에서 힐링할 수 있었어요. 일상에서 벗어나 잠시 쉬어가는 것도 중요한 것 같아요.', TO_TIMESTAMP('2023-09-04 15:07:31.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 24, '주말 여행 계획', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(11, 84, '이번에 새로 생긴 카페를 방문해봤어요. 분위기가 아늑하고 커피도 맛있어서 자주 가게 될 것 같아요. 친구들과 함께 가기 좋은 장소에요.', TO_TIMESTAMP('2024-04-17 07:04:33.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 11, '새로 생긴 카페 방문기', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(12, 17, '최근에 시작한 그림 그리기가 정말 재미있어요. 처음에는 서툴렀지만, 점점 나아지는 모습에 뿌듯함을 느끼고 있어요. 창의력을 키우는 데도 도움이 돼요.', TO_TIMESTAMP('2023-12-26 16:15:26.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 46, '새로 시작한 취미', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(13, 73, '이번 달 독서 클럽에서 읽은 책은 정말 감명 깊었어요. 다양한 주제를 다루면서도 깊이 있는 내용이 많아서 많은 것을 배울 수 있었어요.', TO_TIMESTAMP('2024-05-07 21:07:19.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 25, '독서 클럽 모임', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(14, 73, '집 근처에 새로 생긴 카페가 정말 좋았어요. 분위기가 아늑하고, 커피도 맛있어서 자주 가게 될 것 같아요. 친구들과 함께 가기 좋은 장소에요.', TO_TIMESTAMP('2023-03-21 00:34:45.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 9, '새로 생긴 카페 방문기', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(15, 63, '요즘 운동 루틴을 바꿔봤어요. 새로운 운동을 시도하니 몸이 더 좋아지는 느낌이에요. 여러분도 가끔은 운동 방법을 바꿔보세요.', TO_TIMESTAMP('2023-03-01 04:01:52.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 11, '운동 루틴 공유', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(16, 65, '어제 본 영화가 정말 재미있었어요. 스토리와 연기가 훌륭해서 보는 내내 눈을 뗄 수 없었어요. 여러분께도 꼭 추천드리고 싶은 영화입니다.', TO_TIMESTAMP('2023-07-24 00:35:49.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 21, '영화 감상 후기', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(17, 19, '오늘은 날씨가 좋아서 오랜만에 산책을 다녀왔어요. 맑은 하늘과 따뜻한 햇살이 마음까지 따뜻하게 해주는 것 같아요. 여러분도 산책해보세요.', TO_TIMESTAMP('2023-08-04 10:08:22.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 47, '산책을 다녀왔어요', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(18, 77, '새로 시작한 취미가 정말 재미있어요. 이번에는 수채화를 그려봤는데, 색감과 표현이 마음에 들어서 뿌듯했어요. 여러분도 새로운 취미를 찾아보세요.', TO_TIMESTAMP('2023-10-21 19:55:25.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 47, '새로 시작한 취미', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(19, 66, '오늘 점심으로 먹은 파스타가 정말 맛있었어요. 특별한 소스와 신선한 재료 덕분에 집에서 먹는 것 같지 않았어요. 여러분도 꼭 한번 만들어보세요.', TO_TIMESTAMP('2023-02-20 17:02:56.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 45, '오늘의 점심 메뉴', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(20, 91, '이번 달 독서 클럽에서 읽은 책은 정말 감명 깊었어요. 다양한 주제를 다루면서도 깊이 있는 내용이 많아서 많은 것을 배울 수 있었어요.', TO_TIMESTAMP('2023-11-01 21:46:26.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 4, '독서 클럽 모임', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(21, 23, '집 근처에 새로 생긴 카페가 정말 좋았어요. 분위기가 아늑하고, 커피도 맛있어서 자주 가게 될 것 같아요. 친구들과 함께 가기 좋은 장소에요.', TO_TIMESTAMP('2023-09-14 23:26:38.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 0, '새로 생긴 카페 방문기', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(22, 71, '주말에 가까운 곳으로 여행을 다녀왔어요. 짧은 시간이었지만, 자연 속에서 힐링할 수 있었어요. 일상에서 벗어나 잠시 쉬어가는 것도 중요한 것 같아요.', TO_TIMESTAMP('2023-12-21 05:22:43.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 0, '주말 여행 계획', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(23, 1, '새로 시작한 취미가 정말 재미있어요. 이번에는 수채화를 그려봤는데, 색감과 표현이 마음에 들어서 뿌듯했어요. 여러분도 새로운 취미를 찾아보세요.', TO_TIMESTAMP('2024-01-30 03:13:46.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 22, '새로 시작한 취미', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(24, 80, '어제 본 영화가 정말 감동적이었어요. 스토리와 연기가 훌륭해서 보는 내내 눈을 뗄 수 없었어요. 여러분께도 꼭 추천드리고 싶은 영화입니다.', TO_TIMESTAMP('2023-06-28 02:03:13.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 0, '어제 영화봤어요~', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(25, 53, '매일 아침 산책을 시작했는데, 기분이 좋아요. 상쾌한 아침 공기와 함께 걷다 보면 하루가 활기차게 시작되는 것 같아요. 건강에도 좋고 추천합니다.', TO_TIMESTAMP('2024-02-26 11:40:14.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 17, '산책 루틴 공유!!', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(26, 34, '요즘 요리에 빠져 있어요. 다양한 레시피를 시도하면서 요리 실력이 늘어가는 게 느껴져요. 여러분도 새로운 요리를 시도해보세요.', TO_TIMESTAMP('2023-11-29 21:39:01.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 29, '요리 이야기', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(27, 89, '오늘 점심으로 먹은 샌드위치가 정말 맛있었어요. 신선한 재료와 특별한 소스로 만든 샌드위치, 여러분도 꼭 한번 만들어보세요.', TO_TIMESTAMP('2024-03-05 09:30:21.564', 'YYYY-MM-DD HH24:MI:SS.FF'), 19, '오늘의 점심 메뉴', 1);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(28, 22, '어제 본 드라마가 정말 재미있었어요. 스토리와 연기가 훌륭해서 보는 내내 눈을 뗄 수 없었어요. 여러분께도 꼭 추천드리고 싶은 드라마입니다.', TO_TIMESTAMP('2023-10-26 17:43:48.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 13, '드라마 감상 후기', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(29, 67, '최근에 시작한 사진 촬영이 정말 재미있어요. 다양한 각도와 조명으로 찍어보면서 새로운 세계를 발견한 것 같아요.', TO_TIMESTAMP('2023-12-26 16:15:26.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 23, '사진 촬영 이야기', 2);
INSERT INTO "COMMUNITY_BOARD" (COMMUNITY_SEQ, COMMUNITY_CNT, COMMUNITY_CONTENT, COMMUNITY_D_REGDATE, COMMUNITY_GOODPOINT, COMMUNITY_TITLE, NO_DATA) VALUES
(30, 38, '집 근처에 새로 생긴 레스토랑에 다녀왔어요. 분위기가 좋고 음식도 맛있어서 자주 가게 될 것 같아요. 여러분도 가보세요.', TO_TIMESTAMP('2023-09-14 23:26:38.000', 'YYYY-MM-DD HH24:MI:SS.FF'), 10, '새로운 레스토랑 방문기', 1);


