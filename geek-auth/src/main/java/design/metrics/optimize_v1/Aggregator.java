package design.metrics.optimize_v1;

import design.metrics.original.RequestInfo;
import design.metrics.original.RequestStat;

import java.util.*;

public class Aggregator {
    public Map<String, RequestStat> aggregate(Map<String, List<RequestInfo>> requestInfos, long durationInMillis) {
        Map<String, RequestStat> requestStats = new HashMap<>();
        for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
            String apiName = entry.getKey();
            List<RequestInfo> requestInfosPerApi = entry.getValue();
            RequestStat requestStat = doAggregate(requestInfosPerApi, durationInMillis);
            requestStats.put(apiName, requestStat);
        }
        return requestStats;
    }

    private RequestStat doAggregate(List<RequestInfo> requestInfos, long durationInMillis) {
        List<Double> respTimes = new ArrayList<>();
        for (RequestInfo requestInfo : requestInfos) {
            double respTime = requestInfo.getResponseTime();
            respTimes.add(respTime);
        }

        RequestStat requestStat = new RequestStat();
        requestStat.setMaxResponseTime(max(respTimes));
        requestStat.setMinResponseTime(min(respTimes));
        requestStat.setAvgResponseTime(avg(respTimes));
        requestStat.setP999ResponseTime(percentile999(respTimes));
        requestStat.setP99ResponseTime(percentile99(respTimes));
        requestStat.setCount(respTimes.size());
        requestStat.setTps((long) tps(respTimes.size(), durationInMillis / 1000));
        return requestStat;
    }

    // 以下的函数的代码实现均省略...
    private double max(List<Double> dataset) {
        return dataset.stream().max(Comparator.comparing(Double::doubleValue)).get();
    }

    private double min(List<Double> dataset) {
        return dataset.stream().min(Comparator.comparing(Double::doubleValue)).get();
    }

    private double avg(List<Double> dataset) {
        return dataset.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
    }

    private double tps(int count, double duration) {
        return (long) (count / duration * 1000);
    }

    private double percentile999(List<Double> dataset) {
        int idx999 = (int) (dataset.size() * 0.999);
        return dataset.get(idx999);
    }

    private double percentile99(List<Double> dataset) {
        int idx99 = (int) (dataset.size() * 0.99);
        return dataset.get(idx99);
    }

//    private double percentile(List<Double> dataset, double ratio) {
//    }
}