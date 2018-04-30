package com.example.PermissionGrantingTool.Repository;

import com.example.PermissionGrantingTool.Entity.PGT;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PGTRepository extends MongoRepository<PGT,String> {

}
