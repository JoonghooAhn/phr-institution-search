package com.cenacle.edge.mapper;

import com.cenacle.edge.constant.DayOfWeek;
import com.cenacle.edge.constant.PharmacyOperatingStatus;
import com.cenacle.edge.model.PharmacyAutocompleteItemDto;
import com.cenacle.edge.model.PharmacyDetailDto;
import com.cenacle.edge.model.PharmacyFavoriteItemDto;
import com.cenacle.edge.model.entity.Pharmacy;
import com.cenacle.edge.model.index.PharmacyIndex;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = OpeningHoursMapper.class)
public interface PharmacyMapper {
    private static String tryConvertStreetLevelAddress(String address) {
        String streetLevelAddress = address;
        String[] splitAddress = address.split(" ");
        if (splitAddress.length > 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(splitAddress[0]);
            sb.append(" ");
            sb.append(splitAddress[1]);
            for (int i = 2; i < splitAddress.length; i++) {
                if (splitAddress[i].contains("로")) {
                    // 뒤에 로 하나 더 있는지 확인, 있다면 그쪽으로 이동? 여기는 테스트 필요
                    if (i + 1 < splitAddress.length && splitAddress[i + 1].contains("로")) {
                        sb.append(" ");
                        sb.append(splitAddress[i]);
                        i++;
                    }
                    // 로까지 절단
                    sb.append(" ");
                    sb.append(truncateAfterWord(splitAddress[i], "로"));
                }
            }
            streetLevelAddress = sb.toString();
        }
        return streetLevelAddress;
    }

    private static String truncateAfterWord(String input, String word) {
        int index = input.lastIndexOf(word);
        if (index != -1) {
            return input.substring(index);
        }
        return input; // 단어가 없으면 원래 문자열 반환
    }

    PharmacyAutocompleteItemDto toAutocompleteItemDto(PharmacyIndex index);

    PharmacyDetailDto toDetailDto(PharmacyIndex index);

    @Mapping(target = "openingHours", ignore = true)
    PharmacyDetailDto toDetailDto(Pharmacy entity);

    PharmacyFavoriteItemDto toFavoriteItemDto(Pharmacy entity);

    @AfterMapping
    default void afterUpdateDetailDto(PharmacyIndex index, @MappingTarget PharmacyDetailDto dto) {
        dto.setSearchDateTime(ZonedDateTime.now());
        DayOfWeek todayValue = DayOfWeek.getTodayValue();
        if (index.getOpeningHours().getBeginTime(todayValue) != null) {
            LocalTime todayBeginTime = LocalTime.parse(index.getOpeningHours().getBeginTime(todayValue), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime todayEndTime = LocalTime.parse(index.getOpeningHours().getEndTime(todayValue), DateTimeFormatter.ofPattern("HH:mm"));
            dto.setTodayBeginTime(todayBeginTime);
            dto.setTodayEndTime(todayEndTime);
            dto.setTodayStatus(PharmacyOperatingStatus.getStatus(dto.getSearchDateTime().toLocalTime(), todayBeginTime, todayEndTime));
        } else {
            dto.setTodayStatus(PharmacyOperatingStatus.DAY_OFF);
        }

        DayOfWeek tomorrowValue = DayOfWeek.getTomorrowValue();
        dto.setOpenTomorrow(index.getOpeningHours().getBeginTime(tomorrowValue) != null);
        if (Boolean.TRUE.equals(dto.getOpenTomorrow())) {
            LocalTime tomorrowBeginTime = LocalTime.parse(index.getOpeningHours().getBeginTime(tomorrowValue), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime tomorrowEndTime = LocalTime.parse(index.getOpeningHours().getEndTime(tomorrowValue), DateTimeFormatter.ofPattern("HH:mm"));
            dto.setTomorrowBeginTime(tomorrowBeginTime);
            dto.setTomorrowEndTime(tomorrowEndTime);
        }
        dto.setOpenHoliday(index.getOpeningHours().getHolidayBeginTime() != null);
        dto.setStreetLevelAddress(tryConvertStreetLevelAddress(index.getAddress()));
    }
}
