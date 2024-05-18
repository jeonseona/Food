/**
 * 마이페이지 스크립트
*/

/**
 * 개인정보 수정 (닉네임, 비밀번호, 이메일) 
 */
function change_info() {
	if ($("#password").val() == "") {
		alert("변경할 비밀번호를 입력하세요.");
		$("#password").focus();
		return false;
	} else if ($("#password").val() != $("#pwdcheck").val()) {
		alert("비밀번호가 일치하지 않습니다.");
		$("#password").focus();
		return false;
	} else if ($("#nickname").val() == "") {
		alert("변경할 닉네임을 입력하세요.");
		$("#nickname").focus();
		return false;
	} else if ($("#nickname").val() != $("#renickname").val()) {
		alert("닉네임 중복 체크를 해주세요.");
		$("#nickname").focus();
		return false;
	} else if ($("#email").val() == "") {
		alert("변경할 이메일을 입력하세요.");
		$("#email").focus();
		return false;
	} else {
		$("#update_info").attr("action", "update_info").submit();
	}
}
/*
** 닉네임 중복확인 화면 출력요청
*/
function nickcheck() {
	if ($("#nickname").val() == "") {
		alert("닉네임을 입력해 주세요!");
		$("#nickname").focus();
		return false;
	}

	// 닉네임 중복확인 창 오픈
	var url = "nickname_check_form?nickname=" + $("#nickname").val();
	window.open(url, "_blank_", "toolbar=no, menubar=no, scrollbars=no, " +
		"resizable=yes, width=600, height=400");
}
/**
 * 바디데이터 수정
 */
function change_bodydata() {
	if ($("#age").val() == "") {
		alert("나이를 입력해 주세요.");
		$("#age").focus();
		return false;
	} else if ($("#height").val() == "") {
		alert("키를 입력해 주세요.");
		$("#height").focus();
		return false;
	} else if ($("#weight").val() == "") {
		alert("몸무게를 입력해 주세요.");
		$("#weight").focus();
		return false;
	} else if ($("#bmi").val() == "") {
		alert("BMI를 계산해 주세요.");
		$("#bmi").focus();
		return false;
	} else if ($("#goal").val() == "") {
		alert("목표를 입력해 주세요.");
		$("#goal").focus();
		return false;
	} else {
		$("#update_body").attr("action", "update_body").submit();
	}
}

/*
** bmi 계산 버튼
*/
function my_bmi() {
	var height = document.getElementById('height').value;
	var weight = document.getElementById('weight').value;
	var age = document.getElementById('age').value;
	var gender = document.getElementById('gender').value;

	if (height > 0 && weight > 0 && age > 0 && (gender === 'Male' || gender === 'Female')) {
		var height_m = height / 100;
		var weight_kg = weight;
		var bmi = weight_kg / (height_m ** 2);

		var bmr;
		if (gender === 'Male') {
			bmr = 88.362 + (13.397 * weight_kg) + (4.799 * height_m * 100) - (5.677 * age);
		} else {
			bmr = 447.593 + (9.247 * weight_kg) + (3.098 * height_m * 100) - (4.330 * age);
		}

		var calories_threshold = bmr * 1.2;

		document.getElementById('bmi').value = bmi.toFixed(2);
		document.getElementById('calories_threshold').value = calories_threshold.toFixed(2);
	} else {
		alert('키와 몸무게, 나이, 성별을 올바르게 입력하세요.');
	}
}

// 체중변화 입력값 받기
function weight_record() {
    if ($("#re_date").val() === "") {
        alert("날짜를 제대로 입력해 주세요.");
        $("#re_date").focus();
        return false;
    } else if ($("#re_weight").val() === "") {
        alert("몸무게를 제대로 입력해 주세요.");
        $("#re_weight").focus();
        return false;
    } else {
        // 폼 데이터를 직렬화하여 전송
        $.post("/weight_record", $("#weight_chart").serialize())
            .done(function(response) {
                alert("저장 성공!!");
                // 그래프 업데이트
                updateCharts();
            })
            .fail(function() {
                alert("저장 실패. 다시 시도해 주세요.");
            });
    }
}

// 차트 그리기
$(document).ready(function() {
    if ($('#weeklyChart').length) {
        google.charts.load('current', {packages: ['corechart'], language: 'ko'});
        google.charts.setOnLoadCallback(function() {
            $.ajax({
                url: '/getRecordChart',
                method: 'GET',
                headers: {
                    Accept: 'application/json'
                },
                success: function(response) {
                    if (response.redirect) {
                        window.location.href = response.redirect;
                        return;
                    }

                    var weeklyData = JSON.parse(response.split('<br>')[0]);
                    var monthlyData = JSON.parse(response.split('<br>')[1]);

                    drawWeightChart(weeklyData, '주간 체중 기록', '주', '체중', 'weeklyChart');
                    drawWeightChart(monthlyData, '월간 체중 기록', '월', '체중', 'monthlyChart');
                },
                error: function(err) {
                    console.error('Error fetching chart data', err);
                }
            });
        });
    }
});

function drawWeightChart(data, title, xAxisLabel, yAxisLabel, chartElementId) {
    var chartData = new google.visualization.DataTable();
    chartData.addColumn('date', xAxisLabel);
    chartData.addColumn('number', yAxisLabel);

    data.forEach(function(record) {
        chartData.addRow([new Date(record.re_date), record.re_weight]);
    });

    var options = {
        title: title,
        curveType: 'function',
        legend: { position: 'bottom' }
    };

    var chart = new google.visualization.LineChart(document.getElementById(chartElementId));
    chart.draw(chartData, options);
}


$(document).ready(function() {
    $('.menu-link').on('click', function(event) {
        event.preventDefault();
        var url = $(this).data('url');
        $('#content').load(url);
    });
});