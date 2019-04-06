package com.lujin.demo.vo;

import com.lujin.demo.pojo.Patient;
import com.lujin.demo.pojo.Record;
import lombok.Data;

@Data
public class RecordPatient {
    Record record;
    Patient patient;
}
