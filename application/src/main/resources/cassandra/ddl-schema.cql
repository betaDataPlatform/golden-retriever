CREATE TABLE IF NOT EXISTS dc.data_point(
   metric text,
   tag_json text,
   day date,
   offset time,
   value double,
   PRIMARY KEY((metric, tag_json, day), offset)
)
WITH gc_grace_seconds = 0
AND default_time_to_live = 31536000
AND memtable_flush_period_in_ms = 600
AND compaction = {'compaction_window_size': '14', 'compaction_window_unit': 'DAYS', 'class': 'org.apache.cassandra.db.compaction.TimeWindowCompactionStrategy'};

CREATE TABLE IF NOT EXISTS dc.metric_tag(
   metric text,
   tag_name text,
   tag_value text,
   tag text,
   PRIMARY KEY ((metric, tag_name), tag_value, tag)
)
WITH gc_grace_seconds = 0
AND memtable_flush_period_in_ms = 600;
