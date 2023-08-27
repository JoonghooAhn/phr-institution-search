package com.cenacle.edge.service;

import com.cenacle.edge.jpa.PharmacyFavoriteSpecs;
import com.cenacle.edge.mapper.PharmacyMapper;
import com.cenacle.edge.model.PharmacyDetailDto;
import com.cenacle.edge.model.entity.Pharmacy;
import com.cenacle.edge.repository.PharmacyFavoriteRepository;
import com.cenacle.edge.repository.PharmacyRepository;
import com.cenacle.edge.support.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PharmacyService {
    // todo: user 검증 기능 구현 필요. 지금은 모든 user id 참이라고 가정ㅂ
    // todo: controller에서?

    private final PharmacyRepository pharmacyRepository;
    private final PharmacyFavoriteRepository pharmacyFavoriteRepository;
    private final PharmacyMapper pharmacyMapper;

    public PharmacyDetailDto getPharmacyDetail(User user, String pharmacyId) {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElseThrow();
        boolean exists = pharmacyFavoriteRepository.exists(PharmacyFavoriteSpecs.withUserId(user.getUserId())
                .and(PharmacyFavoriteSpecs.withDeleted(false))
                .and(PharmacyFavoriteSpecs.withPharmacyId(pharmacyId)));

        return pharmacyMapper.toDetailDto(pharmacy).setFavorite(exists);
    }
}
