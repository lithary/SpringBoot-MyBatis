package com.shawn.model.dto;

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
public class Error implements Serializable {

    private static final long serialVersionUID = 7660756960387438399L;

    private int code; // Error code
    private String message; // Error message

}
