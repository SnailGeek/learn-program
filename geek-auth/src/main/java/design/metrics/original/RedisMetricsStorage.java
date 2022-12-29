package design.metrics.original;

import java.util.List;
import java.util.Map;

/**
 * @program: RedisMetricsStorage
 * @description:
 * @author: wangf-q
 * @date: 2022-12-29 14:00
 **/
public class RedisMetricsStorage implements MetricsStorage {

    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {

    }

    @Override
    public List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMillis) {
        return null;
    }

    @Override
    public Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis) {
        return null;
    }
}
