package com.g.customer.service;

import com.g.commons.base.entity.vo.result.Result;
import com.g.customer.entity.vo.CartVo;

import java.util.List;

/**
 * @Author: Gtf
 * @Date: 2022/4/22-04-22-20:05
 * @Description: com.g.customer.service
 * @Version: 1.0
 */
public interface CartService {
    /**
     * 添加商品到购物车
     * @param shoppingCart
     * @return
     */
    Result addCart(CartVo shoppingCart);

    /**
     * 更新购物车内商品具体信息
     * @param shoppingCart
     * @return
     */
    Result updateCart(List<CartVo> shoppingCart);

    /**
     * 清空购物车
     * @return
     */
    Result clearCartList();

    /**
     * 获取购物车内所有商品信息
     * @return
     */
    Result getCartList();
}
