import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { QueryRoutingModule } from './query-routing.module';

import { QueryComponent } from './query.component';

import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzIconModule } from 'ng-zorro-antd/icon';

import { HighchartsChartModule } from 'highcharts-angular';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    QueryRoutingModule,
    NzGridModule,
    NzDatePickerModule,
    NzSelectModule,
    NzButtonModule,
    NzDividerModule,
    NzFormModule,
    NzIconModule,
    HighchartsChartModule
  ],
  declarations: [QueryComponent],
  exports: [QueryComponent]
})
export class QueryModule { }
