package com.ProjetoIntermediario.ProjetoIntermediario.repository;

import com.ProjetoIntermediario.ProjetoIntermediario.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface urlRepository extends JpaRepository<Url, Long> {
}
