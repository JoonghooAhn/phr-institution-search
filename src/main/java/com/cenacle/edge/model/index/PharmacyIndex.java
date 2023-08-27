package com.cenacle.edge.model.index;

import com.cenacle.edge.model.OpeningHours;
import com.cenacle.edge.swagger.PharmacySchemas;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "${cenacle.search.index-name}", createIndex = false)
@FieldNameConstants
@Data
@Accessors(chain = true)
public class PharmacyIndex {
    @Schema(ref = PharmacySchemas.ID)
    private String id;

    @Schema(ref = PharmacySchemas.INSTITUTION_ID)
    private String institutionId;

    @Schema(ref = PharmacySchemas.NAME)
    private String name;

    @Schema(ref = PharmacySchemas.DISTANCE_IN_METER)
    private Integer distanceInMeter;

    @Schema(ref = PharmacySchemas.ADDRESS)
    private String address;

    @Schema(ref = PharmacySchemas.GEO_LOCATION)
    private GeoPoint location;

    @Schema(ref = PharmacySchemas.PHONE_NUMBER)
    private String phoneNumber;

    @Schema(ref = PharmacySchemas.OPENING_HOURS)
    private OpeningHours openingHours;
}
