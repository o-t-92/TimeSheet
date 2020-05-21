const deptListWidget = {
    view: "list",
    id: "deptList",
    select: true,
    scroll: "auto",
    width: 175,
    type: {height: "auto"},
    template: "#name#",
    url: "/api/department/",
    on: {
        "onAfterSelect": function () {
            refreshGridData();
        }
    }
};
const monthsTabBarWidget = {
    view: "tabbar",
    id: "monthsTabBar",
    value: new Date().getMonth() + 1,
    tabMinWidth: 70,
    options: [
        {value: "Январь", id: 1},
        {value: "Февраль", id: 2},
        {value: "Март", id: 3},
        {value: "Апрель", id: 4},
        {value: "Май", id: 5},
        {value: "Июнь", id: 6},
        {value: "Июль", id: 7},
        {value: "Август", id: 8},
        {value: "Сентябрь", id: 9},
        {value: "Октябрь", id: 10},
        {value: "Ноябрь", id: 11},
        {value: "Декабрь", id: 12}
    ],
    on: {
        "onChange": function () {
            refreshGridColumns();
            refreshGridData();
        }
    }
};
const monthGridWidget = {
    view: "datatable",
    id: "monthGrid",
    css: "webix_header_border webix_data_border",
    padding: 8,
    rowHeight: 30,
    leftSplit: 3,
    columns: getColumns(),
};

webix.ui({
    cols: [
        {
            rows: [
                {view: "template", template: "Департаменты", type: "section"},
                deptListWidget
            ]
        },
        {view: "resizer"},
        {
            rows: [
                monthsTabBarWidget,
                monthGridWidget
            ]
        }
    ]
});

function refreshGridData() {
    let deptId = $$("deptList").getSelectedId();
    if (deptId) {
        let now = new Date();
        let year = now.getFullYear();
        let month = $$("monthsTabBar")?.getValue() || now.getMonth() + 1;
        webix.ajax().get(`/api/calendar/timesheet/${year}/${month}?dept_id=${deptId}`)
            .then(
                function (data) {
                    data = data.json();

                    for (let empl of data.emplsWithMarks) {
                        Object.assign(empl, empl.marks);
                        delete empl.marks;
                        empl.total = Object.entries(empl.markCounter)
                            .map(kvPair => kvPair.join(": "))
                            .join("; ");
                    }

                    let grid = $$("monthGrid");
                    grid.clearAll();
                    grid.parse(data.emplsWithMarks);
                    grid.sort("fullName");
                },
                function (err) {
                    console.log(err);
                });
    } else {
        webix.message("Не выбран департамент");
    }
}

function refreshGridColumns() {
    let grid = $$("monthGrid");
    grid.config.columns = getColumns();
    grid.refreshColumns();
}

function getColumns() {
    let columns = [
        {id: "fullName", header: "ФИО", adjust: true, sort: "string"},
        {id: "positionName", header: "Должность", adjust: true, sort: "string"},
        {id: "emplId", header: "Табельный №", width: 120, sort: "int"}
    ];
    let year = new Date().getFullYear();
    let month = $$("monthsTabBar")?.getValue() || new Date().getMonth() + 1;

    let prodCal = JSON.parse(webix.ajax().sync().get(`/api/calendar/prod_calendar/${year}/${month}`).responseText);
    for (let [date, dayType] of Object.entries(prodCal)) {
        columns.push({
            id: date,
            header: {text: date.substring(8), css: dayType},
            width: 39,
            css: dayType
        });
    }
    columns.push({id: "total", header: "Итого", adjust: true})

    return columns;
}