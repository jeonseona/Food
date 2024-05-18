/**
 * 
 */

 // BoardList
 
 //접기
$(document).ready(function() {
    $('#toggleButton').off('click').on('click', function(event) {
        event.preventDefault(); // 기본 동작 방지
        $('#main_head').toggleClass('hidden');
    });
});

//메뉴바
$(document).ready(function() {
    $('.menu-link').on('click', function(event) {
        event.preventDefault();
        var url = $(this).data('url');
        $('#content').load(url);
    });
});

//글자수제한
function truncateText(selector, maxLength) {
    var elements = document.querySelectorAll(selector);
    elements.forEach(function(element) {
      var text = element.textContent;
      if (text.length > maxLength) {
        element.textContent = text.substring(0, maxLength) + '...';
      }
    });
  }

  document.addEventListener("DOMContentLoaded", function() {
    truncateText('.truncate', 9); // 9 글자로 제한
  });

        
 function go_view(seq) {
		var theForm = document.createElement('form');
		theForm.method = "get";
		theForm.action = "/com_board_detail";

		var hiddenSeq = document.createElement('input');
		hiddenSeq.type = "hidden";
		hiddenSeq.name = "seq";
		hiddenSeq.value = seq;

		theForm.appendChild(hiddenSeq);
		document.body.appendChild(theForm);
		theForm.submit();
	}

	function keyClick(event) {
		var category = event.target.getAttribute('data-category');
		var url = '/category';
		if (category === 'cnt_sort' || category === 'goodpoint_sort'
				|| category === 'date_sort') {
			url = '/sorted_board_list';
			url += '?sort=' + category;
		} else {
			url += '?category=' + encodeURIComponent(category);
		}
		window.location.href = url;
	}
	
	function submitSearch() {
	    document.getElementById('searchForm').submit();
	}
	
	
	//BoardDetail
	function go_list() {
		var theForm = document.frm;
		theForm.method = "get";
		theForm.action = "board_list";
		theForm.submit();
	}

	function board_update() {
		var theForm = document.frm;
		theForm.method = "get";
		theForm.action = "board_update";
		theForm.submit();
	}

	function board_delete() {
		var theForm = document.frm;
		theForm.method = "get";
		theForm.action = "board_delete";
		theForm.submit();

	}

	//댓글등록	
	function reply_save() {
		var theForm = document.replyfrm;
		theForm.method = "post";
		theForm.action = "reply_save";
		theForm.submit();
	}

//댓글출력
function reply_list() {
	var seq = $('#seq').val();

	$.ajax({
		url : '/reply_list',
		type : 'post',
		data : {
			seq : seq
		},
		success : function(response) {
			var updatedContent = $(response).find('#reply_list').html(); // 응답에서 댓글 목록 부분만 추출
			$('#reply_list').html(updatedContent); // 페이지의 댓글 목록 부분만 업데이트
		},
		error : function(error) {
			console.error("댓글 로드 중 오류 발생: ", error);
		}
	});
}

	// 페이지 로드 완료 후 댓글 목록 로드
	$(document).ready(function() {
		 if ($('#replyjs').length) {
        reply_list();
    }
	});

	//클립보드복사
	function copyboard() {
		var copy = document.createElement('input'), text = window.location.href;

		document.body.appendChild(copy);
		copy.value = text;
		copy.select();
		document.execCommand('copy');
		document.body.removeChild(copy);

		alert('복사되었습니다');
	}

	//추천
	function goodpoint_plus(button) {
		var theForm = document.frm;
		theForm.method = "post";
		theForm.action = "goodpoint";
		theForm.submit();

	}

	

function deleteReply(replynum, event) {
    event.preventDefault(); // 기본 이벤트 방지

    if (confirm('이 댓글을 삭제하시겠습니까?')) {
        fetch(`/reply_delete?replynum=${replynum}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ replynum: replynum })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`네트워크 응답이 올바르지 않습니다. 상태 코드: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                alert('댓글이 삭제되었습니다.');
                reply_list(); // 댓글 목록을 새로 고침
            } else {
                throw new Error(data.message); // 서버에서 처리 실패 시
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert(`댓글 삭제 중 오류가 발생하였습니다: ${error.message}`);
        });
    }
}


function toggleEditMode(button, event) {
    event.preventDefault();

    const replynum = button.getAttribute('data-replynum');
    const replyElement = button.closest(`tr[data-replynum='${replynum}']`);
    if (!replyElement) {
        console.error('Error: Unable to find the reply element.');
        return;
    }
    const contentView = replyElement.querySelector(`span[data-content-view='${replynum}']`);
    const contentEdit = replyElement.querySelector(`textarea[data-content-edit='${replynum}']`);
    const saveButton = replyElement.querySelector(`button[data-save-button='${replynum}']`);

    if (!contentView || !contentEdit || !saveButton) {
        console.error('Error: Unable to find the necessary HTML elements.');
        return;
    }

    if (contentView.style.display === 'none') {
        contentView.style.display = 'block';
        contentEdit.style.display = 'none';
        button.style.display = 'inline'; // 수정 버튼 표시
        saveButton.style.display = 'none'; // 저장 버튼 숨김
    } else {
        contentView.style.display = 'none';
        contentEdit.style.display = 'block';
        button.style.display = 'none'; // 수정 버튼 숨김
        saveButton.style.display = 'inline'; // 저장 버튼 표시
    }
}

//***** 댓글 내용 저장 *****
function saveChanges(button, event) {
    event.preventDefault(); // 기본 이벤트 방지

    // 댓글 번호를 가져옵니다.
    const replynum = button.getAttribute('data-replynum');
    const replyElement = button.closest(`tr[data-replynum='${replynum}']`);
    if (!replyElement) {
        console.error('Error: Unable to find the reply element.');
        return;
    }
    const content = replyElement.querySelector(`textarea[data-content-edit='${replynum}']`).value;

    // 디버깅: replynum과 content를 출력합니다.
    console.log('Reply Number:', replynum);
    console.log('Content:', content);

    // AJAX 요청을 통해 댓글 내용을 업데이트합니다.
    fetch('/reply_update', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ replynum: replynum, content: content })
    })
    .then(response => {
        // 디버깅: 응답 상태 코드를 출력합니다.
        console.log('Response Status:', response.status);
        return response.json();
    })
    .then(data => {
        if (data.success) {
            alert('댓글이 수정되었습니다.');
            replyElement.querySelector(`span[data-content-view='${replynum}']`).textContent = content;

            // 저장 후 수정 버튼과 저장 버튼의 상태를 조정합니다.
            const editButton = replyElement.querySelector(`button[data-replynum='${replynum}']`);
            editButton.style.display = 'inline'; // 수정 버튼 표시
            button.style.display = 'none'; // 저장 버튼 숨김

            const contentView = replyElement.querySelector(`span[data-content-view='${replynum}']`);
            const contentEdit = replyElement.querySelector(`textarea[data-content-edit='${replynum}']`);
            contentView.style.display = 'block'; // 내용 보기 표시
            contentEdit.style.display = 'none'; // 내용 편집 숨김
        } else {
            throw new Error(data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('댓글 수정 중 오류가 발생했습니다: ' + error.message);
    });
}

//Boardupdate
function go_mov() {
		var theForm = document.frm
		theForm.method = "get";
		theForm.action = "board_list";
		theForm.submit();
	}

	function go_mod_save() {
		var theForm = document.getElementById('update_form');
		if (!theForm.seq.value) {
			alert('문서 번호가 없습니다. 다시 시도해 주세요.');
			return false; // 폼 제출 방지
		}
		theForm.method = "post";
		theForm.action = "/board_update_t";
		theForm.submit();
	}
	
	//BoardWrite
	
	function go_save() {	
	if ($("#title").val() == "") {
		alert("요리명을 입력하세요.");
		$("#title").focus();
		return false;
	} else if ($("#gredient").val() == "") {
		alert("재료를 입력하세요.");
		$("#gredient").focus();
		return false;
	} else if ($("#manual01").val() == "") {
		alert("조리법을 입력하세요.");
		$("#manual01").focus();
		return false;
	} else{
		var theform = $("#write_form");
		theform.attr("method", "post");
		theform.attr("enctype", "multipart/form-data");
		theform.attr("action", "/board_write_t");
		theform.submit();
	}
		}
	
		
function go_list()
{
	   var theForm = document.frm;
	   theForm.method = "get";
	   theForm.action="board_list";
	   theForm.submit();
}		


// 그래프

	$(document).ready(function() {
		 if ($('#boardlistbody').length) {
			 google.charts.load('current', {packages: ['corechart'], language: 'ko'});
    		google.charts.setOnLoadCallback(drawChart);
    		google.charts.setOnLoadCallback(drawAnthonyChart);
    
        drawChart();
        drawAnthonyChart();
    }
	});

    

    function drawChart() {
    $.ajax({
        url: '/sorted_board_list',
        data: {
            seq: 1,
            page: 1,
            size: 10,
            sort: 'cnt_sort'
        },
        method: 'GET',
        headers: {
            Accept: 'application/json'
        },
        success: function(response) {
            var chartData = response;
            if (!Array.isArray(chartData)) {
                console.error('Unexpected response format:', response);
                return;
            }

            // 차트 데이터를 설정
            var dataArray = [['Recipe Name', 'Count', { role: 'style' }]];
            chartData.forEach(function(item) {
                dataArray.push([item.com_recipe.rcp_nm, item.cnt, '#8EC7D0']);
            });

            var data = google.visualization.arrayToDataTable(dataArray);

            // 차트 옵션
            var barchart_options = {
                title: '조회수 Top 3',
                width: 600 ,
                height: 300,
                legend: 'none',
                titleTextStyle: {
                    fontSize: 18 // 제목 글씨 크기
                },
                vAxis: {
                    minValue: 0,
                    viewWindow: {
                        min: 0
                    },
                    gridlines: {
                        count: 2 // 주 그리드라인 개수
                    },
                    minorGridlines: {
                        count: 1 // 소 그리드라인 간격
                    }
                }
            };


            // 차트 그리기
            var barchart = new google.visualization.ColumnChart(document.getElementById('visitChart'));
            barchart.draw(data, barchart_options);
        },
        error: function(err) {
            console.error('Error fetching chart data', err);
        }
    });
}
    
    
    
     function drawAnthonyChart() {
        $.ajax({
        url: '/sorted_board_list',
        data: {
            seq: 1,
            page: 1,
            size: 10,
            sort: 'goodpoint_sort'
        },
        method: 'GET',
        headers: {
            Accept: 'application/json'
        },
        success: function(response) {
            var chartData = response;
            if (!Array.isArray(chartData)) {
                console.error('Unexpected response format:', response);
                return;
            }

            // 차트 데이터를 설정
            var dataArray = [['Recipe Name', 'Count', { role: 'style' }]];
            chartData.forEach(function(item) {
                dataArray.push([item.com_recipe.rcp_nm, item.goodpoint, '#8EA8DB']); // 예: 색상은 임의로 지정
            });

            var data = google.visualization.arrayToDataTable(dataArray);

            // 차트 옵션
            var barchart_options = {
                title: '추천수 Top 3',
                width: 600,
                height: 300,
                legend: 'none',
                titleTextStyle: {
                    fontSize: 18 // 제목 글씨 크기
                },
                vAxis: {
                    minValue: 0,
                    viewWindow: {
                        min: 0
                    },
                    gridlines: {
                        count: -1, // 자동 계산
                        interval: 1
                    }
                }
            };

            // 차트 그리기
            var barchart = new google.visualization.ColumnChart(document.getElementById('goodpointChart'));
            barchart.draw(data, barchart_options);
        },
        error: function(err) {
            console.error('Error fetching chart data', err);
        }
    });
    
}
      