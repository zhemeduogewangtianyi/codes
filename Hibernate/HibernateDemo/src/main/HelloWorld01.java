package main;

import com.wty.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.StandardServiceInitiators;

import java.util.Date;
import java.util.List;
import java.util.Properties;


public class HelloWorld01 {

    private static SessionFactory factory = null;

    public static void main(String[] args){

        Configuration configuration = new Configuration().configure();

//        configuration.addClass(Student.class);
//
//        StandardServiceRegistry build = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
//
//        configuration.buildSessionFactory(build);

        factory = configuration.buildSessionFactory();

        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();

        Student student = new Student();
        student.setId(1);
        student.setUsername("wty");
        student.setPassword("123456");
        student.setCreateTime(new Date());

//        session.save(student);

        Query from_student = session.createQuery("from Student");
        List list = from_student.list();
        for(Object stu : list){
            System.out.println(((Student)stu).toString());
        }


        transaction.commit();

        session.close();

        factory.close();


    }

}
