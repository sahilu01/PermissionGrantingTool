package com.example.PermissionGrantingTool.Repository;

import com.example.PermissionGrantingTool.Entity.PgtFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PGTFileRepository extends MongoRepository<PgtFile,String> {

    @Query("{name : ?0}")
    public PgtFile findByFileName(String filnName);

}
