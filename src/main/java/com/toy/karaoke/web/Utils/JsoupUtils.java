package com.toy.karaoke.web.Utils;

import com.toy.karaoke.web.entity.SongForm;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsoupUtils {

   public static boolean isJapaneseSong(String s) {
      for (char c : s.toCharArray()) {
         if ((c >= 0x4E00 && c <= 0x9FFF) //한중일 통합 한자 유니코드
               || (c >= 0x3040 && c <= 0x309F)  // 히라가나 유니코드
               || (c >= 0x30A0 && c <= 0x30FF)) { // 가타카나 유니코드
            //중국어 한자 제외 코드
            if (!(c >= 0x3400 && c <= 0x4DBF)
                  && !(c >= 0x20000 && c <= 0x2A6DF)) {
               return true;
            }
         }
      }
      return false;
   }

   public static List<SongForm> getSongs() {
      log.info("탔음");
      String url = "https://www.tjmedia.com/tjsong/song_monthNew.asp";
      String selector = "div#BoardType1 table.board_type1 tbody tr";
      Document doc = null;

      try {
         doc = org.jsoup.Jsoup.connect(url).get();
      } catch (IOException e) {
         e.printStackTrace();
      }

      Elements songs = doc.select(selector);

      List<SongForm> songFormList = new ArrayList<>();

      for (Element element : songs) {
         String texts = element.text();
         Elements tds = element.select("td");
         if (tds.size() > 0) {
            // 곡 번호를 가져온다.
            String songNumber = tds.get(0).text();
            // 곡 번호중 첫번째를 가져온다.
            String is6 = songNumber.substring(0, 1);
            // 만약 곡 번호중 첫번째가 1이고, 일본어(히라가나,가타카나,한중일통합한자)라면 가져온다.
            if (is6.startsWith("6")) {
               if (isJapaneseSong(texts)) {
                  SongForm songFormObj = new SongForm();
                  songFormObj.setSongNumber(tds.get(0).text());
                  songFormObj.setSongName(tds.get(1).text());
                  songFormObj.setSongSinger(tds.get(2).text());
                  songFormObj.setSongLyricist(tds.get(3).text());
                  songFormObj.setSongComposer(tds.get(4).text());
                  songFormList.add(songFormObj);
               }
            }

         }

      }
      log.info("songFormList={}",songFormList);
      return songFormList;
   }


   public static String getMonth(){
      // 주소
      String url = "https://www.tjmedia.com/tjsong/song_monthNew.asp";
      // css선택자
      String selector = "#boardTitle1 ul";
      Document doc = null;
      // doc connect
      try {
         doc = org.jsoup.Jsoup.connect(url).get();
      } catch (IOException e) {
         e.printStackTrace();
      }

      Elements month = doc.select(selector);
      // 이번달을 가져온다.
      String thisMonth = month.text();
      log.info("thisMonth={}",thisMonth);

      return thisMonth;
   }
}