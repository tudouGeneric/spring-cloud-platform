package org.honeybee.base.test;

import lombok.Data;
import org.honeybee.base.common.OptionalBean;

/**
 * 测试OptionalBean的类
 */
@Data
public class TestOptionalBeanUser {

    private String name;

    private School school;

    @Data
    public static class School {

        private String scName;

        private String scAddress;

    }

    public static void main(String[] args) {
        TestOptionalBeanUser user = new TestOptionalBeanUser();
        TestOptionalBeanUser.School school = new TestOptionalBeanUser.School();

//        user.setName("hello");

        //1. 基本调用
        String value = OptionalBean.ofNullable(user)
                            .getBean(TestOptionalBeanUser::getName).get();
        System.out.println("1.学生名字:" + value);

        String value1 = OptionalBean.ofNullable(user)
                            .getBean(TestOptionalBeanUser::getSchool)
                            .getBean(School::getScAddress).get();
        System.out.println("1.学校地址:" + value1);

        //2. 扩展的isPresent方法, 用法与Optional一样
        boolean present = OptionalBean.ofNullable(user)
                            .getBean(TestOptionalBeanUser::getName).isPresent();
        System.out.println("2.学生姓名是否存在：" + present);

        boolean present1 = OptionalBean.ofNullable(user)
                            .getBean(TestOptionalBeanUser::getSchool)
                            .getBean(School::getScAddress).isPresent();
        System.out.println("2.学校地址是否存在: " + present1);

        //3.扩展的ifPresent方法
        OptionalBean.ofNullable(user)
                    .getBean(TestOptionalBeanUser::getName)
                    .ifPresent(nam -> {
                        nam = nam + "+自定义操作";
                        System.out.println(String.format("3.学生姓名存在: %s", nam));
                    });

        OptionalBean.ofNullable(user)
                    .getBean(TestOptionalBeanUser::getSchool)
                    .getBean(TestOptionalBeanUser.School::getScAddress)
                    .ifPresent(adress ->  {
                        adress = adress + "+自定义操作";
                        System.out.println(String.format("3.学校地址存在: %s", adress));
                    });

        //4.扩展的orElse方法
        String val = OptionalBean.ofNullable(user)
                        .getBean(TestOptionalBeanUser::getName).orElse("佚名");
        System.out.println("4.学生姓名: " + val);

        String val1 = OptionalBean.ofNullable(user)
                        .getBean(TestOptionalBeanUser::getSchool)
                        .getBean(TestOptionalBeanUser.School::getScAddress).orElse("家里蹲");
        System.out.println("4.学校地址: " + val1);

        //5.扩展的 orElseThrow

        try {
            String values = OptionalBean.ofNullable(user)
                    .getBean(TestOptionalBeanUser::getName)
                    .orElseThrow(() -> {
                        return new RuntimeException("5.学生姓名空指针了");
                    });
            System.out.println("5.学生姓名:" + values);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            String values1 = OptionalBean.ofNullable(user)
                    .getBean(TestOptionalBeanUser::getSchool)
                    .getBean(TestOptionalBeanUser.School::getScAddress).orElseThrow(() -> new RuntimeException("5.学校地址空指针了"));
            System.out.println("5.学校地址:" + values1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

}
