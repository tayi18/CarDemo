package com.suixing.service;


import com.suixing.entity.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public interface EsCarService extends ElasticsearchRepository<Car,Integer> {
    List<Car> findByCarName(String CarName);
    List<Car> findByCarBrand(String CarBrand);

}
