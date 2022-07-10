package com.g.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.g.commons.base.entity.dto.UserForm;
import com.g.commons.base.entity.dto.UserInfo;
import com.g.commons.base.entity.po.User;
import com.g.commons.base.entity.vo.result.Result;
import com.g.oauth.entity.UserDetailsDto;

/**
 * @Author: Gtf
 * @Date: 2022/5/2-05-02-19:10
 * @Description: com.g.auth.service.usertest
 * @Version: 1.0
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User getUserByName(String username);

    User getUserByNum(String num);

    User getUserByWeChat(String oid);

    UserDetailsDto getUserDetailsByName(String name);

    UserDetailsDto getUserDetailsByNum(String num);

    UserDetailsDto getUserDetailsByWechat(String openId);

    Result registerUser(UserForm userForm);

    Result registerUserByOid(User user);

    Result registerUserByNum(String num);

    Result deleteUser(String name);

    Result updateUser(UserInfo userInfo);
}
