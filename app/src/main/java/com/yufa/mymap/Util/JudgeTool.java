package com.yufa.mymap.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luyufa on 2016/9/3.
 */
public class JudgeTool {

    public boolean isPhoneNumber(String str){
        String regx = "0?(13|14|15|18)[0-9]{9}";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            if(matcher.group().equals(str)){
                return true;
            }
        }
        return false;
    }

}
