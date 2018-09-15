var Script = function () {
    var RxY = 666;
    var OTCY = 666;
    var RxA = 666;
    var OTCA = 666;
    var RxZ = 666;
    var OTCZ = 666;
    //饼图
    var doughnutData = [
        {
            value: RxY,
            color: "#46BFBD" //Rx蓝色 Y
        },
        {
            value: OTCY,
            color: "#98FB98" //OTC绿色 Y
        },
        {
            value: RxA,
            color: "#FDB45C" //Rx橙色 A
        },
        {
            value: OTCA,
            color: "#FF0000" //OTC红色 A
        },
        {
            value: RxZ,
            color: "#4D5360" //Rx黑色 Z
        },
        {
            value: OTCZ,
            color: "#8B8B7A" //OTC灰色 Z
        }
    ];
    //折线图
    var lineChartData = {
        labels: ["", "", "", "", "", "", ""],
        datasets: [
            {
                fillColor: "rgba(220,220,220,0.5)",
                strokeColor: "rgba(220,220,220,1)",
                pointColor: "rgba(220,220,220,1)",
                pointStrokeColor: "#fff",
                data: [45, 59, 90, 81, 56, 55, 40]
            },
            {
                fillColor: "rgba(151,187,205,0.5)",
                strokeColor: "rgba(151,187,205,1)",
                pointColor: "rgba(151,187,205,1)",
                pointStrokeColor: "#fff",
                data: [28, 48, 40, 19, 96, 27, 100]
            }
        ]

    };

    //new Chart(document.getElementById("doughnut").getContext("2d")).Doughnut(doughnutData);
    //$("#demo").append("Rx橙：A，OTC红：A，Rx蓝：Y，OTC绿：Y，Rx黑：Z，OTC灰：Z");
    //new Chart(document.getElementById("line").getContext("2d")).Line(lineChartData);

}();