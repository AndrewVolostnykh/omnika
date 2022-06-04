package io.omnika.services.management.model;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;

// TODO: investigate should I use BaseEntity and move it to common module
public class BaseEntity {

    private LocalDateTime createdDate;

    private JsonNode metadata;

    private boolean isActive;

}
