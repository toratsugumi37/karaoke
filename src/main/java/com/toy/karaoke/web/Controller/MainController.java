package com.toy.karaoke.web.Controller;

import com.toy.karaoke.web.Utils.JsoupUtils;
import com.toy.karaoke.web.entity.SongForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MainController {
    @GetMapping("/new")
    public String main(Model model){
        List<SongForm> songForm = new ArrayList<>();
        songForm = JsoupUtils.getSongs();
        String thisMonth = JsoupUtils.getMonth();
        model.addAttribute("thisMonth",thisMonth);
        model.addAttribute("songList",songForm);
        return "main";
    }
}
