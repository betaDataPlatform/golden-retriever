import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timer } from 'rxjs';

import { Metric } from './Metric';

import * as Highcharts from 'highcharts';

@Component({
  selector: 'app-monitor',
  templateUrl: './monitor.component.html',
  styleUrls: ['./monitor.component.css']
})
export class MonitorComponent implements OnInit {

  Highcharts: typeof Highcharts = Highcharts;

  metricCount_chart: any;
  metricCount_chartCallback: any;
  metricCount_chartOptions: Highcharts.Options = {
    chart: {
      type: 'spline',
    },
    title: {
      "text": '数据处理监控'
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
        text: "次数"
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
      name: "成功次数",
      data: []
    },
    {
      type: 'spline',
      name: "失败次数",
      data: []
    }]
  };

  constructor(private httpClient: HttpClient) {
    const self = this;
    this.metricCount_chartCallback = (chart : Highcharts.Chart) => {
      self.metricCount_chart = chart;
    }
   }

  ngOnInit() {

    const source = timer(1000, 30 * 1000);
    const subscribe = source.subscribe(val => this.getMessage());
  }

  getMessage() {
    let url = '/monitor';
    this.httpClient.get(url).subscribe((data: any) => {
      let metrics: Metric[] = data;
      metrics.forEach(metric => {
        if (metric.name === 'dp.metricValue.count.save') {
          this.metricCount_chart.series[0].addPoint(metric.datapoints[0], true, false);
        } else if (metric.name === 'dp.metricValue.count.drop') {
          this.metricCount_chart.series[1].addPoint(metric.datapoints[0], true, false);
        }
      });
    });
  }

}
