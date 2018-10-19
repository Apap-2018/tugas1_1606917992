package com.apap.tugas1.controller;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private JabatanService jabatanService;

    @Autowired
    private InstansiService instansiService;

    @RequestMapping("/")
    private String home(Model model) {
        model.addAttribute("title", "Home");

        List<JabatanModel> listAllJabatan = jabatanService.getListJabatan();
        model.addAttribute("listAllJabatan", listAllJabatan);

        List<InstansiModel> listAllInstansi = instansiService.getListInstansi();
        model.addAttribute("listAllInstansi", listAllInstansi);

        return "home";
    }

    @RequestMapping(value = "/pegawai", method = RequestMethod.GET)
    private String viewPegawai(@RequestParam(value = "nip", required = true) String nip, Model model) {
        PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
        double gajiPegawai = pegawai.getGaji();

        model.addAttribute("title", "View Detail Pegawai");
        model.addAttribute("pegawai", pegawai);
        model.addAttribute("gajiPegawai", gajiPegawai);
        return "view-pegawai";
    }

    @RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
    private String viewPegawaiTertuaTermuda(@RequestParam(value = "idInstansi") Long id, Model model){
        InstansiModel instansi = instansiService.getDetailInstansiById(id);
        PegawaiModel pegawaiTermuda = pegawaiService.getPegawaiTermuda(instansiService.getDetailInstansiById(id));
        PegawaiModel pegawaiTertua = pegawaiService.getPegawaiTertua(instansiService.getDetailInstansiById(id));
        model.addAttribute("pegawaiTermuda", pegawaiTermuda);
        model.addAttribute("pegawaiTertua", pegawaiTertua);
        model.addAttribute("title", "View Pegawai Tertua dan Termuda");
        return "view-termudatertua";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
    private String tambahPegawai(Model model){
        return "tambah-pegawai";
    }
}
