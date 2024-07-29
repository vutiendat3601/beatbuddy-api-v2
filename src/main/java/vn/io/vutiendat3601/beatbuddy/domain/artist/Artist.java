package vn.io.vutiendat3601.beatbuddy.domain.artist;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.io.vutiendat3601.beatbuddy.common.entity.AuditEntity;
import vn.io.vutiendat3601.beatbuddy.converter.StringSetConverter;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
    name = "artist",
    uniqueConstraints = {
      @UniqueConstraint(name = "tracks_id_key", columnNames = "id"),
      @UniqueConstraint(name = "tracks_ref_code_key", columnNames = "id"),
      @UniqueConstraint(name = "tracks_urn_key", columnNames = "id")
    })
public class Artist extends AuditEntity {
  @Id
  @Column(name = "pk_id", nullable = false, updatable = false)
  private UUID pkId;

  @Column(name = "id", nullable = false, updatable = false, length = 16)
  private String id;

  @Column(name = "urn", nullable = false, updatable = false)
  private String urn;

  @Column(name = "name", nullable = false)
  private String name;

  @Builder.Default
  @Column(name = "is_verified", nullable = false)
  private Boolean isVerified = false;

  @Builder.Default
  @Column(name = "is_public", nullable = false)
  private Boolean isPublic = true;

  @Column(name = "real_name")
  private String realName;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "description")
  private String description;

  @Column(name = "nationality")
  private String nationality;

  @Column(name = "biography")
  private String biography;

  @Column(name = "thumbnail")
  private String thumbnail;

  @Column(name = "background")
  private String background;

  @Builder.Default
  @Column(name = "tags", nullable = false)
  @Convert(converter = StringSetConverter.class)
  private Set<String> tags = new HashSet<>();

  @Column(name = "ref_code", updatable = false)
  private String refCode;

  @Builder.Default
  @Column(name = "total_likes", nullable = false)
  private Long totalLikes = 0L;

  @Builder.Default
  @Column(name = "total_views", nullable = false)
  private Long totalViews = 0L;
}
