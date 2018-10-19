package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;

import java.util.List;

public interface JabatanService {
    void addJabatan(JabatanModel jabatanModel);

    void deleteJabatan(JabatanModel jabatanModel);

    void updateJabatan(JabatanModel jabatanModel);

    JabatanModel getDetailJabatanById(Long id);

    List<JabatanModel> getListJabatan();
}
