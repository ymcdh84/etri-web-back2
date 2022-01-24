package com.iljin.apiServer.core.files;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByDocumentHIdAndFileTypeOrderBySeq(Long id, String fileType);

    Optional<File> findById(Long id);

    List<File> findByCompCdAndDocumentHId(String compCd, String documentHId);

    Optional<File> findByStoredName(String fileName);

    Optional<File> findFirstByOriginalNameContains(String storedName);

    List<File> findAllByOriginalNameContains(String originalName);
}
