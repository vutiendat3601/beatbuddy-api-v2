package vn.io.vutiendat3601.beatbuddy.domain.track;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.io.vutiendat3601.beatbuddy.common.entity.AuditEntity;
import vn.io.vutiendat3601.beatbuddy.domain.artist.Artist;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(
    name = "track",
    uniqueConstraints = {
      @UniqueConstraint(name = "tracks_id_key", columnNames = "id"),
      @UniqueConstraint(name = "tracks_ref_code_key", columnNames = "id"),
      @UniqueConstraint(name = "tracks_urn_key", columnNames = "id")
    })
public class Track extends AuditEntity {
  @Id
  @GeneratedValue(generator = "pg-uuid")
  private UUID pkId;

  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  @Column(name = "urn", nullable = false, updatable = false)
  private String urn;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "duration_sec")
  private Integer durationSec;

  @Column(name = "description")
  private String description;

  @Column(name = "released_date")
  private String releasedDate;

  @Column(name = "thumbnail")
  private String thumbnail;

  @Builder.Default
  @Column(name = "is_public", nullable = false)
  private Boolean isPublic = false;

  @Builder.Default
  @Column(name = "is_playable", nullable = false)
  private Boolean isPlayable = false;

  @Builder.Default
  @Column(name = "tags", nullable = false)
  private String tags = "";

  @Column(name = "ref_code", updatable = false)
  private String refCode;

  @Column(name = "file_m3u8")
  private String fileM3u8;

  @Column(name = "total_likes", nullable = false)
  @Builder.Default
  private Long totalLikes = 0L;

  @Builder.Default
  @Column(name = "total_views", nullable = false)
  private Long totalViews = 0L;

  @Builder.Default
  @Column(name = "total_listens", nullable = false)
  private Long totalListens = 0L;

  @Builder.Default
  @ManyToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      fetch = FetchType.EAGER)
  @JoinTable(
      name = "track_artist",
      joinColumns = @JoinColumn(name = "track_pk_id"),
      inverseJoinColumns = @JoinColumn(name = "artist_pk_id"))
  private List<Artist> artists = new LinkedList<>();
}
