package tests;

import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

public class APIValidation {
    Playwright playwright;
    APIRequestContext apiRequest;



    @BeforeMethod(alwaysRun = true)
    public void setup(){
        playwright = Playwright.create();
        apiRequest = playwright.request().newContext();
    }
    @Test(groups = {"smoke"})
    public void e2eAPITest(){
        HashMap<Object, Object> loginPayload = new HashMap<>();
        loginPayload.put("email", "aiintelligence335@gmail.com");
        loginPayload.put("password", "Demo@123");

        APIResponse loginResponse = apiRequest.post("https://api.eventhub.rahulshettyacademy.com/api/auth/login",
                RequestOptions.create().setData(loginPayload));
        loginResponse.ok();
        Assert.assertTrue(loginResponse.ok());
        System.out.println(loginResponse.text());

        String loginToken = JsonPath.read(loginResponse.text(), "$.token");
        System.out.println("loginSuccess" + ": " + loginToken);


        //Create Event

        HashMap<Object, Object> createEventPayload = new HashMap<>();
        String eventTitle = "Playwright API Testing";

        createEventPayload.put("title",eventTitle);
        createEventPayload.put("description", "api" );
        createEventPayload.put("category", "Conference" );
        createEventPayload.put("venue", "ABC" );
        createEventPayload.put("city", "Mumbai" );
        createEventPayload.put("eventDate", "2026-12-29T15:23:00.000Z" );
        createEventPayload.put("price", 100 );
        createEventPayload.put("totalSeats", 200);



        APIResponse createEventResponse = apiRequest.post("https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create().setHeader("Authorization", "Bearer " +loginToken)
                .setData(createEventPayload));

        Assert.assertTrue(createEventResponse.ok(), "Create Event Succeed");

        int eventId = JsonPath.read(createEventResponse.text(), "$.data.id");
        System.out.println("Event created and its id is: "+eventId);


        //Get Event

        APIResponse availableEvents = apiRequest.get("https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create().setQueryParam("page", "1")
                        .setQueryParam("limit", "100")
                        .setHeader("Authorization", "Bearer "+loginToken));

        Assert.assertTrue(availableEvents.ok(), "Event is present");

        // * will read all the events and extract
        List<Integer> allEventids  = JsonPath.read(availableEvents.text(), "$.data[*].id");
        System.out.println(allEventids);

        System.out.println(allEventids.contains(eventId));
        Assert.assertTrue(allEventids.contains(eventId));


        //Delete the Event
        APIResponse deleteEvent = apiRequest.delete("https://api.eventhub.rahulshettyacademy.com/api/events/"+eventId,
                RequestOptions.create().setHeader("Authorization", "Bearer " + loginToken ));
        Assert.assertTrue(deleteEvent.ok());

        APIResponse eventsAfterDelete = apiRequest.get("https://api.eventhub.rahulshettyacademy.com/api/events",
                RequestOptions.create().setQueryParam("page", "1")
                        .setQueryParam("limit", "100")
                        .setHeader("Authorization", "Bearer "+loginToken));

        Assert.assertTrue(eventsAfterDelete.ok(), "Event is present");

        // Reading all the events after the deletion

        // * will read all the events and extract
        List<Integer> deletedEvent  = JsonPath.read(eventsAfterDelete.text(), "$.data[*].id");
        System.out.println(deletedEvent);

        System.out.println(deletedEvent.contains(eventId));
        Assert.assertFalse(deletedEvent.contains(eventId));




    }


}
