package com.shawn.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Xiaoyue Xiao
 */
@Data
public class Book implements Serializable {

    private static final long serialVersionUID = 8604990093149376515L;

    private Long id;
    private String name;
    private String author;
    private Double price;
    private String topic;
    private Date publishDate;

    private Long bookStoreId;

    private List<BookStore> bookStoreList;

}
