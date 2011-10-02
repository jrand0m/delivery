package helpers;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import enumerations.LogActionType;

import models.geo.City;
import models.geo.Coordinates;
import models.geo.IpGeoData;
import models.geo.IpGeoData.HQL;
import models.settings.SystemSetting;
import models.settings.SystemSetting.DEFAULT_VALUES;
import models.settings.SystemSetting.KEYS;
import play.cache.Cache;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.HttpResponse;

public class GeoDataHelper {
	private static final String GEOIP_EXTERNAL_URL = "http://api.hostip.info/?ip=%s&position=%s";

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
					Logger.logSystemWarn(
							LogActionType.DUMP,
							"Cannot access or read from external geodata API for IP %s ",
							ip);
					return;
				}
				NodeList nameNodes = doc.getElementsByTagNameNS("gml", "name");
				if (nameNodes.getLength() > 0) {
					String cityName = nameNodes.item(0).getTextContent();
					play.Logger
							.debug("City name for IP %s is %s", ip, cityName);
					result = new IpGeoData();
					City city = City.find("cityNameEN = ?", cityName).first();
					if (city == null) {
						play.Logger
								.debug("City '%s' was not found in DB, creating new one",
										cityName);
						city = new City();
						city.display = false;
						city.cityNameEN = cityName;
						city.create();
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
					Logger.logSystemWarn(
							LogActionType.DUMP,
							"Coordinates tag present but contains invalid content(%s)",
							textContent);
				}
				return resultCoordinates;
			}
		});
		geoDataFetchThread.start();
		return geoDataPromise;
	}

	public static City getSystemDefaultCity() {
		City city = (City) Cache.get(CACHE_KEYS.DEFAULT_CITY);
		if (city == null){
			String defCityId = PropertyVault.getSystemValueFor(SystemSetting.KEYS.DEFAULT_CITY_ID);
			if (defCityId == null){
				city = City.findById(SystemSetting.DEFAULT_VALUES.DEFAULT_CITY_ID);
			} else {
				city = City.findById(Long.valueOf(defCityId));
			}
			Cache.set(CACHE_KEYS.DEFAULT_CITY, city);
		}
		
		return city ;
	}
}
