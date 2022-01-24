package com.iljin.apiServer.core.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    Optional<User> findById(Long id);
    List<User> findAllByLoginIdContains(String loginId);
    Optional<User> findByLoginId(String loginId);

    @Modifying
    @Transactional
    void deleteByLoginId(String loginId);

}
