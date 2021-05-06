(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! /Users/chenhao/Documents/GitHub/golden-retriever/client/src/main.ts */"zUnb");


/***/ }),

/***/ "AytR":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
const environment = {
    production: false
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "Hhmg":
/*!******************************************!*\
  !*** ./src/app/icons-provider.module.ts ***!
  \******************************************/
/*! exports provided: IconsProviderModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "IconsProviderModule", function() { return IconsProviderModule; });
/* harmony import */ var ng_zorro_antd_icon__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ng-zorro-antd/icon */ "FwiY");
/* harmony import */ var _ant_design_icons_angular_icons__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @ant-design/icons-angular/icons */ "kVq8");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "fXoL");



const icons = [_ant_design_icons_angular_icons__WEBPACK_IMPORTED_MODULE_1__["MenuFoldOutline"], _ant_design_icons_angular_icons__WEBPACK_IMPORTED_MODULE_1__["MenuUnfoldOutline"], _ant_design_icons_angular_icons__WEBPACK_IMPORTED_MODULE_1__["DashboardOutline"], _ant_design_icons_angular_icons__WEBPACK_IMPORTED_MODULE_1__["FormOutline"]];
class IconsProviderModule {
}
IconsProviderModule.ɵfac = function IconsProviderModule_Factory(t) { return new (t || IconsProviderModule)(); };
IconsProviderModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdefineNgModule"]({ type: IconsProviderModule });
IconsProviderModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵdefineInjector"]({ providers: [
        { provide: ng_zorro_antd_icon__WEBPACK_IMPORTED_MODULE_0__["NZ_ICONS"], useValue: icons }
    ], imports: [[ng_zorro_antd_icon__WEBPACK_IMPORTED_MODULE_0__["NzIconModule"]], ng_zorro_antd_icon__WEBPACK_IMPORTED_MODULE_0__["NzIconModule"]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_2__["ɵɵsetNgModuleScope"](IconsProviderModule, { imports: [ng_zorro_antd_icon__WEBPACK_IMPORTED_MODULE_0__["NzIconModule"]], exports: [ng_zorro_antd_icon__WEBPACK_IMPORTED_MODULE_0__["NzIconModule"]] }); })();


/***/ }),

/***/ "Sy1n":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "fXoL");
/* harmony import */ var ng_zorro_antd_layout__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ng-zorro-antd/layout */ "yW9e");
/* harmony import */ var ng_zorro_antd_menu__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ng-zorro-antd/menu */ "Q8cG");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "tyNb");
/* harmony import */ var ng_zorro_antd_icon__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ng-zorro-antd/icon */ "FwiY");





class AppComponent {
    constructor() {
        this.isCollapsed = false;
    }
}
AppComponent.ɵfac = function AppComponent_Factory(t) { return new (t || AppComponent)(); };
AppComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({ type: AppComponent, selectors: [["app-root"]], decls: 27, vars: 4, consts: [[1, "app-layout"], ["nzCollapsible", "", "nzWidth", "256px", "nzBreakpoint", "md", 1, "menu-sidebar", 3, "nzCollapsed", "nzTrigger", "nzCollapsedChange"], ["nz-menu", "", "nzTheme", "dark", "nzMode", "inline", 3, "nzInlineCollapsed"], ["nz-submenu", "", "nzOpen", "", "nzTitle", "golden-retriver", "nzIcon", "dashboard"], ["nz-menu-item", "", "nzMatchRouter", ""], ["routerLink", "/query"], ["routerLink", "/monitor"], ["routerLink", "/systemMonitor"], ["nz-submenu", "", "nzOpen", "", "nzTitle", "Form", "nzIcon", "form"], [1, "app-header"], [1, "header-trigger", 3, "click"], ["nz-icon", "", 1, "trigger", 3, "nzType"], [1, "inner-content"]], template: function AppComponent_Template(rf, ctx) { if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "nz-layout", 0);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](1, "nz-sider", 1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("nzCollapsedChange", function AppComponent_Template_nz_sider_nzCollapsedChange_1_listener($event) { return ctx.isCollapsed = $event; });
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](2, "ul", 2);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](3, "li", 3);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](4, "ul");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](5, "li", 4);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](6, "a", 5);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](7, "\u65F6\u5E8F\u6570\u636E\u67E5\u8BE2");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](8, "li", 4);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](9, "a", 6);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](10, "\u5B9E\u65F6\u6027\u80FD\u76D1\u63A7");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](11, "li", 4);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](12, "a", 7);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](13, "\u7CFB\u7EDF\u6027\u80FD\u76D1\u63A7");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](14, "li", 8);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](15, "ul");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](16, "li", 4);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](17, "a");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](18, "Basic Form");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](19, "nz-layout");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](20, "nz-header");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](21, "div", 9);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](22, "span", 10);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("click", function AppComponent_Template_span_click_22_listener() { return ctx.isCollapsed = !ctx.isCollapsed; });
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](23, "i", 11);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](24, "nz-content");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](25, "div", 12);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](26, "router-outlet");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
    } if (rf & 2) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("nzCollapsed", ctx.isCollapsed)("nzTrigger", null);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("nzInlineCollapsed", ctx.isCollapsed);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](21);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("nzType", ctx.isCollapsed ? "menu-unfold" : "menu-fold");
    } }, directives: [ng_zorro_antd_layout__WEBPACK_IMPORTED_MODULE_1__["NzLayoutComponent"], ng_zorro_antd_layout__WEBPACK_IMPORTED_MODULE_1__["NzSiderComponent"], ng_zorro_antd_menu__WEBPACK_IMPORTED_MODULE_2__["NzMenuDirective"], ng_zorro_antd_menu__WEBPACK_IMPORTED_MODULE_2__["NzSubMenuComponent"], ng_zorro_antd_menu__WEBPACK_IMPORTED_MODULE_2__["NzMenuItemDirective"], _angular_router__WEBPACK_IMPORTED_MODULE_3__["RouterLinkWithHref"], ng_zorro_antd_layout__WEBPACK_IMPORTED_MODULE_1__["NzHeaderComponent"], ng_zorro_antd_icon__WEBPACK_IMPORTED_MODULE_4__["NzIconDirective"], ng_zorro_antd_layout__WEBPACK_IMPORTED_MODULE_1__["NzContentComponent"], _angular_router__WEBPACK_IMPORTED_MODULE_3__["RouterOutlet"]], styles: ["[_nghost-%COMP%] {\n  display: flex;\n  text-rendering: optimizeLegibility;\n  -webkit-font-smoothing: antialiased;\n  -moz-osx-font-smoothing: grayscale;\n}\n\n.app-layout[_ngcontent-%COMP%] {\n  height: 100vh;\n}\n\n.menu-sidebar[_ngcontent-%COMP%] {\n  position: relative;\n  z-index: 10;\n  min-height: 100vh;\n  box-shadow: 2px 0 6px rgba(0,21,41,.35);\n}\n\n.header-trigger[_ngcontent-%COMP%] {\n  height: 64px;\n  padding: 20px 24px;\n  font-size: 20px;\n  cursor: pointer;\n  transition: all .3s,padding 0s;\n}\n\n.trigger[_ngcontent-%COMP%]:hover {\n  color: #1890ff;\n}\n\n.sidebar-logo[_ngcontent-%COMP%] {\n  position: relative;\n  height: 64px;\n  padding-left: 24px;\n  overflow: hidden;\n  line-height: 64px;\n  background: #001529;\n  transition: all .3s;\n}\n\n.sidebar-logo[_ngcontent-%COMP%]   img[_ngcontent-%COMP%] {\n  display: inline-block;\n  height: 32px;\n  width: 32px;\n  vertical-align: middle;\n}\n\n.sidebar-logo[_ngcontent-%COMP%]   h1[_ngcontent-%COMP%] {\n  display: inline-block;\n  margin: 0 0 0 20px;\n  color: #fff;\n  font-weight: 600;\n  font-size: 14px;\n  font-family: Avenir,Helvetica Neue,Arial,Helvetica,sans-serif;\n  vertical-align: middle;\n}\n\nnz-header[_ngcontent-%COMP%] {\n  padding: 0;\n  width: 100%;\n  z-index: 2;\n}\n\n.app-header[_ngcontent-%COMP%] {\n  position: relative;\n  height: 64px;\n  padding: 0;\n  background: #fff;\n  box-shadow: 0 1px 4px rgba(0,21,41,.08);\n}\n\nnz-content[_ngcontent-%COMP%] {\n  margin: 24px;\n}\n\n.inner-content[_ngcontent-%COMP%] {\n  padding: 24px;\n  background: #fff;\n  height: 100%;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbImFwcC5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0VBQ0UsYUFBYTtFQUNiLGtDQUFrQztFQUNsQyxtQ0FBbUM7RUFDbkMsa0NBQWtDO0FBQ3BDOztBQUVBO0VBQ0UsYUFBYTtBQUNmOztBQUVBO0VBQ0Usa0JBQWtCO0VBQ2xCLFdBQVc7RUFDWCxpQkFBaUI7RUFDakIsdUNBQXVDO0FBQ3pDOztBQUVBO0VBQ0UsWUFBWTtFQUNaLGtCQUFrQjtFQUNsQixlQUFlO0VBQ2YsZUFBZTtFQUNmLDhCQUE4QjtBQUNoQzs7QUFFQTtFQUNFLGNBQWM7QUFDaEI7O0FBRUE7RUFDRSxrQkFBa0I7RUFDbEIsWUFBWTtFQUNaLGtCQUFrQjtFQUNsQixnQkFBZ0I7RUFDaEIsaUJBQWlCO0VBQ2pCLG1CQUFtQjtFQUNuQixtQkFBbUI7QUFDckI7O0FBRUE7RUFDRSxxQkFBcUI7RUFDckIsWUFBWTtFQUNaLFdBQVc7RUFDWCxzQkFBc0I7QUFDeEI7O0FBRUE7RUFDRSxxQkFBcUI7RUFDckIsa0JBQWtCO0VBQ2xCLFdBQVc7RUFDWCxnQkFBZ0I7RUFDaEIsZUFBZTtFQUNmLDZEQUE2RDtFQUM3RCxzQkFBc0I7QUFDeEI7O0FBRUE7RUFDRSxVQUFVO0VBQ1YsV0FBVztFQUNYLFVBQVU7QUFDWjs7QUFFQTtFQUNFLGtCQUFrQjtFQUNsQixZQUFZO0VBQ1osVUFBVTtFQUNWLGdCQUFnQjtFQUNoQix1Q0FBdUM7QUFDekM7O0FBRUE7RUFDRSxZQUFZO0FBQ2Q7O0FBRUE7RUFDRSxhQUFhO0VBQ2IsZ0JBQWdCO0VBQ2hCLFlBQVk7QUFDZCIsImZpbGUiOiJhcHAuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIjpob3N0IHtcbiAgZGlzcGxheTogZmxleDtcbiAgdGV4dC1yZW5kZXJpbmc6IG9wdGltaXplTGVnaWJpbGl0eTtcbiAgLXdlYmtpdC1mb250LXNtb290aGluZzogYW50aWFsaWFzZWQ7XG4gIC1tb3otb3N4LWZvbnQtc21vb3RoaW5nOiBncmF5c2NhbGU7XG59XG5cbi5hcHAtbGF5b3V0IHtcbiAgaGVpZ2h0OiAxMDB2aDtcbn1cblxuLm1lbnUtc2lkZWJhciB7XG4gIHBvc2l0aW9uOiByZWxhdGl2ZTtcbiAgei1pbmRleDogMTA7XG4gIG1pbi1oZWlnaHQ6IDEwMHZoO1xuICBib3gtc2hhZG93OiAycHggMCA2cHggcmdiYSgwLDIxLDQxLC4zNSk7XG59XG5cbi5oZWFkZXItdHJpZ2dlciB7XG4gIGhlaWdodDogNjRweDtcbiAgcGFkZGluZzogMjBweCAyNHB4O1xuICBmb250LXNpemU6IDIwcHg7XG4gIGN1cnNvcjogcG9pbnRlcjtcbiAgdHJhbnNpdGlvbjogYWxsIC4zcyxwYWRkaW5nIDBzO1xufVxuXG4udHJpZ2dlcjpob3ZlciB7XG4gIGNvbG9yOiAjMTg5MGZmO1xufVxuXG4uc2lkZWJhci1sb2dvIHtcbiAgcG9zaXRpb246IHJlbGF0aXZlO1xuICBoZWlnaHQ6IDY0cHg7XG4gIHBhZGRpbmctbGVmdDogMjRweDtcbiAgb3ZlcmZsb3c6IGhpZGRlbjtcbiAgbGluZS1oZWlnaHQ6IDY0cHg7XG4gIGJhY2tncm91bmQ6ICMwMDE1Mjk7XG4gIHRyYW5zaXRpb246IGFsbCAuM3M7XG59XG5cbi5zaWRlYmFyLWxvZ28gaW1nIHtcbiAgZGlzcGxheTogaW5saW5lLWJsb2NrO1xuICBoZWlnaHQ6IDMycHg7XG4gIHdpZHRoOiAzMnB4O1xuICB2ZXJ0aWNhbC1hbGlnbjogbWlkZGxlO1xufVxuXG4uc2lkZWJhci1sb2dvIGgxIHtcbiAgZGlzcGxheTogaW5saW5lLWJsb2NrO1xuICBtYXJnaW46IDAgMCAwIDIwcHg7XG4gIGNvbG9yOiAjZmZmO1xuICBmb250LXdlaWdodDogNjAwO1xuICBmb250LXNpemU6IDE0cHg7XG4gIGZvbnQtZmFtaWx5OiBBdmVuaXIsSGVsdmV0aWNhIE5ldWUsQXJpYWwsSGVsdmV0aWNhLHNhbnMtc2VyaWY7XG4gIHZlcnRpY2FsLWFsaWduOiBtaWRkbGU7XG59XG5cbm56LWhlYWRlciB7XG4gIHBhZGRpbmc6IDA7XG4gIHdpZHRoOiAxMDAlO1xuICB6LWluZGV4OiAyO1xufVxuXG4uYXBwLWhlYWRlciB7XG4gIHBvc2l0aW9uOiByZWxhdGl2ZTtcbiAgaGVpZ2h0OiA2NHB4O1xuICBwYWRkaW5nOiAwO1xuICBiYWNrZ3JvdW5kOiAjZmZmO1xuICBib3gtc2hhZG93OiAwIDFweCA0cHggcmdiYSgwLDIxLDQxLC4wOCk7XG59XG5cbm56LWNvbnRlbnQge1xuICBtYXJnaW46IDI0cHg7XG59XG5cbi5pbm5lci1jb250ZW50IHtcbiAgcGFkZGluZzogMjRweDtcbiAgYmFja2dyb3VuZDogI2ZmZjtcbiAgaGVpZ2h0OiAxMDAlO1xufVxuIl19 */"] });


/***/ }),

/***/ "ZAI4":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/platform-browser */ "jhN1");
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./app-routing.module */ "vY5A");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app.component */ "Sy1n");
/* harmony import */ var ng_zorro_antd_i18n__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ng-zorro-antd/i18n */ "Rm4T");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common */ "ofXK");
/* harmony import */ var _angular_common_locales_zh__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/common/locales/zh */ "Kd/A");
/* harmony import */ var _angular_common_locales_zh__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(_angular_common_locales_zh__WEBPACK_IMPORTED_MODULE_5__);
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ "3Pt+");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/common/http */ "tk/3");
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/platform-browser/animations */ "R1ws");
/* harmony import */ var _icons_provider_module__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./icons-provider.module */ "Hhmg");
/* harmony import */ var ng_zorro_antd_layout__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ng-zorro-antd/layout */ "yW9e");
/* harmony import */ var ng_zorro_antd_menu__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ng-zorro-antd/menu */ "Q8cG");
/* harmony import */ var ng_zorro_antd_grid__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ng-zorro-antd/grid */ "B+r4");
/* harmony import */ var ng_zorro_antd_icon__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ng-zorro-antd/icon */ "FwiY");
/* harmony import */ var _ant_design_icons_angular_icons__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @ant-design/icons-angular/icons */ "kVq8");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/core */ "fXoL");


















Object(_angular_common__WEBPACK_IMPORTED_MODULE_4__["registerLocaleData"])(_angular_common_locales_zh__WEBPACK_IMPORTED_MODULE_5___default.a);
const antDesignIcons = _ant_design_icons_angular_icons__WEBPACK_IMPORTED_MODULE_14__;
const icons = Object.keys(antDesignIcons).map(key => antDesignIcons[key]);
class AppModule {
}
AppModule.ɵfac = function AppModule_Factory(t) { return new (t || AppModule)(); };
AppModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_15__["ɵɵdefineNgModule"]({ type: AppModule, bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_2__["AppComponent"]] });
AppModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_15__["ɵɵdefineInjector"]({ providers: [{ provide: ng_zorro_antd_i18n__WEBPACK_IMPORTED_MODULE_3__["NZ_I18N"], useValue: ng_zorro_antd_i18n__WEBPACK_IMPORTED_MODULE_3__["zh_CN"] }], imports: [[
            _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
            _app_routing_module__WEBPACK_IMPORTED_MODULE_1__["AppRoutingModule"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_6__["FormsModule"],
            _angular_common_http__WEBPACK_IMPORTED_MODULE_7__["HttpClientModule"],
            _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_8__["BrowserAnimationsModule"],
            _icons_provider_module__WEBPACK_IMPORTED_MODULE_9__["IconsProviderModule"],
            ng_zorro_antd_layout__WEBPACK_IMPORTED_MODULE_10__["NzLayoutModule"],
            ng_zorro_antd_menu__WEBPACK_IMPORTED_MODULE_11__["NzMenuModule"],
            ng_zorro_antd_grid__WEBPACK_IMPORTED_MODULE_12__["NzGridModule"],
            ng_zorro_antd_icon__WEBPACK_IMPORTED_MODULE_13__["NzIconModule"].forRoot(icons)
        ]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_15__["ɵɵsetNgModuleScope"](AppModule, { declarations: [_app_component__WEBPACK_IMPORTED_MODULE_2__["AppComponent"]], imports: [_angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
        _app_routing_module__WEBPACK_IMPORTED_MODULE_1__["AppRoutingModule"],
        _angular_forms__WEBPACK_IMPORTED_MODULE_6__["FormsModule"],
        _angular_common_http__WEBPACK_IMPORTED_MODULE_7__["HttpClientModule"],
        _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_8__["BrowserAnimationsModule"],
        _icons_provider_module__WEBPACK_IMPORTED_MODULE_9__["IconsProviderModule"],
        ng_zorro_antd_layout__WEBPACK_IMPORTED_MODULE_10__["NzLayoutModule"],
        ng_zorro_antd_menu__WEBPACK_IMPORTED_MODULE_11__["NzMenuModule"],
        ng_zorro_antd_grid__WEBPACK_IMPORTED_MODULE_12__["NzGridModule"], ng_zorro_antd_icon__WEBPACK_IMPORTED_MODULE_13__["NzIconModule"]] }); })();


/***/ }),

/***/ "vY5A":
/*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
/*! exports provided: AppRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppRoutingModule", function() { return AppRoutingModule; });
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/router */ "tyNb");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "fXoL");



const routes = [
    { path: '', pathMatch: 'full', redirectTo: '/query' },
    { path: 'query', loadChildren: () => Promise.all(/*! import() | pages-query-query-module */[__webpack_require__.e("default~pages-monitor-monitor-module~pages-query-query-module~pages-systemMonitor-systemMonitor-module"), __webpack_require__.e("pages-query-query-module")]).then(__webpack_require__.bind(null, /*! ./pages/query/query.module */ "k0Xu")).then(m => m.QueryModule) },
    { path: 'monitor', loadChildren: () => Promise.all(/*! import() | pages-monitor-monitor-module */[__webpack_require__.e("default~pages-monitor-monitor-module~pages-query-query-module~pages-systemMonitor-systemMonitor-module"), __webpack_require__.e("pages-monitor-monitor-module")]).then(__webpack_require__.bind(null, /*! ./pages/monitor/monitor.module */ "pqN0")).then(m => m.MonitorModule) },
    { path: 'systemMonitor', loadChildren: () => Promise.all(/*! import() | pages-systemMonitor-systemMonitor-module */[__webpack_require__.e("default~pages-monitor-monitor-module~pages-query-query-module~pages-systemMonitor-systemMonitor-module"), __webpack_require__.e("pages-systemMonitor-systemMonitor-module")]).then(__webpack_require__.bind(null, /*! ./pages/systemMonitor/systemMonitor.module */ "pz7/")).then(m => m.SystemMonitorModule) },
    { path: 'welcome', loadChildren: () => __webpack_require__.e(/*! import() | pages-welcome-welcome-module */ "pages-welcome-welcome-module").then(__webpack_require__.bind(null, /*! ./pages/welcome/welcome.module */ "TtBp")).then(m => m.WelcomeModule) }
];
class AppRoutingModule {
}
AppRoutingModule.ɵfac = function AppRoutingModule_Factory(t) { return new (t || AppRoutingModule)(); };
AppRoutingModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineNgModule"]({ type: AppRoutingModule });
AppRoutingModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineInjector"]({ imports: [[_angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"].forRoot(routes)], _angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵsetNgModuleScope"](AppRoutingModule, { imports: [_angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"]], exports: [_angular_router__WEBPACK_IMPORTED_MODULE_0__["RouterModule"]] }); })();


/***/ }),

/***/ "zUnb":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/platform-browser */ "jhN1");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "fXoL");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "ZAI4");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "AytR");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["enableProdMode"])();
}
_angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["platformBrowser"]().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(err => console.error(err));


/***/ }),

/***/ "zn8P":
/*!******************************************************!*\
  !*** ./$$_lazy_route_resource lazy namespace object ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "zn8P";

/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map