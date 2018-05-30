package beans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class BackgroundBean {

    //default background: road2.jpg
    private static String image = "road2.jpg";

    //default header color: #D9F5F9
    private static String headerColor = "#D9F5F9";

    public String getImage() {
        return BackgroundBean.image;
    }

    public void setImage(String image) {
        BackgroundBean.image = image;
    }

    public String getHeaderColor() {
        return BackgroundBean.headerColor;
    }

    public void setHeaderColor(String headerColor) {
        BackgroundBean.headerColor = headerColor;
    }

    public void changeImage(String img){
        BackgroundBean.image = img;
    }
    public void changeHeaderColor(String clr){
        BackgroundBean.headerColor = clr;
    }
}
