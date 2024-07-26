package vn.io.vutiendat3601.beatbuddy.common.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@SuperBuilder
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@MappedSuperclass
public abstract class AuditEntity {
  @CreatedDate protected ZonedDateTime createdAt;

  @LastModifiedDate protected ZonedDateTime updatedAt;

  @CreatedBy protected String createdBy;

  @LastModifiedBy protected String updatedBy;
}
