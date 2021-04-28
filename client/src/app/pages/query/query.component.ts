import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import * as Highcharts from 'highcharts';

@Component({
  selector: 'app-query',
  templateUrl: './query.component.html',
  styleUrls: ['./query.component.css']
})
export class QueryComponent implements OnInit {

  ranges = { Today: [new Date(), new Date()] };

  selectedMetric = '';
  metrics = [];
  nzFilterOption = () => true;

  groupBys = [];
  tagsOfMetric = [];

  selectedAggregator = null;
  aggregators: Array<{ label: string; value: string }> = [];

  tags: Array<{ tagKey: string; tagValue: string }> = [];
  tagValues = [];

  Highcharts: typeof Highcharts = Highcharts;
  updateFlag = false;
  seriesData: Array<any> = [1, 2, 3, 4];
  chartOptions: Highcharts.Options = {
    chart: {
      type: 'spline'
    },
    series: [
      {
        type: 'line',
        data: []
      }
    ]
  };

  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
    let beginDate = new Date();
    beginDate.setHours(0, 0, 0, 0);
    let endDate = new Date();
    endDate.setHours(23, 59, 59, 999);
    this.ranges.Today = [beginDate, endDate];

    this.aggregators.push({ label: "平均值", value: "AVG" });
    this.aggregators.push({ label: "最大值", value: "MAX" });
    this.aggregators.push({ label: "最小值", value: "MIN" });
    this.aggregators.push({ label: "总和值", value: "SUM" });

    this.tags.push({ tagKey: "", tagValue: "" });

  }

  onDateOk(result: Date | Date[] | null): void {
    console.log('onOk', result);
  }

  onMetricSerach(value: string): void {
    if (value === '') {
      return;
    }
    let url = "/api/metricTag/queryMetric";
    let body: any = {};
    body["metric"] = value;
    this.httpClient
      .post(url, body, {})
      .subscribe((data: any) => {
        this.metrics = data;
      });
  }

  queryTagKeyOfMetric() {
    console.log(this.selectedMetric);
    if (this.selectedMetric === '') {
      return;
    }
    let url = "/api/metricTag/queryTagKeyOfMetric";
    let body: any = {};
    body["metric"] = this.selectedMetric;
    this.httpClient
      .post(url, body, {})
      .subscribe((data: any) => {
        this.tagsOfMetric = data;
      });
  }

  queryTagValueOfMetric(index: number,value: string) {
    console.log(this.selectedMetric);
    if (this.selectedMetric === '') {
      return;
    }
    let url = "/api/metricTag/queryTagKeyOfMetric";
    let body: any = {};
    body["metric"] = this.selectedMetric;
    body["tagKey"] = this.tags[index].tagKey;
    this.httpClient
      .post(url, body, {})
      .subscribe((data: any) => {
        this.tags[index].tagValue = data;
      });
  }

  addTag(e?: MouseEvent): void {
    if (e) {
      e.preventDefault();
    }
    this.tags.push({ tagKey: "", tagValue: "" });
  }

  deleteTag(index: number, e: MouseEvent): void {
    if (e) {
      e.preventDefault();
    }
    this.tags.splice(index, 1);
  }

  onSearch(): void {

    this.seriesData = [1, 2, 3, 4];
    this.chartOptions = {
      chart: {
        type: 'spline'
      },
      title: {
        text: 'updated'
      },
      xAxis: {
        type: 'datetime',
        dateTimeLabelFormats: {
          day: '%m-%d'
        },
        labels: {
          overflow: 'justify'
        }
      },
      tooltip: {
        headerFormat: '<b>{series.name}</b><br>',
        pointFormat: '{point.x:%Y-%m-%d %H:%M:%S}: {point.y:.2f}'
      },
      series: [
        {
          type: 'line',
          data: this.seriesData
        }
      ]
    };

    this.updateFlag = true;
  }
}
