package com.laoyancheng.www.db.mapper;

import com.laoyancheng.www.db.DTO.MarketRegionDTO;
import com.laoyancheng.www.db.domain.MarketRegion;
import com.laoyancheng.www.db.domain.MarketRegionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MarketRegionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    long countByExample(MarketRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    int deleteByExample(MarketRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    int insert(MarketRegion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    int insertSelective(MarketRegion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    MarketRegion selectOneByExample(MarketRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    MarketRegion selectOneByExampleSelective(@Param("example") MarketRegionExample example, @Param("selective") MarketRegion.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    List<MarketRegion> selectByExampleSelective(@Param("example") MarketRegionExample example, @Param("selective") MarketRegion.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    List<MarketRegion> selectByExample(MarketRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    MarketRegion selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") MarketRegion.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    MarketRegion selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") MarketRegion record, @Param("example") MarketRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") MarketRegion record, @Param("example") MarketRegionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(MarketRegion record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table market_region
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MarketRegion record);

    List<MarketRegionDTO> selectRegionHierarchy();
}