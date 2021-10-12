package com.leniolabs.training.repository;

import com.leniolabs.training.model.Kaiju;
import com.leniolabs.training.model.KaijuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface KaijuRepository extends JpaRepository<Kaiju, Long> {

    Kaiju findByDna(String dna);

    @Query(value ="select k.type from kaiju k", nativeQuery = true)
    List<KaijuType> findAllKaijuType();

    //select avg(clientwisecnt.clientidcnt) from ( select clientid, count(clientid) as clientidcnt from t group by clientid ) as clientwisecnt;
    //@Query("SELECT AVG(Select COUNT(*) FROM Kaiju WHERE type = :type GROUP BY type) * 100")
    //List<Map<String, Object>> findPercentageOfKaijuGroupedByType(@Param("type") String type);
}
