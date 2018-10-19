package com.apap.tugas1.controller;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class JabatanController {
    @Autowired
    private JabatanService jabatanService;

    @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
    private String tambahJabatan(Model model) {
        model.addAttribute("title", "Tambah Jabatan");
        model.addAttribute("jabatan", new JabatanModel());
        return "tambah-jabatan";
    }

    @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
    private String tambahJabatanSubmit(@ModelAttribute JabatanModel newJabatan, Model model) {
        jabatanService.addJabatan(newJabatan);
        model.addAttribute("title", "Tambah Jabatan");
        String message = "Jabatan " + newJabatan.getNama() + " telah berhasil dibuat.";
        model.addAttribute("message", message);
        return "tambah-jabatan";
    }

    @RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
    private String viewJabatan(@RequestParam(value = "idJabatan", required = true) Long id, Model model) {
        model.addAttribute("title", "View Detail Jabatan");

        JabatanModel jabatan = jabatanService.getDetailJabatanById(id);
        model.addAttribute("jabatan", jabatan);

        return "view-jabatan";
    }

    @RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
    private String ubahJabatan(@RequestParam(value = "idJabatan") Long id, Model model) {
        model.addAttribute("title", "Ubah Jabatan");
        model.addAttribute("jabatan", jabatanService.getDetailJabatanById(id));
        return "edit-jabatan";
    }

    @RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
    private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model){
        jabatanService.updateJabatan(jabatan);
        model.addAttribute("title", "Ubah Jabatan");
        model.addAttribute("message", "Data jabatan berhasil diubah.");
        model.addAttribute("jabatan", jabatan);
        return "edit-jabatan";
    }

    @RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
    private String hapusJabatan(@ModelAttribute JabatanModel jabatan, Model model) throws Exception{
        try {
            System.out.println(jabatan.getId());
            jabatanService.deleteJabatanById(jabatan.getId());
            model.addAttribute("message", "Jabatan berhasil dihapus");
            return "home";
        } catch (Exception e) {
            model.addAttribute("message", "Jabatan gagal dihapus.");
            model.addAttribute("jabatan", jabatanService.getDetailJabatanById(jabatan.getId()));
            List<JabatanModel> listAllJabatan = jabatanService.getListJabatan();
            model.addAttribute("listAllJabatan", listAllJabatan);
            return "view-jabatan";
        }
    }
}
