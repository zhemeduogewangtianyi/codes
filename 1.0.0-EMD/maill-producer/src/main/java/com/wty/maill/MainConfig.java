package com.wty.maill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc //启用spring mvc
@Configuration //项目启动时识别配置，表示这个类是个配置类 -> xml
@ComponentScan({"com.wty.maill.*"}) //@Service 啥的进行扫描
//@ImportResource(value = "") //帮你引入你自己写的xml
@MapperScan(basePackages="com.wty.maill.mapper")
public class MainConfig {
}
