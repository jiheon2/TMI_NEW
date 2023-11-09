package kopo.poly.controller;

import kopo.poly.util.WebDriverUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CrawlingController {
    @GetMapping(value = "/crawling")
    public void crawling() {
        WebDriverUtil webDriverUtil = new WebDriverUtil();
        webDriverUtil.useDriver("https://www.youtube.com/c/youtubekorea/videos");
    }

}
