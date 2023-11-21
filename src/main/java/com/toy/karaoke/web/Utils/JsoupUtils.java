package com.toy.karaoke.web.Utils;

import com.toy.karaoke.web.entity.AnimeNameForm;
import com.toy.karaoke.web.entity.SongForm;
import com.toy.karaoke.web.entity.SongToDatabaseForm;
import com.toy.karaoke.web.entity.SongWrapper;
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

      return thisMonth;
   }

   /**
    * DB에 **애니** 노래를 추가 하기 위한 메서드.
    * @return dbSongList
    */
   public static List<SongWrapper> animeDBSongList(){
      List<SongWrapper> songwrapperList = new ArrayList<>();
      // url
      String url = "https://namu.wiki/w/%EC%95%A0%EB%8B%88%EB%A9%94%EC%9D%B4%EC%85%98%20%EC%9D%8C%EC%95%85/%EB%85%B8%EB%9E%98%EB%B0%A9%20%EC%88%98%EB%A1%9D%20%EB%AA%A9%EB%A1%9D/%EC%A0%84%EC%B2%B4%EA%B3%A1%20%EC%9D%BC%EB%9E%8C";
      // css 선택자(tr)
      String tables = "table.TiHaw-AK";
      String selector = ".TiHaw-AK tbody tr";


      Document doc = null;
      // doc connect
      try {
         doc = org.jsoup.Jsoup.connect(url).get();
      } catch (IOException e) {
         e.printStackTrace();
      }

      // 3번째 테이블을 가져오고 싶음. (클래스명이 동일하기에 요소화시켜서 가져오는 방법 채택)
      Elements getTables = doc.select(tables);
      for(int i = 2; i < 16; i++) {
         Element table = getTables.get(i);
         Elements animeSongTr = table.select("tr");

         // 가져온 tr중 td를 하나씩 분해
         for (Element element : animeSongTr) {
            String texts = element.text();
            Elements tds = element.select("td");
            if (tds.size() >= 3) {
               SongWrapper dbAnimeSongObj = new SongWrapper();
               dbAnimeSongObj.setSongToDatabaseForm(new SongToDatabaseForm());
               dbAnimeSongObj.getSongToDatabaseForm().setStdbfTjNumber(tds.get(0).text());
               dbAnimeSongObj.getSongToDatabaseForm().setStdbfKyNumber(tds.get(1).text());
               dbAnimeSongObj.getSongToDatabaseForm().setStdbfSongName(tds.get(2).text());
               dbAnimeSongObj.getSongToDatabaseForm().setStdbfSongSinger(tds.get(3).text());
               if(tds.get(2).text().equals("곡 제목")){
                  dbAnimeSongObj.setSongToDatabaseForm(null);
               }
               songwrapperList.add(dbAnimeSongObj);
            } else if(tds.size() == 2) {
               SongWrapper animeNameForm = new SongWrapper();
               animeNameForm.setAnimeNameForm(new AnimeNameForm());
               animeNameForm.getAnimeNameForm().setAnfAnimeName(tds.get(0).text());
               animeNameForm.getAnimeNameForm().setAnfSingerNumber(tds.get(1).text());

               songwrapperList.add(animeNameForm);
            } else if(tds.size() == 1){
               SongWrapper animeNameForm = new SongWrapper();
               animeNameForm.setAnimeNameForm(new AnimeNameForm());
               animeNameForm.getAnimeNameForm().setAnfAnimeName(tds.get(0).text());
            }
         }
      }
      return songwrapperList;
   }
}