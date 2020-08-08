$(document).ready(function(){
	console.log('Script loaded');
	var freePhysicalMemory = $("#FreePhysicalMemory").val();
	var freeVirtualMemory = $("#FreeVirtualMemory").val();
	var ctx = $('#myChart');
	var myChart = new Chart(ctx, {
    type: 'pie',
    data: {
        labels: ['Free Physical Memory', 'Free Virtual Memory'],
        datasets: [{
            label: 'Free Total Memory',
            data: [freePhysicalMemory, freeVirtualMemory],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        },
        responsive : false
    }
});
});