package main;

import com.wty.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestSessionCache02 {

    private static Configuration configuration = new Configuration().configure();

    private static SessionFactory factory = configuration.buildSessionFactory();

    public static void main(String[] args) {

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            Student student = session.get(Student.class, 1);
            System.out.println(student.toString());
            session.close();

            session = factory.openSession();
            Student student1 = session.get(Student.class, 1);
            System.out.println(student1.toString());
            session.close();

            session = factory.openSession();
            Student student2 = session.get(Student.class, 1);
            System.out.println(student2.toString());


        }catch(Exception e){
            e.printStackTrace();
        }finally {
            session.close();
            factory.close();
        }



    }

}
