package com.laoyancheng.www.db.domain;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/24 23:18
 */
@Data
public class MarketUserGrowth {
    private Integer id;
    private LocalDate day;
    private Integer users;
}
