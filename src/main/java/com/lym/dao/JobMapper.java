package com.lym.dao;

import com.lym.entity.Job;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobMapper {
    List<Job> selectAllJob(@Param("job") Job job, @Param("start") Integer start);

    Job selectUpdateJob(Integer id);

    int totalcountJob(Job job);

    int addJob(Job job);

    int updateJob(Job job);

    int delJob(Integer id);

    List<Job>selectAll();

    int checkJobName(String name);
}
