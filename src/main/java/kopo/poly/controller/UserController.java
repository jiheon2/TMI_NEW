package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping(value = "/user")
public class UserController {
    @RequestMapping(value = "/login")
    public String login() {
        log.info("start!");
        return "/user/login";
    }

    @RequestMapping(value = "/userIndex")
    public String userIndex() {
        log.info("start!");
        return "/user/userIndex";
    }

    @RequestMapping(value = "/cart")
    public String cart() {
        log.info("start!");
        return "/user/cart";
    }

    @RequestMapping(value = "/userSignUp")
    public String userSignUp() {
        log.info("start!");
        return "/user/userSignUp";
    }

    @RequestMapping(value = "/shop")
    public String shop() {
        log.info("start!");
        return "/user/shop";
    }

    @RequestMapping(value = "/map")
    public String map() {
        log.info("start!");
        return "/user/map";
    }

    @RequestMapping(value = "/market")
    public String market() {
        log.info("start!");
        return "/user/market";
    }

    @RequestMapping(value = "/chat")
    public String chat() {
        log.info("start!");
        return "/user/chat";
    }

    @RequestMapping(value = "/single-product")
    public String singleProduct() {
        log.info("start!");
        return "/user/single-product";
    }
}
