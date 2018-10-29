package com.apap.tugas1.repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PegawaiDb extends JpaRepository<PegawaiModel, Long> {
    PegawaiModel findByNip(String nip);
    List<PegawaiModel> findByInstansiOrderByTanggalLahirDesc(InstansiModel idInstansi);
    List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
    PegawaiModel findFirstByNipStartingWithOrderByNipDesc(String nipPegawaiWithoutSequence);
    List<PegawaiModel> findByInstansi(InstansiModel instansi);
}
