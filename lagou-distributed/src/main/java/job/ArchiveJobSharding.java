package job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import utils.JdbcUtil;

import java.util.List;
import java.util.Map;

public class ArchiveJobSharding implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        int shardingItem = shardingContext.getShardingItem();
        System.out.println("========》分片ID: " + shardingItem);
        String sql = "select * from resume where state = '未归档' AND education = '" +
                shardingContext.getShardingParameter() + "'" + "limit 1";
        List<Map<String, Object>> list = JdbcUtil.executeQuery(sql);
        if (list == null || list.size() == 0) {
            return;
        }

        Map<String, Object> stringObjectMap = list.get(0);
        long id = (long) stringObjectMap.get("id");
        String name = (String) stringObjectMap.get("name");
        String education = (String) stringObjectMap.get("education");
        System.out.println("========》id: " + id + " name: " + name + " education: " + education);

        String updateSql = "update resume set state = '已归档' where id = ?";
        JdbcUtil.executeUpdate(updateSql, id);

        String insertSql = "insert into resume_bak select * from resume where id = ?";
        JdbcUtil.executeUpdate(insertSql, id);
    }
}
