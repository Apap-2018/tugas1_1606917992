package com.apap.tugas1.controller;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @Autowired
    private JabatanService jabatanService;

    @Autowired
    private InstansiService instansiService;

    @Autowired
    private ProvinsiService provinsiService;

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

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
    private String tambahPegawai(Model model){
        model.addAttribute("title", "Tambah Pegawai");

        PegawaiModel pegawai = new PegawaiModel();
        ArrayList<JabatanModel> listJabatanPegawai = new ArrayList<>();
        pegawai.setJabatanList(listJabatanPegawai);
        pegawai.getJabatanList().add(new JabatanModel());
        model.addAttribute("pegawai", pegawai);

        List<ProvinsiModel> listProvinsi = provinsiService.getListProvinsi();
        model.addAttribute("listProvinsi", listProvinsi);

        List<InstansiModel> listInstansi = instansiService.getListInstansi();
        model.addAttribute("listInstansi", listInstansi);

        List<JabatanModel> listJabatan = jabatanService.getListJabatan();
        model.addAttribute("listJabatan", listJabatan);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("tanggalLahir", dateFormat.format(new Date()));

        return "tambah-pegawai";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params = {"addJabatan"})
    private String addRowJabatan(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model){
        model.addAttribute("title", "Tambah Pegawai");

        pegawai.getJabatanList().add(new JabatanModel());
        model.addAttribute("pegawai", pegawai);

        List<ProvinsiModel> listProvinsi = provinsiService.getListProvinsi();
        model.addAttribute("listProvinsi", listProvinsi);

        List<InstansiModel> listInstansi = instansiService.getListInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
        model.addAttribute("listInstansi", listInstansi);

        List<JabatanModel> listJabatan = jabatanService.getListJabatan();
        model.addAttribute("listJabatan", listJabatan);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("tanggalLahir", dateFormat.format(pegawai.getTanggalLahir()));

        return "tambah-pegawai";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params = {"deleteJabatan"})
    private String deleteRowJabatan(@ModelAttribute PegawaiModel pegawai, Model model, HttpServletRequest req){
        model.addAttribute("title", "Tambah Pegawai");

        Integer idRowJabatan = Integer.valueOf(req.getParameter("deleteJabatan"));
        pegawai.getJabatanList().remove(idRowJabatan.intValue());
        model.addAttribute("pegawai", pegawai);

        List<ProvinsiModel> listProvinsi = provinsiService.getListProvinsi();
        model.addAttribute("listProvinsi", listProvinsi);

        List<InstansiModel> listInstansi = instansiService.getListInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
        model.addAttribute("listInstansi", listInstansi);

        List<JabatanModel> listJabatan = jabatanService.getListJabatan();
        model.addAttribute("listJabatan", listJabatan);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("tanggalLahir", dateFormat.format(pegawai.getTanggalLahir()));

        return "tambah-pegawai";
    }

    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
    public String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model){
        model.addAttribute("title", "Tambah Pegawai");

        pegawaiService.addPegawai(pegawai);

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("gajiPegawai", pegawai.getGaji());
        return "view-pegawai";
    }
}
