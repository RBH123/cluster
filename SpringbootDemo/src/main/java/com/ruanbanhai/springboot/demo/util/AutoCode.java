package com.ruanbanhai.springboot.demo.util;

import com.zengtengpeng.autoCode.StartCode;

public class AutoCode {
    public static void main(String[] args) {
        StartCode startCode = t->{};
        startCode.start(StartCode.saxYaml("auto-code.yml"));
    }
}
