/**
 * 
 */

// Chart.js 파일 내용 예시

// 차트 생성을 위한 데이터
const labels = ['레시피1', '레시피2', '레시피3', '레시피4', '레시피5', '레시피6', '레시피7', '레시피8', '레시피9', '레시피10'];
const data = [100, 95, 90, 85, 80, 75, 70, 65, 60, 55];

// 바 차트 생성
const ctx = document.getElementById('myChart').getContext('2d');
const myChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: labels,
    datasets: [{
      label: '조회수 Top 10',
      data: data,
      backgroundColor: 'rgba(255, 99, 132, 0.2)',
      borderColor: 'rgba(255, 99, 132, 1)',
      borderWidth: 1
    }]
  },
  options: {
    scales: {
      y: {
        beginAtZero: true
      }
    }
  }
});
