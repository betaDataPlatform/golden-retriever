(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["pages-systemMonitor-systemMonitor-module"],{

/***/ "36Gm":
/*!****************************************************************!*\
  !*** ./src/app/pages/systemMonitor/systemMonitor.component.ts ***!
  \****************************************************************/
/*! exports provided: SystemMonitorComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SystemMonitorComponent", function() { return SystemMonitorComponent; });
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! rxjs */ "qCKp");
/* harmony import */ var highcharts__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! highcharts */ "6n/F");
/* harmony import */ var highcharts__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(highcharts__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var highcharts_themes_dark_unica__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! highcharts/themes/dark-unica */ "Zpr+");
/* harmony import */ var highcharts_themes_dark_unica__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(highcharts_themes_dark_unica__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ "fXoL");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common/http */ "tk/3");
/* harmony import */ var highcharts_angular__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! highcharts-angular */ "kAVD");






highcharts_themes_dark_unica__WEBPACK_IMPORTED_MODULE_2___default()(highcharts__WEBPACK_IMPORTED_MODULE_1__);
class SystemMonitorComponent {
    constructor(httpClient) {
        this.httpClient = httpClient;
        this.Highcharts = highcharts__WEBPACK_IMPORTED_MODULE_1__;
        this.mem_chartOptions = {
            chart: {
                type: 'spline',
            },
            time: {
                useUTC: false,
            },
            title: {
                "text": '内存使用监控'
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
                    text: "容量(M)"
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
                    name: "Eden Space",
                    data: []
                },
                {
                    type: 'spline',
                    name: "Survivor Space",
                    data: []
                },
                {
                    type: 'spline',
                    name: "Old Gen",
                    data: []
                }]
        };
        this.cpu_chartOptions = {
            chart: {
                type: 'spline',
            },
            time: {
                useUTC: false,
            },
            title: {
                "text": 'CPU使用监控'
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
                    text: "百分比(%)"
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
                    name: "系统",
                    data: []
                },
                {
                    type: 'spline',
                    name: "业务",
                    data: []
                }]
        };
        const self = this;
        this.mem_chartCallback = (chart) => {
            self.mem_chart = chart;
        };
        this.cpu_chartCallback = (chart) => {
            self.cpu_chart = chart;
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
            metrics.forEach(metric => {
                if (metric.name === 'jvm.memory.used') {
                    if (metric.tags['id'].endsWith('Eden Space')) {
                        this.mem_chart.series[0].addPoint(metric.datapoints[0], true, false);
                    }
                    else if (metric.tags['id'].endsWith('Survivor Space')) {
                        this.mem_chart.series[1].addPoint(metric.datapoints[0], true, false);
                    }
                    else if (metric.tags['id'].endsWith('Old Gen')) {
                        this.mem_chart.series[2].addPoint(metric.datapoints[0], true, false);
                    }
                }
                else if (metric.name === 'system.cpu.usage') {
                    this.cpu_chart.series[0].addPoint(metric.datapoints[0], true, false);
                }
                else if (metric.name === 'process.cpu.usage') {
                    this.cpu_chart.series[1].addPoint(metric.datapoints[0], true, false);
                }
            });
        });
    }
}
SystemMonitorComponent.ɵfac = function SystemMonitorComponent_Factory(t) { return new (t || SystemMonitorComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdirectiveInject"](_angular_common_http__WEBPACK_IMPORTED_MODULE_4__["HttpClient"])); };
SystemMonitorComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdefineComponent"]({ type: SystemMonitorComponent, selectors: [["app-systemMonitor"]], decls: 6, vars: 8, consts: [["nz-row", ""], ["nz-col", "", "nzSpan", "24"], [2, "width", "100%", "height", "400px", "display", "block", 3, "Highcharts", "options", "callbackFunction", "oneToOne"], ["nz-row", "", 2, "margin-top", "20px"]], template: function SystemMonitorComponent_Template(rf, ctx) { if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](0, "div", 0);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](1, "div", 1);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelement"](2, "highcharts-chart", 2);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](3, "div", 3);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementStart"](4, "div", 1);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelement"](5, "highcharts-chart", 2);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵelementEnd"]();
    } if (rf & 2) {
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵadvance"](2);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵproperty"]("Highcharts", ctx.Highcharts)("options", ctx.cpu_chartOptions)("callbackFunction", ctx.cpu_chartCallback)("oneToOne", true);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵadvance"](3);
        _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵproperty"]("Highcharts", ctx.Highcharts)("options", ctx.mem_chartOptions)("callbackFunction", ctx.mem_chartCallback)("oneToOne", true);
    } }, directives: [highcharts_angular__WEBPACK_IMPORTED_MODULE_5__["HighchartsChartComponent"]], styles: ["\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzeXN0ZW1Nb25pdG9yLmNvbXBvbmVudC5jc3MifQ== */"] });


/***/ }),

/***/ "J/Ln":
/*!*********************************************************************!*\
  !*** ./src/app/pages/systemMonitor/systemMonitor-routing.module.ts ***!
  \*********************************************************************/
/*! exports provided: SystemMonitorRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SystemMonitorRoutingModule", function() { return SystemMonitorRoutingModule; });
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/router */ "tyNb");
/* harmony import */ var _systemMonitor_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./systemMonitor.component */ "36Gm");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "fXoL");




const routes = [
    { path: '', component: _systemMonitor_component__WEBPACK_IMPORTED_MODULE_1__["SystemMonitorComponent"] },
];
class SystemMonitorRoutingModule {
}
SystemMonitorRoutingModule.ɵfac = function SystemMonitorRoutingModule_Factory(t) { return new (t || SystemMonitorRoutingModule)(); };
SystemMonitorRoutingModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdefineNgModule"]({ type: SystemMonitorRoutingModule });
SystemMonitorRoutingModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdefineInjector"]({ imports: [[_angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"].forChild(routes)], _angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵsetNgModuleScope"](SystemMonitorRoutingModule, { imports: [_angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"]], exports: [_angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"]] }); })();


/***/ }),

/***/ "pz7/":
/*!*************************************************************!*\
  !*** ./src/app/pages/systemMonitor/systemMonitor.module.ts ***!
  \*************************************************************/
/*! exports provided: SystemMonitorModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SystemMonitorModule", function() { return SystemMonitorModule; });
/* harmony import */ var _systemMonitor_routing_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./systemMonitor-routing.module */ "J/Ln");
/* harmony import */ var _systemMonitor_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./systemMonitor.component */ "36Gm");
/* harmony import */ var highcharts_angular__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! highcharts-angular */ "kAVD");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ "fXoL");




class SystemMonitorModule {
}
SystemMonitorModule.ɵfac = function SystemMonitorModule_Factory(t) { return new (t || SystemMonitorModule)(); };
SystemMonitorModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdefineNgModule"]({ type: SystemMonitorModule });
SystemMonitorModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵdefineInjector"]({ imports: [[
            _systemMonitor_routing_module__WEBPACK_IMPORTED_MODULE_0__["SystemMonitorRoutingModule"],
            highcharts_angular__WEBPACK_IMPORTED_MODULE_2__["HighchartsChartModule"]
        ]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_3__["ɵɵsetNgModuleScope"](SystemMonitorModule, { declarations: [_systemMonitor_component__WEBPACK_IMPORTED_MODULE_1__["SystemMonitorComponent"]], imports: [_systemMonitor_routing_module__WEBPACK_IMPORTED_MODULE_0__["SystemMonitorRoutingModule"],
        highcharts_angular__WEBPACK_IMPORTED_MODULE_2__["HighchartsChartModule"]], exports: [_systemMonitor_component__WEBPACK_IMPORTED_MODULE_1__["SystemMonitorComponent"]] }); })();


/***/ })

}]);
//# sourceMappingURL=pages-systemMonitor-systemMonitor-module.js.map