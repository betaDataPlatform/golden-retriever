(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["pages-monitor-monitor-module"],{

/***/ "Aubn":
/*!****************************************************!*\
  !*** ./src/app/pages/monitor/monitor.component.ts ***!
  \****************************************************/
/*! exports provided: MonitorComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MonitorComponent", function() { return MonitorComponent; });
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! rxjs */ "qCKp");
/* harmony import */ var highcharts__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! highcharts */ "6n/F");
/* harmony import */ var highcharts__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(highcharts__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var highcharts_themes_dark_unica__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! highcharts/themes/dark-unica */ "Zpr+");
/* harmony import */ var highcharts_themes_dark_unica__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(highcharts_themes_dark_unica__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ "fXoL");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common/http */ "tk/3");
/* harmony import */ var highcharts_angular__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! highcharts-angular */ "kAVD");






highcharts_themes_dark_unica__WEBPACK_IMPORTED_MODULE_2___default()(highcharts__WEBPACK_IMPORTED_MODULE_1__);
class MonitorComponent {
    constructor(httpClient) {
        this.httpClient = httpClient;
        this.Highcharts = highcharts__WEBPACK_IMPORTED_MODULE_1__;
        this.metricCount_chartOptions = {
            chart: {
                type: 'spline',
            },
            time: {
                useUTC: false,
            },
            title: {
                "text": '??????????????????'
            },
            xAxis: {
                type: 'datetime',
                dateTimeLabelFormats: {
                    day: '%m-%d',
                },
                labels: {
                    overflow: 'justify',
                },
            },
            yAxis: {
                allowDecimals: false,
                title: {
                    text: "??????"
                }
            },
            tooltip: {
                headerFormat: '<b>{point.x:%Y-%m-%d %H:%M:%S}</b><br>',
                pointFormat: '{point.y:.2f}',
            },
            plotOptions: {
                spline: {
                    marker: {
                        enabled: true
                    }
                }
            },
            series: [{
                    type: 'spline',
                    name: "??????????????????",
                    data: []
                },
                {
                    type: 'spline',
                    name: "??????????????????",
                    data: []
                }]
        };
        this.queryCount_chartOptions = {
            chart: {
                type: 'spline',
            },
            time: {
                useUTC: false,
            },
            title: {
                "text": '??????????????????'
            },
            xAxis: {
                type: 'datetime',
                dateTimeLabelFormats: {
                    day: '%m-%d',
                },
                labels: {
                    overflow: 'justify',
                },
            },
            yAxis: {
                allowDecimals: false,
                title: {
                    text: "??????"
                }
            },
            tooltip: {
                formatter: function () {
                    let point = this.point;
                    console.log(point);
                    return '<b>count: ' + point.y + '</b><br><b>avg: '
                        + point.avg + '</b><br><b>max: ' + point.max + '</b><br><b>95th: '
                        + point.th95 + '</b><br><b>99th: ' + point.th99 + '</b><br>';
                }
            },
            plotOptions: {
                spline: {
                    marker: {
                        enabled: true
                    }
                }
            },
            series: [{
                    type: 'spline',
                    name: "????????????",
                    data: []
                }]
        };
        const self = this;
        this.metricCount_chartCallback = (chart) => {
            self.metricCount_chart = chart;
        };
        this.queryCount_chartCallback = (chart) => {
            self.queryCount_chart = chart;
        };
    }
    ngOnInit() {
        const source = Object(rxjs__WEBPACK_IMPORTED_MODULE_0__["timer"])(1000, 30 * 1000);
        const subscribe = source.subscribe(val => this.getMessage());
    }
    getMessage() {
        let url = '/monitor';
        this.httpClient.get(url).subscribe((data) => {
            let metrics = data;
            // ????????????????????????
            metrics.forEach(metric => {
                if (metric.name === 'dp.metricValue.count.save') {
                    this.metricCount_chart.series[0].addPoint(metric.datapoints[0], true, false);
                }
                else if (metric.name === 'dp.metricValue.count.drop') {
                    this.metricCount_chart.series[1].addPoint(metric.datapoints[0], true, false);
                }
            });
            // ????????????????????????
            let queryMetric = {};
            metrics.forEach(metric => {
                if (metric.name === 'dp.query.timer.count') {
                    queryMetric['x'] = metric.datapoints[0][0];
                    queryMetric['y'] = metric.datapoints[0][1];
                }
                else if (metric.name === 'dp.query.timer.max') {
                    queryMetric['max'] = metric.datapoints[0][1];
                }
                else if (metric.name === 'dp.query.timer.avg') {
                    queryMetric['avg'] = metric.datapoints[0][1];
                }
                else if (metric.name === 'dp.query.timer.percentile') {
                    if (metric.tags['phi'] === '0.99') {
                        queryMetric['th99'] = metric.datapoints[0][1];
                    }
                    else if (metric.tags['phi'] === '0.95') {
                        queryMetric['th95'] = metric.datapoints[0][1];
                    }
                }
            });
            this.queryCount_chart.series[0].addPoint(queryMetric, true, false);
        });
    }
}
MonitorComponent.??fac = function MonitorComponent_Factory(t) { return new (t || MonitorComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_3__["????directiveInject"](_angular_common_http__WEBPACK_IMPORTED_MODULE_4__["HttpClient"])); };
MonitorComponent.??cmp = _angular_core__WEBPACK_IMPORTED_MODULE_3__["????defineComponent"]({ type: MonitorComponent, selectors: [["app-monitor"]], decls: 6, vars: 8, consts: [["nz-row", ""], ["nz-col", "", "nzSpan", "24"], [2, "width", "100%", "height", "400px", "display", "block", 3, "Highcharts", "options", "callbackFunction", "oneToOne"], ["nz-row", "", 2, "margin-top", "20px"]], template: function MonitorComponent_Template(rf, ctx) { if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????elementStart"](0, "div", 0);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????elementStart"](1, "div", 1);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????element"](2, "highcharts-chart", 2);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????elementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????elementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????elementStart"](3, "div", 3);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????elementStart"](4, "div", 1);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????element"](5, "highcharts-chart", 2);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????elementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????elementEnd"]();
    } if (rf & 2) {
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????advance"](2);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????property"]("Highcharts", ctx.Highcharts)("options", ctx.metricCount_chartOptions)("callbackFunction", ctx.metricCount_chartCallback)("oneToOne", true);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????advance"](3);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["????property"]("Highcharts", ctx.Highcharts)("options", ctx.queryCount_chartOptions)("callbackFunction", ctx.queryCount_chartCallback)("oneToOne", true);
    } }, directives: [highcharts_angular__WEBPACK_IMPORTED_MODULE_5__["HighchartsChartComponent"]], styles: ["\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJtb25pdG9yLmNvbXBvbmVudC5jc3MifQ== */"] });


/***/ }),

/***/ "eVmM":
/*!*********************************************************!*\
  !*** ./src/app/pages/monitor/monitor-routing.module.ts ***!
  \*********************************************************/
/*! exports provided: MonitorRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MonitorRoutingModule", function() { return MonitorRoutingModule; });
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/router */ "tyNb");
/* harmony import */ var _monitor_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./monitor.component */ "Aubn");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "fXoL");




const routes = [
    { path: '', component: _monitor_component__WEBPACK_IMPORTED_MODULE_1__["MonitorComponent"] },
];
class MonitorRoutingModule {
}
MonitorRoutingModule.??fac = function MonitorRoutingModule_Factory(t) { return new (t || MonitorRoutingModule)(); };
MonitorRoutingModule.??mod = _angular_core__WEBPACK_IMPORTED_MODULE_2__["????defineNgModule"]({ type: MonitorRoutingModule });
MonitorRoutingModule.??inj = _angular_core__WEBPACK_IMPORTED_MODULE_2__["????defineInjector"]({ imports: [[_angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"].forChild(routes)], _angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_2__["????setNgModuleScope"](MonitorRoutingModule, { imports: [_angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"]], exports: [_angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"]] }); })();


/***/ }),

/***/ "pqN0":
/*!*************************************************!*\
  !*** ./src/app/pages/monitor/monitor.module.ts ***!
  \*************************************************/
/*! exports provided: MonitorModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MonitorModule", function() { return MonitorModule; });
/* harmony import */ var _monitor_routing_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./monitor-routing.module */ "eVmM");
/* harmony import */ var _monitor_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./monitor.component */ "Aubn");
/* harmony import */ var highcharts_angular__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! highcharts-angular */ "kAVD");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ "fXoL");




class MonitorModule {
}
MonitorModule.??fac = function MonitorModule_Factory(t) { return new (t || MonitorModule)(); };
MonitorModule.??mod = _angular_core__WEBPACK_IMPORTED_MODULE_3__["????defineNgModule"]({ type: MonitorModule });
MonitorModule.??inj = _angular_core__WEBPACK_IMPORTED_MODULE_3__["????defineInjector"]({ imports: [[
            _monitor_routing_module__WEBPACK_IMPORTED_MODULE_0__["MonitorRoutingModule"],
            highcharts_angular__WEBPACK_IMPORTED_MODULE_2__["HighchartsChartModule"]
        ]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_3__["????setNgModuleScope"](MonitorModule, { declarations: [_monitor_component__WEBPACK_IMPORTED_MODULE_1__["MonitorComponent"]], imports: [_monitor_routing_module__WEBPACK_IMPORTED_MODULE_0__["MonitorRoutingModule"],
        highcharts_angular__WEBPACK_IMPORTED_MODULE_2__["HighchartsChartModule"]], exports: [_monitor_component__WEBPACK_IMPORTED_MODULE_1__["MonitorComponent"]] }); })();


/***/ })

}]);
//# sourceMappingURL=pages-monitor-monitor-module.js.map