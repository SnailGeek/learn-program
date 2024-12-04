package map;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author wangf-q
 * @since 2024-08-12
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("earthwork_task_layer_catalog")
public class EarthworkTaskLayerCatalog {

    /**
     * 编号
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 名称
     */
    private String name;

    /**
     * 收方任务ID
     */
    private Long taskId;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 全路径
     */
    private String fullPath;

    /**
     * 勾选状态
     */
    private Boolean checked;


    private Integer type;

    /**
     * 序号
     */
    private Integer order;
}
