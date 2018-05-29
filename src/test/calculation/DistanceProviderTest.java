package calculation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DistanceProviderTest {

    @Test
    public void getDistance() {
        float fromBerlinToHamburg = 288800f;
        float fromMadridToPrague = 2316600f;

        DistanceProvider dp = new DistanceProvider();

        float dist1 = dp.getDistance("Berlin", "Hamburg");
        float dist2 = dp.getDistance("Madrid", "Prag");

        //big deltas due to different routes depending on current traffic
        assertEquals(dist1, fromBerlinToHamburg, 50000f);
        assertEquals(dist2, fromMadridToPrague, 300000f);
    }
}