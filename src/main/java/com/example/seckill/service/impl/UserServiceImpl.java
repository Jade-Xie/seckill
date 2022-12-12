package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.Exception.GlobalException;
import com.example.seckill.mapper.UserMapper;
import com.example.seckill.pojo.User;
import com.example.seckill.service.IUserService;
import com.example.seckill.utils.CookieUtil;
import com.example.seckill.utils.MD5Util;
import com.example.seckill.utils.MobileValidationUtil;
import com.example.seckill.utils.UUIDUtil;
import com.example.seckill.vo.LoginVo;
import com.example.seckill.vo.RespBean;
import com.example.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xieys
 * @since 2022-12-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest req, HttpServletResponse resp) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//        //判断是否为空
//        if(StringUtils.isEmpty(mobile) && StringUtils.isEmpty(password))
//        {
//            return RespBean.error((RespBeanEnum.LOGIN_ERROR));
//        }
//        //手机号校验
//        if(!MobileValidationUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        //查数据库
        User user = userMapper.selectById(mobile);
        if(null == user){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //将前端传来的一次加密的密码进行二次加密，再和数据库比对
        if(!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        String ticket = UUIDUtil.uuid();

//        1.利用SpringSession存登录信息
//        req.getSession().setAttribute(ticket, user);

//        2.信息放入redis
        redisTemplate.opsForValue().set("user:"+ticket, user);

        CookieUtil.setCookie(req, resp, "userTicket", ticket);
        return RespBean.success(ticket);
    }

    @Override
    public User getUserByCookie(String ticket, HttpServletRequest req, HttpServletResponse resp) {
        if(StringUtils.isEmpty(ticket)) return null;

        User user = (User) redisTemplate.opsForValue().get("user:"+ticket);

        if (user != null){
            CookieUtil.setCookie(req, resp, "userTicket", ticket);
        }

        return user;


    }

}
