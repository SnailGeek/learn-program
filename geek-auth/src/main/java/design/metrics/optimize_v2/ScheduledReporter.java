package design.metrics.optimize_v2;

import design.metrics.optimize_v1.Aggregator;
import design.metrics.optimize_v1.StatViewer;
import design.metrics.original.MetricsStorage;
import design.metrics.original.RequestInfo;
import design.metrics.original.RequestStat;

import java.util.List;
import java.util.Map;

/**
 * @program: ScheduledReporter
 * @description:
 * @author: wangf-q
 * @date: 2022-12-29 14:55
 **/
public class ScheduledReporter {
    protected MetricsStorage metricsStorage;
    protected Aggregator aggregator;
    protected StatViewer viewer;

    public ScheduledReporter(MetricsStorage metricsStorage, Aggregator aggregator, StatViewer viewer) {
        this.metricsStorage = metricsStorage;
        this.aggregator = aggregator;
        this.viewer = viewer;
    }

    protected void doStatAndReport(long durationInMillis, long endTimeInMillis, long startTimeInMillis) {
        Map<String, List<RequestInfo>> requestInfos =
                metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
        Map<String, RequestStat> stats = aggregator.aggregate(requestInfos, durationInMillis);
        viewer.output(stats, startTimeInMillis, endTimeInMillis);
    }
}
