package com.fastcampus.boardserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserDTO {
    public static boolean hasNullDataBeforeRegister(UserDTO userDTO) {
        return userDTO.getUserId() == null || userDTO.getPassword() == null || userDTO.getNickName() == null;
    }

    public enum Status {
        DEFAULT, ADMIN, DELETED
    }

    private Integer id;
    private String userId;
    private String password;
    private String nickName;

    @JsonProperty("isAdmin")
    private boolean isAdmin;

    private Date createTime;

    @JsonProperty("isWithDraw")
    private boolean isWithDraw;

    private Status status;

    private Date updateTime;

}
