package vn.io.vutiendat3601.beatbuddy.domain.playlist;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.io.vutiendat3601.beatbuddy.common.entity.AuditEntity;
import vn.io.vutiendat3601.beatbuddy.converter.StringListConverter;
import vn.io.vutiendat3601.beatbuddy.converter.StringSetConverter;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "playlist")
public class Playlist extends AuditEntity {
  @Id
  @GeneratedValue(generator = "pg-uuid")
  @Column(name = "pk_id", nullable = false, updatable = false)
  private UUID pkId;

  @Column(name = "id", nullable = false, updatable = false, length = 16)
  private String id;

  @Column(name = "urn", nullable = false, updatable = false, length = 16)
  private String urn;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "thumbnail")
  private String thumbnail;

  @Column(name = "description")
  private String description;

  @Builder.Default
  @Column(name = "is_public", nullable = false)
  private Boolean isPublic = true;

  @Column(name = "owner_id", nullable = false, updatable = false)
  private String ownerId;

  @Column(name = "ref_code")
  private String refCode;

  @Builder.Default
  @Column(name = "tags", nullable = false)
  @Convert(converter = StringSetConverter.class)
  private Set<String> tags = new HashSet<>();

  @Builder.Default
  @Column(name = "item_urns", nullable = false)
  @Convert(converter = StringListConverter.class)
  private List<String> itemUrns = new LinkedList<>();
}
