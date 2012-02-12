package services.mybatis.mappings;

import models.geo.City;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Mike Stetsyshyn
 */
public interface CityMapper {

    @Select("select * from vd_city c where c.city_id = #{1}")
    City selectCityById(Long id);
    @Select("select * from vd_city c where c.display = true")
    List<City> selectVisibleCities();
}
