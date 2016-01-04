package com.nixsolutions.ponarin.dao.impl;

import static org.dbunit.Assertion.assertEqualsIgnoreCols;

import org.dbunit.dataset.ITable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nixsolutions.ponarin.dao.RoleDao;
import com.nixsolutions.ponarin.entity.Role;
import com.nixsolutions.ponarin.utils.DbTestHelper;

public class JdbcRoleDaoTest {
    private static final String DATASET_COMMON = "dataset/role/common.xml";
    private static final String TABLE_EMPTY = "dataset/role/empty.xml";
    private static final String TABLE_NAME = "ROLE";
    private static final String[] IGNORE_COLS = { "ROLE_ID" };
    private RoleDao roleDao;
    private DbTestHelper dbTestHelper;
    private Role[] roles;

    @BeforeClass
    public static void generalSetUp() throws Exception {
        new DbTestHelper().initDb();
    }

    @Before
    public void setUp() throws Exception {
        roleDao = new JdbcRoleDao();
        dbTestHelper = new DbTestHelper();
        dbTestHelper.fill(DATASET_COMMON);

        // Configure Roles
        roles = new Role[3];

        Role role1 = new Role();
        role1.setId(1);
        role1.setName("role1");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("role2");

        Role role3 = new Role();
        role3.setId(3);
        role3.setName("role3");

        roles[0] = role1;
        roles[1] = role2;
        roles[2] = role3;
    }

    @Test(expected = NullPointerException.class)
    public void testCreateWithNull() {
        roleDao.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithBadArgs() {
        Role role = roles[1];
        role.setName("");
        roleDao.create(role);
    }

    @Test
    public void testCreate() throws Exception {
        dbTestHelper.fill(TABLE_EMPTY);
        String afterCreate = "dataset/role/afterCreate.xml";

        roleDao.create(roles[1]);

        ITable expected = dbTestHelper.getTableFromFile(TABLE_NAME,
                afterCreate);
        ITable actual = dbTestHelper.getTableFromSchema(TABLE_NAME);

        assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateWithNull() {
        roleDao.update(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateWithBadArgs() {
        Role role = roles[1];
        role.setName("");
        roleDao.update(role);
    }

    @Test
    public void testUpdate() throws Exception {
        String afterUpdate = "dataset/role/afterUpdate.xml";
        Role role = roles[2];
        role.setName("role55");

        roleDao.update(role);

        ITable expected = dbTestHelper.getTableFromFile(TABLE_NAME,
                afterUpdate);
        ITable actual = dbTestHelper.getTableFromSchema(TABLE_NAME);

        assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveWithNull() {
        roleDao.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveWithBadArg() {
        roles[0].setId(null);
        roleDao.remove(roles[0]);
    }

    @Test
    public void testRemove() throws Exception {
        String afterRemove = "dataset/role/afterRemove.xml";

        roleDao.remove(roles[1]);

        ITable expected = dbTestHelper.getTableFromFile(TABLE_NAME,
                afterRemove);
        ITable actual = dbTestHelper.getTableFromSchema(TABLE_NAME);

        assertEqualsIgnoreCols(expected, actual, IGNORE_COLS);
    }

    @Test(expected = NullPointerException.class)
    public void testFindByNameWithNull() {
        roleDao.findByName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByNameWithBadArg() {
        roleDao.findByName("");
    }

    @Test
    public void testFindByName() {
        Role role = roleDao.findByName(roles[2].getName());
        Assert.assertEquals("Roles must equals", roles[2], role);
    }

    @Test
    public void testFindById() {
        JdbcRoleDao jdbcRoleDao = (JdbcRoleDao) roleDao;
        Role role = jdbcRoleDao.findByRoleId(roles[0].getId());

        Assert.assertEquals("Roles must equals", roles[0], role);
    }
}
