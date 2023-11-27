package com.laoyancheng.www.db.job;

import com.laoyancheng.www.db.domain.MarketOrder;
import com.laoyancheng.www.service.MarketOrderGoodsService;
import com.laoyancheng.www.service.MarketOrderService;
import com.laoyancheng.www.service.MarketOrderStatService;
import com.laoyancheng.www.service.impl.MarketOrderGoodsServiceImpl;
import com.laoyancheng.www.service.impl.MarketOrderServiceImpl;
import com.laoyancheng.www.service.impl.MarketOrderStatServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @Description: 订单统计定时任务
 * @Author: JuRan
 * @Date: 2023/11/26 22:55
 */
public class OrderStatJob implements Job {
    private MarketOrderService marketOrderService = new MarketOrderServiceImpl();
    private MarketOrderStatService marketOrderStatService = new MarketOrderStatServiceImpl();
    private MarketOrderGoodsService marketOrderGoodsService = new MarketOrderGoodsServiceImpl();


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalDateTime start = LocalDateTime.now().minusHours(24).with(LocalTime.of(0, 0, 0));
        LocalDateTime end = start.with(LocalTime.of(23, 59, 59));

        HashMap<String, Object> statData = getOrderStat(start, end);

        // 将上面的数据全部添加到market_order_stat表中去

        marketOrderStatService.create((Date)statData.get("date"), (BigDecimal)statData.get("amount"), (Integer)statData.get("users"), (Integer)statData.get("orders"), (BigDecimal)statData.get("pcr"), (Integer)statData.get("products"));
        // 将上面的数据全部更新到market_order_stat表中去
        // 统计前一天的数据（防止用户0点前下单，0点后付款未统计进去）
        statData = getOrderStat(start.minusHours(24), end.minusHours(24));
        marketOrderStatService.updateByDate((Date)statData.get("date"), (BigDecimal)statData.get("amount"), (Integer)statData.get("users"), (Integer)statData.get("orders"), (BigDecimal)statData.get("pcr"), (Integer)statData.get("products"));
    }

    private HashMap<String, Object> getOrderStat(LocalDateTime start, LocalDateTime end) {
        // 统计一天营业额
        // 统计一天下单用户数量
        List<MarketOrder> orderList = marketOrderService.selectPaidOrderListByTime(start, end);
        BigDecimal amount = new BigDecimal(0);
        Set<Integer> uniqueUserIds = new HashSet<>();
        ArrayList<Integer> orderIdList = new ArrayList<>();
        for(MarketOrder order : orderList){
            amount = amount.add(order.getActualPrice());
            // 遍历订单列表,order.getUserId(),获取用户id，然后去重，得到用户数量
            uniqueUserIds.add(order.getUserId());
            orderIdList.add(order.getId());
        }
        int usersPaid = uniqueUserIds.size();
        // 统计一天订单数量
        int orders = orderList.size();
        // 统计用户平均消费金额
        BigDecimal pcr = amount.divide(new BigDecimal(usersPaid));
        // 统计下单的商品数量
        Integer products = marketOrderGoodsService.countGoodsOrderedByOrderIdList(orderIdList);
        HashMap<String, Object> statData = new HashMap<>();
        statData.put("amount", amount);
        statData.put("users", usersPaid);
        statData.put("orders", orders);
        statData.put("pcr", pcr);
        statData.put("date", Date.valueOf(start.toLocalDate()));
        statData.put("products", products);
        return statData;
    }
}
