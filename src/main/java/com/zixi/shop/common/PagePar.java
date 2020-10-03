package com.zixi.shop.common;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: zx
 * @date: 2020/10/3
 */
@Data
public class PagePar {
	@NotBlank(message = "页码不能为空")
	private int page;
	@NotBlank(message = "条数不能为空")
	private int pageSize;
}
