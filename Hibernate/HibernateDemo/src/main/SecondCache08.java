package main;

import com.wty.hql_12.Employee;
import factory.HibernateFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cache.spi.QueryCache;

public class SecondCache08 {

    public static void main(String[] args) {
//        Session session = HibernateFactory.getSession();

        try{

//            testOneLevelCache(session);

//            System.out.println(0.0001);
//            System.out.println(10000000000000000000000.0);

            for (int i = 0 ; i < 10 ; i++){
                int random = (int) (Math.random() * 5) + 1;
                System.out.println(random);
            }


            String a = "hh";
            String b = a + "11";
            System.out.println(System.identityHashCode(b) + ":" + System.identityHashCode("hh11"));

        }finally {
//            HibernateFactory.close(session);
        }
    }

    public static void testOneLevelCache(Session session){
        /**
         * 一级缓存，作用与同一个Session
         * session关闭，缓存消失
         * */
        Transaction transaction = session.beginTransaction();

        Employee employee = session.get(Employee.class, 1L);
        System.out.println(employee);
        System.out.println(employee.getDepartment());

        transaction.commit();
        session.close();

        session = HibernateFactory.getSession();

        Employee employee1 = session.get(Employee.class, 1L);
        System.out.println(employee1);
        System.out.println(employee1.getDepartment());

    }

}
