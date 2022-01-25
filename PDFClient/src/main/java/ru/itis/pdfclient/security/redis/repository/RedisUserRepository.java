package ru.itis.pdfclient.security.redis.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.itis.pdfclient.security.redis.model.RedisUser;

public interface RedisUserRepository extends KeyValueRepository<RedisUser, String> {

}
