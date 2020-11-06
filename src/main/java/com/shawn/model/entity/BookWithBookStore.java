package com.shawn.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Xiaoyue Xiao
 */
@Data
public class BookWithBookStore extends Book {

    private static final long serialVersionUID = -4858710159989616286L;

    private BookStore bookStore;

}
