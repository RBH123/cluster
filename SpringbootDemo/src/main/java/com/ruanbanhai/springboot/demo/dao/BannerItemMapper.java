package com.ruanbanhai.springboot.demo.dao;

import com.ruanbanhai.springboot.demo.pojo.BannerItem;
import com.ruanbanhai.springboot.demo.pojo.BannerItemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_item
     *
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    int countByExample(BannerItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_item
     *
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    int deleteByExample(BannerItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_item
     *
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    int insert(BannerItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_item
     *
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    int insertSelective(BannerItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_item
     *
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    List<BannerItem> selectByExample(BannerItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_item
     *
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    int updateByExampleSelective(@Param("record") BannerItem record, @Param("example") BannerItemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table banner_item
     *
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    int updateByExample(@Param("record") BannerItem record, @Param("example") BannerItemExample example);
}