import { Metric } from './Metric';

export class QueryBuilder {

    start_absolute: number | undefined;

    end_absolute: number | undefined;

    cache_time: number = 0;

    metrics: Metric[] =[];
}