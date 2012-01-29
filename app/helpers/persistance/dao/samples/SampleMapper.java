package helpers.persistance.dao.samples;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * User: mike
 * Date: 1/26/12
 * Time: 1:35 AM
 */
public interface SampleMapper {
    @Insert("insert into SAMPLE (\"TEXTX\") values (#{text})")
    void create(String text);
    @Select("select * from SAMPLE where id = #{id}")
    Sample get(Integer id);
}
