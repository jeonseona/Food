/**
 * 
 */
/**
 * 약관 동의 여부 확인
 */
function go_next(){
		if($(".agree")[0].checked == true){ //동의함에 체크
			$("#join").attr("action", "join_form").submit();//서버로 url전송
		}else if($(".agree")[1].checked == true){
			alert("약관에 동의하셔야 가입할 수 있습니다.")
		}
	}

/**
 * 회원가입 */	
function go_save(){
	if($("#id").val() == ""){
		alert("아이디를 입력해주세요")
		$("#id").focus();
		return false;
	}else if($("#id").val() != $("#reid").val()){
		alert("아이디 중복체크를 해주세요")
		}else if($("#pwd").val() == ""){
		alert("비밀번호를 입력해주세요")
		$("#pwd").focus();
		return false;
	}else if($("#pwd").val() != $("#pwdCheck").val()){
		alert("비밀번호가 일치하지 않습니다")
		$("#pwd").focus();
		return false;
	}else if($("#name").val() == ""){
		alert("이름을 입력해주세요")
		$("#name").focus();
		return false;
	}
	else if($("#email").val() == ""){
		alert("이메일을 입력해주세요")
		$("#email").focus();
		return false;
	}else{
		$("#join").attr("action","join").submit();
	}
}
/**
 * id 중복확인 화면 출력요청
 */

function idcheck() {
	if($("#id").val() == ""){
		alert("아이디를 입력해 주세요!")
		$("#id").focus();
		return false;
	}
	
var url = "id_check_form?id="+$("#id").val();
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=no, " +
			"resizable=yes, width=350, height=200");

}

function post_zip() {
	var url = "find_zip_num";
	
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=no, " +
	"resizable=yes, width=500, height=350");
}

/**
 * 아이디, 비밀번호 찾기 화면 요청
 * 
 */
function find_id_form(){
	var url = "find_id_form";
	
	window.open(url, "_blank_", "toolbar=no , menubar = no, scrollbars = no, " 
	+ "resizable=yes, width=550, height=450");
}

/**
 * 아이디 찾기 요청
 */
function findMemberId(){
		if($("#name").val() == ""){
		alert("이름을 입력해주세요")
		$("#name").focus();
		return false;
		
		}else if($("#email").val() == ""){
		alert("이메일을 입력해주세요")
		$("#email").focus();
		return false;
		}
		else{
			var form = $("#findId");
			 form.action = "find_id"; //컨트롤러 요청 url
			form.submit(); //컨트롤러로 전송
			//("#findId").attr("action","find_id").submit();
		}	
}

function findPassword(){
		if($("#id2").val() == ""){
		alert("아이디를 입력해주세요")
		$("#id2").focus();
		return false;
		}else if($("#name2").val() == ""){
		alert("이름을 입력해주세요")
		$("#name2").focus();
		return false;
		}else if($("#email2").val() == ""){
		alert("이메일을 입력해주세요")
		$("#email2").focus();
		return false;
		}
		else{
			//("#findPW").attr("action","find_pwd").submit();
			var form = $("#findPW");
			form.action = "find_pwd";
			form.submit(); 
		}
}

function changepwd(){
		if($("#pwd2").val() == ""){
		alert("변경할 비밀번호를 입력해주세요")
		$("#pwd2").focus();
		return false;
		}else{
			var form = $("#Pwd_change");
			form.action = "new_pwd";
			form.submit(); 
		}
}







































