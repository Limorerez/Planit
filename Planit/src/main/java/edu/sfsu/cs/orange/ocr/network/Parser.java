package edu.sfsu.cs.orange.ocr.network;//package com.prizeforlife.healthcare.network;
//
//import com.android.volley.VolleyError;
//import com.google.android.gms.maps.model.LatLng;
//import com.quickode.ballytaxi.ballytaxidriver.models.CountryCodeModel;
//import com.quickode.ballytaxi.ballytaxidriver.models.Driver;
//import com.quickode.ballytaxi.ballytaxidriver.models.Estimation;
//import com.quickode.ballytaxi.ballytaxidriver.models.Passenger;
//import com.quickode.ballytaxi.ballytaxidriver.models.Place;
//import com.quickode.ballytaxi.ballytaxidriver.models.Ride;
//import com.quickode.ballytaxi.ballytaxidriver.models.User;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * Created by Android- on 4/22/2015.
// */
//public class Parser {
//
//
//    public static String parsePhoneNumber(String response) {
//        String phone = "";
//
//        JSONObject json = null;
//        try {
//            json = new JSONObject(response);
//            phone = json.getString("phoneFormatted");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        return phone;
//    }
//
//    public static String parseUploadImage(String response) {
//        String imageid = response;
//
//        JSONObject json = null;
//        try {
//            json = new JSONObject(response);
//            imageid = json.getString("phoneFormatted");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        return imageid;
//    }
//
//
//    public static User parseUser(String response) {
//        User user = new User();
//
//        JSONObject json = null;
//        try {
//            json = new JSONObject(response);
//            user.setName(json.getString("name"));
//            user.setPhone(json.getString("phone"));
//            user.setImageURL(json.getString("imageURL"));
//            user.setToken(json.getString("token"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        return user;
//    }
//
//    public static Ride parseRide(String response) {
//        Ride ride = (Ride)parseRideDetailsLogin(response);
//        try {
//
//            Driver driver = parseDriver(response);
//            ride.setDriver(driver);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ride;
//
//    }
//
//
//
//
//
//    public static Driver parseDriver(String response) {
//        Driver driver = new Driver();
//        try {
//
//                JSONObject json = new JSONObject(response);
//                driver.setStatus(json.getInt("driverStatus"));
//                JSONObject driverJson = json.getJSONObject("driverObject");
//
//                driver.setId(driverJson.getInt("driverID"));
//                driver.setImageurl(driverJson.getString("imageID"));
//                driver.setLicenseCardNumber(driverJson.getString("taxiLicense"));
//                driver.setPhoneNumber(driverJson.getString("phoneNumber"));
//                driver.setName(driverJson.getString("name"));
//
//                JSONObject carJson = driverJson.getJSONObject("carObject");
//
//                driver.setLicensePlateNumber(carJson.getString("licensePlate"));
//                driver.setCarType(carJson.getString("carType"));
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return driver;
//
//    }
//
//    public static Driver parseDriverIntenrally(String response) {
//        Driver driver = new Driver();
//        try {
//
//            JSONObject driverJson = new JSONObject(response);
//
//            driver.setId(driverJson.getInt("driverID"));
//            driver.setImageurl(driverJson.getString("imageID"));
//            driver.setLicenseCardNumber(driverJson.getString("taxiLicense"));
//            driver.setPhoneNumber(driverJson.getString("phoneNumber"));
//            driver.setName(driverJson.getString("name"));
//
//            JSONObject carJson = driverJson.getJSONObject("carObject");
//
//            driver.setLicensePlateNumber(carJson.getString("licensePlate"));
//            driver.setCarType(carJson.getString("carType"));
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return driver;
//
//    }
//
//
//    public static HashMap<String, ArrayList<CountryCodeModel>> parseCountries(String response) {
//
//        HashMap<String, ArrayList<CountryCodeModel>> hash = new HashMap<String, ArrayList<CountryCodeModel>>();
//        hash.put("supported", new ArrayList<CountryCodeModel>());
//        hash.put("unSupported", new ArrayList<CountryCodeModel>());
//
//        ArrayList<CountryCodeModel> mSupportCountries = new ArrayList<CountryCodeModel>();
//        try {
//            JSONObject json = new JSONObject(response);
//            JSONArray support = json.getJSONArray("arrSupportedCountries");
//            ///JSONArray notSupport = json.getJSONArray("arrOtherCountries");
//
//            iterateJson(support, hash.get("supported"));
//          //  iterateJson(notSupport, hash.get("unSupported"));
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        return hash;
//    }
//
//    public static ArrayList<LatLng> parseAvailableTaxis(String response) {
//        ArrayList<LatLng> mTaxiLocations = new ArrayList<LatLng>();
//        try {
//            JSONArray jsonArray = new JSONArray(response);
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject locJson = jsonArray.getJSONObject(i);
//                String lat = locJson.getString("lat");
//                String lon = locJson.getString("lon");
//                LatLng latLang = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
//                mTaxiLocations.add(latLang);
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        return mTaxiLocations;
//    }
//
//    private static void iterateJson(JSONArray arr, ArrayList<CountryCodeModel> toInsert) {
//        try {
//            for (int i = 0; i < arr.length(); i++) {
//                JSONObject countryJson = arr.getJSONObject(i);
//                String name = countryJson.getString("name");
//                String phonePrefix = countryJson.getString("phonePrefix");
//                String flagURL = countryJson.getString("flagURL");
//                CountryCodeModel country = new CountryCodeModel(name, phonePrefix, flagURL);
//                toInsert.add(country);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String parseErrorMessage(VolleyError error) {
//        String err = "server error";
//        try {
//            JSONObject errJson = new JSONObject(new String(error.networkResponse.data));
//            err = errJson.getString("message");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return err;
//    }
//
//
//    public static ArrayList<Place> parseAddressesList(String response) {
//        ArrayList<Place> lastPlaces = new ArrayList<Place>();
//        try {
//            JSONArray arr = new JSONArray(response);
//            for (int i = 0; i < arr.length(); i++) {
//                JSONObject placeJson = arr.getJSONObject(i);
//                double lat = Double.valueOf(placeJson.getDouble("lat"));
//                double lon = Double.valueOf(placeJson.getDouble("lon"));
//                LatLng latlng = new LatLng(lat, lon);
//                String title = placeJson.getString("address");
//                Place newPlace = new Place(latlng, title);
//                lastPlaces.add(newPlace);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return lastPlaces;
//    }
//
//    public static ArrayList<Ride> parseFavoriteRides(String response) {
//        ArrayList<Ride> rides = new ArrayList<Ride>();
//        try {
//            JSONArray arr = new JSONArray(response);
//            for (int i = 0; i < arr.length(); i++) {
//                Ride ride = new Ride();
//                JSONObject json = arr.getJSONObject(i);
//
//
//                double pickupLatitude = json.getDouble("lat");
//                double pickupLongitude = json.getDouble("lon");
//                String pickupAddress = json.getString("address");
//                ride.setPickupLatitude(pickupLatitude);
//                ride.setPickupLongitude(pickupLongitude);
//                ride.setPickUpAddress(pickupAddress);
//                rides.add(ride);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rides;
//
//    }
//
//    public static Object parseEstimation(String response) {
//        Estimation est = new Estimation();
//
//        try {
//            JSONObject json = new JSONObject(response);
//            est.setEstimateTime(json.getInt("estimateTime"));
//            est.setLatitude(json.getDouble("lat"));
//            est.setLongnitude(json.getDouble("lon"));
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return est;
//
//    }
//
//    public static Object parseRideDetails(String response) {
//        Ride mRide = new Ride();
//
//        try {
//
//
//            JSONObject json = new JSONObject(response);
//
//            Passenger passenger = new Passenger();
//
//            JSONObject passJson = json.getJSONObject("passengerObject");
//            passenger.setName(passJson.getString("fullName"));
//            passenger.setId(passJson.getInt("passengerID"));
//            passenger.setImage(passJson.getString("imageID"));
//            passenger.setPhone(passJson.getString("phone"));
//
//
//            mRide.setPassenger(passenger);
//            if(!json.getString("destinationAddress").equals("null")){
//                mRide.setDestAddress((String) json.get("destinationAddress"));
//            }
//            mRide.setPickUpAddress((String) json.get("pickupAddress"));
//            mRide.setTime((int) json.getInt("time"));
//            if(!json.getString("destinationLatitude").equals("null")){
//                mRide.setDestinationLatitude(json.getDouble("destinationLatitude"));
//            }
//            if(!json.getString("destinationLongitude").equals("null")){
//                mRide.setDestinationLongitude(json.getDouble("destinationLongitude"));
//            }
//            mRide.setPickupLatitude(json.getDouble("pickupLatitude"));
//            mRide.setPickupLongitude(json.getDouble("pickupLongitude"));
//            if(!json.getString("destinationAddress").equals("null")){
//                mRide.setNotes((String) json.get("notes"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return mRide;
//
//    }
//
//    public static Object parseRideDetailsOrder(String response) {
//        Ride mRide = new Ride();
//
//        try {
//            JSONObject parentJson  = new JSONObject(response);
//
//            JSONObject json =parentJson.getJSONObject("order_result");
//
//            Passenger passenger = new Passenger();
//
//            Driver driver = parseDriverIntenrally(json.getString("driverObject"));
//            mRide.setDriver(driver);
//
//            JSONObject passJson = json.getJSONObject("passengerObject");
//            passenger.setName(passJson.getString("fullName"));
//            passenger.setId(passJson.getInt("passengerID"));
//            passenger.setImage(passJson.getString("imageID"));
//            passenger.setPhone(passJson.getString("phone"));
//
//            mRide.setId(json.getInt("orderID"));
//            mRide.setType(json.getInt("type"));
//
//
//            mRide.setPassenger(passenger);
//            if(!json.getString("destinationAddress").equals("null")){
//                mRide.setDestAddress((String) json.get("destinationAddress"));
//            }
//            mRide.setPickUpAddress((String) json.get("pickupAddress"));
//            mRide.setTime((int) json.getInt("time"));
//            if(!json.getString("destinationLatitude").equals("null")){
//                mRide.setDestinationLatitude(json.getDouble("destinationLatitude"));
//            }
//            if(!json.getString("destinationLongitude").equals("null")){
//                mRide.setDestinationLongitude(json.getDouble("destinationLongitude"));
//            }
//            mRide.setPickupLatitude(json.getDouble("pickupLatitude"));
//            mRide.setPickupLongitude(json.getDouble("pickupLongitude"));
//            if(!json.getString("destinationAddress").equals("null")){
//                mRide.setNotes((String) json.get("notes"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return mRide;
//
//    }
//
//
//    public static Object parseRideDetailsLogin(String response) {
//        Ride mRide = new Ride();
//
//        try {
//
//            JSONObject parentJson = new JSONObject(response);
//            JSONObject json = parentJson.getJSONObject("nextRideObject");
//
//            Passenger passenger = new Passenger();
//
//            JSONObject passJson = json.getJSONObject("passengerObject");
//            passenger.setName(passJson.getString("fullName"));
//            passenger.setId(passJson.getInt("passengerID"));
//            passenger.setImage(passJson.getString("imageID"));
//            passenger.setPhone(passJson.getString("phone"));
//            mRide.setId(json.getInt("orderID"));
//
//            mRide.setPassenger(passenger);
//            if(!json.getString("destinationAddress").equals("null")){
//                mRide.setDestAddress((String) json.get("destinationAddress"));
//            }
//            mRide.setPickUpAddress((String) json.get("pickupAddress"));
//            mRide.setTime((int) json.getInt("time"));
//            if(!json.getString("destinationLatitude").equals("null")){
//                mRide.setDestinationLatitude(json.getDouble("destinationLatitude"));
//            }
//            if(!json.getString("destinationLongitude").equals("null")){
//                mRide.setDestinationLongitude(json.getDouble("destinationLongitude"));
//            }
//            mRide.setPickupLatitude(json.getDouble("pickupLatitude"));
//            mRide.setPickupLongitude(json.getDouble("pickupLongitude"));
//            if(!json.getString("destinationAddress").equals("null")){
//                mRide.setNotes((String) json.get("notes"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return mRide;
//
//    }
//}
