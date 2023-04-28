package com.minair.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Location {

    // KOREA
    INCHEON("인천", "ICN", 126.705, 37.456),

    // japan
    OSAKA("오사카", "KIX", 135.50, 34.69),
    TOKYO("도쿄", "NRT", 139.69, 35.69),
    FUKUOKA("후쿠오카", "FUK", 130.42, 33.60),
    OKINAWA("오키나와", "OKA", 127.97, 26.54),
    SAPPORO("삿포로", "CTS", 141.35, 43.07),
    NAGOYA("나고야", "NGO", 136.91, 35.18),

    // china
    BEIJING("베이징", "PEK", 116.40, 39.90),
    SHANGHAI("상하이", "PVG", 121.47, 31.23),
    GUANGZHOU("광저우", "CAN",113.26, 23.129),
    QINGDAO("청도", "TAO",120.38, 36.06),
    SHENZHEN("심천","SZX", 114.06, 22.54),
    YANJI("연길", "YNJ", 129.50, 42.90),
    HANGZHOU("항주", "HGH", 120.16, 30.25),
    SHENYANG("심양", "SHE", 123.40, 41.799),
    DALIAN("대련", "DLC", 121.639, 38.92),

    // south-east asia
    BANGKOK("방콕", "BKK", 100.52, 13.73),
    DANANG("다낭",  "DAD",108.21, 16.068),
    HOCHIMINH("호치민", "SGN",106.629, 10.82),
    HONGKONG("홍콩",  "HKG",114.16, 22.279),
    TAIPEI("타이베이",  "TPE",121.56, 25.037),
    MANILA("마닐라",  "MNL",120.978, 14.59),
    HANOI("하노이",  "HAN",105.67, 20.97),
    SINGAPORE("싱가포르",  "SIN",103.819, 1.357),
    CEBU("세부",  "CEB",123.90, 10.29),
    CLARKIELD("클라크필드", "CRK", 120.55, 15.18),
    KOTAKINABALU("코타키나발루", "BKI", 116.07, 5.978),
    MACAU("마카오", "MFM", 113.538, 22.189),
    NHATRANG("나트랑", "CXR", 109.189, 12.24),
    KUALALUMPUR("쿠알라룸푸르", "KUL", 101.69, 3.15),
    PHUKET("푸켓", "HKT",98.35, 7.93),

    // europe
    PARIS("파리", "CDG", 48.858, 2.32),
    LONDON("런던", "LHR", -0.127, 51.507),
    BARCELONA("바르셀로나", "BCN", 2.177, 41.38),
    FRANKFURT("프랑크푸르트", "FRA", 8.68, 50.11),
    ROME("로마", "FCO", 12.48, 41.89),
    PRAGUE("프라하", "PRG", 14.44, 50.059),
    ISTANBUL("이스탄불", "IST", 28.96, 41.009),
    MUNICH("뮌헨", "MUC", 48.137, 11.57),
    MADRID("마드리드", "MAD", -3.70, 40.41),
    VLADIVOSTOK("블라디보스톡", "VVO", 131.88, 43.11),
    MOSCOW("모스크바", "SVO", 37.617, 55.75),
    VIENNA("비엔나", "VIE", 16.37, 48.208),
    VENICE("베니스", "VCE", 12.328, 45.44),
    ZURICH("취리히", "ZRH", 8.54, 47.37),
    AMSTERDAM("암스테르담", "AMS", 4.89, 52.37),
    MILAN("밀라노", "MXP", 9.189, 45.46),
    WARSAW("바르샤바", "WAW", 21.07, 52.23),
    ZAGREB("자그레브", "ZAG", 15.96, 45.84),

    //America
    LOS_ANGELAS("로스 앤젤레스", "PHX", 34.052, -118.244),
    HONOLULU("호놀룰루", "HNL", -157.805, 21.314),
    NEW_YORK("뉴욕", "JFK", -73.939, 40.664),
    VANCOUVER("밴쿠버", "YVR", 49.283, -123.121),
    SAN_FRANCISCO("샌프란시스코", "SFO", -122.42, 37.775),
    TORONTO("토론토", "YYZ", 43.653, -79.383),

    //Great Western
    GUAM("괌", "GUM", 13.444, 144.794),
    SYDNEY("시드니", "SYD", -33.867, 151.207),
    MELBOURN("멜버른", "MEL", -37.814, 144.963),
    BRISBORN("브리즈번", "BNE", 153.03, -27.47),
    SAIPAN("사이판", "SPN", 15.178, 145.751),
    AUCHLAND("오클란드", "AKL", 174.792, -37.01),
    ;

    private final String cityName;
    private final String airportCode;
    private final double longitude;
    private final double latitude;

    public static City of(Location location) {
        return City.builder()
                .name(location.getCityName())
                .airportCode(location.getAirportCode())
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .build();
    }
}
