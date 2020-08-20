package com.peachyy.jscache.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Xs.Tao
 */
@Getter
@Setter
public class User implements Serializable {
    private String name;
    private Integer id;
}
