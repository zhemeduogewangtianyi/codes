package com.wty.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wty.redis.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApplication.class})
public class RedisApplicationTests {

    @Test
    public void contextLoads() {

        Jedis j = new Jedis("192.168.112.131",6379);
        j.auth("wty");
        List<String> mget = j.mget("name");
        System.out.println(mget.toString());

        Map<String,String> map = new HashMap<>();
        map.put("name","wty");
        map.put("age","19");
        map.put("username","admin");
        map.put("password","admin");
        j.hmset("user",map);

        List<String> user = j.hmget("user", "name", "age", "username", "password");
        System.out.println(user.toString());

    }

    @Test
    public void userToRedis(){
        Jedis jedis = new Jedis("192.168.112.131",6379);
        jedis.auth("wty");

        Map<String,String> map = new HashMap<>();
        for(int i = 0 ; i < 10 ; i++){
            User u ;
            String uuid ;
            if(i % 2 == 0){
                uuid = UUID.randomUUID().toString();
                u = new User(uuid,"w"+i,18+i,"n");
            }else{
                uuid = UUID.randomUUID().toString();
                u = new User(uuid,"w"+i,18+i,"v");
            }
            map.put(uuid, JSON.toJSONString(u));
        }

        for(String s : map.keySet()){
            System.out.println(map.get(s).toString());
        }

        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println();

        jedis.hmset("SYS_USER_TABLE",map);

        Set<String> sys_user_table_key = jedis.hkeys("SYS_USER_TABLE");
        System.out.println(sys_user_table_key);

        List<String> sys_user_table_value = jedis.hvals("SYS_USER_TABLE");
        System.out.println(sys_user_table_value);

    }

    @Test
    public void userFilter(){
        Jedis j = new Jedis("192.168.112.131",6379);
        j.auth("wty");

        /**
         * select * from user where age = 25
         * select * from user where age = 25 and gender = 'n'
         *
         * 想办法结合其他的类型。。。
         * 比如 Hash 和 Set同时使用
         *
         * 指定业务：
         *  USER_SEL_AGE_25
         *  USER_SEL_GENDER_n
         *  USER_SEL_GENDER_v
         * */

        final String USER_TABLE = "USER_TABLE";
        final String USER_SEL_AGE_25 = "USER_SEL_AGE_25";
        final String USER_SEL_GENDER_n = "USER_SEL_GENDER_n";
        final String USER_SEL_GENDER_v = "USER_SEL_GENDER_v";


        Map<String,String> map = new HashMap<>();
        for(int i = 0 ; i < 10 ; i++){
            User u ;
            String uuid ;
            int age;
            String gender;
            if(i % 2 == 0){
                age = 18+i;
                gender = "n";
                uuid = UUID.randomUUID().toString();
                u = new User(uuid,"w"+i,age,gender);
                if(age == 25){
                    j.sadd(USER_SEL_AGE_25,uuid);
                }
                j.sadd(USER_SEL_GENDER_n,uuid);
            }else{
                age = 18+i;
                gender = "v";
                uuid = UUID.randomUUID().toString();
                u = new User(uuid,"z"+i,18+i,gender);
                if(age == 25){
                    j.sadd(USER_SEL_AGE_25,uuid);
                }
                j.sadd(USER_SEL_GENDER_v,uuid);
            }
            map.put(uuid, JSON.toJSONString(u));
        }

        j.hmset(USER_TABLE,map);

    }

    @Test
    public void userFilterGet(){
        Jedis j = new Jedis("192.168.112.131",6379);
        j.auth("wty");

        /**
         * select * from user where age = 25
         * select * from user where age = 25 and gender = 'n'
         * select * from user where age = 25 and gender = 'v'
         * select * from user where age = 25 and gender = 'n'
         */

        //思路就是，把条件的key作为要查询的key去差USER_TABLE

        final String USER_TABLE = "USER_TABLE";
        final String USER_SEL_AGE_25 = "USER_SEL_AGE_25";
        final String USER_SEL_GENDER_n = "USER_SEL_GENDER_n";
        final String USER_SEL_GENDER_v = "USER_SEL_GENDER_v";

        //年龄25  select * from user where age = 25
        Set<String> age = j.smembers(USER_SEL_AGE_25);
        String[] age_25 = age.toArray(new String[]{});
        List<String> a_25 = j.hmget(USER_TABLE, age_25);
        System.out.println(a_25.toString());

        //性别男  select * from user where age = 25 and gender = 'n'
        Set<String> nan = j.smembers(USER_SEL_GENDER_n);
        String[] g_n_array = nan.toArray(new String[]{});
        List<String> g_n = j.hmget(USER_TABLE, g_n_array);
        System.out.println(g_n.toString());

        //性别女  select * from user where age = 25 and gender = 'v'
        Set<String> nv = j.smembers(USER_SEL_GENDER_v);
        String[] g_v_array = nv.toArray(new String[]{});
        List<String> g_v = j.hmget(USER_TABLE, g_v_array);
        System.out.println(g_v.toString());

        //年龄25 性别男  select * from user where age = 25 and gender = 'n'
        Set<String> sinter = j.sinter(USER_SEL_AGE_25,USER_SEL_GENDER_v);
        String[] param_key = sinter.toArray(new String[]{});
        List<String> res = new ArrayList<>();
        if(param_key != null && param_key.length > 0){
            res = j.hmget(USER_TABLE, param_key);
        }
        List<User> users = new ArrayList<>();
        for(String str : res){
            User user = JSONObject.parseObject(str, User.class);
            users.add(user);
        }
        System.out.println(users.toString());

    }



}
