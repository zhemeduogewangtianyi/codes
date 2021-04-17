package main;

import com.wty.hql_12.Department;
import com.wty.hql_12.Employee;
import factory.HibernateFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;

import java.util.List;

public class QBCMain06 {

    public static void main(String[] args){
        Session session = HibernateFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try{

            qbc(session);

            qub2(session);

            qbc3(session);

            qucOrderAndPage(session);

            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            HibernateFactory.close(session);
        }
    }

    /**
     * qbc 查询
     * */
    public static void qbc(Session session){
        /**
         * 创建一个Criteria对象
         * */
        Criteria criteria = session.createCriteria(Employee.class);
        /**
         * 添加查询条件 在可以使用 Criteria 来表示
         * Criterion 可以通过 Restrictions 静态方法得到
         * */
        criteria.add(Restrictions.like("email","%alibaba%"));
        criteria.add(Restrictions.lt("salary",4F));
        //执行查询
        List list = criteria.list();
        System.err.println(list.toString());

    }

    /**
     * and 和 or 使用
     * */
    public static void qub2(Session session){
        Criteria criteria = session.createCriteria(Employee.class);
        /**
         * AND 的使用
         * Conjunction 本身就是一个Criteria对象
         * 其中可以添加Criteria对象
         * */
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("email","%alibaba%", MatchMode.ANYWHERE));
        Department dept = new Department();
        dept.setId(6L);
        conjunction.add(Restrictions.eq("dept",dept));
        System.err.println(conjunction);
        /**
         * OR 使用
         * */
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.le("salary",4F));
        disjunction.add(Restrictions.isNotNull("email"));

        criteria.add(disjunction);
        conjunction.add(conjunction);

        List list = criteria.list();
        System.out.println(list.toString());
    }

    /**
     * qbc 统计查询
     * */
    public static void qbc3(Session session){
        Criteria criteria = session.createCriteria(Employee.class);

        criteria.setProjection(Projections.max("salary"));

        List list = criteria.list();
        System.out.println(list);
    }

    /**
     * qbc 排序 + 分页
     * */
    public static void qucOrderAndPage(Session session){
        Criteria criteria = session.createCriteria(Employee.class);

        criteria.addOrder(Order.desc("id"));

        criteria.setFirstResult(1);
        criteria.setMaxResults(3);

        List list = criteria.list();
        System.out.println(list);
    }

}
