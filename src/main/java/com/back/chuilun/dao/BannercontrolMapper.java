package com.back.chuilun.dao;

import com.back.chuilun.entity.Bannercontrol;

import java.util.List;

public interface BannercontrolMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bannercontrol
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer bannerId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bannercontrol
     *
     * @mbg.generated
     */
    int insert(Bannercontrol record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bannercontrol
     *
     * @mbg.generated
     */
    Bannercontrol selectByPrimaryKey(Integer bannerId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bannercontrol
     *
     * @mbg.generated
     */
    List<Bannercontrol> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bannercontrol
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Bannercontrol record);
}