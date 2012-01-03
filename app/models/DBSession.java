package models;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBSession {

    public SqlSession openSession() throws IOException {
        SqlSessionFactory sessionFactory = getFactory();
        return sessionFactory.openSession();
    }

    private SqlSessionFactory getFactory() throws IOException {
        String resource = "/conf/mybatis.conf.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        return sessionFactory;
    }
}