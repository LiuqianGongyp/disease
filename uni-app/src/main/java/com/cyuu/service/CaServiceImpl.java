package com.cyuu.service;

import com.cyuu.dao.CADaoMapper;
import com.cyuu.dataObject.CADao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaServiceImpl implements CaService{
    @Autowired
    private CADaoMapper caDaoMapper;

    @Override
    public int createCipher(CADao caDao) {
        caDaoMapper.insert(caDao);
        return 1;
    }

    @Override
    public CADao searchCipher() {
        return caDaoMapper.selectInfo();
    }
}
