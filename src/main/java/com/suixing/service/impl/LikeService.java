package com.suixing.service.impl;

import com.suixing.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private JedisAdapter jedisAdapter;

    //判断是点赞还是点反对
    public int getLikeStatus(int userId,int entityId){
        //根据当前用户的userid分别生成一个likeKey 和 disLikeKey,再分别判断这两个值是否在对应的Like集合中和disLikeKey集合中
        //比如如果在likeKey集合中，就返回一个1，否则返回-1
        String likeKey = RedisKeyUtil.getLikeKey(entityId);
        if (jedisAdapter.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId);
        return jedisAdapter.sismember(disLikeKey,String.valueOf(userId)) ? -1 : 0;
    }

    //点赞：即当前用户点赞后，被点赞用户的like集合中就会加上一个该点赞的用户信息
    public long like(int userId,int entityId){
        //在当前news上点赞后获取key:   LIKE:2
        String likeKey = RedisKeyUtil.getLikeKey(entityId);
        //在喜欢集合中添加当前操作用户的userId(即当前用户点赞后，被点赞用户的like集合中就会加上一个点赞的用户信息)
        jedisAdapter.sadd(likeKey,String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId);
        jedisAdapter.srem(disLikeKey,String.valueOf(userId));
        //返回点赞数量
        return jedisAdapter.scard(likeKey);
    }

    //反对 ：即当前用户点反对后，被点反对用户的like集合中就会加上一个该点反对的用户信息
    public long disLike(int useId,int entityId){

        //谁点击反对，谁就出现在key为dislikeKey的Set集合中
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId);
        jedisAdapter.sadd(disLikeKey,String.valueOf(useId));

        //从赞中删除
        String likeKey = RedisKeyUtil.getLikeKey(entityId);
        jedisAdapter.srem(likeKey,String.valueOf(useId));

        return jedisAdapter.scard(likeKey);

    }

    //获得该物品被点赞的总数
    public long likeCount(int entityId){
        //在当前news上点赞后获取key:   LIKE:2
        String likeKey = RedisKeyUtil.getLikeKey(entityId);

        return jedisAdapter.scard(likeKey);

    }



}
