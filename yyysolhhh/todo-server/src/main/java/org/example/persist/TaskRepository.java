package org.example.persist;

import org.example.constants.TaskStatus;
import org.example.persist.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> { //JpaRepository - 상속받은 인터페이스에서 메서드 만들면 메서드 이름 규칙에 따라 쿼리를 생성해주는 기능 제공

    List<TaskEntity> findAllByDueDate(Date dueDate); //TODO 왜 하나만 나오지?
    List<TaskEntity> findAllByStatus(TaskStatus status);
}
