package com.lym.dao;

import com.lym.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeMapper {

    List<Notice> selectAllNotice(@Param("notice") Notice notice,@Param("start") Integer start);

    Notice selectNotice(Integer id);

    int addNotice(Notice notice);

    int updateNotice(Notice notice);

    int delNotice(Integer id);

    int totalcountNotice(Notice notice);
}
