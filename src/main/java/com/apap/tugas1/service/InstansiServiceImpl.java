package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
    @Autowired
    private InstansiDb instansiDb;

    @Override
    public InstansiModel getDetailInstansiById(Long id) {
        return instansiDb.findById(id).get();
    }

    @Override
    public List<InstansiModel> getListInstansi() {
        return instansiDb.findAll();
    }
}
