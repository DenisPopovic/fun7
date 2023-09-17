package com.outfit7.fun7.model.database;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Fun7DatabaseModel {

  @Id
  @Column(name = "uuid", unique = true, nullable = false, updatable = false)
  private UUID uuid = UUID.randomUUID();

  @Column(name = "created", nullable = false, updatable = false)
  private Instant created = Instant.now();

  @Column(name = "modified", nullable = false)
  private Instant modified = Instant.now();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Fun7DatabaseModel that = (Fun7DatabaseModel) o;

    return uuid.equals(that.getUuid());
  }

  @Override
  public int hashCode() {
    return uuid.hashCode();
  }
}
