package com.cenacle.edge.model.entity;

import com.cenacle.edge.support.jpa.AbstractBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_pharmacy_favorite", indexes = @Index(columnList = "userId"))
@ToString
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class PharmacyFavorite extends AbstractBaseEntity<String> {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)", nullable = false)
    private String id;

    @Column(columnDefinition = "CHAR(32)", nullable = false)
    private String userId;

    @Column(columnDefinition = "CHAR(32)")
    private String prevId;

    @Column(columnDefinition = "CHAR(32)")
    private String nextId;

    @Column(columnDefinition = "CHAR(32)", updatable = false, nullable = false)
    private String pharmacyId;
}
