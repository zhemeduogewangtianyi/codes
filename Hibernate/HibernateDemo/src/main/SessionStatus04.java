package main;

import com.wty.entity.Student;
import com.wty.entity.Task;
import com.wty.entity.Worker;
import com.wty.joined_subclass_09.Children02;
import com.wty.joined_subclass_09.Parent02;
import com.wty.one_many2many_06.Category;
import com.wty.one_many2many_06.Item;
import com.wty.many_to_one_02.Consumer;
import com.wty.many_to_one_02.Order;
import com.wty.one2one_foregin_04.IdCard01;
import com.wty.one2one_foregin_04.People01;
import com.wty.one2one_primary_05.IdCard02;
import com.wty.one2one_primary_05.People02;
import com.wty.one_to_many_03.Industry;
import com.wty.one_to_many_03.Project;
import com.wty.strategy_11.Consumer02;
import com.wty.strategy_11.Order02;
import com.wty.subclass_08.Children;
import com.wty.subclass_08.Parent;
import com.wty.two_many2many_07.Category02;
import com.wty.two_many2many_07.Item02;
import com.wty.union_subclass_10.Children03;
import com.wty.union_subclass_10.Parent03;
import factory.HibernateFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class SessionStatus04 {

    public static void main(String[] args) {
        Session session = HibernateFactory.getSession();
        Transaction transaction = session.beginTransaction();
        try{

//            save(session);

//            persistent(session);

//            get(session);

//            load(session);

//            update(session);

//            saveOrupdate(session);

//            delete(session);

//            evict(session);

//            doWork(session);

//            dynamicUpdate(session);

//            increment(session);

//            identity(session);

//            formula(session);

//            component(session);

//            manyToOneInsert(session);

//            manyToOneQuery(session);

//            manyToOneQueryFromUpdate(session);

//            manyToOneDeleteConsumer(session);

//            OneToManyInsert(session);

//            OneToManyQuery(session);

//            OneToManyUpdate(session);

//            OneToManyDelete(session);

//            cascade(session);

//            one2oneInsert(session);

//            one2oneQuery(session);

//            one2oneQuery02(session);

//            one2oneSave(session);

//            one2oneGet(session);

//            many2manyinsert(session);

//            many2manyQuery(session);

//            many2manySave(session);

//            many2manyGet(session);

//            subClassInster(session);

//            subClassQuery(session);

//            joinedSubClassInsert(session);

//            joinedSubClassQuery(session);

//            unionSubClassInsert(session);

//            unionSubClassQuery(session);

//            unionSubClassUpdate(session);

//            classLevelStrategySave(session);

//            classLevelStrategyQuery(session);

//            oneToManyLevelStrategyQuery(session);

//            setBatchSize(session);

            many2OneStragetegy(session);

            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            HibernateFactory.close(session);
        }
    }

    /** ??????????????????????????????????????????????????????????????????????????? */
    public static void save(Session session){
        Student stu = new Student();
        stu.setUsername("wtywty");
        stu.setPassword("123456");
        stu.setCreateTime(new Date());

        System.out.println(stu);

        session.save(stu);

        /**
         * ??????????????????????????????????????????
         * ???????????????ID
         * flush????????????????????????inset??????
         * ???save???????????????ID??????????????????
         * ???????????????ID?????????????????????
        *
         * */
        System.out.println(stu);
//        stu.setId(4);
    }

    public static void persistent(Session session){
        Student stu = new Student();
        stu.setId(5);
        stu.setUsername("admin");
        stu.setPassword("123456");
        stu.setCreateTime(new Date());

        System.out.println(stu);

        /**
         * persist ???????????? insert
         * persist ??? save ?????????
         * ???persist?????????????????????????????????ID?????????????????????
         *
         * */
        session.persist(stu);

        System.out.println(stu);
    }

    public static void get(Session session){

        Student student = session.get(Student.class, 1);
        System.out.println(student);

    }

    public static Student load(Session session){

        /**
         * ???????????????id????????????????????????load????????????get ??????  null
         * load???????????????
         * get????????????????????????????????????????????????????????????
         * load???????????????????????????????????????????????????getXXX?????????????????????????????????????????????
         *
         * load????????????????????????????????????
         * */
        Student load = session.load(Student.class, 10);
        System.out.println(load.getPassword());
        return load;

    }

    public  static void update(Session session){

        /**
         * 1???????????????????????????????????????????????????????????????????????????????????? flush???????????????????????????????????????????????????
         * ?????????update,?????????session.update???????????????????????????????????????
         *
         * 2:?????????????????????????????????session?????????????????????session????????????????????????student ??????????????????????????????????????????
         *  ????????????update??????????????????
         *  ?????????????????????????????????????????????update??????
         *
         *  3???????????????????????????????????????????????????????????????
         *
         *  4???????????????update??????????????????update?????????
         *  ???hbm.xml?????? ??? <class> ?????? ?????????????????? select-before-update=true
         * 	????????????????????????
         *
         *  5?????????update??????????????????????????????????????????
         *
         * 	6???Session???????????????????????????????????????????????????Session??????????????????????????????????????????
         *
         * */
        Student stu = session.get(Student.class,8);
        stu.setUsername("??????1");

        session.close();

        Session session1 = HibernateFactory.getSession();
        Transaction transaction = session1.beginTransaction();

        //Session???????????????????????????????????????????????????Session??????????????????????????????????????????
        Student stu2 = session1.get(Student.class, 8);
        session1.update(stu);

        transaction.commit();
        session.close();


//        session.close();
//        session = HibernateFactory.getSession();
//        stu.setUsername("??????1");

//        session.update(stu);
    }



    /**
     * 1?????? OID ?????? null????????????????????????????????????????????????????????????
     * */
    public static void saveOrupdate(Session session){

        Student student = new Student("Aa","aa",new Date());
        session.saveOrUpdate(student);
    }


    /**
     * delete : ??????????????????
     * ??????OID???????????????????????????????????????????????????
     * ??????OID ????????????????????????????????????????????????
     *
     * ??????????????????hibernate??? hibernate.use_identifier_rollback ?????? ??? true,
     * ????????????????????????OID?????? null
     * */
    public static void delete(Session session){

        //????????????
        Student student = new Student();
        student.setId(8);

        //???????????????
        Student student1 = session.get(Student.class, 13);

        session.delete(student1);

        System.out.println(student1);

    }

    /**
     * ???Session?????????????????????????????????????????????
     * */
    public static void evict(Session session){
        Student stu = session.get(Student.class, 14);
        Student stu1 = session.get(Student.class, 15);

        stu.setUsername("??????");
        stu1.setUsername("admin");

        //??????????????????stu?????????
        session.evict(stu);
    }


    /**
     * Hibernate??????????????????
     * */
    public static void doWork(Session session){
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println(connection);
                //??????????????????????????????JDBC????????????
            }
        });
    }

    /**
     * ????????????
     * */
    public static void dynamicUpdate(Session session){
        //dynamic-update="true",sql??????????????????????????????????????????????????????
        Student student = session.get(Student.class, 14);
        student.setUsername("AAAAABBBCC");

    }

    /**
     * increment ??????????????????
     * */
    public static void increment(Session session){
        //<generator class="increment"></generator>
        session.save(new Student("wty","123",new Date()));
    }

    /**
     * identity ??????????????????
     * */
    public static void identity(Session session){
        //<generator class="increment"></generator>
        session.save(new Student("identity","123",new Date()));
    }

    /**
     * formula
     * */
    public static void formula(Session session){
        //<property name="desc" formula="(select concat(username,':',password) from student s where s.id = id)"></property>
        Student student = session.get(Student.class, 1);
        System.out.println(student);
    }

    /**
     * component
     * */
    public static void component(Session session){
        Worker work = new Worker();
        Task task = new Task();
        task.setOne("one");
        task.setTwo("two");
        work.setName("work");
        work.setTask(task);
        session.save(work);
    }

    /**
     * ???????????????????????? Insert
     * */
    public static void manyToOneInsert(Session session){
        Consumer consumer = new Consumer();
        consumer.setName("??????");
        consumer.setPassword("123456");

        Order order1 = new Order();
        order1.setOrderName("????????????");

        order1.setConsumer(consumer);

        Order order2 = new Order();
        order2.setOrderName("???????????? - Plus");

        order2.setConsumer(consumer);

        /**
         * ??????????????????????????????????????? -> ??????insert??????
         * */
//        session.save(consumer);
        session.save(order1);
        session.save(order2);
        /**
         * ??????????????????????????????????????? -> ??????insert???????????????update ???????????????????????????
         * */
        session.save(consumer);

    }

    /**
     * ???????????????????????? query
     * */
    public static void manyToOneQuery(Session session){
        Order order = session.get(Order.class, 2L);
        System.out.println(order);
        //????????????Consumer??????????????????????????????????????????????????? LazyInitializationException
        System.out.println(order.getConsumer());
    }

    /**
     * ???????????????????????? ??????query ??? update
     * */
    public static void manyToOneQueryFromUpdate(Session session){

        Order order = session.get(Order.class, 2L);
        order.getConsumer().setName("??????");

    }

    /**
     * ???????????????????????? ?????????  Consumer
     * */
    public static void manyToOneDeleteConsumer(Session session){
        //????????????????????????????????????
        //???????????????????????????????????????????????????
        Consumer consumer = session.get(Consumer.class, 2L);
        session.delete(consumer);
    }

    /**
     * ??????????????????????????? ??????
     * */
    public static void OneToManyInsert(Session session){
        Project project = new Project();
        project.setProjectName("?????????????????????");

        Industry industry = new Industry();
        industry.setIndustryName("????????????");
        industry.setProject(project);

        Industry industry1 = new Industry();
        industry1.setIndustryName("????????????");
        industry1.setProject(project);

        Set<Industry> list = new HashSet<>();

        list.add(industry);
        list.add(industry1);

        project.setIndustrySet(list);

        /** ??????Insert + ?????? update?????? */
        session.save(industry);
        session.save(industry1);
        /** ??????Insert + ?????? update?????? */
        session.save(project);
//        session.save(industry);
//        session.save(industry1);

        /**
         * ???????????????????????????????????????????????????
         * many-to-one
         * one-to-many
         * ???????????????
         *  1?????????Project???????????????????????????,???many-to-one?????????????????????
         *  2???<set>?????????reverse?????? true
         *  3:?????????????????????????????????????????????????????????
         *
         * */
    }

    /**
     * ??????????????????????????? ??????
     * */
    public static void OneToManyQuery(Session session){
        Project project = session.get(Project.class, 1L);
        /**
         * ??????StackOverflowError
         * 1?????? n ?????????????????????????????????
         * */
        //        System.out.println(project.toString());
        System.out.println(project.getProjectName());

//        session.close();

        /**
         * org.hibernate.collection.internal.PersistentSet
         * 2????????????????????????????????? hibernate ?????????????????????
         * ?????????????????????????????????????????????????????????
         *
         * 3?????????????????????????????????
         * */
        System.out.println(project.getIndustrySet().getClass().getName());

        /**
         * 4:????????????????????????????????????????????????????????????
         * ?????????????????????????????????????????????????????????
         * ??????????????????
         * ???????????????????????????????????????????????????toString()???????????????????????????????????????
         *
         *
         * */

        Set<Industry> industrySet = project.getIndustrySet();
        for(Industry industry : industrySet){
//            System.out.println(industry.getId() + "," + industry.getIndustryName() + ":" + industry.getProject());
            System.out.println(industry.toString());
        }

    }


    /**
     * ??????????????????????????? ??????
     * */
    public static void OneToManyUpdate(Session session){

        Project project = session.get(Project.class, 1L);
        project.setProjectName("????????????AAA");
        for(Industry industry : project.getIndustrySet()){
            if(industry.getId() == 2){
                industry.setIndustryName("?????????????????????");
            }
        }
    }

    /**
     * ??????????????????????????? ??????
     * ???????????????????????????????????????????????? ???
     * */
    public static void OneToManyDelete(Session session){
        Project project = session.get(Project.class, 1L);
        session.delete(project);
    }

    public static void cascade(Session session){
        /**
         * ??????????????????????????????????????????hbm.xml????????????????????????????????????????????????
         * cascade="delete-orphan"
         * */
//        Project project = session.get(Project.class, 2L);
//        project.getIndustrySet().clear();

        /**
         * ?????????
         * */
//        Project project = session.get(Project.class, 3L);
//        project.getIndustrySet().clear();

        /**
         * cascade="save-update"
         * */
//        Project project = new Project();
//        project.setProjectName("????????????????????????");
//
//        Industry industry = new Industry();
//        industry.setIndustryName("???????????????");
//
//        Set<Industry> set = new HashSet<>();
//        set.add(industry);
//
//        project.setIndustrySet(set);
//
//        session.save(project);

        /**
         * order-by="id desc"
         * */

//        Project project = session.get(Project.class, 6L);
//        System.out.println(project.getIndustrySet().toString());
    }


    /**
     * one-to-one ?????????????????? ??????
     * */
    public static void one2oneInsert(Session session){
        People01 people01 = new People01();
        people01.setName("?????????");

        IdCard01 idCard01 = new IdCard01();
        idCard01.setNum("333333");
        idCard01.setPeople(people01);

        people01.setIdCard(idCard01);

//        session.save(people01);
        session.save(idCard01);
        session.save(people01);

        /**
         * ??????sql??????????????????update..??????????????????????????????????????????
         * 1????????????????????????????????????.
         *      ??????????????????????????????????????????
         * */

    }

    /**
     * one-to-one ?????????????????? ??????1
     * */
    public static void one2oneQuery(Session session){
        People01 people01 = session.get(People01.class, 1L);
        System.out.println(people01);
        /**
         * ???????????????????????????????????????
         * ??????toString()...
         * */
        System.out.println(people01.getIdCard());

        /**
         * <one-to-one name="people" class="People01" property-ref="idCard"></one-to-one>
         * property??????????????????????????????????????????
         * ??????property?????????????????? name??????????????????
         *
         * */

    }

    /**
     * one-to-one ?????????????????? ??????2
     * */
    public static void one2oneQuery02(Session session){

        /**
         * ????????? ?????????sql??????????????????People?????????
         * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         * */
        IdCard01 idCard01 = session.get(IdCard01.class, 1L);
        System.out.println(idCard01);

    }


    /**
     * one-to-one ?????????????????? ??????
     * */
    public static void one2oneSave(Session session){
        People02 people02 = new People02();
        people02.setName("???111???");

        IdCard02 idCard02 = new IdCard02();
        idCard02.setNum("4534354354");
        idCard02.setPeople(people02);

        people02.setIdCard(idCard02);

        session.save(idCard02);
        session.save(people02);
    }

    /**
     * one-to-one ?????????????????? ??????
     * */
    public static void one2oneGet(Session session){
        People02 people02 = session.get(People02.class, 2L);
        System.out.println(people02);

        IdCard02 idCard021 = session.get(IdCard02.class, 2L);
        /**
         * ???????????????????????????property-ref ?????????????????????????????????????????????
         * */
        System.out.println(idCard021);
    }

    /**
     * ??????many-to-many ???????????? ??????
     * */
    public static void many2manyinsert(Session session){
        Category category = new Category();
        category.setName("??????02");

        Item item = new Item();
        item.setName("????????????1");

        Item item1 = new Item();
        item1.setName("????????????2");

        Set<Item> items = new HashSet<>();
        items.add(item);
        items.add(item1);

        category.setItems(items);

        session.save(category);
        session.save(item);
        session.save(item1);
    }

    /**
     * ??????many-to-many ???????????? ??????
     * */
    public static void many2manyQuery(Session session){
        Category category = session.get(Category.class, 1L);
        System.out.println(category);
        System.out.println(category.getItems().toString());
    }

    /**
     * ??????any-to-many ???????????? ??????
     * */
    public static void many2manySave(Session session){
        Category02 category02 = new Category02();
        category02.setName("??????-?????????-????????????");

        Item02 item = new Item02();
        item.setName("AAA");

        Item02 item1 = new Item02();
        item1.setName("BBB");

        Set<Category02>  category02s = new HashSet<>();
        category02s.add(category02);

        item.setCategorys(category02s);
        item1.setCategorys(category02s);


        /**
         * ?????????????????????????????????????????????????????????????????????
         * 1?????????Category?????????item
         * 2???inverse??????
         * */
        Set<Item02> item02s = new HashSet<>();
        item02s.add(item);
        item02s.add(item1);

        category02.setItems(item02s);

        session.save(category02);
        session.save(item);
        session.save(item1);
    }

    /**
     * ??????any-to-many ???????????? ??????
     * */
    public static void many2manyGet(Session session){
        Category02 category02 = session.get(Category02.class, 1L);
        System.out.println(category02);
        System.out.println(category02.getItems().toString());
    }

    /**
     * ?????????????????? subclass ??????
     * */
    public static void subClassInster(Session session){
        /**
         * ???????????????????????????????????????
         * ???????????????Hibernate????????????
         * */
        Parent parent = new Parent();
        parent.setName("AAA");

        Children children = new Children();
        children.setName("BBB");
        children.setPassword("123");

        session.save(parent);
        session.save(children);
    }

    /**
     * ?????????????????? subclass ??????
     * */
    public static void subClassQuery(Session session){
        List from_parent_ = session.createQuery("from Parent ").list();
        System.out.println(from_parent_.toString());

        /**
         * ??????????????????????????????????????????
         * ?????????????????????????????????????????????
         *
         * ?????????
         *  ???????????????????????????
         *  ?????????????????????????????????????????????
         *  ??????????????????????????????????????????????????????????????????????????????
         * */
        List from_children_ = session.createQuery("from Children ").list();
        System.out.println(from_children_.toString());
    }

    /**
     * ?????????????????? joined-subclass ??????
     * */
    public static void joinedSubClassInsert(Session session){

        /**
         * ??????????????????????????????
         * */
        Parent02 parent02 = new Parent02();
        parent02.setName("ABC");

        Children02 children02 = new Children02();
        children02.setPassword("123456");
        children02.setName("abc");

        session.save(parent02);
        session.save(children02);
    }

    /**
     * ?????????????????? joined-subclass ??????
     * */
    public static void joinedSubClassQuery(Session session){

        /**
         * ?????????
         * ???????????????????????????
         * ??????????????????????????????????????????
         * ????????????????????????????????????
         * */
        List from_parent_ = session.createQuery("from Parent02 ").list();
        System.out.println(from_parent_.toString());

        List from_children_ = session.createQuery("from Children02 ").list();
        System.out.println(from_children_.toString());
    }

    /**
     * ?????????????????? union-subclass ??????
     * */
    public static void unionSubClassInsert(Session session){

//         <id name="id" column="id" type="java.lang.Long">
//            <generator class="assigned" />
//        </id>

        Parent03 parent03 = new Parent03();

        parent03.setName("00003");
        parent03.setId(1L);

        session.save(parent03);

        Children03 children03 = new Children03();
        children03.setName("aaaaa");
        children03.setPassword("123456");
        children03.setId(2L);

        session.save(children03);
    }

    /**
     * ?????????????????? union-subclass ??????
     * */
    public static void unionSubClassQuery(Session session){
        /**
         * ?????????
         *  ???????????????????????????
         *  ??????????????????????????????
         *
         * ?????????
         *  ????????????????????????????????????????????????????????????????????????
         *  ??????????????????
         *  ??????????????????
         *
         * */

        List from_parent_ = session.createQuery("from Parent03 ").list();
        System.out.println(from_parent_.toString());

        List from_children_ = session.createQuery("from Children03 ").list();
        System.out.println(from_children_.toString());
    }

    /**
     * ?????????????????? union-subclass ?????? --  ??????????????????????????????????????????
     * */
    public static void unionSubClassUpdate(Session session){
        /**
         * hql ?????????????????????
         * */
        String hql = "update Parent03 p set p.name = '???????????????????????????????????????' where p.id = 1";
        int i = session.createQuery(hql).executeUpdate();
        System.out.println(i);
    }

    /**
     * ???????????? ???????????????
     * */
    public static void classLevelStrategySave(Session session){
        Consumer02 consumer = new Consumer02();
        consumer.setName("AAA");
        consumer.setPassword("aaa");
        Order02 order = new Order02();
        order.setOrderName("111");
        order.setConsumer(consumer);
        session.save(consumer);
        session.save(order);

        Consumer02 consumer1 = new Consumer02();
        consumer1.setName("BBB");
        consumer1.setPassword("bbb");
        Order02 order1 = new Order02();
        order1.setOrderName("222");
        order1.setConsumer(consumer1);
        session.save(consumer1);
        session.save(order1);

        Consumer02 consumer2 = new Consumer02();
        consumer2.setName("CCC");
        consumer2.setPassword("ccc");
        Order02 order2 = new Order02();
        order2.setOrderName("333");
        order2.setConsumer(consumer2);
        session.save(consumer2);
        session.save(order2);

        Consumer02 consumer3 = new Consumer02();
        consumer3.setName("DDD");
        consumer3.setPassword("ddd");
        session.save(consumer3);

        Order02 order3 = new Order02();
        order3.setOrderName("444");
        session.save(order3);
    }

    /**
     * ???????????????????????? ??????
     * */
    public static void classLevelStrategyQuery(Session session){
        //<class name="Consumer02" table="consumer02" lazy="false">
        /**
         * ?????????true ????????? ???????????????
         * ?????????false ????????????????????????????????????
         * */
        Consumer02 consumer02 = session.load(Consumer02.class, 1L);
        System.out.println(consumer02.getClass().getName());

        /**
         * lazy="true"
         * ??????????????????????????????????????????????????? OID ?????????, Hibernate ???????????????????????????
         * */
        System.out.println(consumer02.getId());

        System.out.println(consumer02.getName());
    }

    /**
     * ????????????????????????????????????: ??????
     * */
    public static void oneToManyLevelStrategyQuery(Session session){

        //<class name="Consumer02" table="consumer02" lazy="true">
        Consumer02 consumer = session.get(Consumer02.class, 1L);

        /**
         * ??????Order????????????????????????Order
         * <set>?????????lazy??????????????????????????????
         * lazy ?????????????????? false
         *
         * <set name="orders" table="order02" lazy="extra"> ??????????????????
         * */
//        System.out.println(consumer.getOrders());
        /**
         * lazy="extra"????????????count(id) ????????? Order????????????
         * ??????????????????????????????????????????
         * */

        System.out.println(consumer.getOrders().size());

        //??????lazy=false ???????????????????????????????????????
        Hibernate.initialize(consumer.getOrders());

    }

    /**
     * batch-size
     * */
    public static void setBatchSize(Session session){
        List<Consumer02> res = session.createQuery("From Consumer02").list();

        System.out.println(res.size());

        //lazy=true
        for(Consumer02 consumer : res){
            if(consumer.getOrders() != null){
                /**
                 * 4 ??? SQL ??????
                 * ??????????????????????????????????????????????????????????????????????????????
                 *
                 * ????????????Orders???????????????????????????
                 * <set> ???????????????????????? batch-size="5" ??????????????????
                 * */
                System.out.println(consumer.getOrders().size());
            }
        }
    }


    /**
     * ????????????????????????: ??????
     * */
    public static void many2OneStragetegy(Session session){
        /**
         * ????????????Order,????????????Consumer
         * */
        //lazy=false fetch="join"
        Order order = session.get(Order.class,2L);
        System.out.println(order);

        //?????????????????????????????????????????????fetch="join"??????????????????????????????
        System.out.println(order.getConsumer().getPassword());
    }


}
