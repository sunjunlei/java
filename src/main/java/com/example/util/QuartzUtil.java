package com.example.util;

import com.example.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author sjl
 */
public class QuartzUtil {
    private static final Logger logger = LoggerFactory.getLogger(QuartzUtil.class);
    public static void invokeMethod(TaskEntity entity){
        logger.info("-------------------------【创建任务对象】开始创建任务对象----------------------" );
        String className = entity.getRunJobClassName();
        String methodName = entity.getRunJobMethodName();
        Object object = null;
        Class clazz = null;
        try{
            clazz = Class.forName(className);
            object = clazz.newInstance();

            Method declaredMethod = clazz.getDeclaredMethod(methodName);
            declaredMethod.invoke(object);
            logger.info("-------------------------【创建任务对象】创建任务对象成功，任务名：{}----------------------",entity.getJobName());
        }catch (ClassNotFoundException notFoundException){
            logger.error("------------------【创建任务对象】执行任务错误，找不到对应类{}-----------------",notFoundException.getMessage() );
        }catch (NoSuchMethodException noSuchMethodException){
            logger.error("------------------【创建任务对象】执行任务错误，找不到对应方法{}-----------------",noSuchMethodException.getMessage() );
        }catch (InstantiationException instantiationException){
            logger.error("------------------【创建任务对象】执行任务错误，无法实例化对象，建议：查看是否存在无参构造{}-----------------",instantiationException.getMessage() );
        }catch (IllegalAccessException illegalAccessException){
            logger.error("------------------【创建任务对象】执行任务错误，无法实例化对象，建议：查看类全限定名是否正确{}-----------------",illegalAccessException.getMessage() );
        }catch (InvocationTargetException invocationTargetException){
            logger.error("------------------【创建任务对象】执行任务错误，运行方法失败{}-----------------",invocationTargetException.getMessage() );
        }catch (Exception e){
            logger.error("------------------【创建任务对象】执行任务错误，未知错误{}-----------------",e.getMessage() );
        }

    }
}
