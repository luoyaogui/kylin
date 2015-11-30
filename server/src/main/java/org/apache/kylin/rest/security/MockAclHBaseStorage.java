package org.apache.kylin.rest.security;

import java.io.IOException;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.kylin.rest.service.AclService;
import org.apache.kylin.rest.service.UserService;
import org.h2.util.StringUtils;

/**
 */
public class MockAclHBaseStorage implements AclHBaseStorage {

    private HTableInterface mockedAclTable;
    private HTableInterface mockedUserTable;
    private static final String aclTableName = "MOCK-ACL-TABLE";
    private static final String userTableName = "MOCK-USER-TABLE";

    @Override
    public String prepareHBaseTable(Class clazz) throws IOException {

        if (clazz == AclService.class) {
            mockedAclTable = new MockHTable(aclTableName, ACL_INFO_FAMILY, ACL_ACES_FAMILY);
            return aclTableName;
        } else if (clazz == UserService.class) {
            mockedUserTable = new MockHTable(userTableName, USER_AUTHORITY_FAMILY);
            return userTableName;
        } else {
            throw new IllegalStateException("prepareHBaseTable for unknown class: " + clazz);
        }
    }

    @Override
    public HTableInterface getTable(String tableName) throws IOException {
        if (StringUtils.equals(tableName, aclTableName)) {
            return mockedAclTable;
        } else if (StringUtils.equals(tableName, userTableName)) {
            return mockedUserTable;
        } else {
            throw new IllegalStateException("getTable failed" + tableName);
        }
    }
}