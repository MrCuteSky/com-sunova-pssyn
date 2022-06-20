package com.sunova.psinfo.mapper;

import com.sunova.psinfo.entities.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AuthorityMapper {
    int updateAuthority(Authority authority);

    Authority getAuthorityByName(String application);
}
