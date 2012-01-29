package unit.persistance.dao;

import helpers.persistance.dao.samples.Sample;
import helpers.persistance.dao.samples.SampleService;
import org.junit.Before;
import org.junit.Test;
import play.modules.guice.InjectSupport;
import play.test.UnitTest;

import javax.inject.Inject;

/**
 * Created by IntelliJ IDEA.
 * BaseUser: mike
 * Date: 1/26/12
 * Time: 1:15 AM
 * To change this template use File | Settings | File Templates.
 */

@InjectSupport
public class MyBatsConfigurationTest extends UnitTest {
    @Inject
    private static SampleService service;

    @Before
    public void setUp() throws Exception {
              // TODO Migrations, maybe steal sources of mybatis migration tool?
              // TODO Maybe tune play framework to use same login/pwd/db schema as mybatis so play evolutions will come in natural way....
    }

    @Test
    public void sampleDbTest(){
        service.create("test");

        Integer id = 1;
        Sample s = service.getById(id);
        assertNotNull(s);
        assertEquals("test", s.text);
    }
}
