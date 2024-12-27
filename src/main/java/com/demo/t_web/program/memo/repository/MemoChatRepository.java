package com.demo.t_web.program.memo.repository;

import com.demo.t_web.program.memo.model.MemoChat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 * com.demo.t_web.program.memo.repository.MemoChatRepository
 *   - MemoChatRepository.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : MemoChatRepository
 * @description :
 * @date : 12/27/24
 */
public interface MemoChatRepository extends JpaRepository<MemoChat, Long> {
}
