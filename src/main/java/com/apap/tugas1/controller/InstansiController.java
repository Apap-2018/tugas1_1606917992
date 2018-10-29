package com.apap.tugas1.controller;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.ProvinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class InstansiController {
    @Autowired
    private InstansiService instansiService;

    @Autowired
    private ProvinsiService provinsiService;

    @RequestMapping(value = "/instansi/getFromProvinsi", method = RequestMethod.GET)
    @ResponseBody
    private List<InstansiModel> getInstansiByProvinsi(@RequestParam (value = "provinsiId", required = true) Long provinsiId) {
        ProvinsiModel provinsi = provinsiService.getDetailProvinsiById(provinsiId);
        List<InstansiModel> listInstansi = provinsi.getInstansiList();
        return listInstansi;
    }
}
