package com.cf.provider.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 基础实体类
 * @author linxiunan
 * @date 2018年10月18日
 */
@Data
public class BaseEntity implements Serializable {

	@ApiModelProperty(value = "id")
	@NotNull(message = "所需唯一标识不能为空")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_time")
	private Date createdTime;

	@ApiModelProperty(value = "最后一次更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "last_modified_time")
	private Date lastModifiedTime;

	@ApiModelProperty(value = "启用禁用状态: 1 启用(默认) 0  禁用")
	@Column(name = "is_enabled")
	private Integer isEnabled;

	@ApiModelProperty(value = "删除标识: 0 未删除(默认) 1 已删除")
	@Column(name = "is_deleted")
	private Integer isDeleted;
}
