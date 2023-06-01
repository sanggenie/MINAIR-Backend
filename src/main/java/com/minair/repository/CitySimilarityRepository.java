package com.minair.repository;

import com.minair.domain.CitySimilarity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface CitySimilarityRepository extends JpaRepository<CitySimilarity, Long> {

    @Query("SELECT cs FROM CitySimilarity cs JOIN cs.city c WHERE c.id = :cityId")
    List<CitySimilarity> findAllByCityId(@Param("cityId") Long cityId);

    @Query("select cs from CitySimilarity cs " +
            "join cs.city c " +
            "join cs.targetCity tc " +
            "where c.id = :cityId and tc.id = :targetCityId")
    Optional<CitySimilarity> findByCityIdAndTargetCityId(@Param("cityId") Long cityId,
                                                         @Param("targetCityId") Long targetCityId);
}
