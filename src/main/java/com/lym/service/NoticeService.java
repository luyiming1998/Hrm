package com.lym.service;

import com.lym.entity.Notice;

import java.util.List;

public interface NoticeService {

    List<Notice> selectAllNotice(Notice notice,Integer start);

    Notice selectNotice(Integer id);

    int addNotice(Notice notice);

    int updateNotice(Notice notice);

    int totalcountNotice(Notice notice);

    int delNotice(Integer id);
}
