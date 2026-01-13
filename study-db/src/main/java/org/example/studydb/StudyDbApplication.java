package org.example.studydb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 扫描所有Mapper接口包

@MapperScan("org.example.studydb.mapper")
@SpringBootApplication(scanBasePackages = "org.example.studydb")
public class StudyDbApplication {


    public static void main(String[] args) {
        SpringApplication.run(StudyDbApplication.class, args);
        System.out.println("===== 学习管理系统启动成功 =====");
    }

}
