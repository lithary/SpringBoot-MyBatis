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
public class PaginatedResult implements Serializable {

    private static final long serialVersionUID = 6191745064790884707L;

    private int currentPage; // Current page number
    private int totalPage; // Number of total pages
    private Object data; // Paginated resources

}
