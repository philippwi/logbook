package calculation;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import static config.Configuration.GOOGLE_API_KEY;
import static utility.Tools.handleException;

public class DistanceProvider {

    private static final GeoApiContext context = new GeoApiContext.Builder().apiKey(GOOGLE_API_KEY).build();

    public DistanceProvider() {
    }

    /**
     * Calculates the distance between two places using the
     * DistanceMatrixApi from Google.
     *
     * @param origin      name of the starting point
     * @param destination name of the destination point
     * @return distance in meters
     */
    public long getDistance(String origin, String destination) {
        DistanceMatrixApiRequest rq = new DistanceMatrixApiRequest(context);
        DistanceMatrix matrix = null;

        try {
            matrix = rq.origins(origin)
                    .destinations(destination)
                    .mode(TravelMode.DRIVING)
                    .avoid(DirectionsApi.RouteRestriction.FERRIES)
                    .language("de-DE")
                    .await();

            return matrix.rows[0].elements[0].distance.inMeters;
        } catch (Exception e) {
            handleException(e);
            return 0;
        }
    }
}
