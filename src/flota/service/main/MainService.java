package flota.service.main;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import flota.service.beans.BeanDelegatieAprobare;
import flota.service.model.OperatiiAdresa;
import flota.service.model.OperatiiDelegatii;
import flota.service.model.OperatiiTraseu;

@Path("delegatii")
public class MainService {

	@Path("localitati")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String localitati(@QueryParam("codJudet") String codJudet) {

		return new OperatiiAdresa().getLocalitatiJudet(codJudet).toString();

	}

	@Path("adaugaDelegatie")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String adaugaDelegatie(@FormParam("codAngajat") String codAngajat, @FormParam("tipAngajat") String tipAngajat, @FormParam("dataP") String dataPlecare,
			@FormParam("oraP") String oraPlecare, @FormParam("dataS") String dataSosire, @FormParam("distcalc") String distCalc, @FormParam("stops") String stops,
			@FormParam("nrAuto") String nrAuto) {

		boolean success = new OperatiiDelegatii().adaugaDelegatie(codAngajat, tipAngajat, dataPlecare, oraPlecare, distCalc, stops, dataSosire, nrAuto);
		return success ? "1" : "0";
	}

	@Path("afisDelegatiiAprob")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public List<BeanDelegatieAprobare> afiseazaDelegatiiAprobare(@QueryParam("tipAngajat") String tipAngajat, @QueryParam("unitLog") String unitLog) {

		return new OperatiiDelegatii().getDelegatiiAprobari(tipAngajat, unitLog);

	}

	@Path("aprobaDelegatie")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String aprobaDelegatie(@FormParam("idDelegatie") String idDelegatie, @FormParam("tipAngajat") String tipAngajat, @FormParam("kmRespinsi") String kmRespinsi,
			@FormParam("codAngajat") String codAngajat) {

		new OperatiiDelegatii().aprobaDelegatie(idDelegatie, tipAngajat, kmRespinsi, codAngajat);
		return "!";

	}

	@Path("respingeDelegatie")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String respingeDelegatie(@FormParam("idDelegatie") String idDelegatie, @FormParam("tipAngajat") String tipAngajat, @FormParam("codAngajat") String codAngajat) {

		new OperatiiDelegatii().respingeDelegatie(idDelegatie, tipAngajat, codAngajat);
		return "!";

	}

	@Path("afiseazaDelegatii")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public List<BeanDelegatieAprobare> afiseazaDelegatii(@QueryParam("codAngajat") String codAngajat, @QueryParam("dataStart") String dataStart,
			@QueryParam("dataStop") String dataStop) {
		return new OperatiiDelegatii().afiseazaDelegatii(codAngajat, dataStart, dataStop);

	}

	@Path("getCoordsTraseu")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public String getCoordonateTraseu(@QueryParam("idDelegatie") String idDelegatie) {
		return new OperatiiTraseu().getCoordonateTraseu(idDelegatie).toString();
	}

}
