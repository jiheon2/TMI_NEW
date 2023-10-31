package kopo.poly.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Slf4j
@Controller
@RequestMapping(value = "/chat")
public class ChatController {
    @GetMapping(value="intro")
    public String intro() {
        log.info(this.getClass().getName() + ".intro Start!");

        log.info(this.getClass().getName() + ".intro End!");

        return "/chat/intro";
    }

    @PostMapping(value="chatroom")
    public String chatroom() {
        log.info(this.getClass().getName() + ".chatroom Start!");

        log.info(this.getClass().getName() + ".chatroom End!");

        return "/chat/chatroom";
    }

    @RequestMapping(value = "roomList")
    @ResponseBody
    public Set<String> roomList() {
        log.info(this.getClass().getName() + ".roomList Start!");

        log.info(this.getClass().getName() + ".roomList End!");

        return ChatHandler.roomInfo.keySet();
    }
}
