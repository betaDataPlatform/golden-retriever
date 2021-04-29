import { NgModule } from '@angular/core';

import { MonitorRoutingModule } from './monitor-routing.module';

import { MonitorComponent } from './monitor.component';
import { HighchartsChartModule } from 'highcharts-angular';

@NgModule({
  imports: [
    MonitorRoutingModule,
    HighchartsChartModule
  ],
  declarations: [MonitorComponent],
  exports: [MonitorComponent]
})
export class MonitorModule { }
