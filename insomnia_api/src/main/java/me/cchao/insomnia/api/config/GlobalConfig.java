package me.cchao.insomnia.api.config;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

import lombok.Data;
import me.cchao.insomnia.common.constant.Constant;

/**
 * @author : cchao
 * @version 2019-03-12
 */
@Data
@ConfigurationProperties(prefix = "project")
@Component
public class GlobalConfig {

    /**
     * 文件上传的保存路径
     */
    public String uploadDirPath = "/www/file/upload/";
    @Value("${sourceServerPath}")
    public static final String sourceServerPath = "https://insomnia-1254010092.cos.ap-hongkong.myqcloud.com/";
    @Value("${defaultAvatarPath}")
    public static final String defaultAvatarPath = sourceServerPath + "common/default/avatar.png";
    public static final String defaultAvatar = "common/default/avatar.png";
    public static String mUserHome = System.getProperty("user.home");


    public static String getSourceServerPath() {
        return sourceServerPath;
    }

    public static String getUploadFileName(String suffix) {
        return System.currentTimeMillis() % 100000 + RandomUtils.nextInt() % 10000 + suffix;
    }

    public static String getUploadDir(String type) {
        return "/upload/" + type + getTimeFormatPath();
    }

    public static String getTimeFormatPath() {
        return "/" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy_MM_dd/");
    }

    public static File getTempSaveFile(String uploadDirPath, String fileName) {
        String dir = mUserHome + uploadDirPath;
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }
        return new File(dir + fileName);
    }

    /**
     * 根据 id 获取不同的 类型
     * fall_music id 大于 2000000
     * fall_image id 大于 1000000
     */
    public static int getTypeById(long id) {
        if (id > 2000000) {
            return Constant.TYPE.FALL_MUSIC;
        } else if (id > 1000000) {
            return Constant.TYPE.FALL_IMAGE;
        } else {
            return Constant.TYPE.FALL_IMAGE;
        }
    }
}
