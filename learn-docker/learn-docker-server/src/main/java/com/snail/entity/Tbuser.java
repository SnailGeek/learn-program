package com.snail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "tbuser")
public class Tbuser {
    @TableId(type = IdType.AUTO)
    private Integer userid;
    private String username, password, userroles, nickname;
}