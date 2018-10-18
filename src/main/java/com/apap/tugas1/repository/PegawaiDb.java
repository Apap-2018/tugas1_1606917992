package com.apap.tugas1.repository;

import com.apap.tugas1.model.PegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PegawaiDb extends JpaRepository<PegawaiModel, Long> {
    PegawaiModel findByNip(String nip);
}
