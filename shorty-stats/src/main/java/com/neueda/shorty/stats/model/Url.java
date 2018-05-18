package com.neueda.shorty.stats.model;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data @RequiredArgsConstructor
@Entity
public class Url {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NonNull private String shortUrl;

    @NonNull private String longUrl;

    private String status;

    private Integer requests = 0;

    private Date created = new Date();

    private Date requested;

    public Url(){}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Url{");
        builder.append("id=" + id);
        builder.append(" shortUrl='" + shortUrl);
        builder.append(" longUrl='" + longUrl);
        builder.append(", status=" + status);
        builder.append(", created=" + created);
        builder.append(", lastRequest=" + requested);
        builder.append(", numberOfRequests=" + requests);
        builder.append("}");
        return builder.toString();
    }
}

