import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timer } from 'rxjs';

import { Metric } from './Metric';

import * as Highcharts from 'highcharts';

@Component({
  selector: 'app-systemMonitor',
  templateUrl: './systemMonitor.component.html',
  styleUrls: ['./systemMonitor.component.css']
})
export class SystemMonitorComponent implements OnInit {

  Highcharts: typeof Highcharts = Highcharts;

  mem_chart: any;
  mem_chartCallback: any;
  mem_chartOptions: Highcharts.Options = {
    chart: {
      type: 'spline',
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

  cpu_chart: any;
  cpu_chartCallback: any;
  cpu_chartOptions: Highcharts.Options = {
    chart: {
      type: 'spline',
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

  constructor(private httpClient: HttpClient) {
    const self = this;
    this.mem_chartCallback = (chart: Highcharts.Chart) => {
      self.mem_chart = chart;
    }

    this.cpu_chartCallback = (chart: Highcharts.Chart) => {
      self.cpu_chart = chart;
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
        if (metric.name === 'jvm.memory.used') {
          if (metric.tags['id'] === 'PS Eden Space') {
            this.mem_chart.series[0].addPoint(metric.datapoints[0], true, false);
          } else if (metric.tags['id'] === 'PS Survivor Space') {
            this.mem_chart.series[1].addPoint(metric.datapoints[0], true, false);
          } else if (metric.tags['id'] === 'PS Old Gen') {
            this.mem_chart.series[2].addPoint(metric.datapoints[0], true, false);
          }
        } else if (metric.name === 'system.cpu.usage') {
          this.cpu_chart.series[0].addPoint(metric.datapoints[0], true, false);
        } else if (metric.name === 'process.cpu.usage') {
          this.cpu_chart.series[1].addPoint(metric.datapoints[0], true, false);
        }
      });
    });
  }

}
