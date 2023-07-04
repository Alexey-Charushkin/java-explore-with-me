package ru.practicum.main.compilation.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.model.Compilation;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Integer> {

//    @Query("SELECT c " +
//            "FROM Compilation AS c " +
//            "WHERE :pinned IS NULL OR c.pinned = :pinned")
//    List<Compilation> findAllByPinned(@Param("pinned") Boolean pinned, Pageable pageable);
    List<Compilation> findAllByPinnedIs(boolean pinned, Pageable pageable);

}
