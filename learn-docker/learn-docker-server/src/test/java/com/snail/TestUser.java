package com.snail;

import com.snail.entity.Tbuser;
import com.snail.service.UserService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

public class TestUser extends DockerServerApplicationTest {
    @Resource
    private UserService userService;

    @Test
    public void testQueryUsers() {
        List<Tbuser> tbusers = this.userService.queryUsers();
    }
}