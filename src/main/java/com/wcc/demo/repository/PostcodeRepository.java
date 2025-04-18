package com.wcc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wcc.demo.model.entity.Postcode;

public interface PostcodeRepository extends JpaRepository<Postcode, Long> {

    Postcode findByPostcode(String postcode);
}
