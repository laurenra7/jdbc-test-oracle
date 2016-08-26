# jdbcTestOracle
Test JDBC connection using Oracle JDBC driver.

See pom.xml for what driver version is used. Currently **ojdbc6** version **11.2.0.3.0.**

**Build it:** 

```shell
mvn clean package
```

**Run it:**

```shell
java -jar target/jdbcTestOracle.jar
```

You can include the username and connection URL on the command line. If you don't include them on the command line, you will be prompted for them. You will always be prompted for the password. 

**Command line options:**

```
usage: java -jar jdbcTest.jar [-h] [-n <userName>] [-u <URL>]

Test JDBC connections

  -h,--help                  Show this help
  -n,--username <userName>   Database user name
  -u,--url <URL>             URL to test

Example:

  java -jar jdbcTestOracle.jar -u jdbc:oracle:thin:@ldap://my.server.com:456/dbname,cn=MyContext,dc=organization,dc=domain
```

