package com.laoyancheng.www.service;

import com.laoyancheng.www.db.DTO.MarketCategoryDTO;
import com.laoyancheng.www.db.DTO.MarketCategoryLabelDTO;
import com.laoyancheng.www.db.domain.MarketCategory;

import java.util.List;

public interface MarketCategoryService {
    List<MarketCategoryDTO> list();

    Object create(MarketCategory marketCategory);

    List<MarketCategoryLabelDTO> listL1();

    void update(MarketCategory marketCategory);

    void delete(Integer id);

    List<MarketCategoryLabelDTO> listLabel();

    List<Integer> selectCategoryIdHierarchyListByL2(Integer categoryId);
}
