package com.example.dynamic_datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author jiangchen
 * @since 2022-08-25
 */
@Getter
@Setter
@TableName("db_config")
public class DbConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Long parkId;

    private String url;

    private String user;

    private String pwd;

    private String driver;

    private LocalDateTime createTime;
}
