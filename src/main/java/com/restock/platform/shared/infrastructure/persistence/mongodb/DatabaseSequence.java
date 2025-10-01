package com.restock.platform.shared.infrastructure.persistence.mongodb;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
@NoArgsConstructor
@Getter
@Setter
public class DatabaseSequence {
    @Id
    private String id;

    private long seq;
}
