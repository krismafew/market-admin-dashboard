package com.laoyancheng.www.db.mapper;

import com.laoyancheng.www.db.domain.MarketGroupon;
import com.laoyancheng.www.db.domain.MarketGrouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MarketGrouponMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    long countByExample(MarketGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    int deleteByExample(MarketGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    int insert(MarketGroupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    int insertSelective(MarketGroupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    MarketGroupon selectOneByExample(MarketGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    MarketGroupon selectOneByExampleSelective(@Param("example") MarketGrouponExample example, @Param("selective") MarketGroupon.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    List<MarketGroupon> selectByExampleSelective(@Param("example") MarketGrouponExample example, @Param("selective") MarketGroupon.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    List<MarketGroupon> selectByExample(MarketGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    MarketGroupon selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") MarketGroupon.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    MarketGroupon selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    MarketGroupon selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MarketGroupon record, @Param("example") MarketGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MarketGroupon record, @Param("example") MarketGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MarketGroupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MarketGroupon record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") MarketGrouponExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_groupon
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}