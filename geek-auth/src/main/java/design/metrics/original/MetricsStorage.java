package design.metrics.original;

import java.util.List;
import java.util.Map;

/**
 * @program: MetricsStorage
 * @description:
 * @author: wangf-q
 * @date: 2022-12-29 13:53
 **/
public interface MetricsStorage {
    void saveRequestInfo(RequestInfo requestInfo);

    List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMillis);

    Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis);
}
