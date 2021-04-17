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

    /** 把一个临时对象变为一个持久化对象，并且保存到数据库 */
    public static void save(Session session){
        Student stu = new Student();
        stu.setUsername("wtywty");
        stu.setPassword("123456");
        stu.setCreateTime(new Date());

        System.out.println(stu);

        session.save(stu);

        /**
         * 使一个临时对象变为持久化对象
         * 为对象分配ID
         * flush缓存时会发送一条inset语句
         * 在save方法之前的ID设置是无效的
         * 持久化对象ID是不能被修改的
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
         * persist 也会执行 insert
         * persist 和 save 的区别
         * 在persist方法执行之前若是设置了ID，则抛出异常。
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
         * 当查询一个id不存在的数据时，load抛异常，get 返回  null
         * load会延迟加载
         * get查一级缓存，没有查二级缓存，没有查数据库
         * load查一级缓存，查不到就创建代理，实际getXXX时，才会去查询二级缓存和数据库
         *
         * load方法可能会抛出懒加载异常
         * */
        Student load = session.load(Student.class, 10);
        System.out.println(load.getPassword());
        return load;

    }

    public  static void update(Session session){

        /**
         * 1：查询到数据，修改实体，在有事务的情况下，事务提交会执行 flush操作，把缓存的数据和数据库进行同步
         * 会自动update,所以说session.update可以不写，但是写了不报错。
         *
         * 2:查询到数据，事务提交，session关闭，再次获得session、开器事务，修改student 的值，是不会去修改数据库的。
         *  但是用了update就可以修改。
         *  更新一个游离对象，需要主动调用update才行
         *
         *  3：游离对象哪怕没发生数据变化，也会去更新。
         *
         *  4：如何不让update盲目的去发送update语句？
         *  在hbm.xml文件 的 <class> 标签 设置一个属性 select-before-update=true
         * 	一般不需要设置。
         *
         *  5：如果update了数据库不存在的数据会报错。
         *
         * 	6：Session缓存中已经有了一个对象，这时候这个Session就不能去操作另外一个游离对象
         *
         * */
        Student stu = session.get(Student.class,8);
        stu.setUsername("白萝1");

        session.close();

        Session session1 = HibernateFactory.getSession();
        Transaction transaction = session1.beginTransaction();

        //Session缓存中已经有了一个对象，这时候这个Session就不能去操作另外一个游离对象
        Student stu2 = session1.get(Student.class, 8);
        session1.update(stu);

        transaction.commit();
        session.close();


//        session.close();
//        session = HibernateFactory.getSession();
//        stu.setUsername("白萝1");

//        session.update(stu);
    }



    /**
     * 1：若 OID 不为 null，但是数据库中还没有相应的记录，会抛异常
     * */
    public static void saveOrupdate(Session session){

        Student student = new Student("Aa","aa",new Date());
        session.saveOrUpdate(student);
    }


    /**
     * delete : 执行删除操作
     * 只要OID和数据库中的一条记录对应，就删除。
     * 如果OID 在数据表中没有对应记录，抛出异常
     *
     * 可以通过设置hibernate的 hibernate.use_identifier_rollback 属性 为 true,
     * 是删除对象后，把OID置为 null
     * */
    public static void delete(Session session){

        //临时对象
        Student student = new Student();
        student.setId(8);

        //持久化对象
        Student student1 = session.get(Student.class, 13);

        session.delete(student1);

        System.out.println(student1);

    }

    /**
     * 从Session缓存中把指定的持久化对象移除。
     * */
    public static void evict(Session session){
        Student stu = session.get(Student.class, 14);
        Student stu1 = session.get(Student.class, 15);

        stu.setUsername("白萝");
        stu1.setUsername("admin");

        //移除缓存中的stu对象。
        session.evict(stu);
    }


    /**
     * Hibernate调用存储过程
     * */
    public static void doWork(Session session){
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println(connection);
                //调用存储过程，我们在JDBC讲过了。
            }
        });
    }

    /**
     * 动态更新
     * */
    public static void dynamicUpdate(Session session){
        //dynamic-update="true",sql里面只包含改了的数据，不改的不更新。
        Student student = session.get(Student.class, 14);
        student.setUsername("AAAAABBBCC");

    }

    /**
     * increment 主键生成策略
     * */
    public static void increment(Session session){
        //<generator class="increment"></generator>
        session.save(new Student("wty","123",new Date()));
    }

    /**
     * identity 主键生成策略
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
     * 多对一的关系映射 Insert
     * */
    public static void manyToOneInsert(Session session){
        Consumer consumer = new Consumer();
        consumer.setName("小强");
        consumer.setPassword("123456");

        Order order1 = new Order();
        order1.setOrderName("情趣小强");

        order1.setConsumer(consumer);

        Order order2 = new Order();
        order2.setOrderName("情趣小强 - Plus");

        order2.setConsumer(consumer);

        /**
         * 先插入客户，再插入两个订单 -> 三条insert语句
         * */
//        session.save(consumer);
        session.save(order1);
        session.save(order2);
        /**
         * 先插入两个订单，再插入客户 -> 三条insert语句，两个update 修改订单的关联关系
         * */
        session.save(consumer);

    }

    /**
     * 多对一的关系映射 query
     * */
    public static void manyToOneQuery(Session session){
        Order order = session.get(Order.class, 2L);
        System.out.println(order);
        //若关联的Consumer是一个代理对象，就会发生懒加载异常 LazyInitializationException
        System.out.println(order.getConsumer());
    }

    /**
     * 多对一的关系映射 使用query 去 update
     * */
    public static void manyToOneQueryFromUpdate(Session session){

        Order order = session.get(Order.class, 2L);
        order.getConsumer().setName("二强");

    }

    /**
     * 多对一的关系映射 删除一  Consumer
     * */
    public static void manyToOneDeleteConsumer(Session session){
        //外键引用，无法删除。。。
        //要想删除必须要先清楚与之关联的对象
        Consumer consumer = session.get(Consumer.class, 2L);
        session.delete(consumer);
    }

    /**
     * 双向一对多映射关系 新增
     * */
    public static void OneToManyInsert(Session session){
        Project project = new Project();
        project.setProjectName("一站式项目管理");

        Industry industry = new Industry();
        industry.setIndustryName("公交行业");
        industry.setProject(project);

        Industry industry1 = new Industry();
        industry1.setIndustryName("地铁行业");
        industry1.setProject(project);

        Set<Industry> list = new HashSet<>();

        list.add(industry);
        list.add(industry1);

        project.setIndustrySet(list);

        /** 三条Insert + 四条 update语句 */
        session.save(industry);
        session.save(industry1);
        /** 三条Insert + 两条 update语句 */
        session.save(project);
//        session.save(industry);
//        session.save(industry1);

        /**
         * 原因是双端都在维护者两者之间的关系
         * many-to-one
         * one-to-many
         * 解决方法：
         *  1：不往Project里面的集合添加元素,让many-to-one自己来维护关系
         *  2：<set>标签的reverse属性 true
         *  3:提高效率的话，先去创建一，再去创建多。
         *
         * */
    }

    /**
     * 双向一对多映射关系 查询
     * */
    public static void OneToManyQuery(Session session){
        Project project = session.get(Project.class, 1L);
        /**
         * 引发StackOverflowError
         * 1：对 n 的一段延迟加载。。。。
         * */
        //        System.out.println(project.toString());
        System.out.println(project.getProjectName());

//        session.close();

        /**
         * org.hibernate.collection.internal.PersistentSet
         * 2：返回多的一端的集合是 hibernate 内置的集合类型
         * 该类型具有延迟加载和存放代理对象的功能
         *
         * 3：可能会抛出懒加载异常
         * */
        System.out.println(project.getIndustrySet().getClass().getName());

        /**
         * 4:在集合的元素需要使用的时候进行加载。。。
         * 怎么获取到数据呢？？我不想报错。。。。
         * 答案：迭代器
         * 坑爹的是。。。多的那一方的实体覆盖toString()的时候不要覆盖一的那一方。
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
     * 双向一对多映射关系 更新
     * */
    public static void OneToManyUpdate(Session session){

        Project project = session.get(Project.class, 1L);
        project.setProjectName("项目管理AAA");
        for(Industry industry : project.getIndustrySet()){
            if(industry.getId() == 2){
                industry.setIndustryName("一站式地铁行业");
            }
        }
    }

    /**
     * 双向一对多映射关系 删除
     * 级联关系下，不能直接删除被关联的 一
     * */
    public static void OneToManyDelete(Session session){
        Project project = session.get(Project.class, 1L);
        session.delete(project);
    }

    public static void cascade(Session session){
        /**
         * 友情提示：以下全部设置在一的hbm.xml里面，而且开发的时候别这么玩。。
         * cascade="delete-orphan"
         * */
//        Project project = session.get(Project.class, 2L);
//        project.getIndustrySet().clear();

        /**
         * 不设置
         * */
//        Project project = session.get(Project.class, 3L);
//        project.getIndustrySet().clear();

        /**
         * cascade="save-update"
         * */
//        Project project = new Project();
//        project.setProjectName("网吧上网下机系统");
//
//        Industry industry = new Industry();
//        industry.setIndustryName("互联网行业");
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
     * one-to-one 外键映射关系 保存
     * */
    public static void one2oneInsert(Session session){
        People01 people01 = new People01();
        people01.setName("张三狗");

        IdCard01 idCard01 = new IdCard01();
        idCard01.setNum("333333");
        idCard01.setPeople(people01);

        people01.setIdCard(idCard01);

//        session.save(people01);
        session.save(idCard01);
        session.save(people01);

        /**
         * 观察sql会多出来一个update..和之前双向对应关系一样的情况
         * 1：先保存身份证，在保存人.
         *      其实就是先保存没有外键的一方
         * */

    }

    /**
     * one-to-one 外键映射关系 查询1
     * */
    public static void one2oneQuery(Session session){
        People01 people01 = session.get(People01.class, 1L);
        System.out.println(people01);
        /**
         * 这里也可能会出现懒加载异常
         * 注意toString()...
         * */
        System.out.println(people01.getIdCard());

        /**
         * <one-to-one name="people" class="People01" property-ref="idCard"></one-to-one>
         * property属性加上之后会以外键连表查询
         * 不加property属性的话会以 name属性作为条件
         *
         * */

    }

    /**
     * one-to-one 外键映射关系 查询2
     * */
    public static void one2oneQuery02(Session session){

        /**
         * 看日志 输入的sql，居然查询了People。。。
         * 在查询没有外键的指定对象时，会使用联表查询，查询出关联对象和本对象，一并初始化加载。
         * */
        IdCard01 idCard01 = session.get(IdCard01.class, 1L);
        System.out.println(idCard01);

    }


    /**
     * one-to-one 主键映射关系 保存
     * */
    public static void one2oneSave(Session session){
        People02 people02 = new People02();
        people02.setName("高111狗");

        IdCard02 idCard02 = new IdCard02();
        idCard02.setNum("4534354354");
        idCard02.setPeople(people02);

        people02.setIdCard(idCard02);

        session.save(idCard02);
        session.save(people02);
    }

    /**
     * one-to-one 主键映射关系 查询
     * */
    public static void one2oneGet(Session session){
        People02 people02 = session.get(People02.class, 2L);
        System.out.println(people02);

        IdCard02 idCard021 = session.get(IdCard02.class, 2L);
        /**
         * 主键关联不需要使用property-ref 属性，不然以外键查询就不对了。
         * */
        System.out.println(idCard021);
    }

    /**
     * 单向many-to-many 映射关系 新增
     * */
    public static void many2manyinsert(Session session){
        Category category = new Category();
        category.setName("商品02");

        Item item = new Item();
        item.setName("商品属性1");

        Item item1 = new Item();
        item1.setName("商品属性2");

        Set<Item> items = new HashSet<>();
        items.add(item);
        items.add(item1);

        category.setItems(items);

        session.save(category);
        session.save(item);
        session.save(item1);
    }

    /**
     * 单向many-to-many 映射关系 查询
     * */
    public static void many2manyQuery(Session session){
        Category category = session.get(Category.class, 1L);
        System.out.println(category);
        System.out.println(category.getItems().toString());
    }

    /**
     * 双向any-to-many 映射关系 新增
     * */
    public static void many2manySave(Session session){
        Category02 category02 = new Category02();
        category02.setName("双向-多对多-映射关系");

        Item02 item = new Item02();
        item.setName("AAA");

        Item02 item1 = new Item02();
        item1.setName("BBB");

        Set<Category02>  category02s = new HashSet<>();
        category02s.add(category02);

        item.setCategorys(category02s);
        item1.setCategorys(category02s);


        /**
         * 两边都维护关联关系，导致主键重复，一遍维护就行
         * 1：只让Category里面放item
         * 2：inverse属性
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
     * 双向any-to-many 映射关系 查询
     * */
    public static void many2manyGet(Session session){
        Category02 category02 = session.get(Category02.class, 1L);
        System.out.println(category02);
        System.out.println(category02.getItems().toString());
    }

    /**
     * 映射继承关系 subclass 新增
     * */
    public static void subClassInster(Session session){
        /**
         * 对于子类来说，直接插入即可
         * 辨别关系，Hibernate来维护。
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
     * 映射继承关系 subclass 查询
     * */
    public static void subClassQuery(Session session){
        List from_parent_ = session.createQuery("from Parent ").list();
        System.out.println(from_parent_.toString());

        /**
         * 查询子类只需要查询一张数据表
         * 查询子类也只需要查询一张数据表
         *
         * 缺点：
         *  使用了辨别者列。。
         *  子类独有的字段不能添加非空约束
         *  若继承层次比较深，则头疼。因为数据库表的字段也多了。
         * */
        List from_children_ = session.createQuery("from Children ").list();
        System.out.println(from_children_.toString());
    }

    /**
     * 映射继承关系 joined-subclass 新增
     * */
    public static void joinedSubClassInsert(Session session){

        /**
         * 需要额外插入一张表。
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
     * 映射继承关系 joined-subclass 查询
     * */
    public static void joinedSubClassQuery(Session session){

        /**
         * 优点：
         * 不需要使用辨别者列
         * 子类独有的字段能添加非空约束
         * 父子之间的数据没有冗余。
         * */
        List from_parent_ = session.createQuery("from Parent02 ").list();
        System.out.println(from_parent_.toString());

        List from_children_ = session.createQuery("from Children02 ").list();
        System.out.println(from_children_.toString());
    }

    /**
     * 映射继承关系 union-subclass 新增
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
     * 映射继承关系 union-subclass 查询
     * */
    public static void unionSubClassQuery(Session session){
        /**
         * 优点：
         *  不需要使用辨别者列
         *  子类可以添加非空约束
         *
         * 缺点：
         *  查询父表记录需要子查询，然后数据汇总，效率低点。
         *  存在冗余字段
         *  更新比较麻烦
         *
         * */

        List from_parent_ = session.createQuery("from Parent03 ").list();
        System.out.println(from_parent_.toString());

        List from_children_ = session.createQuery("from Children03 ").list();
        System.out.println(from_children_.toString());
    }

    /**
     * 映射继承关系 union-subclass 更新 --  更新父表效率太低，映射最简单
     * */
    public static void unionSubClassUpdate(Session session){
        /**
         * hql 语句后面会说到
         * */
        String hql = "update Parent03 p set p.name = '我是一个粉刷匠，粉刷本领强' where p.id = 1";
        int i = session.createQuery(hql).executeUpdate();
        System.out.println(i);
    }

    /**
     * 检索策略 新增数据。
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
     * 类级别的检索策略 查询
     * */
    public static void classLevelStrategyQuery(Session session){
        //<class name="Consumer02" table="consumer02" lazy="false">
        /**
         * 设置为true 的时候 会代理对象
         * 设置为false 会直接从数据库获取到多项
         * */
        Consumer02 consumer02 = session.load(Consumer02.class, 1L);
        System.out.println(consumer02.getClass().getName());

        /**
         * lazy="true"
         * 在应用程序第一次访问代理类实例的非 OID 属性时, Hibernate 会初始化代理类实例
         * */
        System.out.println(consumer02.getId());

        System.out.println(consumer02.getName());
    }

    /**
     * 一对多和多对多的检索策略: 查询
     * */
    public static void oneToManyLevelStrategyQuery(Session session){

        //<class name="Consumer02" table="consumer02" lazy="true">
        Consumer02 consumer = session.get(Consumer02.class, 1L);

        /**
         * 使用Order的属性时候才加载Order
         * <set>标签的lazy属性可以修改检索策略
         * lazy 不建议设置为 false
         *
         * <set name="orders" table="order02" lazy="extra"> 增强延迟检索
         * */
//        System.out.println(consumer.getOrders());
        /**
         * lazy="extra"会整一个count(id) 来查询 Order表。。。
         * 尽可能延迟集合初始化的时机。
         * */

        System.out.println(consumer.getOrders().size());

        //即使lazy=false 执行这个代码也会去加载数据
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
                 * 4 条 SQL 语句
                 * 原因是，有一个没有关联的数据，他返回了一个空的集合。
                 *
                 * 批量的让Orders集合进行初始化么？
                 * <set> 标签里面可以设置 batch-size="5" 的方式来实现
                 * */
                System.out.println(consumer.getOrders().size());
            }
        }
    }


    /**
     * 多对一的检索策略: 查询
     * */
    public static void many2OneStragetegy(Session session){
        /**
         * 不但获取Order,也获取了Consumer
         * */
        //lazy=false fetch="join"
        Order order = session.get(Order.class,2L);
        System.out.println(order);

        //不会再有另外的查询语句了，因为fetch="join"，一并查询所有数据了
        System.out.println(order.getConsumer().getPassword());
    }


}
