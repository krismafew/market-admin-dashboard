package com.laoyancheng.www.service;

import com.laoyancheng.www.db.DTO.MarketBrandLabelDTO;
import com.laoyancheng.www.db.domain.MarketBrand;

import java.util.List;

public interface MarketBrandService {
    List<MarketBrand> list(Integer pageNum, Integer pageSize, String sort, String order);

    MarketBrand create(MarketBrand marketBrand);

    MarketBrand update(MarketBrand marketBrand);

    void delete(Integer id);

    List<MarketBrandLabelDTO> listLabel();
}
