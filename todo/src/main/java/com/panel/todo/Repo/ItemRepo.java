package com.panel.todo.Repo;

import com.panel.todo.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface ItemRepo extends JpaRepository<Item,Integer> {

    @Query("SELECT f FROM Item f WHERE f.title LIKE %?1%")
    List<Item> findByName(String name);
}
