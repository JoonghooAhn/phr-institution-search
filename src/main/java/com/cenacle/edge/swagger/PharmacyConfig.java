package com.cenacle.edge.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class PharmacyConfig {
    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact();
        contact.setName("이정배");
        contact.setEmail("jb.lee@cenacle.com");
        Info info = new Info().title("Pharmacy")
                .version("2023-08-25")
                .description("약국 APIs")
                .contact(contact);

        Server server = new Server();
        server.setDescription("개발 환경 도메인");
        server.setUrl("https://dev.edge.cenacle.com");

        Components components = new Components()
                .addSchemas(PharmacySchemas.ID, PharmacySchemas.ID_SCHEMA)
                .addSchemas(PharmacySchemas.INSTITUTION_ID, PharmacySchemas.INSTITUTION_ID_SCHEMA)
                .addSchemas(PharmacySchemas.NAME, PharmacySchemas.NAME_SCHEMA)
                .addSchemas(PharmacySchemas.DISTANCE_IN_METER, PharmacySchemas.DISTANCE_IN_METER_SCHEMA)
                .addSchemas(PharmacySchemas.ADDRESS, PharmacySchemas.ADDRESS_SCHEMA)
                .addSchemas(PharmacySchemas.STREET_LEVEL_ADDRESS, PharmacySchemas.STREET_LEVEL_ADDRESS_SCHEMA)
                .addSchemas(PharmacySchemas.TODAY_STATUS, PharmacySchemas.TODAY_STATUS_SCHEMA)
                .addSchemas(PharmacySchemas.TODAY_BEGIN_TIME, PharmacySchemas.TODAY_BEGIN_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.TODAY_END_TIME, PharmacySchemas.TODAY_END_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.TOMORROW_BEGIN_TIME, PharmacySchemas.TOMORROW_BEGIN_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.TOMORROW_END_TIME, PharmacySchemas.TOMORROW_END_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.OPEN_TOMORROW, PharmacySchemas.OPEN_TOMORROW_SCHEMA)
                .addSchemas(PharmacySchemas.OPEN_ON_HOLIDAYS, PharmacySchemas.OPEN_ON_HOLIDAYS_SCHEMA)
                .addSchemas(PharmacySchemas.OPENING_HOURS, PharmacySchemas.OPENING_HOURS_SCHEMA)
                .addSchemas(PharmacySchemas.FAVORITE, PharmacySchemas.FAVORITE_SCHEMA)
                .addSchemas(PharmacySchemas.GEO_LOCATION, PharmacySchemas.GEO_LOCATION_SCHEMA)
                .addSchemas(PharmacySchemas.PHONE_NUMBER, PharmacySchemas.PHONE_NUMBER_SCHEMA)
                .addSchemas(PharmacySchemas.AUTO_COMPLETE_FAVORITES, PharmacySchemas.AUTO_COMPLETE_FAVORITES_SCHEMA)
                .addSchemas(PharmacySchemas.AUTO_COMPLETE_SEARCH_RESULTS, PharmacySchemas.AUTO_COMPLETE_SEARCH_RESULTS_SCHEMA)
                .addSchemas(PharmacySchemas.CURRENTLY_OPEN, PharmacySchemas.CURRENTLY_OPEN_SCHEMA)
                .addSchemas(PharmacySchemas.LATE_NIGHT, PharmacySchemas.LATE_NIGHT_SCHEMA)
                .addSchemas(PharmacySchemas.OPEN_YEAR_ROUND, PharmacySchemas.OPEN_YEAR_ROUND_SCHEMA)
                .addSchemas(PharmacySchemas.SEARCH_DATE_TIME, PharmacySchemas.SEARCH_DATE_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.MON_BEGIN_TIME, PharmacySchemas.MON_BEGIN_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.MON_END_TIME, PharmacySchemas.MON_END_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.TUE_BEGIN_TIME, PharmacySchemas.TUE_BEGIN_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.TUE_END_TIME, PharmacySchemas.TUE_END_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.WED_BEGIN_TIME, PharmacySchemas.WED_BEGIN_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.WED_END_TIME, PharmacySchemas.WED_END_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.THR_BEGIN_TIME, PharmacySchemas.THR_BEGIN_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.THR_END_TIME, PharmacySchemas.THR_END_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.FRI_BEGIN_TIME, PharmacySchemas.FRI_BEGIN_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.FRI_END_TIME, PharmacySchemas.FRI_END_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.SAT_BEGIN_TIME, PharmacySchemas.SAT_BEGIN_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.SAT_END_TIME, PharmacySchemas.SAT_END_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.SUN_BEGIN_TIME, PharmacySchemas.SUN_BEGIN_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.SUN_END_TIME, PharmacySchemas.SUN_END_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.HOLIDAY_BEGIN_TIME, PharmacySchemas.HOLIDAY_BEGIN_TIME_SCHEMA)
                .addSchemas(PharmacySchemas.HOLIDAY_END_TIME, PharmacySchemas.HOLIDAY_END_TIME_SCHEMA);

        return new OpenAPI()
                .info(info)
                .components(components)
                .servers(List.of(server));
    }
}
