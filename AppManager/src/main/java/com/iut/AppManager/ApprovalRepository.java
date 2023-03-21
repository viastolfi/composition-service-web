package com.iut.AppManager;

import jakarta.persistence.Entity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRepository extends CrudRepository<Approval, Long> {
    Optional<Approval> findById(Long id);
    List<Approval> findAll();
    void save(Entity app);
}
