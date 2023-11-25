package com.laoyancheng.www.db.DTO;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/25 10:47
 */
@Data
public class MarketRegionDTO {
    private Integer id;
    private Integer code;
    private String name;
    private Short type;

    private List<MarketRegionDTO> children;
}
