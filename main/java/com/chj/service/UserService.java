package com.chj.service;

import com.chj.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张三
 * @since 2020-07-25
 */
public interface UserService extends IService<User> {
    public User login(User user);
}
