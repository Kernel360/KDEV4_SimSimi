package org.example.service;

import org.example.constants.TaskStatus;
import org.example.persist.TaskRepository;
import org.example.persist.entity.TaskEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // mockito를 사용하는 테스트에서 필요한 mockito 기능 활성화
class TaskServiceTest {

    @Mock // mock 객체 생성 - 실제 객체 대체하고 mock 객체 동작 검증, 해당 클래스 의존성 제거
    private TaskRepository taskRepository;

    @InjectMocks // 해당 클래스 인스턴스 생성하면서 mock 객체 포함해서 모든 의존성을 자동으로 주입
    private TaskService taskService;

    @Test
    @DisplayName("할 일 추가 기능 테스트")
    void add() {
        var title = "test";
        var description = "test description";
        var dueDate = LocalDate.now();

        //mock 객체 메서드 어떻게 동작하게 될지 정의 - db 동작은 실제로 정상적으로 동작한다고 가정
        when(taskRepository.save(any(TaskEntity.class))) // any - 매개변수 지정하지 않고 모든 값(TaskEntity)을 허용
                .thenAnswer(invocation -> {
                    var e = (TaskEntity) invocation.getArgument(0);
                    e.setId(1L);
                    e.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    e.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    return e;
                });

        var actual = taskService.add(title, description, dueDate);

        //repository 딱 한번 호출됐는
        verify(taskRepository, times(1)).save(any());

        assertEquals(1L, actual.getId());
        assertEquals(title, actual.getTitle());
        assertEquals(description, actual.getDescription());
        assertEquals(dueDate.toString(), actual.getDueDate());
        assertEquals(TaskStatus.TODO, actual.getStatus());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getUpdatedAt());
    }
}