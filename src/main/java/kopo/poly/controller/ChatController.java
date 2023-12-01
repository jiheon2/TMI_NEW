package kopo.poly.controller;

import kopo.poly.chat.ChatHandler;
import kopo.poly.dto.MarketDTO;
import kopo.poly.service.IMarketService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
public class ChatController {
    private final IMarketService marketService;
    @GetMapping(value="intro")
    public String intro(Model model) throws Exception {
        log.info(this.getClass().getName() + ".intro Start!");

        List<MarketDTO> rList = marketService.getMarketName();

        log.info(rList.toString());

        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".intro End!");

        return "/chat/intro";
    }

    @PostMapping(value="chatroom")
    public String chatroom(HttpServletRequest request, Model model) {
        log.info(this.getClass().getName() + ".chatroom Start!");

        String roomName = CmmUtil.nvl(request.getParameter("marketName"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));

        log.info("roomName : " + roomName);
        log.info("userName : " + userName);

        model.addAttribute("roomName", roomName);
        model.addAttribute("userName", userName);

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