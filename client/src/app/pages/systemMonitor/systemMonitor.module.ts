import { NgModule } from '@angular/core';

import { SystemMonitorRoutingModule } from './systemMonitor-routing.module';

import { SystemMonitorComponent } from './systemMonitor.component';
import { HighchartsChartModule } from 'highcharts-angular';

@NgModule({
  imports: [
    SystemMonitorRoutingModule,
    HighchartsChartModule
  ],
  declarations: [SystemMonitorComponent],
  exports: [SystemMonitorComponent]
})
export class SystemMonitorModule { }
