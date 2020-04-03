package com.ipanel.web.app.cv.task;

import com.ipanel.web.app.cv.business.area.service.impl.AreaServiceImpl;
import com.ipanel.web.app.cv.global.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author zhaolei
 * Create: 2018/12/19 10:25
 * Modified By:
 * Description:
 */
@Component
public class MyTask {
    private static Logger logger = LoggerFactory.getLogger(MyTask.class);

    @Async
    public void sayHello(String name) throws MyException {
        logger.info(name + ":Hello World!");
        for (int i = 0; i < 10; i++) {
            logger.info(name + " say:" + i);
            throw new MyException("异步线程抛出异常测试");
            //int l = 1/0;
        }
    }

}