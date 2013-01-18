package services.mybatis.mappings;

import models.geo.City;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Mike Stetsyshyn
 */
public interface CityMapper {

    @Select("select * from vd_city c where c.city_id = #{1}")
    City selectCityById(Long id);
    @Select("select * from vd_city c where c.display = true")
    List<City> selectVisibleCities();
    @Select("select * from vd_city")
    List<City> getAllCities();
    @Select("insert into vd_city (\"cityNameKey\", \"cityAliasName\", \"display\" ) values (#{cityNameKey}, #{cityAliasName}, #{display}) returning *")
    City insertCity(City city);
    @Update("update vd_city set \"cityAliasName\" = #{cityAliasName}, \"cityNameKey\"= #{cityNameKey}, \"display\" = #{display}  where \"city_id\" = #{city_id}")
    int updateCity(City city);
}
