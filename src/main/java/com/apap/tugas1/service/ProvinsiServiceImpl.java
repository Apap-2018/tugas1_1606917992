package com.apap.tugas1.service;

import com.apap.tugas1.repository.ProvinsiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService {
    @Autowired
    private ProvinsiDb provinsiDb;
}
