package com.restock.platform.shared.infrastructure.persistence.mongodb;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SequenceGeneratorService {
    private final MongoOperations mongoOperations;

    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(String seqName) {
        var query = new Query(Criteria.where("_id").is(seqName));
        var update = new Update().inc("seq", 1);
        var options = FindAndModifyOptions.options().returnNew(true).upsert(true);
        var sequence = mongoOperations.findAndModify(query, update, options, DatabaseSequence.class);
        return Objects.requireNonNull(sequence).getSeq();
    }
}
