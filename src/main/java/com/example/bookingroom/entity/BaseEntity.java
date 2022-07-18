package com.example.bookingroom.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Basic Entity for class entity to extend.
 * createdBy, modifiedBy : execute by (user?)
 * createAt, modifyAt: time that execute
 * version(optlock): check current version, only allow if true, otherwise throw OptimisticLockException
 * @see <a href="https://www.baeldung.com/jpa-optimistic-locking">jpa optimistic locking</a>
 */
@MappedSuperclass
@Getter
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    public LocalDateTime modifyAt;

    @Version
    @Column(name = "optlock", columnDefinition = "bigint DEFAULT 0", nullable = false)
    private Long version = 0L;

    @PrePersist
    public void setCreationDate() {
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setModifyDate() {
        this.modifyAt = LocalDateTime.now();
    }
}
