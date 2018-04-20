package calculation;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import static config.Configuration.GOOGLE_API_KEY;
import static utility.GeneralUtility.handleException;

public class DistanceProvider {

    private static final GeoApiContext context =  new GeoApiContext.Builder().apiKey(GOOGLE_API_KEY).build();

    public DistanceProvider() {}

    public long getDistance(String origin, String destination){
        DistanceMatrixApiRequest rq = new DistanceMatrixApiRequest(context);
        DistanceMatrix matrix = null;

        try {
            matrix = rq.origins(origin)
                    .destinations(destination)
                    .mode(TravelMode.DRIVING)
                    .avoid(DirectionsApi.RouteRestriction.FERRIES)
                    .language("de-DE")
                    .await();
        } catch (Exception e) {
           handleException(e);
        }
        return matrix.rows[0].elements[0].distance.inMeters;
    }

}