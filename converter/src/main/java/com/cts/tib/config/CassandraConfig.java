package com.cts.tib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@PropertySource(value = { "classpath:cassandra.properties" })
@EnableCassandraRepositories(basePackages = { "com.java.repository" })
public class CassandraConfig extends AbstractCassandraConfiguration {


//this will make the connection with your local host
    @Bean
    public CassandraClusterFactoryBean cluster() {

        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints("localhost");

        return cluster;
    }


    @Bean
    @Override
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {

        BasicCassandraMappingContext mappingContext = new BasicCassandraMappingContext();

        mappingContext.setInitialEntitySet(CassandraEntityClassScanner.scan(getEntityBasePackages()));

        org.springframework.data.convert.CustomConversions customConversions = customConversions();

        mappingContext.setCustomConversions(customConversions);
        mappingContext.setSimpleTypeHolder(customConversions.getSimpleTypeHolder());
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), getKeyspaceName()));

        return mappingContext;
    }

    @Bean
    @Override
    public CassandraConverter cassandraConverter() {

        MappingCassandraConverter mappingCassandraConverter = null;
		try {
			mappingCassandraConverter = new MappingCassandraConverter(cassandraMapping());
	        mappingCassandraConverter.setCustomConversions(customConversions());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        return mappingCassandraConverter;
    }

// this will create a session between your appliation and your keyspaces
    @Bean
    @Override
    public CassandraSessionFactoryBean session() {

        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();

        session.setCluster(cluster().getObject());
        session.setConverter(cassandraConverter());
        session.setKeyspaceName(getKeyspaceName());
        session.setStartupScripts(getStartupScripts());
        session.setShutdownScripts(getShutdownScripts());

        return session;
    }

//this will make the repository bean for your application
    @Bean
    @Override
    public CassandraAdminTemplate cassandraTemplate() throws Exception {
        return new CassandraAdminTemplate(session().getObject(), cassandraConverter());
    }

//This schema action define if you doesn't create table it will create for you.
    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }
//this will return your keyspace name
    @Override
    protected String getKeyspaceName() {
        return "KeyspaceName";
    }
//this is for defining the domain package
    @Override
    public String[] getEntityBasePackages() {
        return new String[] {"com.java.repository"}; //com.example package contains the bean with @table annotation
    }
}
	/*
	 * private static final Logger LOGGER = Logger.getLogger(CassandraConfig.class);
	 * 
	 * @Autowired private Environment environment;
	 * 
	 * @Override protected String getKeyspaceName() { return
	 * environment.getProperty("cassandra.keyspace"); }
	 * 
	 * @Override
	 * 
	 * @Bean public CassandraClusterFactoryBean cluster() { final
	 * CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
	 * cluster.setContactPoints(environment.getProperty("cassandra.contactpoints"));
	 * cluster.setPort(Integer.parseInt(environment.getProperty("cassandra.port")));
	 * LOGGER.info("Cluster created with contact points [" +
	 * environment.getProperty("cassandra.contactpoints") + "] " + "& port [" +
	 * Integer.parseInt(environment.getProperty("cassandra.port")) + "]."); return
	 * cluster; } public CassandraMappingContext cassandraMapping() throws
	 * ClassNotFoundException {
	 * 
	 * BasicCassandraMappingContext mappingContext = new
	 * BasicCassandraMappingContext();
	 * 
	 * mappingContext.setInitialEntitySet(CassandraEntityClassScanner.scan(
	 * getEntityBasePackages()));
	 * 
	 * 
	 * 
	 * return mappingContext; }
	 * 
	 * @Override
	 * 
	 * @Bean public CassandraMappingContext cassandraMapping() throws
	 * ClassNotFoundException { return new BasicCassandraMappingContext(); }
	 * 
	 */