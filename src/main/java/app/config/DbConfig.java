package app.config;

import eco.m1.annotate.Dependency;
import eco.m1.jdbc.BasicDataSource;
import eco.m1.support.M1Helper;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DbConfig {

    public static final String DB = "starter";

    @Dependency
    public BasicDataSource dataSource(){
        Path dbPath = Paths.get("src","main", "h2");
        String url = "jdbc:h2:tcp://localhost:9092/" + M1Helper.getH2Path(DB, dbPath);
        BasicDataSource basicDataSource = new BasicDataSource.Builder()
                            .dbName(DB)
                            .driver("org.h2.Driver")
                            .url(url)
                            .username("sa")
                            .password("")
                            .build();
        return basicDataSource;
    }

}
