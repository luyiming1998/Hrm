package com.lym.dao;

import com.lym.entity.Document;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocumentMapper {

    List<Document> selectAllDocument(@Param("title") String title,@Param("start") Integer start);

    int addDocument(Document document);

    Document selectDocument(Integer id);

    int delDocument(Integer id);

    int updateDocument(Document document);

    int totalcountDocument(@Param("title") String title);
}
