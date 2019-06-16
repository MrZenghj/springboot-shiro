package com.example.demo.mapper;

import com.example.demo.entity.QuartzConfig;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.Cacheable;


import java.util.List;
@Mapper
public interface QuartzConfigMapper {
    
    @Select("select * from quartzconfig")
    List<QuartzConfig> selectAll();

    @Select("select * from quartzconfig where id = #{id}")
    QuartzConfig selectByPrimaryKey(@Param("id") String id);

    @Update("<script>"
            + "update quartzconfig set groupName=#{quartzConfig.groupName},"
            + "name=#{quartzConfig.name},cron=#{quartzConfig.cron},"
            + "status=#{quartzConfig.status},msg=#{quartzConfig.msg},"
            + "quartzClass=#{quartzConfig.quartzClass} where id = #{quartzConfig.id}"
            + "</script>")
    int updateByPrimaryKey(@Param("quartzConfig")QuartzConfig quartzConfig);

}
