package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PegawaiService {
    PegawaiModel getPegawaiDetailByNip(String nip);
    PegawaiModel getPegawaiTermuda(InstansiModel instansi);
    PegawaiModel getPegawaiTertua(InstansiModel instansi);
    void addPegawai(PegawaiModel pegawai);
    List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
}
