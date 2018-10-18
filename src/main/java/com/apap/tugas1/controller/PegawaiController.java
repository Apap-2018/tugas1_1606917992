package com.apap.tugas1.controller;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.PegawaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;

    @RequestMapping("/")
    private String home(Model model) {
        model.addAttribute("title", "Home");
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
}