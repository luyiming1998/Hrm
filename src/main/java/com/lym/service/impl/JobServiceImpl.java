package com.lym.service.impl;

import com.lym.dao.JobMapper;
import com.lym.entity.Job;
import com.lym.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("JobService")
public class JobServiceImpl implements JobService {

    @Autowired
    private JobMapper jobMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Job> selectAllJob(Job job, Integer start) {
        return jobMapper.selectAllJob(job, start);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Job selectUpdateJob(Integer id) {
        return jobMapper.selectUpdateJob(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int totalcountJob(Job job) {
        return jobMapper.totalcountJob(job);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addJob(Job job) {
        return jobMapper.addJob(job);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateJob(Job job) {
        return jobMapper.updateJob(job);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int delJob(Integer id) {
        return jobMapper.delJob(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Job> selectAll() {
        return jobMapper.selectAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int checkJobName(String name) {
        return jobMapper.checkJobName(name);
    }
}
