package br.com.sulamerica.susis.controller;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.sulamerica.susis.core.Cics;

@Controller
@RequestMapping("pagamento")
public class PagamentoBoletoController {
	
	private final JndiLocatorDelegate jndi = JndiLocatorDelegate.createDefaultResourceRefLocator();
	
	@Autowired
	private Cics cics;
	
	@RequestMapping(value="/boleto", method=RequestMethod.GET)
	@ResponseBody
	public String boleto(){
		try {
			String profile = jndi.lookup("nb/sulamerica_env", String.class);
			System.out.println(profile);
			
			cics.execute("teste");
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "sucess";
	}

}
