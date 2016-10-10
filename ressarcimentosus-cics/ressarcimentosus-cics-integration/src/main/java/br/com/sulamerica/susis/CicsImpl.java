package br.com.sulamerica.susis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.Interaction;
import javax.resource.cci.Record;
import javax.resource.cci.Streamable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jca.cci.core.support.CciDaoSupport;

import br.com.sulamerica.susis.core.Cics;

import com.ibm.connector2.cics.ECIInteractionSpec;

public class CicsImpl extends CciDaoSupport implements Cics {

	private Logger logger = Logger.getLogger(CicsImpl.class);
	
	private static final String ENTRADA = "MAPA003001INHOMOLGP001                                                      N32544950                  99     0043275813N                                                                               6500002135007000000165000021349000531620P                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ";

	@Autowired
	@Qualifier("connectionFactoryCics")
	private ConnectionFactory cf;

	public CicsImpl(ConnectionFactory connectionFactory) {
		logger.info("Construtor com conexao cics");
		setConnectionFactory(connectionFactory);
	}

	public String execute(String entrada) {
		
		String retorno = null;
		
		try {
			// 2. Get the connection object from the connection factory
			Connection conn = cf.getConnection();

			// 3. Get an interaction object from the connection
			Interaction interaction = conn.createInteraction();

			// 4. Create a new ECIInteractionSpec
			ECIInteractionSpec is = new ECIInteractionSpec();

			// 5. Use the set methods on ECIInteractionSpec
			// to set the properties of execution.
			// Change these properties to suit the target program
			is.setCommareaLength(24000);
			is.setFunctionName("MAPC0010");
			is.setInteractionVerb(ECIInteractionSpec.SYNC_SEND_RECEIVE);

			// 6. Create a record object to contain the input data and populate
			// data
			// Change the contents to suit the data required by the program
			RecordImpl in = new RecordImpl();
			byte[] commarea = ENTRADA.getBytes("IBM037");
			ByteArrayInputStream inStream = new ByteArrayInputStream(commarea);
			in.read(inStream);

			// 7. Create a record object to contain the output data
			RecordImpl out = new RecordImpl();

			// 8. Call the execute method on the interaction
			interaction.execute(is, in, out);

			// 9. Read the data from the output record
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			out.write(outStream);
			commarea = outStream.toByteArray();
			
			retorno = new String(commarea, "IBM037");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}

	public class RecordImpl implements Streamable, Record {
		private static final long serialVersionUID = -947604396867020977L;

		private String contents = new String("");

		public void read(InputStream is) {
			try {
				int total = is.available();
				byte[] bytes = null;
				if (total > 0) {
					bytes = new byte[total];
					is.read(bytes);
				}
				// Convert the bytes to a string.
				contents = new String(bytes);
			} catch (Exception e) {
				// Log the exception
				e.printStackTrace();
			}
		}

		public void write(OutputStream os) {
			try {
				// Output the string as bytes
				os.write(contents.getBytes());
			} catch (Exception e) {
				// Log the exception
				e.printStackTrace();
			}
		}

		public String getRecordName() {
			// Required by Record, unused in this sample
			return "";
		}

		public void setRecordName(String newName) {
			// Required by Record, unused in this sample
		}

		public void setRecordShortDescription(String newDesc) {
			// Required by Record, unused in this sample
		}

		public String getRecordShortDescription() {
			// Required by Record, unused in this sample
			return "";
		}

		public Object clone() throws CloneNotSupportedException {
			// Required by Record, unused in this sample
			return super.clone();
		}
	}

}
