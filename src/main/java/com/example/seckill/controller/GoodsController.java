package com.example.seckill.controller;


import com.example.seckill.pojo.User;
import com.example.seckill.service.IGoodsService;
import com.example.seckill.service.IUserService;
import com.example.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IGoodsService iGoodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 跳转到商品列表页
     * windows优化前qps 1485   linux 优化前 435
     * @param model
     * @return
     */
//    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @RequestMapping(value = "/toList")
    @ResponseBody
    public String toList(Model model, User user){

        model.addAttribute("user", user);
        model.addAttribute("goodsList", iGoodsService.findGoodsVo());

        return "goodsList";
    }

    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable Long goodsId){
        model.addAttribute("user", user);
        GoodsVo goodsVo = iGoodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();

        //秒杀状态
        int secKillStatus = 0;
        int remainSeconds = 0;
        //1.秒杀未开始
        if(nowDate.before(startDate)){
            secKillStatus = 0;
            //计算倒计时
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
        }
        //2.秒杀已结束
        else if(nowDate.after(endDate)){
            secKillStatus = 2;
            remainSeconds = -1;

        }
        else
        {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("goods", goodsVo);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("secKillStatus", secKillStatus);

        return "goodsDetail";
    }
}
