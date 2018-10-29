package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

import java.util.List;

public interface ProvinsiService {
    List<ProvinsiModel> getListProvinsi();
    ProvinsiModel getDetailProvinsiById(Long id);
}
