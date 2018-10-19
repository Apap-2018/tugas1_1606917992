package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
    @Autowired
    private JabatanDb jabatanDb;

    @Override
    public void addJabatan(JabatanModel jabatanModel){
        jabatanDb.save(jabatanModel);
    }

    @Override
    public void deleteJabatan(JabatanModel jabatanModel) {
        jabatanDb.delete(jabatanModel);
    }

    @Override
    public void updateJabatan(JabatanModel jabatanModel) {
        jabatanDb.save(jabatanModel);
    }

    @Override
    public JabatanModel getDetailJabatanById(Long id) {
        return jabatanDb.findById(id).get();
    }

    @Override
    public List<JabatanModel> getListJabatan() {
        return jabatanDb.findAll();
    }
}
