package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
    @Autowired
    private PegawaiDb pegawaiDb;

    @Override
    public PegawaiModel getPegawaiDetailByNip(String nip) {
        return pegawaiDb.findByNip(nip);
    }

    @Override
    public PegawaiModel getPegawaiTermuda(InstansiModel instansi) {
        List<PegawaiModel> pegawaiTermuda = pegawaiDb.findByInstansiOrderByTanggalLahirDesc(instansi);
        return pegawaiTermuda.get(0);
    }

    @Override
    public PegawaiModel getPegawaiTertua(InstansiModel instansi) {
        List<PegawaiModel> pegawaiTermuda = pegawaiDb.findByInstansiOrderByTanggalLahirDesc(instansi);
        return pegawaiTermuda.get(pegawaiTermuda.size() - 1);
    }
}
