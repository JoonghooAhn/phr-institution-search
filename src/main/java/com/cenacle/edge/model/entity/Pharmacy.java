package com.cenacle.edge.model.entity;

import com.cenacle.edge.support.jpa.AbstractBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_pharmacy")
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Pharmacy extends AbstractBaseEntity<String> {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)", nullable = false)
    private String id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String institutionId;

    @NotNull
    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String phoneNumber;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String address;

    @Column(columnDefinition = "VARCHAR(255)")
    private String roughAddress;

    @Column(columnDefinition = "VARCHAR(10)")
    private String zipCode;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(columnDefinition = "JSON")
    private String openingHours;

    @Column(columnDefinition = "BIT(1)")
    private boolean goingOutOfBusiness;

    @Column(columnDefinition = "CHAR(32)")
    private String inadminId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String originData;

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private String route;
}
