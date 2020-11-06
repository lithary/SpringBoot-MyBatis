package com.shawn.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Xiaoyue Xiao
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 7698862379923111158L;

    private Long id;
    private String username;
    private String password;

}
