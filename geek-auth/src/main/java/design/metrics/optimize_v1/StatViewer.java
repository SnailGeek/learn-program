package design.metrics.optimize_v1;

import design.metrics.original.RequestStat;

import java.util.Map;

public interface StatViewer {
  void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMills);
}

