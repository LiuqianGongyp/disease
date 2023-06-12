package com.cyuu.dataObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Weixin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String province;

    /**
     * 国家
     */
    private String country;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 用户在开放平台的唯一标识
     */
    private String unionId;

    /**
     * 是否授权
     */
    private Integer isAuth;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}