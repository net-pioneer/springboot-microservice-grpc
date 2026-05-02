package com.pouya.library.DTO;

import com.pouya.common.grpc.NotebookResponse;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class NotebookDto {
    private Long id;
    private String name;
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static NotebookDto fromProto(NotebookResponse proto) {
        NotebookDto dto = new NotebookDto();
        BeanUtils.copyProperties(proto, dto);
        return dto;
    }
}