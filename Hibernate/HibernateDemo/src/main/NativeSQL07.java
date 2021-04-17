package main;

import factory.HibernateFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

public class NativeSQL07 {

    public static void main(String[] args) {
        Session session = HibernateFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try{

            nativeSQL(session);

            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            HibernateFactory.close(session);
        }
    }


    /**
     * sql语句新增数据。。。
     * */
    public static void nativeSQL(Session session){
        String sql = "insert into employee (name,email,salary) values(:name,:email,:salary)";
        NativeQuery sqlQuery = session.createSQLQuery(sql);

        sqlQuery.setParameter("name","QWE").setParameter("email","QWE@qq.com")
                .setParameter("salary",9999.99F)
        .executeUpdate();
    }

}
