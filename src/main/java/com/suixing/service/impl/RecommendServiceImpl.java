package com.suixing.service.impl;

import com.suixing.entity.Car;
import com.suixing.mapper.CarMapper;
import com.suixing.service.IRecommendService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-11-04
 */
@Service
@Component
public class RecommendServiceImpl implements IRecommendService {

    @Autowired
    private CarMapper carMapper;
    @Autowired(required = false)
    private DataModel dataModel;


    @Override
    public List<Car> getRecommentCarByUser(Integer userId, Integer howMany) {
        List<Car> carList = null;

        /*计算相似度，相似度的计算方式很多，采用基于皮尔逊相关性的相似度*/
        try {
            UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
            /*
                计算最近邻居，邻居有两种算法：基于固定数量的邻居和基于相似度的邻居
                这里采用基于固定数量的邻居
            */
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100,similarity,dataModel);
            /*构建推荐器，基于用户的协同过滤推荐*/
            Recommender recommender = new GenericBooleanPrefUserBasedRecommender(dataModel,userNeighborhood,similarity);
            long startTime = System.currentTimeMillis();
            /*推荐商品*/
            List<RecommendedItem> recommendedItemList = recommender.recommend(userId,howMany);

            List<Integer> carIds = new ArrayList<>();

            for (RecommendedItem recommendedItem: recommendedItemList){
                System.out.println("recommendedItem:"+recommendedItem);
                carIds.add((int) recommendedItem.getItemID());
            }
            System.out.println("推荐出来的 商品的 id 集合："+carIds);
            /*根据商品id 查询商品*/
            if (carIds !=null && carIds.size() > 0){
                List<Car> cars = new ArrayList<>();
                for (int i=0;i<carIds.size();i++){
                    //System.out.println(carIds.get(i));
                    Car car = carMapper.selectByCarId(carIds.get(i));
                    //System.out.println(car);
                    cars.add(car);
                }
                carList = cars;
                //System.out.println(carList);
            }else {
                carList = new ArrayList<>();
            }
            System.out.println("推荐数量是："+carList.size()+"耗时："+(System.currentTimeMillis()-startTime));
        } catch (TasteException e) {
            throw new RuntimeException(e);
        }
        return carList;
    }

    @Override
    public List<Car> getRecommentCarByCar(Integer userId, Integer carId, Integer howMany) {
        List<Car> carList = null;

        try {
            /*计算相似度，相似度的计算方式很多，采用基于皮尔逊相关性的相似度*/
            ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
            /*构建推荐器，基于物品的协同过滤推荐*/
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel,itemSimilarity);
            long startTime = System.currentTimeMillis();
            /*推荐商品*/
            List<RecommendedItem> recommendedItemList = recommender.recommendedBecause(userId,carId,howMany);

            List<Integer> carIds = new ArrayList<>();
            for (RecommendedItem recommendedItem : recommendedItemList){
                System.out.println("recommendedItm:"+recommendedItem);
                carIds.add((int) recommendedItem.getItemID());
            }
            System.out.println("推荐出来的 商品的 id集合：" +carIds);

            /*根据商品id 查询商品*/
            if (carIds != null && carIds.size() >0){
                List<Car> cars = new ArrayList<>();
                for (Integer carIdss : carIds){
                    Car car = carMapper.selectByCarId(carIdss);
                    cars.add(car);
                }
                carList = cars;
            }else {
                carList = new ArrayList<>();
            }
            System.out.println("推荐数量是：" + carList.size() + ",耗时：" + (System.currentTimeMillis()-startTime));

        } catch (TasteException e) {
            throw new RuntimeException(e);
        }


        return carList;
    }
}
