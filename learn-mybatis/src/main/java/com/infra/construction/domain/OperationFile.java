package com.infra.construction.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 数据准备-批量文件导入
 * </p>
 *
 * @author wangf-q
 * @since 2024-08-07
 */
@Getter
@Setter
@TableName("operation_file")
public class OperationFile {

    private BigInteger id;

    private BigInteger tenantId;

    /**
     * 租户组织ID
     */
    private BigInteger tenantOrgId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 项目组织ID
     */
    private BigInteger orgId;

    /**
     * 项目ID
     */
    private BigInteger projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 上级单位id
     */
    private BigInteger deptId;

    /**
     * 上级单位名称
     */
    private String deptName;

    /**
     * 模型状态
     */
    private String modelStatus;

    /**
     * 图纸状态
     */
    private String drawingStatus;

    /**
     * 进度计划状态
     */
    private String progressStatus;

    /**
     * 清单状态
     */
    private String gbqStatus;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 导入/新建状态
     */
    private String status;

    /**
     * 序号
     */
    private Integer seq;

    /**
     * 版本
     */
    private BigInteger version;

    /**
     * 系统-乐观锁版本号
     */
    private BigInteger sysRevision;

    /**
     * 系统-记录创建时间
     */
    private LocalDateTime sysCreateTime;

    /**
     * 系统-记录修改时间
     */
    private LocalDateTime sysModifiedTime;

    /**
     * 系统-创建人
     */
    private String sysCreatorId;

    /**
     * 系统-最后修改人
     */
    private String sysModifierId;
}
