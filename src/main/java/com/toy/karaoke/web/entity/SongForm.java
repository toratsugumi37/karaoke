package com.toy.karaoke.web.entity;

import lombok.Data;

@Data
public class SongForm {
   private String songNumber;
   private String songName;
   private String songSinger;
   private String songLyricist;
   private String songComposer;
}
