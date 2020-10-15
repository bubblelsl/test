package com.edu.rest.service.Impl;

import com.edu.bean.TbItem;
import com.edu.bean.TbItemDesc;
import com.edu.bean.TbItemParamItem;
import com.edu.bean.TbItemParamItemExample;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.JsonUtils;
import com.edu.mapper.TbItemDescMapper;
import com.edu.mapper.TbItemMapper;
import com.edu.mapper.TbItemParamItemMapper;
import com.edu.rest.dao.RedisDao;
import com.edu.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY ;
    @Value("${REDIS_ITEM_EXPIRE}")
    private int REDIS_ITEM_EXPIRE;
    @Override
    public ShoppingResult getInfo(long itemId) {
        TbItem tbItem = null;
        // 我们要做缓存
        // 1. 从缓存取
        try {
            // 取的时候，我们根据key，去取value的值
            // key 是唯一的
            String infoItem = redisDao.get(REDIS_ITEM_KEY+":"+itemId+":INFO") ;
            if(!StringUtils.isEmpty(infoItem)) {
                // 把json格式的字符串，转换成TbItem对象
                tbItem = JsonUtils.jsonToPojo(infoItem,TbItem.class);
                return ShoppingResult.ok(tbItem);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        // 2.没有取到从数据库中去取，取到了向缓存中存放一份
        tbItem = itemMapper.selectByPrimaryKey(itemId);
        try {
            // 放入到缓存中
            redisDao.set(REDIS_ITEM_KEY+":"+itemId+":INFO",JsonUtils.objectToJson(tbItem));
            // 设置缓存的过期时间
            redisDao.expire(REDIS_ITEM_KEY+":"+itemId+":INFO",REDIS_ITEM_EXPIRE);
            return ShoppingResult.ok(tbItem);
        }catch (Exception e) {
            e.printStackTrace();
            return ShoppingResult.build(500,"出错了.....");
        }
    }

    @Override
    public ShoppingResult getDesc(long itemId) {
        TbItemDesc tbItemDesc = null;
        // 我们要做缓存
        // 1. 从缓存取
        try {
            // 取的时候，我们根据key，去取value的值
            // key 是唯一的
            String infoItem = redisDao.get(REDIS_ITEM_KEY+":"+itemId+":DESC") ;
            if(!StringUtils.isEmpty(infoItem)) {
                // 把json格式的字符串，转换成TbItem对象
                tbItemDesc = JsonUtils.jsonToPojo(infoItem,TbItemDesc.class);
                return ShoppingResult.ok(tbItemDesc);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        // 2.没有取到从数据库中去取，取到了向缓存中存放一份
        tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        try {
            // 放入到缓存中
            redisDao.set(REDIS_ITEM_KEY+":"+itemId+":DESC",JsonUtils.objectToJson(tbItemDesc));
            // 设置缓存的过期时间
            redisDao.expire(REDIS_ITEM_KEY+":"+itemId+":DESC",REDIS_ITEM_EXPIRE);
            return ShoppingResult.ok(tbItemDesc);
        }catch (Exception e) {
            e.printStackTrace();
            return ShoppingResult.build(500,"出错了.....");
        }
    }

    @Override
    public ShoppingResult getParam(long itemId) {
        TbItemParamItem tbItemParamItem = null;
        // 我们要做缓存
        // 1. 从缓存取
        try {
            // 取的时候，我们根据key，去取value的值
            // key 是唯一的
            String infoItem = redisDao.get(REDIS_ITEM_KEY+":"+itemId+":PARAM") ;
            if(!StringUtils.isEmpty(infoItem)) {
                // 把json格式的字符串，转换成TbItem对象
                tbItemParamItem = JsonUtils.jsonToPojo(infoItem,TbItemParamItem.class);
                return ShoppingResult.ok(tbItemParamItem);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        // 2.没有取到从数据库中去取，取到了向缓存中存放一份
        TbItemParamItemExample example = new TbItemParamItemExample() ;
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItems = itemParamItemMapper.selectByExampleWithBLOBs(example);
        try {
            if(null != tbItemParamItems && tbItemParamItems.size() > 0) {
                tbItemParamItem = tbItemParamItems.get(0);
                // 放入到缓存中
                redisDao.set(REDIS_ITEM_KEY+":"+itemId+":PARAM",JsonUtils.objectToJson(tbItemParamItem));
                // 设置缓存的过期时间
                redisDao.expire(REDIS_ITEM_KEY+":"+itemId+":PARAM",REDIS_ITEM_EXPIRE);
                return ShoppingResult.ok(tbItemParamItem);
            }

        }catch (Exception e) {
            e.printStackTrace();
            return ShoppingResult.build(500,"出错了.....");
        }
        return ShoppingResult.build(500,"出错了.....");
    }
}
