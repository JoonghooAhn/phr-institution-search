package com.cenacle.edge.jpa;

import com.cenacle.edge.model.entity.Pharmacy;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public class PharmacySpecs {
    public static Specification<Pharmacy> withDeleted(boolean deleted) {
        return ((root, query, builder) ->
                builder.equal(root.get("deleted"), deleted)
        );
    }

    public static Specification<Pharmacy> withIdIn(Collection<String> pharmacyIds) {
        return (root, query, builder) ->
                root.get("id").in(pharmacyIds);
    }
}
