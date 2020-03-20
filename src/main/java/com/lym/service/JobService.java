package com.lym.service;

import com.lym.entity.Dept;
import com.lym.entity.Job;

import java.util.List;

public interface JobService {

    List<Job> selectAllJob(Job job, Integer start);

    Job selectUpdateJob(Integer id);

    public int totalcountJob(Job job);

    int addJob(Job job);

    int updateJob(Job job);

    int delJob(Integer id);

    List<Job>selectAll();

    int checkJobName(String name);
}
