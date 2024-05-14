package com.neueda.shorty.model;

import com.neueda.shorty.util.UrlStatus;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.*;
import java.util.Date;

@Data @RequiredArgsConstructor
@Entity
@Table(name = "shorty_url")
public class Url {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "SHORT_ID", nullable = false)
    @NonNull private Long shortId;

    @Column(name = "SHORT_URL", nullable = false)
    @NonNull private String shortUrl;

    @Column(name = "LONG_URL", nullable = false)
    @NonNull private String longUrl;

    @Column(name = "STATUS")
    private String status = UrlStatus.CREATED.getStatus();

    @Column(name = "REQUESTS")
    private Integer requests = 0;

    @Column(name = "LAST_REQUEST")
    private Date requested;

    @Column(name = "CREATED_DATE")
    private Date created = new Date();

    @Column(name = "CREATED_BY")
    private Long createdBy;

    public Url(){}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Url{");
        builder.append("id=" + id);
        builder.append(" shortUrl=" + shortUrl);
        builder.append(" longUrl=" + longUrl);
        builder.append(", status=" + status);
        builder.append(", created=" + created);
        builder.append(", lastRequest=" + requested);
        builder.append(", numberOfRequests=" + requests);
        builder.append("}");
        return builder.toString();
    }
}
