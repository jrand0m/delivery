package helpers.persistance.dao.samples;

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 1/26/12
 * Time: 1:51 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SampleService {
    void create (String text);
    void create (Sample sample);
    Sample getById (Integer id);
}
