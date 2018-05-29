package beans;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HomeBeanTest {

    @Test
    public void calculateDistance() {
        HomeBean hb = new HomeBean();

        hb.setOrigin("");
        hb.setDestination("");
        hb.calculateDistance();

        assertEquals(0, hb.getDistance(), 0.001);

        hb.setOrigin("Berlin");
        hb.setDestination("Hamburg");
        hb.calculateDistance();

        //big delta due to different routes depending on current traffic
        assertEquals(288.8f, hb.getDistance(), 50f);
    }
}