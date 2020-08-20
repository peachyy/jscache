package com.peachyy.jscache.dubbo.test;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Xs.Tao
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    private Integer userId;
    private String userName;
}
