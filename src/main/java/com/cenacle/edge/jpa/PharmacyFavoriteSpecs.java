package com.cenacle.edge.jpa;

import com.cenacle.edge.model.entity.PharmacyFavorite;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public class PharmacyFavoriteSpecs {
    public static Specification<PharmacyFavorite> withDeleted(boolean deleted) {
        return ((root, query, builder) ->
                builder.equal(root.get("deleted"), deleted)
        );
    }

    public static Specification<PharmacyFavorite> withUserId(String userId) {
        return ((root, query, builder) ->
                builder.equal(root.get("userId"), userId)
        );
    }

    public static Specification<PharmacyFavorite> withPharmacyIdIn(Collection<String> pharmacyIds) {
        return (root, query, builder) ->
                root.get("pharmacyId").in(pharmacyIds);
    }

    public static Specification<PharmacyFavorite> withPharmacyId(String pharmacyId) {
        return (root, query, builder) ->
                builder.equal(root.get("pharmacyId"), pharmacyId);
    }

    public static Specification<PharmacyFavorite> withPrevId(String prevId) {
        return (root, query, builder) ->
                builder.equal(root.get("nextId"), prevId);
    }

    public static Specification<PharmacyFavorite> withNextId(String nextId) {
        return (root, query, builder) ->
                builder.equal(root.get("nextId"), nextId);
    }
}
