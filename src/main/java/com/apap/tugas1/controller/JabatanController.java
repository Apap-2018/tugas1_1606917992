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

        JabatanModel detailJabatan = jabatanService.getDetailJabatanById(id);
        model.addAttribute("detailJabatan", detailJabatan);

        return "view-jabatan";
    }
}
