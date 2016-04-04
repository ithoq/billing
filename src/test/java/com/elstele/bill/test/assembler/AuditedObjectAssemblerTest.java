package com.elstele.bill.test.assembler;


import com.elstele.bill.assembler.AuditedObjectAssembler;
import com.elstele.bill.builder.bean.AuditedObjectBuilder;
import com.elstele.bill.domain.AuditedObject;
import com.elstele.bill.form.AuditedObjectForm;
import com.elstele.bill.test.builder.form.AuditedObjectFormBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class AuditedObjectAssemblerTest {
    private AuditedObject bean;
    private AuditedObjectForm form;
    private AuditedObjectAssembler assembler;

    @Before
    public void setUp(){
        Date date = new Date();
        AuditedObjectBuilder builder = new AuditedObjectBuilder();
        bean = builder.build()
                .withChangerName("test")
                .withChangedObjID(1)
                .withChangesDate(date)
                .withId(1)
                .getRes();

        AuditedObjectFormBuilder formBuilder = new AuditedObjectFormBuilder();
        form = formBuilder.build()
                .withChangerName("test")
                .withChangedObjID(1)
                .withChangesDate(date)
                .withId(1)
                .getRes();

        assembler = new AuditedObjectAssembler();
    }

    @After
    public void tearDown(){
        bean= null;
        form = null;
        assembler = null;
    }

    @Test
    public void fromBeanToFormTest(){
        AuditedObjectForm actual = assembler.fromBeanToForm(bean);
        assertTrue(actual.equals(form));
    }

    @Test
    public void fromFormToBeanTest(){
        AuditedObject actual = assembler.fromFormToBean(form);
        assertTrue(actual.equals(bean));
    }


}
