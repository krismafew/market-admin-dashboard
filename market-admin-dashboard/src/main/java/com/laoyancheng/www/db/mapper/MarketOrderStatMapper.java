package com.laoyancheng.www.db.mapper;

import com.laoyancheng.www.db.domain.MarketOrderStat;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface MarketOrderStatMapper {
    void insert(@Param("date") Date date, @Param("amount") BigDecimal amount, @Param("users") int usersPaid, @Param("orders") int orders, @Param("pcr") BigDecimal pcr, @Param("products") int products);

    void updateByDate(@Param("date") Date date, @Param("amount") BigDecimal amount, @Param("users") int users, @Param("orders") int orders, @Param("pcr") BigDecimal pcr, @Param("products") int products);

    List<MarketOrderStat> selectAll();
}
