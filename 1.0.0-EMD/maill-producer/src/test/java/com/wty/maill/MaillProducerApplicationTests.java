package com.wty.maill;

import com.alibaba.fastjson.JSON;
import com.wty.maill.constant.Const;
import com.wty.maill.entity.MailSend;
import com.wty.maill.entity.MstDict;
import com.wty.maill.enumeration.MailStatusEnum;
import com.wty.maill.enumeration.RedisPriorityQueueEnum;
import com.wty.maill.mapper.MstDictMapper;
import com.wty.maill.service.MstDictService;
import com.wty.maill.service.impl.sendredis.SendRedisFactory;
import com.wty.maill.utils.KeyUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ClusterOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.DefaultScriptExecutor;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.script.ScriptExecutor;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MaillProducerApplication.class})
public class MaillProducerApplicationTests {

    @Resource
    private MstDictMapper mstDictMapper;

    @Autowired
    private MstDictService mstDictService;

    @Test
    public void contextLoads() {
//        PageHelper.startPage(1,2);
//        List<MstDict> mstDicts = mstDictMapper.selectAll();
//        System.out.println(JSON.toJSONString(mstDicts));

        try {
            List<MstDict> byStatus = mstDictService.findByStatus("1");
            System.out.println(JSON.toJSONString(byStatus));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void testRedisCluster(){
        //操作集群的方法。。。
        //ClusterOperations clusterOperations = redisTemplate.opsForCluster();

        //操作节点。。。
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name","wty");

        Object name = valueOperations.get("name");
        System.out.println(name);

        Boolean delete = redisTemplate.delete("name");
        System.out.println(delete);

        Object name1 = valueOperations.get("name");
        System.out.println(name1);
    }

    @Autowired
    private SendRedisFactory factory;

    @Test
    public void testFactory(){
        MailSend mailSend = new MailSend();
        mailSend.setSendId(KeyUtils.generatorUUID());
        mailSend.setSendCount(0L);
        mailSend.setSendStatus(MailStatusEnum.DRAFT.getCode());
        mailSend.setVersion(0L);
        mailSend.setUpdateBy(Const.SYS_RUNTIME);
        mailSend.setSendPriority(9L);
        boolean send = factory.send(mailSend);
        System.out.println(send);
    }

    private static final String SCRIPT = "local t1 = redis.call('get',KEYS[1]); \r\n" +
            "if type(t1) == 'table' then \r\n" +
            "return t1; \r\n"+
            "end; \r\n";

    @Test
    public void testLua(){
        Jedis jedis = new Jedis("192.168.112.131",7001);
        String shakey = jedis.scriptLoad(SCRIPT);
        List<String> keys = new ArrayList<>();
        keys.add("name");
        List<String> args = new ArrayList<>();
        Object evalsha = jedis.evalsha(shakey, keys, args);
        if(evalsha != null){
            List<String> evalsha1 = (List<String>) evalsha;
            System.out.println(evalsha1);
        }
    }


    @Test
    public void testClusterLua(){

        final String EXECUTE = "local t1 = redis.call('get',KEYS[1]);"+ "\n" +
                "return t1; ";

        RedisScript<String> rs = new DefaultRedisScript(EXECUTE,String.class);

//        ScriptExecutor se = new DefaultScriptExecutor(redisTemplate);

        List<String> keys = new ArrayList<>();
        keys.add("name");

        String execute = redisTemplate.execute(rs, redisTemplate.getStringSerializer(), new GenericToStringSerializer<>(String.class), keys);
        System.out.println(execute);
    }

}
