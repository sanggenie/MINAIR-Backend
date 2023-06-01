package com.minair.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Location {

    // KOREA
    INCHEON("대한민국", "인천", "ICN", 126.705, 37.456),

    // japan
    OSAKA("일본","오사카", "KIX", 135.50, 34.69),
    TOKYO("일본","도쿄", "NRT", 139.69, 35.69),
    FUKUOKA("일본","후쿠오카", "FUK", 130.42, 33.60),
    OKINAWA("일본","오키나와", "OKA", 127.97, 26.54),
    SAPPORO("일본","삿포로", "CTS", 141.35, 43.07),
    NAGOYA("일본","나고야", "NGO", 136.91, 35.18),

    // china
    BEIJING("중국","베이징", "PEK", 116.40, 39.90),
    SHANGHAI("중국","상하이", "PVG", 121.47, 31.23),
    GUANGZHOU("중국","광저우", "CAN",113.26, 23.129),
    QINGDAO("중국","청도", "TAO",120.38, 36.06),
    SHENZHEN("중국","심천","SZX", 114.06, 22.54),
    YANJI("중국","연길", "YNJ", 129.50, 42.9),
    HANGZHOU("중국","항주", "HGH", 120.16, 30.25),
    SHENYANG("중국","심양", "SHE", 123.40, 41.799),
    DALIAN("중국","대련", "DLC", 121.639, 38.92),

    // south-east asia
    BANGKOK("태국","방콕", "BKK", 100.52, 13.73),
    DANANG("베트남","다낭",  "DAD",108.21, 16.068),
    HOCHIMINH("베트남","호치민", "SGN",106.629, 10.82),
    HONGKONG("홍콩","홍콩",  "HKG",114.16, 22.279),
    TAIPEI("대만","타이베이",  "TPE",121.56, 25.037),
    MANILA("필리핀","마닐라",  "MNL",120.978, 14.59),
    HANOI("베트남","하노이",  "HAN",105.67, 20.97),
    SINGAPORE("싱가포르","싱가포르",  "SIN",103.819, 1.357),
    CEBU("필리핀","세부",  "CEB",123.90, 10.29),
    KOTAKINABALU("말레이시아","코타키나발루", "BKI", 116.07, 5.978),
    MACAU("마카오","마카오", "MFM", 113.538, 22.189),
    NHATRANG("베트남","나트랑", "CXR", 109.189, 12.24),
    KUALALUMPUR("말레이시아","쿠알라룸푸르", "KUL", 101.69, 3.15),
    PHUKET("태국","푸켓", "HKT",98.35, 7.93),

    // europe
    PARIS("프랑스","파리", "CDG", 48.858, 2.32),
    LONDON("영국","런던", "LHR", -0.127, 51.507),
    BARCELONA("스페인","바르셀로나", "BCN", 2.177, 41.38),
    FRANKFURT("독일","프랑크푸르트", "FRA", 8.68, 50.11),
    ROME("이탈리아","로마", "FCO", 12.48, 41.89),
    PRAGUE("체코","프라하", "PRG", 14.44, 50.059),
    ISTANBUL("튀르키예","이스탄불", "IST", 28.96, 41.009),
    MUNICH("독일","뮌헨", "MUC", 48.137, 11.57),
    MADRID("스페인","마드리드", "MAD", -3.70, 40.41),
    VLADIVOSTOK("러시아","블라디보스톡", "VVO", 131.88, 43.11),
    MOSCOW("러시아","모스크바", "SVO", 37.617, 55.75),
    VIENNA("오스트리아","비엔나", "VIE", 16.37, 48.208),
    VENICE("이탈리아","베니스", "VCE", 12.328, 45.44),
    ZURICH("스위스","취리히", "ZRH", 8.54, 47.37),
    AMSTERDAM("암스테르담","암스테르담", "AMS", 4.89, 52.37),
    MILAN("이탈리아","밀라노", "MXP", 9.189, 45.46),
    WARSAW("폴란드","바르샤바", "WAW", 21.07, 52.23),
    ZAGREB("크로아티아","자그레브", "ZAG", 15.96, 45.84),

    //America
    LOSANGELAS("미국","로스앤젤레스", "PHX", -118.244, 34.052),
    HONOLULU("미국","호놀룰루", "HNL", -157.805, 21.314),
    NEWYORK("미국","뉴욕", "JFK", -73.939, 40.664),
    VANCOUVER("캐나다","밴쿠버", "YVR", -123.121,49.283),
    SANFRANCISCO("미국","샌프란시스코", "SFO", -122.42, 37.775),
    TORONTO("캐나다","토론토", "YYZ", -79.383,43.653),

    //Great Western
    GUAM("괌","괌", "GUM", 144.794, 13.444),
    SYDNEY("호주","시드니", "SYD", 151.207,-33.867),
    MELBOURNE("호주","멜버른", "MEL", 144.963,-37.814),
    BRISBANE("호주","브리즈번", "BNE", 153.03, -27.47),
    SAIPAN("사이판","사이판", "SPN",145.751,15.178),
    AUCHLAND("뉴질랜드","오클랜드", "AKL", 174.792, -37.01),
    ;

    private final String country;
    private final String cityName;
    private final String airportCode;
    private final double longitude;
    private final double latitude;
    private int cluster;

    public void initCluster(int cluster) {
        this.cluster = cluster;
    }
}
