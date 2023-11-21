package com.toy.karaoke.web.entity;

import lombok.Data;

@Data
public class SongToDatabaseForm {
    private String stdbfTjNumber;
    private String stdbfKyNumber;
    private String stdbfSongName;
    private String stdbfSongSinger;
}
