package com.cenacle.edge.model.entity;

import com.cenacle.edge.support.jpa.AbstractBaseEntity;
import com.cenacle.edge.support.jpa.UuidKeyGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_clinic_favorite")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@GenericGenerator(name = "uuid", type = UuidKeyGenerator.class)
public class ClinicFavorite extends AbstractBaseEntity<String> {
    @Id
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "CHAR(32)", nullable = false)
    private String id;

    @Column(columnDefinition = "CHAR(32)", nullable = false)
    private String clinicId;

    @Column(columnDefinition = "CHAR(32)", nullable = false)
    private String prevId;

    @Column(columnDefinition = "CHAR(32)", nullable = false)
    private String nextId;
}


