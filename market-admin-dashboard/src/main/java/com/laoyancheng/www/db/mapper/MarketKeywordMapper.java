package com.laoyancheng.www.db.mapper;

import com.laoyancheng.www.db.domain.MarketKeyword;
import com.laoyancheng.www.db.domain.MarketKeywordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MarketKeywordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    long countByExample(MarketKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    int deleteByExample(MarketKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    int insert(MarketKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    int insertSelective(MarketKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    MarketKeyword selectOneByExample(MarketKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    MarketKeyword selectOneByExampleSelective(@Param("example") MarketKeywordExample example, @Param("selective") MarketKeyword.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    List<MarketKeyword> selectByExampleSelective(@Param("example") MarketKeywordExample example, @Param("selective") MarketKeyword.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    List<MarketKeyword> selectByExample(MarketKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    MarketKeyword selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") MarketKeyword.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    MarketKeyword selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    MarketKeyword selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MarketKeyword record, @Param("example") MarketKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MarketKeyword record, @Param("example") MarketKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MarketKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MarketKeyword record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") MarketKeywordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_keyword
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}