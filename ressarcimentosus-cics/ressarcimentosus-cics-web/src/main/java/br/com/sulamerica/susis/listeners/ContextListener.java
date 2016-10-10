package br.com.sulamerica.susis.listeners;

import javax.naming.NamingException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.web.context.ConfigurableWebApplicationContext;


public class ContextListener implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {
	
	private Log log = LogFactory.getLog(ContextListener.class);
	private final JndiLocatorDelegate jndi = JndiLocatorDelegate.createDefaultResourceRefLocator();
	private final String AMBIENTE = "nb/sulamerica_env";

	@Override
	public void initialize(ConfigurableWebApplicationContext configurableApplicationContext) {
		log.info(String.format("Iniciando o contexto do spring."));
		try {
			String profile = jndi.lookup(AMBIENTE, String.class);
			log.info(String.format("Configurando o profile: %s", profile));
			ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
			environment.setActiveProfiles(profile);
		} catch (NamingException e) {
			log.error(String.format("Nao foi possivel localizar a variavel de ambiente %s ", AMBIENTE ));
			throw new IllegalArgumentException(String.format("Nao foi configurado a variavel ambiente Servidor: %s", ExceptionUtils.getMessage(e)));
		} 
	}
}