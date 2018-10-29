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
import java.util.Optional;

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
        model.addAttribute("messageGreen", "Pegawai dengan NIP " + pegawai.getNip() + " berhasil ditambahkan.");
        return "view-pegawai";
    }

    @RequestMapping(value = "/pegawai/cari",method = RequestMethod.GET)
    private  String filter(@RequestParam(value = "idProvinsi", required=false) Optional<String> idProvinsi,
                           @RequestParam(value="idInstansi",  required=false) Optional<String> id_instansi,
                           @RequestParam(value="idJabatan", required=false) Optional<String> id_jabatan,
                           Model model) {
        List<JabatanModel> allJabatan = jabatanService.getListJabatan();
        List<InstansiModel> allInstansi = instansiService.getListInstansi();
        List<ProvinsiModel> allProvinsi = provinsiService.getListProvinsi();
        model.addAttribute("allInstansi",allInstansi);
        model.addAttribute("allProvinsi",allProvinsi);
        model.addAttribute("allJabatan",allJabatan);
        model.addAttribute("title", "Cari Pegawai");

        List<PegawaiModel> allPegawai = pegawaiService.getPegawaiList();
        List<PegawaiModel> result = new ArrayList<>();
        if (idProvinsi.isPresent()) {
            if (id_instansi.isPresent() && id_jabatan.isPresent()) {
                List<PegawaiModel> temp = new ArrayList<>();
                Long idInstansi = Long.parseLong(id_instansi.get());
                InstansiModel instansi = instansiService.getDetailInstansiById(idInstansi);
                Long idJabatan = Long.parseLong(id_jabatan.get());
                JabatanModel jabatan = jabatanService.getDetailJabatanById(idJabatan);
                temp = pegawaiService.getPegawaiByInstansi(instansi);
                for (PegawaiModel peg : temp) {
                    for (JabatanModel jab : peg.getJabatanList()) {
                        if (jab.equals(jabatan)) {
                            result.add(peg);
                        }
                    }
                }
            }
            else if (!(id_instansi.isPresent()) && id_jabatan.isPresent()) {
                List<PegawaiModel> temp = new ArrayList<>();
                Long idProv = Long.parseLong(idProvinsi.get());
                ProvinsiModel prov = provinsiService.getDetailProvinsiById(idProv);
                for (PegawaiModel peg : allPegawai) {
                    if (peg.getInstansi().getProvinsi().equals(prov)) {
                        temp.add(peg);
                    }
                }
                Long idJabatan = Long.parseLong(id_jabatan.get());
                JabatanModel jabatan = jabatanService.getDetailJabatanById(idJabatan);
                for (PegawaiModel peg : temp) {
                    for (JabatanModel jab : peg.getJabatanList()) {
                        if (jab.equals(jabatan)) {
                            result.add(peg);
                        }
                    }
                }
            }
            else if(id_instansi.isPresent() && !(id_jabatan.isPresent())) {
                Long idInstansi = Long.parseLong(id_instansi.get());
                InstansiModel instansi = instansiService.getDetailInstansiById(idInstansi);
                result = pegawaiService.getPegawaiByInstansi(instansi);

            }
            else if(!(id_instansi.isPresent()) && !(id_jabatan.isPresent())) {
                Long idProv = Long.parseLong(idProvinsi.get());
                ProvinsiModel prov = provinsiService.getDetailProvinsiById(idProv);
                for (PegawaiModel peg : allPegawai) {
                    if(peg.getInstansi().getProvinsi().equals(prov)) {
                        result.add(peg);
                    }
                }
            }
        }
        else {
            if (id_jabatan.isPresent() && id_instansi.isPresent()) {
                List<PegawaiModel> temp = new ArrayList<>();
                Long idInstansi = Long.parseLong(id_instansi.get());
                InstansiModel instansi = instansiService.getDetailInstansiById(idInstansi);
                Long idJabatan = Long.parseLong(id_jabatan.get());
                JabatanModel jabatan = jabatanService.getDetailJabatanById(idJabatan);
                temp = pegawaiService.getPegawaiByInstansi(instansi);
                for (PegawaiModel peg : temp) {
                    for (JabatanModel jab : peg.getJabatanList()) {
                        if (jab.equals(jabatan)) {
                            result.add(peg);
                        }
                    }
                }
            }
            else if(id_jabatan.isPresent() && !(id_instansi.isPresent())) {
                Long idJabatan = Long.parseLong(id_jabatan.get());
                JabatanModel jabatan = jabatanService.getDetailJabatanById(idJabatan);
                for (PegawaiModel peg : allPegawai) {
                    for (JabatanModel jab : peg.getJabatanList()) {
                        if (jab.equals(jabatan)) {
                            result.add(peg);
                        }
                    }
                }
            }
            else if(!(id_jabatan.isPresent()) && id_instansi.isPresent()) {
                Long idInstansi = Long.parseLong(id_instansi.get());
                InstansiModel instansi = instansiService.getDetailInstansiById(idInstansi);
                result = pegawaiService.getPegawaiByInstansi(instansi);
            }
            else if(!(id_jabatan.isPresent()) && !(id_instansi.isPresent())) {
                result = null;
            }
        }
        model.addAttribute("allData",result);
        return "cari-pegawai";
    }

    @RequestMapping(value = "/pegawai/tambah/instansi",method = RequestMethod.GET)
    private @ResponseBody List<InstansiModel> cekInstansi(@RequestParam(value="provinsiId") long provinsiId) {
        ProvinsiModel getProv = provinsiService.getDetailProvinsiById(provinsiId);

        return getProv.getInstansiList();
    }

    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
    private String ubahPegawai(@RequestParam String nip, Model model){
        model.addAttribute("title", "Ubah Pegawai");

        PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
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

    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params = {"addJabatanUbah"})
    private String addRowJabatanUbah(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model){
        model.addAttribute("title", "Ubah Pegawai");

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

    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params = {"deleteJabatanUbah"})
    private String deleteRowJabatanUbah(@ModelAttribute PegawaiModel pegawai, Model model, HttpServletRequest req){
        model.addAttribute("title", "Ubah Pegawai");

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

    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
    public String ubahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model){
        model.addAttribute("title", "Tambah Pegawai");

        String oldNipPegawai = pegawai.getNip();

        pegawaiService.update(pegawai);

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("gajiPegawai", pegawai.getGaji());
        model.addAttribute("messageGreen", "Pegawai dengan NIP " + pegawai.getNip() + " berhasil diubah.");
        return "view-pegawai";
    }
}
