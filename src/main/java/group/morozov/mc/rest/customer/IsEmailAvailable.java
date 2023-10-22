package group.morozov.mc.rest.customer;

import io.agroal.api.AgroalDataSource;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

class IsEmailAvailableRequest {

    public String customerEmail;
    public Integer websiteId = null;

    public IsEmailAvailableRequest() {
    }

    public IsEmailAvailableRequest(String customerEmail, @DefaultValue("") Integer websiteId) {
        this.customerEmail = customerEmail;
        if (websiteId >= 0) {
            this.websiteId = websiteId;
        }
    }
}

@Path("/rest/{websiteCode}/V1/customers/isEmailAvailable")
public class IsEmailAvailable {

    AgroalDataSource dataSource;

    protected PreparedStatement preparedStatement;
    protected int defaultWebsite;

    protected HashMap<String,Integer> websites;


    @Inject // constructor injection
    IsEmailAvailable(AgroalDataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        Connection conn = dataSource.getConnection();
        ResultSet rs = conn.prepareStatement("select * from store_website").executeQuery();
        websites = new HashMap<String,Integer>();
        while (rs.next()) {
            websites.put(rs.getString("code"), rs.getInt("website_id"));
            if (rs.getInt("is_default") == 1) {
                defaultWebsite = rs.getInt("website_id");
            }
        }

        preparedStatement = dataSource
        .getConnection()
        .prepareStatement("select entity_id from customer_entity where email = ? and website_id = ?");
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean isAvailable(@DefaultValue("default") @PathParam("websiteCode") String websiteCode, IsEmailAvailableRequest request) throws SQLException {
        Integer currentWebsite = 0;
        if (websiteCode.equals("default"))
        {
            currentWebsite = defaultWebsite;
        } else {
            Integer i = websites.get(websiteCode);
            if (i != null) {
                currentWebsite = i;
            }
        }

        if (request.websiteId != null) {
            currentWebsite = request.websiteId;
        }

        preparedStatement.setString(1, request.customerEmail);
        preparedStatement.setInt(2, currentWebsite);

        ResultSet rs = preparedStatement.executeQuery();
        long result = 0;
        while(rs.next()) {
            result = rs.getLong(1);
        }
        return result > 0;
    }
}
