package com.looslidias.playlistsuggestion.model.playlist;

import com.looslidias.playlistsuggestion.model.converter.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 25/09/18
 */
@Entity
@Table(name = "track", indexes = {@Index(name = "track_name_idx",  columnList="name", unique = true)})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Track {
    @Id
    private String name;
    @Column
    private String url;
    @Column
    private Float popularity;
    @Convert(converter = StringListConverter.class)
    private List<String> artists;
}
