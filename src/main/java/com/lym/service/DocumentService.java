package com.lym.service;

import com.lym.entity.Document;

import java.util.List;

public interface DocumentService {

    List<Document> selectAllDocument(String title,Integer start);

    Document selectDocument(Integer id);

    int addDocument(Document document);

    int updateDocument(Document document);

    int delDocument(Integer id);

    int totalcountDocument(String title);

}
