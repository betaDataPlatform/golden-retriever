import { Aggregator } from './Aggregator';
import { GroupBy } from './GroupBy';

export class Metric {

    name: string = "";

    tags: any = {};

    aggregators: Aggregator[] = [];

    group_by: GroupBy[] = [];
}