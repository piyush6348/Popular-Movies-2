package com.example.dell.movieapiproject1a;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by dell on 3/11/2016.
 */
@SimpleSQLConfig(
        name ="TestProvider",
        authority ="just.some.test_provider.authority",
        database = "test.db",
        version = 1
)
public class TestProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
