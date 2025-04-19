package com.wcc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wcc.demo.model.entity.Postcode;
import java.util.Optional;

public interface PostcodeRepository extends JpaRepository<Postcode, Long> {

    Optional<Postcode> findByPostcode(String postcode);
}
