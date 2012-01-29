package helpers.persistance.dao.samples;

import com.google.inject.Inject;

/**
 * User: mike
 * Date: 1/26/12
 * Time: 2:25 AM
 */

public class SampleServiceImpl implements SampleService {
    @Inject
    private SampleMapper mapper;

    @Override
    public void create(String text) {
        mapper.create(text);
    }

    @Override
    public void create(Sample sample) {
        create(sample.text);
    }

    @Override
    public Sample getById(Integer id) {
        return mapper.get(id);
    }

    public void setMapper(SampleMapper mapper) {
        this.mapper = mapper;
    }
}
