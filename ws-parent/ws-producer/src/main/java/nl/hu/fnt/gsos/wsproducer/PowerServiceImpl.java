package nl.hu.fnt.gsos.wsproducer;

import javax.jws.WebService;

import nl.hu.fnt.gsos.wsinterface.Fault;
import nl.hu.fnt.gsos.wsinterface.ObjectFactory;
import nl.hu.fnt.gsos.wsinterface.PowerFault;
import nl.hu.fnt.gsos.wsinterface.PowerRequest;
import nl.hu.fnt.gsos.wsinterface.PowerResponse;
import nl.hu.fnt.gsos.wsinterface.WSInterface;

@WebService( endpointInterface= "nl.hu.fnt.gsos.wsinterface.WSInterface")
public class PowerServiceImpl implements WSInterface {

	@Override
	public PowerResponse calculatePower(PowerRequest request) throws Fault {
		ObjectFactory factory = new ObjectFactory();
		PowerResponse response = factory.createPowerResponse();
		try {
			Double result = Math.pow(request.getX().doubleValue(), request
					.getPower().doubleValue());
			// x en power zijn altijd gehele getallen dan is er geen afronding
			long actualResult = result.longValue();
			response.setResult(actualResult);
		} catch (RuntimeException e) {
			PowerFault x = factory.createPowerFault();
			x.setErrorCode((short) 1);
			x.setMessage("Kan de macht van " + request.getX()
					+ " tot de macht " + request.getPower().toString()
					+ " niet berekenen.");
			Fault fault = new Fault(
					"Er ging iets mis met het berekenen van de power", x);
			throw fault;
		}
		return response;
	}

}
