package com.ProjetoIntermediario.ProjetoIntermediario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalUrl;
    private String encurtadaUrl;
    private LocalDateTime creationDateTime;

    // Construtores, getters e setters

    public Url() {}

    public Url(String originalUrl, String encurtadaUrl, LocalDateTime creationDateTime) {
        this.originalUrl = originalUrl;
        this.encurtadaUrl = encurtadaUrl;
        this.creationDateTime = creationDateTime;
    }
}
