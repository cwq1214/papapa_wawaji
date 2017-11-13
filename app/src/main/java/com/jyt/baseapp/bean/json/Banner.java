package com.jyt.baseapp.bean.json;

import java.util.List;

/**
 * Created by chenweiqi on 2017/11/13.
 */

public class Banner {
    private List<Slide> slide;

    public List<Slide> getSlide() {
        return slide;
    }

    public void setSlide(List<Slide> slide) {
        this.slide = slide;
    }

    public class Slide{
        private String link;
        private String img;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
