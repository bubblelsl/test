package com.edu.portal.bean;

import com.edu.bean.TbItem;

public class ItemCustomer extends TbItem {
    private String[] images;

    public String[] getImages() {
        String image = this.getImage();
        if(null != image && image.length() > 0) {
            return image.split(",");
        }
        return null;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
