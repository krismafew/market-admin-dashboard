package com.laoyancheng.www.db.DTO;

import com.laoyancheng.www.db.domain.MarketCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/20 22:14
 */
@Data
public class MarketCategoryDTO implements Serializable {
    private Integer id;

    private String name;

    private String keywords;

    private String desc;

    private String iconUrl;

    private String picUrl;

    private String level;

    private List<MarketCategoryDTO> children;

}
