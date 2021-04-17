package main;

import com.wty.entity.Student;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class OperatorSessionCache03 {

    private static final Configuration configuration = new Configuration().configure();

    private static final SessionFactory factory = configuration.buildSessionFactory();

    public static void main(String[] args) {

        Session session = null;
        Transaction transaction = null;
        try{
            session = factory.openSession();
            transaction = session.beginTransaction();

//            testFlush(session);

//            testRefresh(session);

            testClear(session);

            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
            factory.close();
        }

    }

    public static void testFlush(Session session){
//        Student student = session.get(Student.class,1);
//        System.out.println(student);
//        student.setPassword("123");
//
//        Student student1 = (Student)session.createCriteria(Student.class).uniqueResult();
//        System.out.println(student1);

        Student student = new Student();
        student.setUsername("wty");
        student.setPassword("123456");
        student.setCreateTime(new Date());

        session.save(student);
    }

    public static void testRefresh(Session session){
        Student student = session.get(Student.class, 3);
        System.out.println(student);
        session.refresh(student);
        student.setPassword("123456");
    }

    public static void testClear(Session session){
        Student student = session.get(Student.class, 3);
        System.out.println(student);

        session.clear();

        Student student1 = session.get(Student.class, 3);
        System.out.println(student1);
    }

}
