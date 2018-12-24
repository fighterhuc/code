package com.huc.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by huangchao on 2018/6/28.
 */
@Slf4j
public class PropertiesUtil {
    private  Properties props;
    public PropertiesUtil(String path){
        loadProps(path);
    }
    private void loadProps(String path){
        props = new Properties();
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
            props.load(in);
        } catch (FileNotFoundException e) {
           log.error("jdbc.properties文件未找到");
        } catch (IOException e) {
            System.out.println("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println("jdbc.properties文件流关闭出现异常");
            }
        }
        log.error("加载properties文件内容完成...........");
    }

    public  String getProperty(String key){
        return props.getProperty(key);
    }

    public  String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
