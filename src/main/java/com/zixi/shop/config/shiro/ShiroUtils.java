package com.zixi.shop.config.shiro;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * shiro加密工具类
 */
public class ShiroUtils {
    /**
     * PWD_SALT_LENGTH: 密码加密盐值长度
     */
    public static final int PWD_SALT_LENGTH = 6;
    /**
     * PWD_ALGORITHM_NAME: 密码加密算法
     */
    public static final String PWD_ALGORITHM_NAME = "SHA-512";

    /**
     * PWD_ALGORITHM_NAME: 密码加密次数
     */
    public static final int PWD_HASH_ITERATIONS = 2;

    public static final String salt = "ZXSHOP";

    /**
     * 生成密码<br/>
     *
     * @param pwd
     * @return
     */
    public static String generatePwdEncrypt(String pwd) {
        String hash =
                new SimpleHash(PWD_ALGORITHM_NAME, pwd, salt, PWD_HASH_ITERATIONS).toHex();
        return hash;
    }


    public static void main(String[] args) {
        System.out.println(generatePwdEncrypt("123456"));

    }

}
