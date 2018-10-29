package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Override
    public void addPegawai(PegawaiModel pegawai) {
        InstansiModel instansi = pegawai.getInstansi();
        Date tanggalLahir = pegawai.getTanggalLahir();
        String tahunMasuk = pegawai.getTahunMasuk();

        String nip = this.nipMaker(instansi, tanggalLahir, tahunMasuk);
        System.out.println("nipnya : " + nip);
        System.out.println("instansi : " + pegawai.getInstansi().getNama() + "; " + pegawai.getInstansi().getDeskripsi());
        pegawai.setNip(nip);
        pegawaiDb.save(pegawai);
    }

    @Override
    public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk) {
        return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
    }

    @Override
    public PegawaiModel findFirstByNipStartingWithOrderByNipDesc(String nipPegawaiWithoutSequence) {
        return pegawaiDb.findFirstByNipStartingWithOrderByNipDesc(nipPegawaiWithoutSequence);
    }

    @Override
    public List<PegawaiModel> getPegawaiList() {
        return pegawaiDb.findAll();
    }

    @Override
    public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
        return pegawaiDb.findByInstansi(instansi);
    }

    @Override
    public void update(PegawaiModel newPegawai) {
        InstansiModel instansi = newPegawai.getInstansi();
        Date tanggalLahir = newPegawai.getTanggalLahir();
        String tahunMasuk = newPegawai.getTahunMasuk();

        newPegawai.setNip(this.nipMaker(instansi, tanggalLahir, tahunMasuk));

        pegawaiDb.save(newPegawai);
    }

    private String nipMaker(InstansiModel instansi, Date tanggalLahir, String tahunMasuk) {
        String nip = "";

        String kodeInstansi = Long.toString(instansi.getId());
        nip += kodeInstansi;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
        String tanggalLahirString = simpleDateFormat.format(tanggalLahir).replace("-", "");
        nip += tanggalLahirString;

        nip += tahunMasuk;

        int nomorPegawai = 1;
//        List<PegawaiModel> listPegawaiNipMirip = this.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
//        if (!listPegawaiNipMirip.isEmpty())
//            nomorPegawai = (int) Long.parseLong(listPegawaiNipMirip.get(listPegawaiNipMirip.size() - 1).getNip())%100 + 1;

        String nipPegawaiWithoutSeq = kodeInstansi + tanggalLahirString + tahunMasuk;
        PegawaiModel lastPegawaiNipMirip = this.findFirstByNipStartingWithOrderByNipDesc(nipPegawaiWithoutSeq);
        if (lastPegawaiNipMirip != null){
            nomorPegawai += Integer.parseInt(lastPegawaiNipMirip.getNip().substring(14));
        }
        String stringNomorPegawai = "";
        if (nomorPegawai / 10 == 0){
            stringNomorPegawai += "0" + nomorPegawai;
        } else {
            stringNomorPegawai += Integer.toString(nomorPegawai);
        }
        nip += stringNomorPegawai;

        return nip;
    }
}
