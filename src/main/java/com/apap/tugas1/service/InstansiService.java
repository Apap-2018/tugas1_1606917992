package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;

import java.util.List;

public interface InstansiService {
    InstansiModel getDetailInstansiById(Long id);
    List<InstansiModel> getListInstansi();
}
