package com.laoyancheng.www.db.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * @Description:
 * @Author: JuRan
 * @Date: 2023/11/27 22:43
 */
@Data
public class MarketOrderStat {
    private Integer id;
    private BigDecimal amount;
    private Integer customers;
    private Integer orders;
    private BigDecimal pcr;
    private LocalDate day;
    private Integer products;

}
