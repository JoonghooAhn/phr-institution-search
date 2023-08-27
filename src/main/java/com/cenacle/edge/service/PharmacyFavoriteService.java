package com.cenacle.edge.service;

import com.cenacle.edge.jpa.PharmacyFavoriteSpecs;
import com.cenacle.edge.jpa.PharmacySpecs;
import com.cenacle.edge.mapper.PharmacyMapper;
import com.cenacle.edge.model.PharmacyDetailDto;
import com.cenacle.edge.model.PharmacyFavoriteItemDto;
import com.cenacle.edge.model.PharmacyFavoriteReorderRequestDto;
import com.cenacle.edge.model.entity.Pharmacy;
import com.cenacle.edge.model.entity.PharmacyFavorite;
import com.cenacle.edge.repository.PharmacyFavoriteRepository;
import com.cenacle.edge.repository.PharmacyRepository;
import com.cenacle.edge.support.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacyFavoriteService {
    // todo: user 검증 기능 구현 필요. 지금은 모든 user id 참이라고 가정
    // todo: controller에서?
    private final PharmacyFavoriteRepository pharmacyFavoriteRepository;
    private final PharmacyRepository pharmacyRepository;
    private final PharmacyMapper pharmacyMapper;

    private static PharmacyFavorite findFirst(Collection<PharmacyFavorite> values) {
        for (PharmacyFavorite node : values) {
            if (node.getPrevId() == null) {
                return node;
            }
        }
        return null;
    }

    private static PharmacyFavorite tryGetNthElement(int targetOrder, Map<String, PharmacyFavorite> map) {
        PharmacyFavorite result = findFirst(map.values());
        if (result == null) {
            return null;
        }
        for (int i = 0; i < targetOrder; i++) {
            if (result.getNextId() == null || !map.containsKey(result.getNextId())) {
                break;
            }
            result = map.get(result.getNextId());
        }
        return result;
    }

    private static List<PharmacyFavorite> generateList(Map<String, PharmacyFavorite> map) {
        PharmacyFavorite node = findFirst(map.values());
        List<PharmacyFavorite> result = new ArrayList<>();
        while (node != null) {
            result.add(node);
            if (node.getNextId() == null || !map.containsKey(node.getNextId())) {
                break;
            }
            node = map.get(node.getNextId());
        }
        return result;
    }

    private static Slice<PharmacyFavorite> generateSlice(List<PharmacyFavorite> list, Pageable pageable) {
        if (pageable == null || pageable.isUnpaged()) {
            return new SliceImpl<>(list, Pageable.unpaged(), false);
        }
        int offset = (int) pageable.getOffset();
        int end = Math.min(offset + pageable.getPageSize(), list.size());
        return new SliceImpl<>(list.subList(offset, end), pageable, end < list.size());
    }

    @Transactional
    public PharmacyDetailDto favoritePharmacy(User user, String pharmacyId) {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElseThrow();
        boolean exists = pharmacyFavoriteRepository.exists(PharmacyFavoriteSpecs.withUserId(user.getUserId())
                .and(PharmacyFavoriteSpecs.withDeleted(false))
                .and(PharmacyFavoriteSpecs.withPharmacyId(pharmacyId)));
        if (!exists) {
            PharmacyFavorite favorite = new PharmacyFavorite()
                    .setUserId(user.getUserId())
                    .setPharmacyId(pharmacyId);

            pharmacyFavoriteRepository.findOne(PharmacyFavoriteSpecs.withUserId(user.getUserId())
                            .and(PharmacyFavoriteSpecs.withDeleted(false))
                            .and(PharmacyFavoriteSpecs.withNextId(null)))
                    .ifPresent(f -> {
                        f.setNextId(favorite.getId());
                        favorite.setPrevId(f.getId());
                    });

            pharmacyFavoriteRepository.save(favorite);
        }

        return pharmacyMapper.toDetailDto(pharmacy).setFavorite(true);
    }

    @Transactional
    public void unfavoritePharmacies(User user, List<String> idsToRemove) {
        pharmacyFavoriteRepository.findAll(PharmacyFavoriteSpecs.withUserId(user.getUserId())
                        .and(PharmacyFavoriteSpecs.withDeleted(false))
                        .and(PharmacyFavoriteSpecs.withPharmacyIdIn(idsToRemove)))
                .forEach(favorite -> favorite.setDeleted(true));

        Map<String, PharmacyFavorite> favoriteMap = pharmacyFavoriteRepository.findAll(PharmacyFavoriteSpecs.withDeleted(false)
                        .and(PharmacyFavoriteSpecs.withUserId(user.getUserId())))
                .stream()
                .collect(Collectors.toMap(PharmacyFavorite::getId, Function.identity()));

        idsToRemove.stream()
                .map(favoriteMap::get)
                .forEach(favorite -> {
                    favorite.setDeleted(true);
                    favoriteMap.computeIfPresent(favorite.getNextId(), (key, value) -> value.setPrevId(favorite.getPrevId()));
                    favoriteMap.computeIfPresent(favorite.getPrevId(), (key, value) -> value.setNextId(favorite.getNextId()));
                });
    }

    @Transactional
    public void unfavoritePharmacy(User user, String pharmacyId) {
        PharmacyFavorite favorite = pharmacyFavoriteRepository.findOne(PharmacyFavoriteSpecs.withUserId(user.getUserId())
                        .and(PharmacyFavoriteSpecs.withDeleted(false))
                        .and(PharmacyFavoriteSpecs.withPharmacyId(pharmacyId)))
                .orElseThrow();

        favorite.setDeleted(true);

        pharmacyFavoriteRepository.findAll(PharmacyFavoriteSpecs.withUserId(user.getUserId())
                        .and(PharmacyFavoriteSpecs.withDeleted(false))
                        .and(PharmacyFavoriteSpecs.withPrevId(favorite.getId()))
                        .and(PharmacyFavoriteSpecs.withNextId(favorite.getId())))
                .forEach(f -> {
                    if (Objects.equals(f.getNextId(), favorite.getId())) {
                        f.setNextId(favorite.getNextId());
                    }
                    if (Objects.equals(f.getPrevId(), favorite.getId())) {
                        f.setPrevId(favorite.getPrevId());
                    }
                });
    }

    @Transactional(readOnly = true)
    public Slice<PharmacyFavoriteItemDto> favoritePharmacies(User user, Pageable pageable) {
        Map<String, PharmacyFavorite> favoriteMap = pharmacyFavoriteRepository.findAll(PharmacyFavoriteSpecs.withDeleted(false)
                        .and(PharmacyFavoriteSpecs.withUserId(user.getUserId())))
                .stream().collect(Collectors.toMap(PharmacyFavorite::getId, Function.identity()));

        Map<String, Pharmacy> pharmacyMap = pharmacyRepository.findAll(PharmacySpecs.withDeleted(false)
                        .and(PharmacySpecs.withIdIn(favoriteMap.values().stream().map(PharmacyFavorite::getPharmacyId).toList())))
                .stream().collect(Collectors.toMap(Pharmacy::getId, Function.identity()));

        List<PharmacyFavorite> list = generateList(favoriteMap);

        return generateSlice(list, pageable)
                .map(PharmacyFavorite::getPharmacyId)
                .map(pharmacyMap::get)
                .map(pharmacyMapper::toFavoriteItemDto);
    }

    @Transactional
    public void reorderFavorite(User user, PharmacyFavoriteReorderRequestDto reorderRequestDto) {
        Map<String, PharmacyFavorite> favoriteMap = pharmacyFavoriteRepository.findAll(PharmacyFavoriteSpecs.withDeleted(false)
                        .and(PharmacyFavoriteSpecs.withUserId(user.getUserId())))
                .stream().collect(Collectors.toMap(PharmacyFavorite::getId, Function.identity()));

        if (favoriteMap.size() <= 1) {
            return;
        }

        PharmacyFavorite targetFavorite = tryGetNthElement(reorderRequestDto.getOrder(), favoriteMap);
        Assert.notNull(targetFavorite, "targetNode 반드시 존재해야 합니다.");

        PharmacyFavorite pharmacyFavorite = favoriteMap.get(reorderRequestDto.getPharmacyId());
        if (Objects.equals(pharmacyFavorite.getId(), targetFavorite.getId())) {
            return;
        }

        PharmacyFavorite beforePrev = favoriteMap.get(pharmacyFavorite.getPrevId());
        PharmacyFavorite beforeNext = favoriteMap.get(pharmacyFavorite.getNextId());
        if (beforePrev != null) {
            beforePrev.setNextId(pharmacyFavorite.getNextId());
        }
        if (beforeNext != null) {
            beforeNext.setPrevId(pharmacyFavorite.getPrevId());
        }

        pharmacyFavorite.setPrevId(targetFavorite.getPrevId());
        pharmacyFavorite.setNextId(targetFavorite.getId());
        targetFavorite.setPrevId(pharmacyFavorite.getId());
    }
}
