package com.cchao.insomnia.dao;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class FallImage {

    @Id
    @GeneratedValue
    private Long id;

    private String url;

    private Integer width;

    private Integer height;

    private Date updateTime;

    private Date createTime;
}
