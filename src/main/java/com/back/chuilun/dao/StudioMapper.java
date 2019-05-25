package com.back.chuilun.dao;

import com.back.chuilun.entity.Studio;

import java.util.List;

public interface StudioMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table studio
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer studioId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table studio
     *
     * @mbg.generated
     */
    int insert(Studio record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table studio
     *
     * @mbg.generated
     */
    Studio selectByPrimaryKey(Integer studioId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table studio
     *
     * @mbg.generated
     */
    List<Studio> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table studio
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Studio record);
}