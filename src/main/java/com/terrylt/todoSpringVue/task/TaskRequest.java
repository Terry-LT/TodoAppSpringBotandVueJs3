package com.terrylt.todoSpringVue.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private Long app_user_id;
    private String title;
    private String body;
    private String priority;
}
