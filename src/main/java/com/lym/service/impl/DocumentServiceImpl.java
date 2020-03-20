package com.lym.service.impl;

import com.lym.dao.DocumentMapper;
import com.lym.entity.Document;
import com.lym.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("DocumentService")
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentMapper documentMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Document> selectAllDocument(String title,Integer start) {
        return documentMapper.selectAllDocument(title,start);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Document selectDocument(Integer id) {
        return documentMapper.selectDocument(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addDocument(Document document) {
        return documentMapper.addDocument(document);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int updateDocument(Document document) {
        return documentMapper.updateDocument(document);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int delDocument(Integer id) {
        return documentMapper.delDocument(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int totalcountDocument(String title) {
        return documentMapper.totalcountDocument(title);
    }
}