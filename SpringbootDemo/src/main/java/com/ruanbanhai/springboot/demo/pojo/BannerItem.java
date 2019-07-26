package com.ruanbanhai.springboot.demo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public class BannerItem {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column banner_item.item_image
     *
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    @JsonProperty("item_image")
    private String itemImage;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column banner_item.item_jump_url
     *
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    @JsonProperty("item_url")
    private String itemJumpUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column banner_item.item_image
     *
     * @return the value of banner_item.item_image
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    public String getItemImage() {
        return itemImage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column banner_item.item_image
     *
     * @param itemImage the value for banner_item.item_image
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column banner_item.item_jump_url
     *
     * @return the value of banner_item.item_jump_url
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    public String getItemJumpUrl() {
        return itemJumpUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column banner_item.item_jump_url
     *
     * @param itemJumpUrl the value for banner_item.item_jump_url
     * @mbggenerated Tue Dec 25 17:35:29 CST 2018
     */
    public void setItemJumpUrl(String itemJumpUrl) {
        this.itemJumpUrl = itemJumpUrl;
    }

}