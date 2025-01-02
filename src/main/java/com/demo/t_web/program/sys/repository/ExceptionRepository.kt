package com.demo.t_web.program.sys.repository;

import com.demo.t_web.program.sys.model.ExceptionHst;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 * com.demo.t_web.program.sys.repository.ExceptionRepository
 *   - ExceptionRepository.java
 * </pre>
 *
 * @author : tarr4h
 * @className : ExceptionRepository
 * @description :
 * @date : 12/18/24
 */
public interface ExceptionRepository extends JpaRepository<ExceptionHst, Long> {
}
