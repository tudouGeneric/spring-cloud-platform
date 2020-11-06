package org.honeybee.rbac;

import org.honeybee.Application;
import org.honeybee.rbac.entity.RbacDepartment;
import org.honeybee.rbac.service.RbacDepartmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RbacServiceTest {

    @Autowired
    private RbacDepartmentService rbacDepartmentService;

    @Test
    public void testRbacDept() {
        List<RbacDepartment> result = rbacDepartmentService.getDepartmentTree(null);

    }

}
