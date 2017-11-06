package com.mycompany.mysystem;

import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.model.*;
import com.structurizr.view.ContainerView;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.ViewSet;

import static com.structurizr.model.InteractionStyle.Asynchronous;

/**
 * This is a simple example of how to get started with Structurizr for Java.
 */
public class Structurizr {

    private static final String API_KEY = "2636bc84-1373-492a-a66b-b05ed9a23e9f";
    private static final String API_SECRET = System.getenv("api.secret");
    private static final long WORKSPACE_ID = 37545;
    public static final String DATABASE = "DATABASE";

    public static void main(String[] args) throws Exception {
        // a Structurizr workspace is the wrapper for a software architecture model, views and documentation
        Workspace workspace = new Workspace("Flight Reservation System", "This is a description of my Flight Reservation System");

        Model model = workspace.getModel();
        final ViewSet views = workspace.getViews();
        Styles styles = views.getConfiguration().getStyles();

        // add some elements to your software architecture model
        Person user = model.addPerson("User", "A user of my software system.");
        SoftwareSystem flightsFast = model.addSoftwareSystem("Flights Fast", "Flights Fast Booking Application");
        SoftwareSystem fodlee = model.addSoftwareSystem("Fodlee", "Third party which provides us with flights");

        // optionally, add some styling
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff");

        Container iosApp = flightsFast.addContainer(
                "Flight Fast IOS",
                "IOS Mobile Application",
                "ios");

        Container androidApplication = flightsFast.addContainer(
                "Flight Fast Android",
                "Android Mobile Application",
                "android");

        Container webapplication = flightsFast.addContainer(
                "Flight Fast Web Site",
                "Web application",
                "Angular JS");

        user.uses(iosApp, "Uses", "UI");
        user.uses(androidApplication, "Uses", "UI");
        user.uses(webapplication, "Uses", "UI");

        Container securityGateway = flightsFast.addContainer(
                "Security Gateway",
                "Gateway for API Requests",
                "GoLang");

        iosApp.uses(securityGateway, "Uses", "HTTP / Rest");
        androidApplication.uses(securityGateway, "Uses", "HTTP / Rest");
        webapplication.uses(securityGateway, "Uses", "HTTP / Rest");

        Container userManagementService = flightsFast.addContainer(
                "User management",
                "Manages users",
                "Java / Spring Boot");

        securityGateway.uses(userManagementService, "Uses", "HTTP / Rest");

        Container userManagementDatabase = flightsFast.addContainer(
                "User Database",
                "Manages User Data",
                "PostgreSQL");
        userManagementDatabase.addTags(DATABASE);

        userManagementService.uses(userManagementDatabase, "Reads from and writes to", "JDBC");

        Container flightBooking = flightsFast.addContainer(
                "Flight Booking",
                "Books Flights",
                "Java / Spring Boot");

        securityGateway.uses(flightBooking, "Uses", "HTTP / Rest");

        Container flightAvailability = flightsFast.addContainer(
                "Flight Search",
                "Searches Available Flights",
                "Java / Spring Boot");

        securityGateway.uses(flightAvailability, "Uses", "HTTP / Rest");
        flightAvailability.uses(fodlee, "Uses", "HTTP / Rest");

        Container paymentService = flightsFast.addContainer(
                "Payment Service",
                "Processes Payments",
                "Java / Spring Boot");

        flightBooking.uses(paymentService, "Uses", "HTTP / Rest");

        Container paymentDatabase = flightsFast.addContainer(
                "Payment Database",
                "Manages Payment Data",
                "PostgreSQL");
        paymentDatabase.addTags(DATABASE);

        paymentService.uses(paymentDatabase, "Reads from and writes to", "JDBC");

        Container fraudService = flightsFast.addContainer(
                "Fraud Service",
                "Detects Fraudulent Transactions",
                "Java / Spring Boot");

        paymentService.uses(fraudService, "Uses", "HTTP / Rest");

        Container fraudDatabase = flightsFast.addContainer(
                "Fraud Database",
                "Manages Fraud Detection Data",
                "PostgreSQL");
        fraudDatabase.addTags(DATABASE);

        fraudService.uses(fraudDatabase, "Reads from and writes to", "JDBC");

        fraudService.uses(userManagementService, "Publishes to", "Kafka", Asynchronous);
        flightAvailability.uses(paymentService, "Publishes to", "Kafka", Asynchronous);

        // Styles
        ContainerView containerView = views.createContainerView(flightsFast, "Containers", "The container diagram for Flights Fast.");
        containerView.add(user);
        containerView.add(fodlee);
        containerView.addAllContainers();

        styles.addElementStyle(DATABASE).shape(Shape.Cylinder);
        model.getRelationships().stream().filter(r -> r.getTechnology().contains("HTTP / Rest")).forEach(r -> r.addTags("HTTP"));
        styles.addRelationshipStyle("HTTP").dashed(false);

        uploadWorkspaceToStructurizr(workspace);
    }

    private static void uploadWorkspaceToStructurizr(Workspace workspace) throws Exception {
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }

}