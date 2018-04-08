package com.dyuanit.atm.springbootatm.util;

import java.util.Random;

/**
 * 字符串操作工具类
 */
public abstract class MyStringUtils {

    public static boolean isDouble(String str)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException ex){

        }
        return false;
    }


    /**
     * 生成会员登录账号
     * @return
     */
    public static String getLoginCode(){
        return getRandom(100000,999999).toString();
    }


    /**
     * 获取指定位数随机数
     * @return
     */
    public static String getRandom(Integer number){
        if(number == 6){
            return getRandom(100000,999999).toString();
        }
        return getRandom(100000,999999).toString();

    }

    /**
     * 生成min~max随机数
     * @param min
     * @param max
     * @return
     */
    public static Integer getRandom(Integer min,Integer max){
        return min+ new Random().nextInt(max-min+1);
    }


}
