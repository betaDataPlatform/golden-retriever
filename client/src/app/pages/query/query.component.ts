import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { endOfMonth } from 'date-fns';
import * as Highcharts from 'highcharts';
import { be } from 'date-fns/locale';

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
  tagsOfMetric = ['a10', 'c12', 'tag'];

  selectedAggregator = null;
  aggregators: Array<{ label: string; value: string }> = [];

  tags: Array<{ tagKey: string; tagValue: string }> = [];
  tagValues = [];

  Highcharts: typeof Highcharts = Highcharts;
  chartOptions: Highcharts.Options = {
    series: [{
      data: [1, 2, 3],
      type: 'line'
    }]
  };
  
  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
    let beginDate = new Date();
    beginDate.setHours(0,0,0,0);
    let endDate = new Date();
    endDate.setHours(23,59,59,999);
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
    let url = "/api/v1/metric";
    let body: any = {};
    body["metric"] = value;
    this.httpClient
      .post(url, body, {})
      .subscribe((data: any) => {
        this.metrics = data;
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

  onTagValueSerach(value: string): void {
    let url = "/api/v1/metric";
    let body: any = {};
    body["metric"] = value;
    this.httpClient
      .post(url, body, {})
      .subscribe((data: any) => {
        //this.tagValues = data;
      });
  }

  onSearch(): void {

  }
}
