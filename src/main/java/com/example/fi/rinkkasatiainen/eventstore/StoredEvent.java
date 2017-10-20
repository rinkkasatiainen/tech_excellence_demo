package com.example.fi.rinkkasatiainen.eventstore;

import com.example.fi.rinkkasatiainen.util.Struct;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "eventstore")
public class StoredEvent implements Serializable, Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "least_significant_bits")
    private Long leastSignificantBits;

    @NotNull
    @Column(name = "most_significant_bits")
    private Long mostSignificantBits;

    @NotNull
    @Column(name = "metadata")
    @Size(max = 255)
    private String metadata;

    @NotNull
    @Column(name = "data")
    @Size(max = 255)
    private String data;

    @NotNull
    @Column(name = "version")
    private Long version;

    @Column(name = "timestamp", insertable = false, updatable = false, columnDefinition="DATETIME  DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;


    public StoredEvent() {
    }

    public StoredEvent(UUID uuid, String metadata, String data, long version) {
        this.leastSignificantBits = uuid.getLeastSignificantBits();
        this.mostSignificantBits = uuid.getMostSignificantBits();
        this.metadata = metadata;
        this.data = data;
        this.version = version;
    }

    public UUID getUuid() {
        return new UUID( this.mostSignificantBits, this.leastSignificantBits );
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setLeastSignificantBits(Long leastSignificantBits) {
        this.leastSignificantBits = leastSignificantBits;
    }

    public void setMostSignificantBits(Long mostSignificantBits) {
        this.mostSignificantBits = mostSignificantBits;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Object o) {
        return  this.id.compareTo( ((StoredEvent) o).id );
    }

    @Override
    public boolean equals(Object o) {
        return new Struct.ForClass(this).equals( o );
    }

    @Override
    public int hashCode() {
        return new Struct.ForClass(this).hashCode();
    }

    @Override
    public String toString() {
        return new Struct.ForClass(this).toString();
    }
}
