package com.cenacle.edge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
//@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ClinicRepository.class))
public class PhrInstitutionSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhrInstitutionSearchApplication.class, args);
    }
}
