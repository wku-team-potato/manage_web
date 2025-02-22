
document.addEventListener('DOMContentLoaded', function() {
    const weightChart = document.getElementById('weightChart');
    if (weightChart) {
        new Chart(weightChart, {
            type: 'line',
            data: {
                // ... 차트 설정
            }
        });
    }

    // 다른 차트들도 같은 방식으로 초기화
    // ...existing chart code...
});