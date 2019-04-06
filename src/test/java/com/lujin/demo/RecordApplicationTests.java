package com.lujin.demo;

import com.lujin.demo.mapper.RecordMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.RowId;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordApplicationTests {
    @Autowired
    RecordMapper recordMapper;
    @Test
    public void contextLoads() {
    }

}

