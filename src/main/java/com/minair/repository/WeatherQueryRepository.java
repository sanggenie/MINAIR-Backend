package com.minair.repository;

import com.minair.domain.Weather;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.minair.domain.QCity.city;
import static com.minair.domain.QWeather.weather;

@Repository
@RequiredArgsConstructor
@Slf4j
public class WeatherQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Weather> findAllWeatherBetween(Long cityId, LocalDate from, LocalDate to) {
        return queryFactory
                .selectFrom(weather)
                .join(weather.city, city).fetchJoin()
                .where(cityIdEq(cityId).and
                        (betweenDate(from.minusYears(1), to.minusYears(1))
                                .or(betweenDate(from.minusYears(2), to.minusYears(2)))
                                .or(betweenDate(from.minusYears(3), to.minusYears(3)))
                        )
                )
                .fetch();
    }

    private BooleanExpression cityIdEq(Long cityId) {
        return city.id.eq(cityId);
    }

    private BooleanExpression betweenDate(LocalDate from, LocalDate to) {
        return weather.date.between(from, to);
    }

}
