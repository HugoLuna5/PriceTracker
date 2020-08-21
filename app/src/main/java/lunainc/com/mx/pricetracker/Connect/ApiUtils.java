package lunainc.com.mx.pricetracker.Connect;

public class ApiUtils {

    private ApiUtils() {}

    private static final String BASE_URL = "https://api.mercadolibre.com";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
