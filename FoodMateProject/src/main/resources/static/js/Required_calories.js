function calculateBMI() {
    const age = parseInt(document.getElementById('age').value); // 나이
    const weight = parseFloat(document.getElementById('weight').value); // 몸무게
    const height = parseFloat(document.getElementById('height').value) / 100; // 키를 미터로 변환
    const gender = document.querySelector('input[name="gender"]:checked').value; // 성별 선택 (라디오 버튼으로 가정)
    const bmi = weight / (height * height); // BMI 계산
    document.getElementById('bmi-result').innerText = `BMI: ${bmi.toFixed(2)}`; // BMI 결과 표시

    let bmr;
    if (age && weight && height) { // 모든 필드가 채워져 있는지 확인
        if (gender === 'Male') { // 남성의 경우
            bmr = 88.362 + (13.397 * weight) + (4.799 * height * 100) - (5.677 * age); // BMR 계산
        } else { // 여성의 경우
            bmr = 447.593 + (9.247 * weight) + (3.098 * height * 100) - (4.330 * age); // BMR 계산
        }
        // 사용자가 필요로 하는 일일 칼로리 계산
        const caloriesThreshold = user_required_calories(height * 100, weight, age, gender);
        document.getElementById('bmr-result').innerText = `BMR: ${bmr.toFixed(2)} kcal`; // BMR 결과 표시
        document.getElementById('calories-result').innerText = `필요한 하루 권장 칼로리: ${caloriesThreshold.toFixed(2)} kcal`; // 계산된 일일 칼로리 표시
    } else {
        alert('모든 항목을 입력해주세요.'); // 필수 항목이 모두 입력되지 않은 경우 경고 메시지 표시
    }
}
