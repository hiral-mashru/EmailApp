package io.chat.chatApp.repository;

import io.chat.chatApp.model.userinfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<userinfo, Integer> {


}
