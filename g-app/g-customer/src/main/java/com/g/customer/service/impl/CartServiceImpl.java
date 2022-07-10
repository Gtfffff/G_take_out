package com.g.customer.service.impl;


import com.g.commons.base.entity.vo.result.Result;
import com.g.customer.entity.vo.CartVo;
import com.g.customer.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Gtf
 * @Date: 2022/4/22-04-22-20:05
 * @Description: com.g.customer.service.impl
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {


//    private final ShoppingCartService shoppingCartService;

//    private final CacheChannel cacheChannel;

    @Override
    public Result addCart(CartVo shoppingCart) {
        //完善用户信息

        //区分是菜品还是套餐

        //查询当前菜品或者套餐是否在购物车中

        //缓存信息

        return null;
    }

    @Override
    public Result updateCart(List<CartVo> shoppingCart) {
        //获取用户id

        //将缓存中相关的商品删除

        //将新的商品加入缓存

        return null;
    }

    @Override
    public Result clearCartList() {
        //获取用户id

        //将缓存中的相关商品删除
        return null;
    }

    @Override
    public Result getCartList() {
        //获取用户id

        //将缓存中的相关商品返回

        //如果缓存中不存在从数据库中查找
        return null;
    }

}
