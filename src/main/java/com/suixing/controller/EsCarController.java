package com.suixing.controller;


import com.suixing.commons.ServerResponse;
import com.suixing.entity.Car;
import com.suixing.service.EsCarService;
import com.suixing.service.ICarService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EsCarController {
    @Autowired
    private ElasticsearchRestTemplate restTemplate;
    @Autowired
    private EsCarService esCarService;
    @Autowired
    private ICarService carService;

//加载数据到Elasticsarch
   @GetMapping("Es/loadCar")
    public ServerResponse loadCar(){
        List<Car> carList = carService.list();
        System.out.println(carList);
        Iterable<Car> cars = esCarService.saveAll(carList);
       return ServerResponse.success("查询成功",cars);
    }
    @GetMapping("Es/getByCarName")
    public ServerResponse getByCarName(@RequestParam("carName")String carName){
       List<Car> cars = esCarService.findByCarName(carName);
       if (cars!=null)
           return ServerResponse.success("查询成功",cars);
       return ServerResponse.success("没有找到相关数据",cars);
    }
    @GetMapping("Es/getCar")
    public ServerResponse CarKeyword(@PathParam("carKeyword") String carKeyword){
        System.out.println("carKeyword:"+carKeyword);
        List<Car> cars = realCarKeyword(carKeyword);
        return ServerResponse.success("查询成功",cars);

    }



//    到Elasticsearch中查询
    public List<Car> realCarKeyword(String carKeyword){
        //多个字段满足一个即可
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("carName",carKeyword))
                .should(QueryBuilders.matchQuery("carBrand",carKeyword))
                .should(QueryBuilders.matchQuery("carModel",carKeyword))
                .should(QueryBuilders.matchQuery("carDisp",carKeyword))
                .should(QueryBuilders.matchQuery("carExhaust",carKeyword));

        //构建高亮查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withHighlightFields(
                        new HighlightBuilder.Field("carName"),
                        new HighlightBuilder.Field("carBrand"),
                        new HighlightBuilder.Field("carModel"),
                        new HighlightBuilder.Field("carDisp"),
                        new HighlightBuilder.Field("carExhaust")
                )
                .withHighlightBuilder(new HighlightBuilder()
                        .preTags("<span style = 'color:red;' >")
                        .postTags("</span>"))
                .build();
//        查询
        SearchHits<Car> search = restTemplate.search(searchQuery,Car.class);

//        获得查询到的内容
        List<SearchHit<Car>> searchHits = search.getSearchHits();

//        设置最后需要返回的实体类集合
        List<Car> carList = new ArrayList<>();
        for (SearchHit<Car> searchHit:searchHits) {
            //高亮
            Map<String,List<String>> highLighFields = searchHit.getHighlightFields();
            //高亮内容填充
            String carName = highLighFields.get("carName") == null ?
                    searchHit.getContent().getCarName() :highLighFields.get("carName").get(0);
            String carBrand = highLighFields.get("carBrand") == null ?
                    searchHit.getContent().getCarBrand() :highLighFields.get("carBrand").get(0);
            String carModel = highLighFields.get("carModel") == null ?
                    searchHit.getContent().getCarModel() :highLighFields.get("carModel").get(0);
            String carDisp = highLighFields.get("carDisp") == null ?
                    searchHit.getContent().getCarDisp() :highLighFields.get("carDisp").get(0);
            String carExhaust = highLighFields.get("carExhaust") == null ?
                    searchHit.getContent().getCarExhaust() :highLighFields.get("carExhaust").get(0);
            searchHit.getContent().setCarName(carName);
            searchHit.getContent().setCarBrand(carBrand);
            searchHit.getContent().setCarModel(carModel);
            searchHit.getContent().setCarDisp(carDisp);
            searchHit.getContent().setCarExhaust(carExhaust);

            //放到实体类
            carList.add(searchHit.getContent());
        }
        return carList;
    }
}
