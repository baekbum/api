package com.bbco.practice.web.storage;

import com.bbco.practice.web.domain.user.dto.User;
import com.bbco.practice.web.domain.user.dto.params.InsertParam;
import com.bbco.practice.web.domain.user.dto.params.UpdateParam;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStorage {

    User insert(InsertParam param);
    User select(String userId);
    User update(String userId, UpdateParam param);
    User delete(String userId);

}
