package io.chat.chatApp.repository;

import io.chat.chatApp.model.chatdata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface DataRepository extends CrudRepository<chatdata, Integer> {

}
