var incomeChartData = [
    #{list items:chartData, as:'data'}
    [Date.UTC(${data.date}), ${data.value}],
    #{/list}
];
