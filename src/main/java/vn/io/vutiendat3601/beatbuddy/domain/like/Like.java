package vn.io.vutiendat3601.beatbuddy.domain.like;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.HashSet;
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
import vn.io.vutiendat3601.beatbuddy.converter.StringSetConverter;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "likes")
public class Like extends AuditEntity {
  @Id
  @GeneratedValue(generator = "pg-uuid")
  @Column(name = "pk_id", nullable = false, updatable = false)
  private UUID pkId;

  private String ownerId;

  @Builder.Default
  @Convert(converter = StringSetConverter.class)
  private Set<String> urns = new HashSet<>();
}
