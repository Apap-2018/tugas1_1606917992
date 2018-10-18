package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
