package com.cchao.sleep.controller;

import com.cchao.sleep.constant.enums.Results;
import com.cchao.sleep.json.user.LoginResp;
import com.cchao.sleep.security.JWTUtil;
import com.cchao.sleep.dao.User;
import com.cchao.sleep.json.RespBean;
import com.cchao.sleep.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService mUserService;

    /**
     * login
     *
     * @param email     email
     * @param password password
     * @return suc
     */
    @RequestMapping(value = "/login")
    public RespBean login(@RequestParam String email, @RequestParam String password) {
        User user = mUserService.findUserByEmail(email);

        if (user.getPassword().equals(password)) {
            String token = JWTUtil.sign(email, user.getId(), password);

            LoginResp loginResp = new LoginResp();
            loginResp.setAge(user.getAge());
            loginResp.setAvatar(user.getNickname());
            loginResp.setEmail(user.getEmail());
            loginResp.setToken(token);

            return RespBean.suc(loginResp).setMsg("登录成功");
        } else {
            return RespBean.fail(Results.LOGIN_FAIL);
        }
    }

    /**
     * signUp
     *
     * @param email     name
     * @param password password
     */
    @RequestMapping(value = "/signup")
    public RespBean signUp(@RequestParam String email, @RequestParam String password) {
        User user = mUserService.signUp(email, password);
        String token = JWTUtil.sign(email, user.getId(), password);

        LoginResp loginResp = new LoginResp();
        loginResp.setAge(user.getAge());
        loginResp.setAvatar(user.getNickname());
        loginResp.setEmail(user.getEmail());
        loginResp.setToken(token);

        return RespBean.suc(loginResp).setMsg("注册成功");
    }

    /**
     * updateUserInfo
     */
    @RequestMapping(value = "/update")
    @RequiresAuthentication
    public RespBean update(@RequestParam Map<String, String> map, HttpServletRequest httpRequest) {

        String name = JWTUtil.getUsername(httpRequest);
        User user = mUserService.saveUserInfo(name, map);
        return RespBean.suc(user);
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public RespBean requireAuth() {
        return new RespBean(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public RespBean requireRole() {
        return new RespBean(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public RespBean requirePermission() {
        return new RespBean(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespBean unauthorized() {
        return new RespBean(401, "Unauthorized", null);
    }
}
