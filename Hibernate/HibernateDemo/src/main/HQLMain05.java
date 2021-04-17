package main;

import com.wty.hql_12.Department;
import com.wty.hql_12.Employee;
import factory.HibernateFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.*;

public class HQLMain05 {

    public static void main(String[] args) {
        Session session = HibernateFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try{

//            init(session);
            hql(session);

            jpaStyle(session);

            namedParameter(session);

            pageQuery(session);

            QueryTag(session);

            propertyQuery(session);

            groupByQuery(session);

            letfJoinFetch(session);

            letfJoin(session);

            innerJoinFetch(session);

            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            HibernateFactory.close(session);
        }
    }

    /**
     * hql
     * */
    public static void hql(Session session){
        //过时了。。。。HQL语句 5.3.1
        //String hql = "From Employee e WHERE e.salary < ? AND e.email LIKE ? ";
        String hql = "From Employee e where e.salary < ?0 and email like ?1";
        Query query = session.createQuery(hql);
        query.setFloat(0,4F).setString(1,"%alibaba%");
        List<Employee> list = query.list();
        System.err.println(list.toString());
    }

    /**
     * JPA_STYLE 的 HQL
     * */
    public static void jpaStyle(Session session){
        String jpaStyle = "From Employee e WHERE e.salary < ?0 AND e.email LIKE ?1 ORDER BY id asc";
        //创建Query对象
        Query jpaDtyleQuery = session.createQuery(jpaStyle);
        //绑定参数
        jpaDtyleQuery.setFloat(0,4.00F).setString(1,"%alibaba%");
        //执行查询
        List<Employee> list = jpaDtyleQuery.list();
        System.err.println(list.toString());

    }

    /**
     * : 命名风格的 HQL
     * */
    public static void namedParameter(Session session){
        String namedParameter = "From Employee e where e.salary < :salary and e.email like :email ORDER BY id asc";
        Query query = session.createQuery(namedParameter);
        query.setParameter("salary",4F).setParameter("email","%alibaba%");
        List<Employee> list = query.list();
        System.err.println(list.toString());
    }

    /**
     * 分页查询
     * */
    public static void pageQuery(Session session){
        String hql = "From Employee e ORDER BY id desc";
        Query query = session.createQuery(hql);

        int pageNum = 0;
        int pageSize = 2;
        query.setFirstResult(pageNum);
        query.setMaxResults(pageSize);

        List<Employee> list = query.list();
        Collections.reverse(list);
        System.err.println("分页 ： " + list.toString());
    }

    /**
     * Employee.hbm.xml 文件的 <query> 标签查询
     * */
    public static void QueryTag(Session session){
        Query query = session.getNamedQuery("query");
        query.setParameter("salary",4F);
        List list = query.list();
        System.err.println("Hibernate <query> 标签" + list.toString());
    }

    /**
     * 投影查询，查部分属性
     */
    public static void propertyQuery(Session session){
        String hql = "select e.name , e.email from Employee e where e.salary < :sal";
        Query query = session.createQuery(hql);
        query.setParameter("sal",4F);
        List<Object[]> list = query.list();
        for(Object[] objects : list){
            List<Object> res = Arrays.asList(objects);
            System.err.println("投影查询 : " + res.toString());
        }
    }

    /**
     * 报表查询
     * */
    public static void groupByQuery(Session session){
        String hql = "select min(salary),max(salary) from Employee group by salary " +
                "having min(salary) > :maxSal and  max(salary) < :minSal  ";
        Query query = session.createQuery(hql);
        query.setParameter("maxSal",1.00F);
        query.setParameter("minSal",4F);
        List<Object[]> list = query.list();
        for(Object[] objects : list){
            List<Object> res = Arrays.asList(objects);
            System.err.println("报表查询：" + res.toString());
        }

    }


    /**
     * 迫切左外链接 LEFT JOIN FETCH
     * */
    public static void letfJoinFetch(Session session){
        /**
         * 这玩意重复数据。。。
         * 或者LinkedHashSet 去重
         * */
        String hql = "from Department d left join fetch d.employees";
//        String hql = "SELECT DISTINCT d from Department d left join fetch d.employees";
        Query query = session.createQuery(hql);
        List<Department> list = query.list();
        list = new ArrayList<>(new LinkedHashSet<>(list));
        System.err.println(list.size());
        System.err.println(list.toString());

        for(Department dept : list){
            System.err.println("迫切左外链接 -> 获取到部门员工信息 ： " + dept.getEmployees().toString());
        }
    }

    /**
     * 左外链接 LEFT JOIN
     * */
    public static void letfJoin(Session session){
//        String hql = "from Department d left join d.employees";
        String hql = "from Department d left join d.employees group by d.id";
        Query query = session.createQuery(hql);
        List<Object[]> list = query.list();
        //没加Fetch就变成了Object[]了。。。。
        System.err.println("左外链接 LEFT JOIN : " + list.toString());

        for(Object[] objs : list){

            List<Object> objects = Arrays.asList(objs);
            //无法去重。。
//            objects = new ArrayList<>(new LinkedHashSet<>(objects));
            System.err.println("左外链接 LEFT JOIN :" + objects.toString());
        }
    }

    /**
     * 迫切内联查询 LEFT JOIN
     * */
    public static void innerJoinFetch(Session session){
        String hql = "from Department d inner join fetch d.employees group by d.id";
        Query query = session.createQuery(hql);
        List<Department> list = query.list();
        System.err.println(list.toString());

        for(Department dept : list){
            System.err.println(dept.getEmployees());
        }
    }






















    public static void init(Session session){
        Department department = new Department();
        department.setName("A部门");

        Set<Employee> set1 = new HashSet<>();
        Employee employee = new Employee("小红","123qq.com",10000.01F,department);
        Employee employee1 = new Employee("小明","aaa@qq.com",1500.01F,department);
        Employee employee2 = new Employee("小张","333qq.com",2500.01F,department);
        Employee employee3 = new Employee("小刘","popo@qq.com",4848.48F,department);
        Employee employee4 = new Employee("小吴","666@qq.com",5678.90F,department);
        Collections.addAll(set1,employee,employee1,employee2,employee3,employee4);

        department.setEmployees(set1);

        session.save(department);
        session.save(employee);
        session.save(employee1);
        session.save(employee2);
        session.save(employee3);
        session.save(employee4);

        Department department1 = new Department();
        department1.setName("2B部门");

        Set<Employee> set2 = new HashSet<>();
        Employee emp = new Employee("董二狗","2b@alibaba.com",1.01F,department1);
        Employee emp1 = new Employee("狗温剩","2B@alibaba.com",2.01F,department1);
        Employee emp2 = new Employee("哈士彬","BDQN@alibaba.com",3.01F,department1);
        Employee emp3 = new Employee("狗屎三","DWB@alibaba.com",4.01F,department1);
        Employee emp4 = new Employee("二逼月","dwb@alibaba.com",5.01F,department1);
        Collections.addAll(set2,emp,emp1,emp2,emp3,emp4);

        department1.setEmployees(set2);

        session.save(department1);
        session.save(emp);
        session.save(emp1);
        session.save(emp2);
        session.save(emp3);
        session.save(emp4);

    }

}
