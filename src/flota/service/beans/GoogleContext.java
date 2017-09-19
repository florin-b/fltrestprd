package flota.service.beans;

import java.util.concurrent.TimeUnit;

import com.google.maps.GeoApiContext;

import flota.service.utils.Constants;

public class GoogleContext {

	private static GeoApiContext instance;

	private static GeoApiContext instance1;
	private static GeoApiContext instance2;
	private static GeoApiContext instance3;
	private static GeoApiContext instance4;
	private static GeoApiContext instance5;
	private static GeoApiContext instance6;
	private static GeoApiContext instance7;
	private static GeoApiContext instance8;

	private static GeoApiContext instance9;
	private static GeoApiContext instance10;
	private static GeoApiContext instance11;
	private static GeoApiContext instance12;
	private static GeoApiContext instance13;
	private static GeoApiContext instance14;
	private static GeoApiContext instance15;
	private static GeoApiContext instance16;

	private static GeoApiContext instance17;
	private static GeoApiContext instance18;
	private static GeoApiContext instance19;
	private static GeoApiContext instance20;
	private static GeoApiContext instance21;
	private static GeoApiContext instance22;
	private static GeoApiContext instance23;
	private static GeoApiContext instance24;

	private GoogleContext() {

	}

	public static GeoApiContext getContext() {
		if (instance == null) {
			instance = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY);
			instance.setQueryRateLimit(2);
			instance.setRetryTimeout(0, TimeUnit.SECONDS);
			instance.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance;
	}

	public static GeoApiContext getContext(int contextNumber) {

		System.out.println("context number: " + contextNumber);

		switch (contextNumber) {
		case 1:
			return getContext1();
		case 2:
			return getContext2();
		case 3:
			return getContext3();
		case 4:
			return getContext4();
		case 5:
			return getContext5();
		case 6:
			return getContext6();
		case 7:
			return getContext7();
		case 8:
			return getContext8();

		case 9:
			return getContext9();
		case 10:
			return getContext10();
		case 11:
			return getContext11();
		case 12:
			return getContext12();
		case 13:
			return getContext13();
		case 14:
			return getContext14();
		case 15:
			return getContext15();
		case 16:
			return getContext16();

		case 17:
			return getContext17();
		case 18:
			return getContext18();
		case 19:
			return getContext19();
		case 20:
			return getContext20();
		case 21:
			return getContext21();
		case 22:
			return getContext22();
		case 23:
			return getContext23();
		case 24:
			return getContext24();

		default:
			return getContext1();
		}
	}

	public static GeoApiContext getContext1() {

		if (instance1 == null) {
			instance1 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_01);
			instance1.setQueryRateLimit(2);
			instance1.setRetryTimeout(0, TimeUnit.SECONDS);
			instance1.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance1;
	}

	public static GeoApiContext getContext2() {

		if (instance2 == null) {
			instance2 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_02);
			instance2.setQueryRateLimit(2);
			instance2.setRetryTimeout(0, TimeUnit.SECONDS);
			instance2.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance2;
	}

	public static GeoApiContext getContext3() {

		if (instance3 == null) {
			instance3 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_03);
			instance3.setQueryRateLimit(2);
			instance3.setRetryTimeout(0, TimeUnit.SECONDS);
			instance3.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance3;
	}

	public static GeoApiContext getContext4() {

		if (instance4 == null) {
			instance4 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_04);
			instance4.setQueryRateLimit(2);
			instance4.setRetryTimeout(0, TimeUnit.SECONDS);
			instance4.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance4;
	}

	public static GeoApiContext getContext5() {

		if (instance5 == null) {
			instance5 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_05);
			instance5.setQueryRateLimit(2);
			instance5.setRetryTimeout(0, TimeUnit.SECONDS);
			instance5.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance5;
	}

	public static GeoApiContext getContext6() {

		if (instance6 == null) {
			instance6 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_06);
			instance6.setQueryRateLimit(2);
			instance6.setRetryTimeout(0, TimeUnit.SECONDS);
			instance6.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance6;
	}

	public static GeoApiContext getContext7() {

		if (instance7 == null) {
			instance7 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_07);
			instance7.setQueryRateLimit(2);
			instance7.setRetryTimeout(0, TimeUnit.SECONDS);
			instance7.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance7;
	}

	public static GeoApiContext getContext8() {

		if (instance8 == null) {
			instance8 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_08);
			instance8.setQueryRateLimit(2);
			instance8.setRetryTimeout(0, TimeUnit.SECONDS);
			instance8.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance8;
	}

	public static GeoApiContext getContext9() {

		if (instance9 == null) {
			instance9 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_09);
			instance9.setQueryRateLimit(2);
			instance9.setRetryTimeout(0, TimeUnit.SECONDS);
			instance9.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance9;
	}

	public static GeoApiContext getContext10() {

		if (instance10 == null) {
			instance10 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_10);
			instance10.setQueryRateLimit(2);
			instance10.setRetryTimeout(0, TimeUnit.SECONDS);
			instance10.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance10;
	}

	public static GeoApiContext getContext11() {

		if (instance11 == null) {
			instance11 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_11);
			instance11.setQueryRateLimit(2);
			instance11.setRetryTimeout(0, TimeUnit.SECONDS);
			instance11.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance11;
	}

	public static GeoApiContext getContext12() {

		if (instance12 == null) {
			instance12 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_12);
			instance12.setQueryRateLimit(2);
			instance12.setRetryTimeout(0, TimeUnit.SECONDS);
			instance12.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance12;
	}

	public static GeoApiContext getContext13() {

		if (instance13 == null) {
			instance13 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_13);
			instance13.setQueryRateLimit(2);
			instance13.setRetryTimeout(0, TimeUnit.SECONDS);
			instance13.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance13;
	}

	public static GeoApiContext getContext14() {

		if (instance14 == null) {
			instance14 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_14);
			instance14.setQueryRateLimit(2);
			instance14.setRetryTimeout(0, TimeUnit.SECONDS);
			instance14.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance14;
	}

	public static GeoApiContext getContext15() {

		if (instance15 == null) {
			instance15 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_15);
			instance15.setQueryRateLimit(2);
			instance15.setRetryTimeout(0, TimeUnit.SECONDS);
			instance15.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance15;
	}

	public static GeoApiContext getContext16() {

		if (instance16 == null) {
			instance16 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_16);
			instance16.setQueryRateLimit(2);
			instance16.setRetryTimeout(0, TimeUnit.SECONDS);
			instance16.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance16;
	}

	public static GeoApiContext getContext17() {

		if (instance17 == null) {
			instance17 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_17);
			instance17.setQueryRateLimit(2);
			instance17.setRetryTimeout(0, TimeUnit.SECONDS);
			instance17.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance17;
	}

	public static GeoApiContext getContext18() {

		if (instance18 == null) {
			instance18 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_18);
			instance18.setQueryRateLimit(2);
			instance18.setRetryTimeout(0, TimeUnit.SECONDS);
			instance18.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance18;
	}

	public static GeoApiContext getContext19() {

		if (instance19 == null) {
			instance19 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_19);
			instance19.setQueryRateLimit(2);
			instance19.setRetryTimeout(0, TimeUnit.SECONDS);
			instance19.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance19;
	}

	public static GeoApiContext getContext20() {

		if (instance20 == null) {
			instance20 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_20);
			instance20.setQueryRateLimit(2);
			instance20.setRetryTimeout(0, TimeUnit.SECONDS);
			instance20.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance20;
	}

	public static GeoApiContext getContext21() {

		if (instance21 == null) {
			instance21 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_21);
			instance21.setQueryRateLimit(2);
			instance21.setRetryTimeout(0, TimeUnit.SECONDS);
			instance21.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance21;
	}

	public static GeoApiContext getContext22() {

		if (instance22 == null) {
			instance22 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_22);
			instance22.setQueryRateLimit(2);
			instance22.setRetryTimeout(0, TimeUnit.SECONDS);
			instance22.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance22;
	}

	public static GeoApiContext getContext23() {

		if (instance23 == null) {
			instance23 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_23);
			instance23.setQueryRateLimit(2);
			instance23.setRetryTimeout(0, TimeUnit.SECONDS);
			instance23.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance23;
	}

	public static GeoApiContext getContext24() {

		if (instance24 == null) {
			instance24 = new GeoApiContext().setApiKey(Constants.GOOGLE_MAPS_API_KEY_24);
			instance24.setQueryRateLimit(2);
			instance24.setRetryTimeout(0, TimeUnit.SECONDS);
			instance24.setConnectTimeout(1, TimeUnit.SECONDS);

		}

		return instance24;
	}

}
