/**
 *  상품을 장바구니에 담기 위한 스크립트
 */
function go_cart(){
	if($("#quantity").val()==""){
		alert("수량을 입력해주세요")
		$("#quantity").focus();
		return false;
	}else if($("#quantity").val() > 100){
		alert("수량이 너무 큽니다")
			$("#quantity").focus();
		return false;}
	 else{
			$("#theform").attr("action", "cart_insert").submit();
		}
}

/**
 * 장바구니 및 주문 관련 자바스크립트
 */

function go_cart_delete(){
	var count = 0; //체크된 항목의 수
	var len = $("[name='cseq']:checked").length;//체크된 상품갯수
	
	//삭제할 항목이 하나일 경우
		if(document.formm.cseq.checked == true){
			count++;
		}else{
			count = len;
		}
	if(count == 0){
		alert("삭제할 물품을 선택해주세요");
		return false;
	}else{
		$("#theform").attr("action","cart_delete").submit();
		
	}
	
	}
	
	/**
	 * 장나구니 내역을 주문테이블에 저장 요청
	 */
	function go_order_insert(){
		$("#theform").attr("action", "order_insert").submit();
		
	}
	