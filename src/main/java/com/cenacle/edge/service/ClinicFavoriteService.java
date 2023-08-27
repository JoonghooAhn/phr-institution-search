package com.cenacle.edge.service;

import com.cenacle.edge.model.ClinicSimpleDto;
import com.cenacle.edge.repository.ClinicFavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ClinicFavoriteService {

    public final ClinicFavoriteRepository clinicFavoriteRepository;

    public List<ClinicSimpleDto> getSimpleList(String userId) {
        return new ArrayList<>();
    }
}
