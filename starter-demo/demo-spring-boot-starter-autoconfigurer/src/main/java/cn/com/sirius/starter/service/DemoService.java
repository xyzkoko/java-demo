package cn.com.sirius.starter.service;

import cn.com.sirius.starter.configuration.DemoProperties;

/**
 * Starter提供的示例服务类
 * @author Sirius
 */
public class DemoService {

    private DemoProperties demoProperties;

    public DemoService(DemoProperties demoProperties) {
        this.demoProperties = demoProperties;
    }

    public void start() {
        System.out.println("start!");
        System.out.println(demoProperties.getPath());
    }
}
