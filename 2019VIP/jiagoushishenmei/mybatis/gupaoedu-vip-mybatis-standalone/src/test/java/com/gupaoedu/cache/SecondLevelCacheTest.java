package com.gupaoedu.cache;

import com.gupaoedu.domain.Blog;
import com.gupaoedu.mapper.BlogMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qingshan
 * @Date: 2019/3/8 10:55
 * @Description: 咕泡学院，只为更好的你
 */
public class SecondLevelCacheTest {
    /**
     * 测试二级缓存一定要打开二级缓存开关
     * @throws IOException
     */
    @Test
    public void testCache() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session1 = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper1 = session1.getMapper(BlogMapper.class);
            System.out.println(mapper1.selectBlogById(1));
            // 事务不提交的情况下，二级缓存会写入吗？
            session1.commit();

            System.out.println("第二次查询");
            BlogMapper mapper2 = session2.getMapper(BlogMapper.class);
            System.out.println(mapper2.selectBlogById(1));
        } finally {
            session1.close();
        }
    }

    /**
     * 测试二级缓存一定要打开二级缓存开关
     * @throws IOException
     */
    @Test
    public void testCacheInvalid() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session1 = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();
        SqlSession session3 = sqlSessionFactory.openSession();
        SqlSession session4 = sqlSessionFactory.openSession();
        try {
            //返回的是代理对象

            BlogMapper mapper1 = session1.getMapper(BlogMapper.class);
            BlogMapper mapper2 = session2.getMapper(BlogMapper.class);
            BlogMapper mapper3 = session3.getMapper(BlogMapper.class);
            BlogMapper mapper4 = session3.getMapper(BlogMapper.class);
            System.out.println(mapper1.selectBlogById(1));
            session1.commit();
            System.out.println(mapper2.selectBlogById(2));
            session2.commit();
            System.out.println(mapper3.selectBlogById(3));
            session3.commit();
            System.out.println(mapper4.selectBlogById(4));
            session4.commit();


            // 是否命中二级缓存
            System.out.println("是否命中二级缓存？");
            System.out.println(mapper2.selectBlogById(1));

            Blog blog = new Blog();
            blog.setBid(1);
            blog.setName("2019年1月6日15:03:38");
            mapper3.updateByPrimaryKey(blog);
            session3.commit();

            System.out.println("更新后再次查询，是否命中二级缓存？");
            // 在其他会话中执行了更新操作，二级缓存是否被清空？
            System.out.println(mapper2.selectBlogById(1));

        } finally {
            session1.close();
            session2.close();
            session3.close();
            session4.close();
        }
    }

    /**
     * 测试Mybtis 查询流程
     * @throws IOException
     */
    @Test
    public void testMybatisProcedure() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session1 = sqlSessionFactory.openSession();
        try {
            //返回的是代理对象
            BlogMapper mapper1 = session1.getMapper(BlogMapper.class);
            // 既然是代理对象，入口 是invoke方法
            System.out.println(mapper1.selectBlogById(1));
            session1.commit();
        } finally {
            session1.close();
        }
    }


    @Test
    public void testMap() throws IOException {
        Map map = new HashMap<>();
        Object key2 = map.computeIfAbsent("key", k ->3);
        //如果没有则进行赋值，否则就用之前的
        Object key3 = map.computeIfAbsent("key", k ->33);
        System.out.println(key2);
        System.out.println(key3);
        System.out.println(map.get("key"));

    }

}
