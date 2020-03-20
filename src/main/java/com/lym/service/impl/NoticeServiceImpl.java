package com.lym.service.impl;

import com.lym.dao.NoticeMapper;
import com.lym.entity.Notice;
import com.lym.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Notice> selectAllNotice(Notice notice,Integer start) {
        return noticeMapper.selectAllNotice(notice,start);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Notice selectNotice(Integer id) {
        return noticeMapper.selectNotice(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addNotice(Notice notice) {
        return noticeMapper.addNotice(notice);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateNotice(Notice notice) {
        return noticeMapper.updateNotice(notice);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int totalcountNotice(Notice notice) {
        return noticeMapper.totalcountNotice(notice);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int delNotice(Integer id) {
        return noticeMapper.delNotice(id);
    }
}
