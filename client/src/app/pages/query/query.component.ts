import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import * as Highcharts from 'highcharts';
import theme from 'highcharts/themes/dark-unica';
theme(Highcharts);

import { QueryBuilder } from './QueryBuilder';
import { Metric } from './Metric';
import { Aggregator } from './Aggregator';
import { GroupBy } from './GroupBy';

@Component({
  selector: 'app-query',
  templateUrl: './query.component.html',
  styleUrls: ['./query.component.css'],
})
export class QueryComponent implements OnInit {
  beginDate: Date = new Date();
  endDate: Date = new Date();

  queryDate: Date[] = [];

  ranges = { Today: [new Date(), new Date()] };

  selectedMetric = '';
  metrics = [];
  nzFilterOption = () => true;

  groupBys = [];
  tagsOfMetric = [];

  selectedAggregator = '';
  aggregators: Array<{ label: string; value: string }> = [];

  tags: Array<{ tagKey: string; tagValue: string }> = [];
  tagValues = [];

  Highcharts: typeof Highcharts = Highcharts;
  updateFlag = false;
  seriesData: Array<any> = [1, 2, 3, 4];
  chartOptions: Highcharts.Options = {
    chart: {
      type: 'spline',
    },
    title: {
      "text": ''
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
    tooltip: {
      headerFormat: '<b>{point.x:%Y-%m-%d %H:%M:%S}</b><br>',
      pointFormat: '{point.y:.2f}',
    },
    series: [
    ]
  };

  queryBuilder: QueryBuilder = new QueryBuilder();

  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
    this.beginDate.setHours(0, 0, 0, 0);
    this.endDate.setHours(23, 59, 59, 999);
    this.queryDate = [this.beginDate, this.endDate];

    this.ranges.Today = [this.beginDate, this.endDate];

    this.aggregators.push({ label: '', value: '' });
    this.aggregators.push({ label: '平均值', value: 'avg' });
    this.aggregators.push({ label: '最大值', value: 'max' });
    this.aggregators.push({ label: '最小值', value: 'min' });
    this.aggregators.push({ label: '总和值', value: 'sum' });

    this.tags.push({ tagKey: '', tagValue: '' });
  }

  onMetricSerach(value: string): void {
    if (value === '') {
      return;
    }
    let url = '/api/metricTag/queryMetric';
    let body: any = {};
    body['metric'] = value;
    this.httpClient.post(url, body, {}).subscribe((data: any) => {
      this.metrics = data;
    });
  }

  queryTagKeyOfMetric() {
    console.log(this.selectedMetric);
    if (this.selectedMetric === '') {
      return;
    }
    let url = '/api/metricTag/queryTagKeyOfMetric';
    let body: any = {};
    body['metric'] = this.selectedMetric;
    this.httpClient.post(url, body, {}).subscribe((data: any) => {
      this.tagsOfMetric = data;
    });
  }

  queryTagValueOfMetric(index: number, value: string) {
    console.log(this.selectedMetric);
    if (this.selectedMetric === '') {
      return;
    }
    let url = '/api/metricTag/queryTagValueOfMetric';
    let body: any = {};
    body['metric'] = this.selectedMetric;
    body['tagKey'] = this.tags[index].tagKey;
    body['tagValue'] = value;
    this.httpClient.post(url, body, {}).subscribe((data: any) => {
      this.tagValues = data;
    });
  }

  addTag(e?: MouseEvent): void {
    if (e) {
      e.preventDefault();
    }
    this.tags.push({ tagKey: '', tagValue: '' });
  }

  deleteTag(index: number, e: MouseEvent): void {
    if (e) {
      e.preventDefault();
    }
    this.tags.splice(index, 1);
  }

  onSearch(): void {
    let metric: Metric = new Metric();
    metric.name = this.selectedMetric;

    this.tags.forEach(function (tag) {
      if (tag.tagKey !== '' && tag.tagValue !== '') {
        if (!metric.tags[tag.tagKey]) {
          metric.tags[tag.tagKey] = [tag.tagValue];
        } else {
          metric.tags[tag.tagKey].push(tag.tagValue);
        }
      }
    });

    if (this.selectedAggregator !== '') {
      let aggregator: Aggregator = new Aggregator();
      aggregator.name = this.selectedAggregator;
      metric.aggregators = [aggregator];
    }

    let groupBy: GroupBy = new GroupBy();
    groupBy.tags = this.groupBys;
    metric.group_by = [groupBy];

    this.queryBuilder.start_absolute = this.queryDate[0].getTime();
    this.queryBuilder.end_absolute = this.queryDate[1].getTime();

    this.queryBuilder.metrics = [metric];

    console.log(this.queryBuilder);

    this.httpClient
      .post('/api/v1/datapoints/query', this.queryBuilder, {
        observe: 'response',
      })
      .subscribe((response) => {
        console.log(response.headers.get('executetime'));
        let series = [];
        let config: any = { ...response.body };
        let results = config['queries'][0].results;
        let sampleSize = config['queries'][0].sample_size;

        for (let i = 0; i < results.length; i++) {
          let serie: any = {};

          let result = results[i];

          serie['name'] = JSON.stringify(result.tags);
          serie['data'] = [];
          let values = result.values;
          values.forEach((element: any) => {
            serie['data'].push([element['timestamp'], element['value']]);
          });
          series.push(serie);
        }

        this.chartOptions = {
          chart: {
            type: 'spline',
          },
          title: {
            text: '查询时间：' + response.headers.get('executetime') + ' ms, 数量: ' + sampleSize,
          },
          time: {
            useUTC: false,
          },
          series: series,
        };

        this.updateFlag = true;
      });
  }
}
