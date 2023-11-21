package com.toy.karaoke.web.Controller;

import com.toy.karaoke.web.Utils.JsoupUtils;
import com.toy.karaoke.web.entity.SongForm;
import com.toy.karaoke.web.entity.SongToDatabaseForm;
import com.toy.karaoke.web.entity.SongWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/new")
    public String main(Model model){
        List<SongForm> songForm = new ArrayList<>();
        songForm = JsoupUtils.getSongs();
        String thisMonth = JsoupUtils.getMonth();
        model.addAttribute("thisMonth",thisMonth);
        model.addAttribute("songList",songForm);
        return "main";
    }

    @GetMapping("/DB")
    public String animeDB(Model model){
        List<SongWrapper> animeSongDB = new ArrayList<>();
        animeSongDB = JsoupUtils.animeDBSongList();
        model.addAttribute("animeSong",animeSongDB);

        return "DB";
    }
}
