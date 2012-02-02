package helpers;

import play.modules.guice.InjectSupport;

@InjectSupport
public class GeoDataHelper {
/*	private static final String GEOIP_EXTERNAL_URL = "http://api.hostip.info/?ip=%s&position=%s";
    @Inject
    private static GeoService geoService;

	public static Promise<IpGeoData> requestFromExternalServer(final String ip) {
		final Promise<IpGeoData> geoDataPromise = new Promise<IpGeoData>();
		Thread geoDataFetchThread = new Thread(new Runnable() {

			private IpGeoData result = null;

			@Override
			public void run() {
				play.Logger.debug("Fetching geodata for %s", ip);
				HttpResponse geoXML = WS.url(
						String.format(GEOIP_EXTERNAL_URL, ip, "true")).get();
				Document doc = geoXML.getXml();
				if (doc == null) {
					geoDataPromise.invoke(null);
					play.Logger
							.warn("Cannot access or read from external geodata API ");
					*//*XXX Logger.logSystemWarn(
							LogActionType.DUMP,
							"Cannot access or read from external geodata API for IP %s ",
							ip);*//*
					return;
				}
				NodeList nameNodes = doc.getElementsByTagNameNS("gml", "name");
				if (nameNodes.getLength() > 0) {
					String cityName = nameNodes.item(0).getTextContent();
					play.Logger
							.debug("City name for IP %s is %s", ip, cityName);
					result = new IpGeoData();
					City city = geoService.getCityByAlias("cityName");
					if (city == null) {
						play.Logger
								.debug("City '%s' was not found in DB, creating new one",
										cityName);
						city = new City();
						city.display = false;
                        city.cityAliasName = cityName;
						geoService.insertCity(city);
					}
					result.city = city;
					result.ip = ip;
					result.lastUpdate = new Date();
				}
				NodeList coordinatesNode = doc.getElementsByTagNameNS("gml",
						"coordinates");
				if (coordinatesNode.getLength() > 0) {
					Coordinates coordin = parseCoordinates(coordinatesNode
							.item(0).getTextContent());
					result.coordinates = coordin;
				}
				if (result != null) {
					result.create();
				}
				geoDataPromise.invoke(result);
			}

			private Coordinates parseCoordinates(String textContent) {
				play.Logger.debug("Coordinates string found (%s), parsing...",
						textContent);
				Coordinates resultCoordinates = null;
				String[] splitCoords = textContent.split(",");
				if (splitCoords.length == 2) {
					Double lat = Double.valueOf(splitCoords[0]);
					Double lon = Double.valueOf(splitCoords[1]);
					play.Logger.debug("Parsed longitude: %s; latitude: %s",
							lon, lat);
					// XXX make system switch like (save new coordinates in db
					// ?)
					// XXX Don't know if we need this ?
					resultCoordinates = Coordinates.find(
							Coordinates.HQL.BY_LON_AND_LAT, lon, lat).first();
					if (resultCoordinates == null) {
						resultCoordinates = new Coordinates();
						resultCoordinates.latitude = lat;
						resultCoordinates.longitude = lon;
						resultCoordinates.create();
					}

				} else {
					*//*XXX Logger.logSystemWarn(
							LogActionType.DUMP,
							"Coordinates tag present but contains invalid content(%s)",
							textContent);*//*
				}
				return resultCoordinates;
			}
		});
		geoDataFetchThread.start();
		return geoDataPromise;
	}
    @Deprecated
	public static City getSystemDefaultCity() {
		*//*City city = (City) Cache.get(CACHE_KEYS.DEFAULT_CITY);
		if (city == null){
			String defCityId = PropertyVault.getSystemValueFor(SystemSetting.KEYS.DEFAULT_CITY_ID);
			if (defCityId == null){
				city = City.findById(SystemSetting.DEFAULT_VALUES.DEFAULT_CITY_ID);
			} else {
				city = City.findById(Long.valueOf(defCityId));
			}
			Cache.set(CACHE_KEYS.DEFAULT_CITY, city);
		} *//*
		
		return null ;
	}*/
}
