package cn.lzt.tmall.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传
 *
 * @author lzt
 * @date 2019/11/25 17:04
 */
public class UploadedImageFile {

    private MultipartFile image;

    public UploadedImageFile() {
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
