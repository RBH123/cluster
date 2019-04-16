package com.ruanbanhai.springboot.demo.bean;

import com.zengtengpeng.common.bean.Page;


/**
 * user bean
 */
public class User extends Page {


    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * id
     */
    private Integer id;

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


}
