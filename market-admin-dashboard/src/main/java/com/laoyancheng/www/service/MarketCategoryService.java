package com.laoyancheng.www.service;

import com.laoyancheng.www.db.DTO.MarketCategoryDTO;
import com.laoyancheng.www.db.DTO.MarketCategoryL1DTO;
import com.laoyancheng.www.db.domain.MarketCategory;

import java.util.List;

public interface MarketCategoryService {
    List<MarketCategoryDTO> list();

    Object create(MarketCategory marketCategory);

    List<MarketCategoryL1DTO> listL1();

    void update(MarketCategory marketCategory);

    void delete(Integer id);
}
