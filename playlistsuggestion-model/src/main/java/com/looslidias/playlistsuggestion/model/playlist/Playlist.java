package com.looslidias.playlistsuggestion.model.playlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 25/09/18
 */
@Entity
@Table(name = "playlist", indexes = {@Index(name = "playlist_genre_idx",  columnList="genre", unique = true)})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
    private String genre;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "playlist_id")
    private List<Track> tracks;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
