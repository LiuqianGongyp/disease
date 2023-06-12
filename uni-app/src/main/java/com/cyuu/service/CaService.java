package com.cyuu.service;

import com.cyuu.dataObject.CADao;

public interface CaService {
    int createCipher(CADao caDao);
    CADao searchCipher();
}
