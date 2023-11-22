package com.laoyancheng.www.db.DTO;

import lombok.Data;

import java.util.List;

/**
 * @Description: 商品一级类目DTO
 * @Author: JuRan
 * @Date: 2023/11/21 11:23
 */
@Data
public class MarketCategoryLabelDTO {
    private Integer value;
    private String label;

    private List<MarketCategoryLabelDTO> children;
}
