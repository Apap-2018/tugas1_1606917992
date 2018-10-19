package com.apap.tugas1.repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PegawaiDb extends JpaRepository<PegawaiModel, Long> {
    PegawaiModel findByNip(String nip);
    List<PegawaiModel> findByInstansiOrderByTanggalLahirDesc(InstansiModel idInstansi);
}
