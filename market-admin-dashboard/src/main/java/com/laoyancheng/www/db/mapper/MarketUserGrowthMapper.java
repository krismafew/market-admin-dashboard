package com.laoyancheng.www.db.mapper;

import com.laoyancheng.www.db.domain.MarketUserGrowth;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

public interface MarketUserGrowthMapper {
    void insertUserGrowth(@Param("growNum") Integer growthNum, @Param("date") Date date);

    List<MarketUserGrowth> selectUserGrowthList();
}
