package com.mj.kimsnote.repository.token;

import com.mj.kimsnote.vo.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<RefreshToken, String> {
}
