package com.edu.sso.service.impl;

import com.edu.bean.TbUser;
import com.edu.bean.TbUserExample;
import com.edu.common.bean.ShoppingResult;
import com.edu.common.util.CookieUtils;
import com.edu.common.util.JsonUtils;
import com.edu.mapper.TbUserMapper;
import com.edu.sso.dao.JedisClientDao;
import com.edu.sso.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClientDao clientDao ;
    @Value("${REDIS_SESSION_TOKEN_KEY}")
    private String REDIS_SESSION_TOKEN_KEY;
    @Value("${REDIS_SESSION_EXPIRE}")
    private Integer REDIS_SESSION_EXPIRE;

    /**
     * 可选参数1、2、3分别代表username、phone、email
     * @param param
     * @param type
     * @return
     */
    @Override
    public ShoppingResult getCheckData(String param, Integer type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if(1 == type) {
            criteria.andUsernameEqualTo(param);
        } else if(2 == type) {
            criteria.andPhoneEqualTo(param);
        } else if(3 == type) {
            criteria.andEmailEqualTo(param);
        }
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if(null != tbUsers && tbUsers.size()>0) {
            // 说明存在这个用户，存在这个用户的话，data:false
            return ShoppingResult.ok(false);
        }
        return ShoppingResult.ok(true);
    }

    @Override
    public ShoppingResult insertUser(TbUser user) {
        try {
            // 补全数据
            user.setCreated(new Date());
            user.setUpdated(new Date());
            // 密码md5加密
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            userMapper.insertSelective(user) ;
            return ShoppingResult.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
          return ShoppingResult.build(400,"注册失败，请重新注册");
        }
    }

    @Override
    public ShoppingResult getLoginByNameAndPwd(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        TbUserExample example = new TbUserExample() ;
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if(null == tbUsers || tbUsers.size() == 0) {
            return ShoppingResult.build(400,"当前用户名或者密码不存在...");
        }
        // 如果不为空,则判断密码是否一致
        TbUser tbUser = tbUsers.get(0) ;
        if(!tbUser.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            // 如果密码不一致
            return ShoppingResult.build(500,"当前用户名或者密码不存在...") ;
        }
        // 密码一致的情况下，则产生一个token
        String token = UUID.randomUUID().toString();
        // 把token 保存到redis中，并设置session 中redis的过期时间
        // 保存数据的时候最好不要把密码保持进去
        tbUser.setPassword(null);
        clientDao.set(REDIS_SESSION_TOKEN_KEY + ":" + token, JsonUtils.objectToJson(tbUser));
        clientDao.expire(REDIS_SESSION_TOKEN_KEY + ":" + token,REDIS_SESSION_EXPIRE) ;
        // 把token写入到cookie中
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);
        return ShoppingResult.ok(token);
    }

    @Override
    public ShoppingResult getUserByToken(String token) {
        String str = clientDao.get(REDIS_SESSION_TOKEN_KEY + ":" + token) ;
        if(StringUtils.isEmpty(str)) {
            return ShoppingResult.build(400,"session已经过期...");
        }
        // 重新设置session的过期时间
        clientDao.expire(REDIS_SESSION_TOKEN_KEY + ":" + token,REDIS_SESSION_EXPIRE) ;
        return ShoppingResult.ok(JsonUtils.jsonToPojo(str,TbUser.class));
    }

    @Override
    public ShoppingResult getLoginOut(String token) {
        clientDao.expire(REDIS_SESSION_TOKEN_KEY + ":" + token,0) ;
        return ShoppingResult.ok();
    }

}

