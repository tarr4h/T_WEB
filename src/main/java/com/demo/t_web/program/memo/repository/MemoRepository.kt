package com.demo.t_web.program.memo.repository;

import com.demo.t_web.program.memo.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 * com.demo.t_web.program.memo.repository.MemoRepository
 *   - MemoRepository.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : MemoRepository
 * @description :
 * @date : 12/27/24
 */
public interface MemoRepository extends JpaRepository<Memo, Long> {
}
