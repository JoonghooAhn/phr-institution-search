package com.cenacle.edge.support.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Component  // todo: static constructor 호출하기 위해 사용. 개선 필요
public class DateUtil {
    private static LocalDate date;
    private static Set<Integer> holidaySetCurrentMonth;
    private static Set<Integer> holidaySetNextMonth;

    // 이번 달과 다음 달의 휴일 정보 요청
    // todo: 처음 호출시 api 요청 실패하면 문제생긴다.
    static {
        date = LocalDate.now(ZoneId.of("Asia/Seoul"));
        updateCurrentMonthHolidaySet();
        updateNextMonthHolidaySet();
    }

    // 오늘이 휴일인지 확인하는 메소드
    public static boolean isHolidayToday() {
        // todo: 임시로 제거
        return false;
//        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
//        // 현재 시간을 확인해서 달이 바뀌었는지 확인한다.
//        if (date.getMonth() != now.getMonth()) {
//            // 달이 바뀌면 holidaySetNextMonth 값을 holidaySetCurrentMonth 에 옮긴다
//            holidaySetCurrentMonth = holidaySetNextMonth;
//            // isHolidayTomorrow 값을 갱신하기 위한 api를 호출한다.
//            date = now;
//            // todo scheduler 사용 or DB 저장 검토
//            updateNextMonthHolidaySet();
//        }
//        return holidaySetCurrentMonth.contains(date.getDayOfMonth());
    }

    // 내일이 휴일인지 확인하는 메소드
    public static boolean isHolidayTomorrow() {
        // todo: 임시로 제거
        return false;
//        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
//        // 현재 시간을 확인해서 달이 바뀌었는지 확인한다.
//        if (date.getMonth() != now.getMonth()) {
//            // 달이 바뀌면 holidaySetNextMonth 값을 holidaySetCurrentMonth 에 옮긴다
//            holidaySetCurrentMonth = holidaySetNextMonth;
//            // isHolidayTomorrow 값을 갱신하기 위한 api를 호출한다.
//            date = now;
//            // todo scheduler 사용 or DB 저장 검토
//            updateNextMonthHolidaySet();
//        }
//        return holidaySetCurrentMonth.contains(date.getDayOfMonth());
    }

    private static void updateCurrentMonthHolidaySet() {
        CompletableFuture.runAsync(() -> holidaySetCurrentMonth = request(LocalDate.now(ZoneId.of("Asia/Seoul"))));
    }

    private static void updateNextMonthHolidaySet() {
        CompletableFuture.runAsync(() -> holidaySetNextMonth = request(LocalDate.now(ZoneId.of("Asia/Seoul")).plusMonths(1)));
    }

    // todo: 개선 필요
    @SneakyThrows
    private static Set<Integer> request(LocalDate date) {
        String key = "hXCYiZm6IiBe27CbmcFn4toG9WiKZi0WJITiNRBcOztssqVh8yK%2BaciJbjjb6uRSm8zwAQgOtIXYKDZw9Q6NfA%3D%3D";
        /*URL*/
        final String urlBuilder = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo" +
                "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(key, StandardCharsets.UTF_8) + /*Service Key*/
                "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8) + /*페이지번호*/
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("10", StandardCharsets.UTF_8) + /*한 페이지 결과 수*/
                "&" + URLEncoder.encode("solYear", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(date.getYear()), StandardCharsets.UTF_8) + /*연*/
                "&" + URLEncoder.encode("solMonth", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.format("%02d", date.getMonthValue()), StandardCharsets.UTF_8) + /*월*/
                "&" + URLEncoder.encode("_type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8); /*json 반환*/
        URL url = new URL(urlBuilder);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        Set<Integer> holidays = new HashSet<>();
        JsonNode jsonNode = new ObjectMapper().readTree(sb.toString());
        JsonNode items = jsonNode.path("response").path("body").path("items").path("item");

        // item이 배열로 처리될 것으로 예상하므로, for-each 반복문을 사용하여 모든 휴일을 처리
        for (JsonNode item : items) {
            int locdate = item.path("locdate").asInt();
            int day = locdate % 100; // 일자만 추출
            holidays.add(day);
        }

        return holidays;
    }
}
