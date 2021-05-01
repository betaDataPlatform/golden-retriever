import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { timer } from 'rxjs';

import { Metric } from './Metric';

import * as Highcharts from 'highcharts';
import theme from 'highcharts/themes/dark-unica';
theme(Highcharts);

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
    time: {
      useUTC: false,
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
      name: "数据保存次数",
      data: []
    },
    {
      type: 'spline',
      name: "数据丢弃次数",
      data: []
    }]
  };

  queryCount_chart: any;
  queryCount_chartCallback: any;
  queryCount_chartOptions: Highcharts.Options = {
    chart: {
      type: 'spline',
    },
    time: {
      useUTC: false,
    },
    title: {
      "text": '查询性能监控'
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
      formatter: function () {
        let point: any = this.point;
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
      name: "查询次数",
      data: []
    }]
  };

  constructor(private httpClient: HttpClient) {
    const self = this;
    this.metricCount_chartCallback = (chart: Highcharts.Chart) => {
      self.metricCount_chart = chart;
    }
    this.queryCount_chartCallback = (chart: Highcharts.Chart) => {
      self.queryCount_chart = chart;
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
      // 获取数据处理指标
      metrics.forEach(metric => {
        if (metric.name === 'dp.metricValue.count.save') {
          this.metricCount_chart.series[0].addPoint(metric.datapoints[0], true, false);
        } else if (metric.name === 'dp.metricValue.count.drop') {
          this.metricCount_chart.series[1].addPoint(metric.datapoints[0], true, false);
        }
      });
      // 获取查询性能指标
      let queryMetric: any = {};
      metrics.forEach(metric => {
        if (metric.name === 'dp.query.timer.count') {
          queryMetric['x'] = metric.datapoints[0][0];
          queryMetric['y'] = metric.datapoints[0][1];
        } else if (metric.name === 'dp.query.timer.max') {
          queryMetric['max'] = metric.datapoints[0][1];
        } else if (metric.name === 'dp.query.timer.avg') {
          queryMetric['avg'] = metric.datapoints[0][1];
        } else if (metric.name === 'dp.query.timer.percentile') {
          if (metric.tags['phi'] === '0.99') {
            queryMetric['th99'] = metric.datapoints[0][1];
          }else if (metric.tags['phi'] === '0.95') {
            queryMetric['th95'] = metric.datapoints[0][1];
          }
        }
      });
      this.queryCount_chart.series[0].addPoint(queryMetric, true, false);

    })

  }

}
