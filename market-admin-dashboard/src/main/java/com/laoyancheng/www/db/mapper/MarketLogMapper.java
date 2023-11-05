package com.laoyancheng.www.db.mapper;

import com.laoyancheng.www.db.domain.MarketLog;
import com.laoyancheng.www.db.domain.MarketLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MarketLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    long countByExample(MarketLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    int deleteByExample(MarketLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    int insert(MarketLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    int insertSelective(MarketLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    MarketLog selectOneByExample(MarketLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    MarketLog selectOneByExampleSelective(@Param("example") MarketLogExample example, @Param("selective") MarketLog.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    List<MarketLog> selectByExampleSelective(@Param("example") MarketLogExample example, @Param("selective") MarketLog.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    List<MarketLog> selectByExample(MarketLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    MarketLog selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") MarketLog.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    MarketLog selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    MarketLog selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MarketLog record, @Param("example") MarketLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MarketLog record, @Param("example") MarketLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MarketLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MarketLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") MarketLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_log
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}